/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controladores;

import autogestionestudiantil.Modelos.InscripcionMateria;
import autogestionestudiantil.Modelos.Materia;
import autogestionestudiantil.DAO.InscripcionMateriaDAO;
import autogestionestudiantil.DAO.MateriaDAO;
import java.util.ArrayList;

/**
 *
 * @author eve
 */
public class InscripcionesController {
    
    private AutogestionEstudiantil vista;
    private InscripcionMateriaDAO inscripcionDao;
    private MateriaDAO materiaDao;
    
    private ArrayList<InscripcionMateria> inscripciones;
    private ArrayList<Materia> materiasDisponibles;
    
    public InscripcionesController(AutogestionEstudiantil vista)
    {
        this.vista = vista;
        this.inscripcionDao = new InscripcionMateriaDAO();
        this.materiaDao     = new MateriaDAO();
        
        this.materiasDisponibles = materiaDao.cargarMaterias(); 
        this.inscripciones       = inscripcionDao.cargarInscripciones(materiasDisponibles);
    }
    
    public ArrayList<InscripcionMateria> getInscripciones() {
        return inscripciones;
    }
    
    public ArrayList<Materia> getMateriasDisponibles() {
        return materiasDisponibles;
    }
    
    public void registrarInscripcion(String codigoMateria) {
        if (codigoMateria.trim().isEmpty()) {
            vista.mostrarError("El código de la materia es obligatorio.");
            return;
        }

        String codigoBuscar = codigoMateria.trim();

        for (InscripcionMateria ins : inscripciones) {
            if (ins == null || ins.getMateria() == null || ins.getMateria().getCodigo() == null) {
                continue;
            }
            if (ins.getMateria().getCodigo().equalsIgnoreCase(codigoBuscar)) {
                vista.mostrarError("Ya te encuentras inscripto en la materia: " + ins.getMateria().getNombre());
                return;
            }
        }

        this.materiasDisponibles = materiaDao.cargarMaterias();

        Materia materiaEncontrada = null;
        for (Materia m : materiasDisponibles) {
            if (m == null || m.getCodigo() == null) {
                continue; 
            }
            if (m.getCodigo().equalsIgnoreCase(codigoBuscar)) {
                materiaEncontrada = m;
                break;
            }
        }

        if (materiaEncontrada == null) {
            vista.mostrarError("No se encontró ninguna materia con el código: " + codigoBuscar);
            return;
        }

        InscripcionMateria nuevaInscripcion = new InscripcionMateria(materiaEncontrada);
        
        inscripciones.add(nuevaInscripcion);
        inscripcionDao.guardarInscripciones(inscripciones);
        
        vista.actualizarTablaInscripciones(inscripciones);
        vista.mostrarMensaje("Inscripción exitosa a: " + materiaEncontrada.getNombre());
    }
    
    public void cancelarInscripcion(int indiceSeleccionado) {
        if (indiceSeleccionado < 0 || indiceSeleccionado >= inscripciones.size()) {
            vista.mostrarError("Por favor, seleccione una materia de la lista para dar de baja.");
            return;
        }

        InscripcionMateria eliminada = inscripciones.remove(indiceSeleccionado);
        inscripcionDao.guardarInscripciones(inscripciones);
        
        vista.actualizarTablaInscripciones(inscripciones);
        vista.mostrarMensaje("Se dio de baja la materia: " + eliminada.getMateria().getNombre());
    }
    
    public void registrarInscripcionDirecta(Materia materiaInstancia) {
        if (materiaInstancia == null || materiaInstancia.getCodigo() == null) {
            vista.mostrarError("La materia no es válida para la inscripción.");
            return;
        }

        String codigoBuscar = materiaInstancia.getCodigo().trim();

        for (InscripcionMateria ins : inscripciones) {
            if (ins != null && ins.getMateria() != null && ins.getMateria().getCodigo() != null) {
                if (ins.getMateria().getCodigo().equalsIgnoreCase(codigoBuscar)) {
                    vista.mostrarError("Ya te encuentras inscripto en la materia: " + ins.getMateria().getNombre());
                    return;
                }
            }
        }

        InscripcionMateria nuevaInscripcion = new InscripcionMateria(materiaInstancia);
        
        inscripciones.add(nuevaInscripcion);
        inscripcionDao.guardarInscripciones(inscripciones);
        
        // 3. Actualizamos la interfaz
        vista.actualizarTablaInscripciones(inscripciones);
        vista.mostrarMensaje("Inscripción exitosa a: " + materiaInstancia.getNombre());
    }
    
    public InscripcionMateria buscarPorCodigo(String codigo) {

        for (InscripcionMateria ins : inscripciones) {

            if (ins != null
                    && ins.getMateria() != null
                    && ins.getMateria().getCodigo() != null
                    && ins.getMateria().getCodigo()
                            .equalsIgnoreCase(codigo)) {

                return ins;
            }
        }

        return null;
    }
    
    public ArrayList<InscripcionMateria> buscarPorNombre(
            String nombre) {

        ArrayList<InscripcionMateria> resultado
                = new ArrayList<>();

        for (InscripcionMateria ins : inscripciones) {

            if (ins != null
                    && ins.getMateria() != null
                    && ins.getMateria().getNombre() != null
                    && ins.getMateria().getNombre()
                            .toLowerCase()
                            .contains(nombre.toLowerCase())) {

                resultado.add(ins);
            }
        }

        return resultado;
    }
    
    //Registrar notas
    public boolean registrarNota(String codigo, double nota) {

        InscripcionMateria inscripcion = buscarPorCodigo(codigo);

        if (inscripcion == null) {
            return false;
        }

        boolean agregada = inscripcion.agregarNota(nota);

        if (agregada) {
            inscripcionDao.guardarInscripciones(inscripciones);
        }

        return agregada;
    }
    
    public String registrarAsistencia(int indice, boolean presente) {

        if (indice < 0 || indice >= inscripciones.size()) {
            return "ERROR";
        }

        InscripcionMateria inscripcion = inscripciones.get(indice);

        inscripcion.registrarClase(presente);

        inscripcionDao.guardarInscripciones(inscripciones);
        vista.actualizarTablaInscripciones(inscripciones); 

        double porcentaje = inscripcion.getPorcentajeAsistencia();

        if (porcentaje < 75) {

            return "ALERTA_CRITICA";

        } else if (porcentaje <= 85) {

            return "RIESGO";
        }

        return "OK";
    }
    
    // 1. Método para calcular los promedios globales y asistencia media generales
public double[] calcularEstadisticasGenerales() {
    if (inscripciones.isEmpty()) {
        return new double[]{0.0, 0.0, 0.0}; // Total, Promedio, Asistencia
    }
    
    double sumaPromedios = 0;
    double sumaAsistencias = 0;
    
    for (InscripcionMateria ins : inscripciones) {
        if (ins != null && ins.getMateria() != null) {
            sumaPromedios += ins.getPromedio();
            sumaAsistencias += ins.getPorcentajeAsistencia();
        }
    }
    
    double promGral = sumaPromedios / inscripciones.size();
    double asisMedia = sumaAsistencias / inscripciones.size();
    
    return new double[]{inscripciones.size(), promGral, asisMedia};
}

// 2. Método para formatear el listado de materias en riesgo para el JList
    public ArrayList<String > obtenerListaRiesgoFormateada() {
        ArrayList<String> lineas = new ArrayList<>();
        ArrayList<InscripcionMateria> riesgo = obtenerMateriasEnRiesgoOrdenadas(); // Tu algoritmo de burbuja

        for (InscripcionMateria ins : riesgo) {
            if (ins != null && ins.getMateria() != null) {
                lineas.add(ins.getMateria().getNombre() + " (" + String.format("%.1f", ins.getPorcentajeAsistencia()) + "% Asis.)");
            }
        }
        if (lineas.isEmpty()) {
            lineas.add("Súper al día! Sin materias en riesgo.");
        }
        return lineas;
    }

    // 3. Método para filtrar y formatear las materias aprobadas para el JList
    public ArrayList<String> obtenerListaAprobadasFormateada() {
        ArrayList<String> lineas = new ArrayList<>();
        for (InscripcionMateria ins : inscripciones) {
            if (ins != null && ins.getMateria() != null) {
                // La regla de negocio de aprobación se evalúa acá adentro
                if (ins.getPromedio() >= 4.0 && ins.getPorcentajeAsistencia() >= 75.0) {
                    lineas.add(ins.getMateria().getNombre() + " [Nota: " + String.format("%.1f", ins.getPromedio()) + "]");
                }
            }
        }
        if (lineas.isEmpty()) {
            lineas.add("Aún no registrás materias aprobadas.");
        }
        return lineas;
    }
    
    //BONUS 
    
    public int buscarIndiceFilaMateria(String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            return -1;
        }
        
        String busqueda = criterio.trim().toLowerCase();
        
        for (int i = 0; i < inscripciones.size(); i++) {
            InscripcionMateria ins = inscripciones.get(i);
            if (ins != null && ins.getMateria() != null) {
                String codigo = ins.getMateria().getCodigo().toLowerCase();
                String nombre = ins.getMateria().getNombre().toLowerCase();
                
                if (codigo.contains(busqueda) || nombre.contains(busqueda)) {
                    return i; 
                }
            }
        }
        return -1; 
    }
    
    public ArrayList<InscripcionMateria> obtenerMateriasEnRiesgoOrdenadas() {
        ArrayList<InscripcionMateria> enRiesgo = new ArrayList<>();

        for (InscripcionMateria ins : inscripciones) {
            if (ins != null) {
                double asis = ins.getPorcentajeAsistencia();
                if (asis < 80.0) {
                    enRiesgo.add(ins);
                }
            }
        }

        int n = enRiesgo.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (enRiesgo.get(j).getPorcentajeAsistencia() > enRiesgo.get(j + 1).getPorcentajeAsistencia()) {
                    InscripcionMateria aux = enRiesgo.get(j);
                    enRiesgo.set(j, enRiesgo.get(j + 1));
                    enRiesgo.set(j + 1, aux);
                }
            }
        }
        
        return enRiesgo;
    }
    
    public double[] calcularMetricasAprobadas() {
        double max = 0.0; 
        double min = 10.0; 
        double sumaNotas = 0.0;
        int aprobadasContador = 0;

        for (InscripcionMateria ins : inscripciones) {
            if (ins != null && ins.getPromedio() > 0.0) { 
                double promedioMateria = ins.getPromedio();

                if (aprobadasContador == 0) {
                    max = promedioMateria;
                    min = promedioMateria;
                } else {
                    if (promedioMateria > max) max = promedioMateria;
                    if (promedioMateria < min) min = promedioMateria;
                }

                sumaNotas += promedioMateria;
                aprobadasContador++;
            }
        }

        if (aprobadasContador == 0) {
            return new double[]{0.0, 0.0, 0.0};
        }

        return new double[]{max, min, (sumaNotas / aprobadasContador)};
    }
    
    public ArrayList<InscripcionMateria> obtenerRankingMaterias() {
        ArrayList<InscripcionMateria> ranking = new ArrayList<>(inscripciones);
        
        int n = ranking.size();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (ranking.get(i).getPuntajeRanking() < ranking.get(j).getPuntajeRanking()) {
                    InscripcionMateria aux = ranking.get(i);
                    ranking.set(i, ranking.get(j));
                    ranking.set(j, aux);
                }
            }
        }
        return ranking;
    }
    
    
}
