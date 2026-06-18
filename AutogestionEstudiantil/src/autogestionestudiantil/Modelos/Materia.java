/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autogestionestudiantil.Modelos;

public class Materia implements Consultable {

    private String nombre;
    private String codigo;
    private int cuatrimestre;
    private int anio;

    public Materia(String nombre,
            String codigo,
            int cuatrimestre,
            int anio) {

        setNombre(nombre);
        setCodigo(codigo);
        setCuatrimestre(cuatrimestre);
        setAnio(anio);
    }

    @Override
    public void mostrarResumen() {

        System.out.println("=== RESUMEN DE MATERIA ===");
        System.out.println("Materia: " + nombre);
        System.out.println("Código: " + codigo);
        System.out.println("Cuatrimestre: " + cuatrimestre);
        System.out.println("Año: " + anio);
    }

    // GETTERS
    public String getNombre() {
        return nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getCuatrimestre() {
        return cuatrimestre;
    }

    public int getAnio() {
        return anio;
    }

    // SETTERS
    public void setNombre(String nombre) {

        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre;
        } else {
            this.nombre = "Sin nombre";
        }
    }

    public void setCodigo(String codigo) {

        if (codigo != null && !codigo.trim().isEmpty()) {
            this.codigo = codigo.trim();
        } else {
            this.codigo = "";
        }
    }

    public void setCuatrimestre(int cuatrimestre) {

        if (cuatrimestre == 1 || cuatrimestre == 2) {
            this.cuatrimestre = cuatrimestre;
        } else {
            this.cuatrimestre = -1;
        }
    }

    public void setAnio(int anio) {

        if (anio > 0) {
            this.anio = anio;
        } else {
            this.anio = -1;
        }
    }

    // DAO
    public String toTexto() {

        return nombre + ";"
                + codigo + ";"
                + cuatrimestre + ";"
                + anio;
    }

    public static Materia fromTexto(String linea) {

        String[] datos = linea.split(";");

        return new Materia(
                datos[0],
                datos[1],
                Integer.parseInt(datos[2]),
                Integer.parseInt(datos[3])
        );
    }
}
