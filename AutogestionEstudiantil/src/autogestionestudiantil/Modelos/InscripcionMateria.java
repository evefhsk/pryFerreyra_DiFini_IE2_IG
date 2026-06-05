/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autogestionestudiantil.Modelos;

/**
 *
 * @author eve
 */
import autogestionestudiantil.Modelos.Materia;

import java.util.ArrayList;


public class InscripcionMateria implements Evaluable {

   
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
    
    // DAO
    public String toTexto() {

        String notasTexto = "";

        for (int i = 0; i < notas.size(); i++) {
            notasTexto += notas.get(i);

            if (i < notas.size() - 1) {
                notasTexto += ",";
            }
        }

        return materia.getCodigo() + ";"
                + totalClases + ";"
                + clasesAsistidas + ";"
                + notasTexto;
    }
    
    public static InscripcionMateria fromTexto(String linea, Materia materia) {

        String[] datos = linea.split(";");

        InscripcionMateria inscripcion
                = new InscripcionMateria(materia);

        inscripcion.totalClases
                = Integer.parseInt(datos[1]);

        inscripcion.clasesAsistidas
                = Integer.parseInt(datos[2]);

        if (datos.length > 3 && !datos[3].isEmpty()) {

            String[] notasTexto = datos[3].split(",");

            for (String nota : notasTexto) {
                inscripcion.notas.add(
                        Double.parseDouble(nota));
            }
        }

        return inscripcion;
    }
    
}
 



