/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autogestionestudiantil;

public class MateriaCuatrimestral extends Materia {

    public MateriaCuatrimestral(String nombre, String codigo, int cuatrimestre, int anio) 
    {
        super(nombre, codigo, cuatrimestre, anio);
    }

    // Personaliza el setter de Materia para que solo permita cuatrimestre 1
    @Override
    public void setCuatrimestre(int cuatrimestre) {
        if (cuatrimestre == 1) {
            super.setCuatrimestre(cuatrimestre);
        } else {
            super.setCuatrimestre(-1);
        }
    }
}