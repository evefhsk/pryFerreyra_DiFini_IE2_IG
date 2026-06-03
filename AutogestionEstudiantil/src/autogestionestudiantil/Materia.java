/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package autogestionestudiantil;

import java.util.HashSet;

public class Materia implements Consultable {

    private String nombre;
    private String codigo;
    private int cuatrimestre;
    private int anio;

    private static HashSet<String> codigosUsados = new HashSet<>();

    public Materia(String nombre, String codigo, int cuatrimestre, int anio) {
        setNombre(nombre);
        setCodigo(codigo);
        setCuatrimestre(cuatrimestre);
        setAnio(anio);
    }

    @Override
    public void mostrarResumen() 
    {
        System.out.println("=== RESUMEN DE MATERIA ===");

        System.out.println("Materia: " + nombre);
        System.out.println("Año: " + anio);

        if (codigo != null) 
        {
            System.out.println("Código: " + codigo);
        } 
        else 
        {
            System.out.println("Código inválido o repetido");
        }

        if (cuatrimestre == 1 || cuatrimestre == 2) 
        {
            System.out.println("Cuatrimestre: " + cuatrimestre);
        } 
        else 
        {
            System.out.println("Cuatrimestre inválido");
        }
    }

    // GETTERS
    public String getNombre() 
    {
        return nombre;
    }

    public String getCodigo() 
    {
        return codigo;
    }

    public int getCuatrimestre() 
    {
        return cuatrimestre;
    }

    public int getAnio() 
    {
        return anio;
    }

    // SETTERS CON VALIDACIÓN
    public void setNombre(String nombre) {
        if (nombre != null && !nombre.isEmpty()) 
        {
            this.nombre = nombre;
        } 
        else 
        {
            this.nombre = "Sin nombre";
        }
    }

    public void setCodigo(String codigo) {
        if (codigo == null || codigo.isEmpty() || codigosUsados.contains(codigo)) 
        {
            this.codigo = null;
        } 
        else 
        {
            this.codigo = codigo;
            codigosUsados.add(codigo);
        }
    }

    public void setCuatrimestre(int cuatrimestre) 
    {
        if (cuatrimestre == 1 || cuatrimestre == 2) 
        {
            this.cuatrimestre = cuatrimestre;
        } 
        else 
        {
            this.cuatrimestre = -1;
        }
    }

    public void setAnio(int anio) 
    {
        if (anio > 0) 
        {
            this.anio = anio;
        } 
        else 
        {
            this.anio = -1;
        }
    }
} 
