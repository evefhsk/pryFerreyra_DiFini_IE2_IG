/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autogestionestudiantil.DAO;

import autogestionestudiantil.Modelos.InscripcionMateria;
import autogestionestudiantil.Modelos.Materia;
import java.io.*;
import java.util.ArrayList;

public class InscripcionMateriaDAO {

   private static final String ARCHIVO = "inscripciones.txt";

    // LEER
    public ArrayList<InscripcionMateria> cargarInscripciones(
            ArrayList<Materia> materias) {

        ArrayList<InscripcionMateria> lista
                = new ArrayList<>();

        File archivo = new File(ARCHIVO);

        if (!archivo.exists()) {
            return lista;
        }

        try (BufferedReader br
                = new BufferedReader(
                        new FileReader(archivo))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                if (!linea.trim().isEmpty()) {

                    String codigo
                            = linea.split(";")[0];

                    Materia materiaEncontrada = null;

                    for (Materia m : materias) {
                        
                        if (m == null || m.getCodigo() == null) {
                            continue;
                        }

                        // CAMBIAZO ACÁ: trim() limpia espacios fantasmas e equalsIgnoreCase ignora mayúsculas/minúsculas
                        if (m.getCodigo().trim().equalsIgnoreCase(codigo.trim())) {
                            materiaEncontrada = m;
                            break;
                        }
                    }

                    if (materiaEncontrada != null) {

                        lista.add(
                                InscripcionMateria
                                        .fromTexto(
                                                linea,
                                                materiaEncontrada));
                    }
                }
            }

        } catch (IOException e) {

            System.out.println(
                    "Error al leer inscripciones: "
                    + e.getMessage());
        }

        return lista;
    }

    // ESCRIBIR
    public void guardarInscripciones(
            ArrayList<InscripcionMateria> lista) {

        try (BufferedWriter bw
                = new BufferedWriter(
                        new FileWriter(ARCHIVO))) {

            for (InscripcionMateria i : lista) {

                bw.write(i.toTexto());
                bw.newLine();
            }

        } catch (IOException e) {

            System.out.println(
                    "Error al guardar inscripciones: "
                    + e.getMessage());
        }
    }
}
