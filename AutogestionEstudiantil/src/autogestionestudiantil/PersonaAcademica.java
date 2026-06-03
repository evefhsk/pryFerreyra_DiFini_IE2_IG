/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autogestionestudiantil;

public abstract class PersonaAcademica {

    private String nombre;
    private String legajo;

    public PersonaAcademica(String nombre, String legajo) {
        this.nombre = nombre;
        this.legajo = legajo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getLegajo() {
        return legajo;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.isEmpty()) {
            this.nombre = nombre;
        } else {
            System.out.println("El nombre no puede estar vacio");
        }
    }

    public void setLegajo(String legajo) {
        if (legajo != null && !legajo.isEmpty()) {
            this.legajo = legajo;
        } else {
            System.out.println("El legajo no puede estar vacio");
        }
    }

    public abstract void mostrarResumen();
}
 
