/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autogestionestudiantil.DAO;

import java.io.*;
import java.util.ArrayList;
import autogestionestudiantil.Modelos.Materia;
public class MateriaDAO {

    // Archivo donde se guardan las materias
    private static final String ARCHIVO = "materias.txt";

    // ── LEER ────────────────────────────────────────────────
    // El controlador llama a cargarMaterias() al iniciar.
    // Lee el archivo y crea objetos Materia.
    public ArrayList<Materia> cargarMaterias() {

        ArrayList<Materia> lista = new ArrayList<>();

        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                if (!linea.trim().isEmpty()) {
                    lista.add(Materia.fromTexto(linea));
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer materias: " + e.getMessage());
        }

        return lista;
    }

    // ── ESCRIBIR ────────────────────────────────────────────
    // Guarda todas las materias actuales en el archivo.
    public void guardarMaterias(ArrayList<Materia> lista) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO))) {

            for (Materia m : lista) {
                bw.write(m.toTexto());
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error al guardar materias: " + e.getMessage());
        }
    }
} 
