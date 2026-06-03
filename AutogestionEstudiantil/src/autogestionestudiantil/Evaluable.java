/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package autogestionestudiantil;

/**
 *
 * @author ferre
 */
public interface Evaluable 
{
    String getCondicion();
    double getPromedio();
    boolean estaAprobada();
    
    //Le pregunte a la ia como implementarlo: "Como se utiliza el default en java"
    default void mostrarEstadoAcademico()
    {
        System.out.println("Condicion: " + getCondicion());
        System.out.println("Promedio: " + getPromedio());
    }
            
}
