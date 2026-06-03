/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autogestionestudiantil;

/**
 *
 * @author Eve
 */


import java.util.ArrayList;

public class Estudiante extends PersonaAcademica implements Consultable{

    private String carrera;
    private int anioIngreso;
    private ArrayList<InscripcionMateria> materias;
    private ArrayList<Materia> materiasPolimorfismo; //Nueva lista para el Bonus
    
    public Estudiante(String nombre, String legajo, String carrera, int anioIngreso) {
        super(nombre, legajo);
        this.carrera = carrera;
        this.anioIngreso = anioIngreso;
        this.materias = new ArrayList<>();
        this.materiasPolimorfismo = new ArrayList<>(); //Del Bonus
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
    

    //PARTE DEL BONUS:
    
    //Agrega materia a la lista
    public void agregarMateria(Materia m) 
    {
        materiasPolimorfismo.add(m);
        System.out.println("Materia agregada: " + m.getNombre()); 
    }
    
    //Para buscar por codigo
    public Materia buscarPorCodigo(String codigo) 
    {
        return buscarRecursivo(codigo, 0);
    }
    
    private Materia buscarRecursivo(String codigo, int i) 
    {
        if (i >= materiasPolimorfismo.size()) {
            return null;
        }

        if (materiasPolimorfismo.get(i).getCodigo().equalsIgnoreCase(codigo)) {
            return materiasPolimorfismo.get(i);
        }

        return buscarRecursivo(codigo, i + 1);
        
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

   
}
  


    

