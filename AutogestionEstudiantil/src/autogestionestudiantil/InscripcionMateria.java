/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autogestionestudiantil;

/**
 *
 * @author eve
 */
import java.util.ArrayList;


public class InscripcionMateria implements Evaluable, Rankeable {

   
    private Materia materia;
    private int totalClases;
    private int clasesAsistidas;
    private ArrayList<Double> notas;

    public InscripcionMateria(Materia materia) {
        this.materia = materia;
        this.totalClases = 0;
        this.clasesAsistidas = 0;
        this.notas = new ArrayList<>();
    }

    
    public Materia getMateria() {
        return materia;
    }

    public int getTotalClases() {
        return totalClases;
    }

    public int getClasesAsistidas() {
        return clasesAsistidas;
    }

    public ArrayList<Double> getNotas() {
        return notas;
    }


    
    //BONUS
    //Para el cálculo de Puntaje ranking
    @Override
    public double getPuntajeRanking() {

        double promedio = 0;

        for (double n : notas) {
            promedio += n;
        }

        promedio = (notas.size() > 0) ? promedio / notas.size() : 0;

        double asistencia = (totalClases > 0)
                ? (clasesAsistidas * 100.0 / totalClases) : 0;

        asistencia = asistencia / 10;

        return (promedio * 0.6) + (asistencia * 0.4);
    }
    
    public void registrarAsistencia(boolean presente) {
        totalClases++;

        if (presente) {
            clasesAsistidas++;
        }
    }
    
    public double getPorcentajeAsistencia() {
        if (totalClases == 0) {
            return 0;
        }

        return (clasesAsistidas * 100.0) / totalClases;
    }
    
    public String getCondicion() {
        return getPorcentajeAsistencia() >= 75 ? "Regular" : "Libre";

    }
    
    public boolean agregarNota(double nota) {
        if (nota < 0 || nota > 10) {
            System.out.println("Nota inválida. Debe estar entre 0 y 10.");
            return false;
        }

        if (notas.size() >= 5) {
            System.out.println("No se pueden agregar más de 5 notas.");
            return false;
        }

        notas.add(nota);
        return true;
    }

    
    public double getPromedio() {
        if (notas.isEmpty()) {
            return 0;
        }

        double suma = 0;

        for (double nota : notas) {
            suma += nota;
        }

        return suma / notas.size();
    }
    
    public boolean estaAprobada() {
        return getPromedio() >= 6 && getCondicion().equals("Regular");
    }
    

    
}
 



