/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autogestionestudiantil;

/**
 *
 * @author eve
 */
public class MateriaAnual extends Materia
{
    private double notaPrimerCuatrimestre;
    private double notaSegundoCuatrimestre;

    private double asistenciaPrimerCuatrimestre;
    private double asistenciaSegundoCuatrimestre;

    public MateriaAnual(String nombre, String codigo, int anio) {

        super(nombre, codigo, 0, anio);

        this.notaPrimerCuatrimestre = 0;
        this.notaSegundoCuatrimestre = 0;

        this.asistenciaPrimerCuatrimestre = 0;
        this.asistenciaSegundoCuatrimestre = 0;
    }
    
    public double getNotaPrimerCuatrimestre() {
        return notaPrimerCuatrimestre;
    }

    public void setNotaPrimerCuatrimestre(double notaPrimerCuatrimestre) {
        this.notaPrimerCuatrimestre = notaPrimerCuatrimestre;
    }

    public double getNotaSegundoCuatrimestre() {
        return notaSegundoCuatrimestre;
    }

    public void setNotaSegundoCuatrimestre(double notaSegundoCuatrimestre) {
        this.notaSegundoCuatrimestre = notaSegundoCuatrimestre;
    }

    public double getAsistenciaPrimerCuatrimestre() {
        return asistenciaPrimerCuatrimestre;
    }

    public void setAsistenciaPrimerCuatrimestre(double asistenciaPrimerCuatrimestre) {
        this.asistenciaPrimerCuatrimestre = asistenciaPrimerCuatrimestre;
    }

    public double getAsistenciaSegundoCuatrimestre() {
        return asistenciaSegundoCuatrimestre;
    }

    public void setAsistenciaSegundoCuatrimestre(double asistenciaSegundoCuatrimestre) {
        this.asistenciaSegundoCuatrimestre = asistenciaSegundoCuatrimestre;
    }


    public double getPromedioAnual() {

        return (notaPrimerCuatrimestre
                + notaSegundoCuatrimestre) / 2;
    }

    public double getAsistenciaTotal() {

        return (asistenciaPrimerCuatrimestre
                + asistenciaSegundoCuatrimestre) / 2;
    }


    public String getCondicion() {

        if (getAsistenciaTotal() >= 70) {
            return "Regular";
        }

        return "Libre";
    }
    
    @Override
    public void mostrarResumen() {

        System.out.println("=== MATERIA ANUAL ===");

        System.out.println("Nombre: " + getNombre());
        System.out.println("Código: " + getCodigo());
        System.out.println("Año: " + getAnio());

        System.out.println("Promedio anual: " + getPromedioAnual());

        System.out.println("Asistencia total: "
                + getAsistenciaTotal());

        System.out.println("Condición: "
                + getCondicion());
    }
}
    

