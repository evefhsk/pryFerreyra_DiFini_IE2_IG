/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autogestionestudiantil.DAO;

import autogestionestudiantil.Modelos.Estudiante;
import java.io.*;
import java.util.ArrayList;

public class EstudianteDAO {

    private static final String ARCHIVO = "estudiantes.txt";

    // LEER
    public ArrayList<Estudiante> cargarEstudiantes() {

        ArrayList<Estudiante> lista = new ArrayList<>();

        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) {
            return lista;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                if (!linea.trim().isEmpty()) {
                    lista.add(Estudiante.fromTexto(linea));
                }
            }

        } catch (IOException e) {
            System.out.println("Error al leer estudiantes: " + e.getMessage());
        }

        return lista;
    }

    // ESCRIBIR
    public void guardarEstudiantes(ArrayList<Estudiante> lista) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO))) {

            for (Estudiante e : lista) {
                bw.write(e.toTexto());
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("Error al guardar estudiantes: " + e.getMessage());
        }
    }
}
