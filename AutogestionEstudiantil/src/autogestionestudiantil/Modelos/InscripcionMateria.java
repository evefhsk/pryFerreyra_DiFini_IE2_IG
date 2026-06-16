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
        
        for (int i = 0; i < 5; i++) {
            this.notas.add(-1.0);
        }
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
    
    public boolean guardarNotaEnPosicion(int posicion, double nota) {
        if (nota < 0 || nota > 10) {
            System.out.println("Nota inválida. Debe estar entre 0 y 10.");
            return false;
        }
        if (posicion < 0 || posicion >= 5) {
            System.out.println("Posición inválida. Debe ser entre 0 y 4.");
            return false;
        }
        
        this.notas.set(posicion, nota); 
        return true;
    }

    public boolean agregarNota(double nota) {
        if (nota < 0 || nota > 10) {
            return false;
        }
        for (int i = 0; i < 5; i++) {
            if (this.notas.get(i) == -1.0) {
                this.notas.set(i, nota);
                return true;
            }
        }
        return false;
    }
    
    public double getPromedio() {
        double suma = 0;
        int cantidadNotasValidas = 0;

        for (double nota : notas) {
            if (nota != -1.0) { 
                suma += nota;
                cantidadNotasValidas++;
            }
        }

        if (cantidadNotasValidas == 0) {
            return 0;
        }
        return suma / cantidadNotasValidas;
    }
    
    public boolean estaAprobada() {
        return getPromedio() >= 6 && getCondicion().equals("Regular");
    }
    
    public String toTexto() {
        String notasTexto = "";
        for (int i = 0; i < 5; i++) {
            notasTexto += this.notas.get(i);
            if (i < 4) {
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

        InscripcionMateria inscripcion = new InscripcionMateria(materia);
        inscripcion.totalClases = Integer.parseInt(datos[1]);
        inscripcion.clasesAsistidas = Integer.parseInt(datos[2]);

        if (datos.length > 3 && !datos[3].isEmpty()) {
            String[] notasTexto = datos[3].split(",");
            
            // Cargamos las posiciones que existan en el archivo de texto
            for (int i = 0; i < notasTexto.length && i < 5; i++) {
                inscripcion.notas.set(i, Double.parseDouble(notasTexto[i]));
            }
        }

        return inscripcion;
    }
    
    public void registrarClase(boolean asistio) {
        this.totalClases++;
        if (asistio) {
            this.clasesAsistidas++;
        }
    }
    
}
 



