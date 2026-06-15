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
    
}
