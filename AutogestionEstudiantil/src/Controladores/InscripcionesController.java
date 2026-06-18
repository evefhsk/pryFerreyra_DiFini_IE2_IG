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
    
    //Promt: Como deberia funcionar el submenu "Guardar" en el menubar
    
    public boolean guardarDatosActuales() {
        try {
            inscripcionDao.guardarInscripciones(inscripciones);
            return true;
        } catch (Exception e) {
            System.err.println("Error al guardar: " + e.getMessage());
            return false;
        }
    }
    
    public boolean cargarDatosDesdeArchivoExterno(String rutaArchivo) {
        try {
            ArrayList<Materia> materiasVivas = this.materiaDao.cargarMaterias();

            // 2. Le pedimos al DAO que cargue la lista desde la ruta específica
            ArrayList<InscripcionMateria> datosFrescos = inscripcionDao.cargarInscripcionesDesdeRuta(materiasVivas, rutaArchivo);

            if (datosFrescos != null) {
                // 3. ACTUALIZACIÓN CRÍTICA: Reemplazamos la lista en memoria del controlador
                this.inscripciones = datosFrescos;
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error al cargar archivo externo: " + e.getMessage());
            return false;
        }
    }
    
    //
    
    public double[] calcularMetricasInicio() {
        if (inscripciones.isEmpty()) {
            return null;
        }

        double sumaPromedios = 0;
        int materiasAprobadas = 0;
        int materiasEnCurso = 0;
        int faltasTotales = 0;
        int materiasEnAlerta = 0;

        for (InscripcionMateria ins : inscripciones) {
            if (ins != null) {
                sumaPromedios += ins.getPromedio();

                boolean tieneLasCincoNotas = true;
                for (int i = 0; i < 5; i++) {
                    if (ins.getNotas().get(i) == -1.0) {
                        tieneLasCincoNotas = false;
                        break;
                    }
                }
                if (ins.getPromedio() >= 4.0 && ins.getPorcentajeAsistencia() >= 75.0 && tieneLasCincoNotas) {
                    materiasAprobadas++;
                } else {
                    materiasEnCurso++;
                }

                int faltasDeEstaMateria = ins.getTotalClases() - ins.getClasesAsistidas();
                faltasTotales += faltasDeEstaMateria;

                if (ins.getPorcentajeAsistencia() < 75.0 || "Libre".equalsIgnoreCase(ins.getCondicion())) {
                    materiasEnAlerta++;
                }
            }
        }

        double promedioGeneral = sumaPromedios / inscripciones.size();

        return new double[]{
            promedioGeneral,
            inscripciones.size(),
            faltasTotales,
            materiasEnAlerta,
            materiasAprobadas,
            materiasEnCurso
        };
    }
    
    public boolean procesarRegistroCalificacion(int filaSeleccionada, int instancia, double notaCargada) {
        if (filaSeleccionada < 0 || filaSeleccionada >= inscripciones.size()) {
            return false;
        }
        InscripcionMateria inscripcion = inscripciones.get(filaSeleccionada);
        double notaExistente = inscripcion.getNotas().get(instancia);

        if (notaExistente != -1.0) {
            String notaFormateada = (notaExistente % 1 == 0) ? String.format("%.0f", notaExistente) : String.valueOf(notaExistente);

            int respuesta = javax.swing.JOptionPane.showConfirmDialog(
                    vista,
                    "Ya tenés la nota " + notaFormateada + " cargada en esta instancia.\n¿Deseas editarla por un " + notaCargada + "?",
                    "Confirmar Edición",
                    javax.swing.JOptionPane.YES_NO_OPTION,
                    javax.swing.JOptionPane.QUESTION_MESSAGE
            );

            if (respuesta != javax.swing.JOptionPane.YES_OPTION) {
                return false; 
            }
        }

        inscripcion.guardarNotaEnPosicion(instancia, notaCargada);
        inscripcionDao.guardarInscripciones(inscripciones);
        return true;
    }
    
    public double[] calcularEstadisticasGenerales() {
        if (inscripciones.isEmpty()) {
            return new double[]{0.0, 0.0, 0.0};
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

        public ArrayList<String> obtenerListaAprobadasFormateada() {
            ArrayList<String> lineas = new ArrayList<>();
            for (InscripcionMateria ins : inscripciones) {
                if (ins != null && ins.getMateria() != null) {
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
    
    public ArrayList<Object[]> obtenerDesgloseNotasFormateado() {
        ArrayList<Object[]> filas = new ArrayList<>();
    
        for (InscripcionMateria ins : inscripciones) {
            if (ins != null && ins.getMateria() != null) {
                // El controlador limpia y maquilla las notas con el operador ternario
                Double n1 = ins.getNotas().get(0) == -1.0 ? 0.0 : ins.getNotas().get(0);
                Double n2 = ins.getNotas().get(1) == -1.0 ? 0.0 : ins.getNotas().get(1);
                Double n3 = ins.getNotas().get(2) == -1.0 ? 0.0 : ins.getNotas().get(2);
                Double n4 = ins.getNotas().get(3) == -1.0 ? 0.0 : ins.getNotas().get(3);
                Double n5 = ins.getNotas().get(4) == -1.0 ? 0.0 : ins.getNotas().get(4);

                filas.add(new Object[]{
                    ins.getMateria().getCodigo(), 
                    ins.getMateria().getNombre(), 
                    n1, n2, n3, n4, n5
                });
            }
        }
        return filas;
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
