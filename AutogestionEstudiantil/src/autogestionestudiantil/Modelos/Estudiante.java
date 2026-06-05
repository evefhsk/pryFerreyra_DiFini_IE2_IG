/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autogestionestudiantil.Modelos;

/**
 *
 * @author Eve
 */

import autogestionestudiantil.Modelos.Materia;
import java.util.ArrayList;

public class Estudiante extends PersonaAcademica implements Consultable{

    private String carrera;
    private int anioIngreso;
    private ArrayList<InscripcionMateria> materias;

    
    public Estudiante(String nombre, String legajo, String carrera, int anioIngreso) {
        super(nombre, legajo);
        this.carrera = carrera;
        this.anioIngreso = anioIngreso;
        this.materias = new ArrayList<>();

    }


    public String getCarrera() {
        return carrera;
    }

    public int getAnioIngreso() {
        return anioIngreso;
    }

    public ArrayList<InscripcionMateria> getMaterias() {
        return materias;
    }
    
     public void inscribirse(Materia m) {

        // Validar duplicados
        for (InscripcionMateria insc : materias) {
            if (insc.getMateria().getCodigo().equals(m.getCodigo())) {
                System.out.println("Ya estás inscripto en esta materia.");
                return;
            }
        }

        InscripcionMateria nueva = new InscripcionMateria(m);
        materias.add(nueva);
        System.out.println("La Materia " + m.getNombre() + " fue inscripta correctamente.");
    }

    public void darDeBaja(String codigo) {

        InscripcionMateria insc = getInscripcion(codigo);

        if (insc != null) {
            materias.remove(insc);
            System.out.println("Materia eliminada.");
        } else {
            System.out.println("Materia no encontrada.");
        }
    }

    public InscripcionMateria getInscripcion(String codigo) {

        for (InscripcionMateria insc : materias) {
            if (insc.getMateria().getCodigo().equalsIgnoreCase(codigo)) {
                return insc;
            }
        }

        return null;
    }
    
    public double getPromedioGeneral() {

        if (materias.isEmpty()) {
            return 0;
        }

        double suma = 0;

        for (InscripcionMateria insc : materias) {
            suma += insc.getPromedio();
        }

        return suma / materias.size();
    }
     
    public ArrayList<InscripcionMateria> getMateriasCriticas() {

        ArrayList<InscripcionMateria> criticas = new ArrayList<>();

        for (InscripcionMateria insc : materias) {
            double asistencia = insc.getPorcentajeAsistencia();

            if (asistencia >= 75 && asistencia <= 85) {
                criticas.add(insc);
            }
        }

        return criticas;

    }
    
    @Override
    public void mostrarResumen() {
        System.out.println("Nombre: " + getNombre());
        System.out.println("Legajo: " + getLegajo());
        System.out.println("Carrera: " + carrera);
        System.out.println("Año de ingreso: " + anioIngreso);
        System.out.println("Promedio general: " + getPromedioGeneral());
    
        
    }

    // DAO
    public String toTexto() {

        return getNombre() + ";"
                + getLegajo() + ";"
                + carrera + ";"
                + anioIngreso;
    }

    public static Estudiante fromTexto(String linea) {

        String[] datos = linea.split(";");

        return new Estudiante(
                datos[0],
                datos[1],
                datos[2],
                Integer.parseInt(datos[3])
        );
    }
}
  


    

