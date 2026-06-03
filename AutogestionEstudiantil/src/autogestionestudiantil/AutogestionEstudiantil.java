/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package autogestionestudiantil;

import java.util.Scanner;


public class AutogestionEstudiantil 
{
    public static void main(String[] args) 
    {
        Scanner sc = new Scanner(System.in);

        // Objeto base del estudiante
        Estudiante alumno = new Estudiante("Ana Garcia", "22001", "Interfaz Grafica", 2023);
        
        //Datos de prueba
        Materia m = new Materia("Matematica", "22033", 1, 2024);
        Materia a = new Materia("Lengua", "22034", 2, 2024);
        
       
        int opcion;
        // MENU PRINCIPAL (SOLO ESTRUCTURA INICIAL)
        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Ver perfil");
            System.out.println("2. Gestion de materias");
            System.out.println("3. Registrar asistencia");
            System.out.println("4. Registrar calificacion");
            System.out.println("5. Ver reportes");
            System.out.println("0. Salir");
            System.out.println("-------------");

            System.out.print("Opcion: ");
            while (!sc.hasNextInt())
            {
                System.out.println("Opción inválida.");
                sc.nextLine();
                System.out.print("Opcion: ");
            }

            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) 
            {
                case 1:
                    System.out.println("PERFIL DE ALUMNO:");
                    alumno.mostrarResumen();
                    
                    volverMenu(alumno, sc);
                    break;

                case 2:
                    menuMaterias(alumno, sc);
                    break; 

                case 3:
                     InscripcionMateria inscA = null;

                    do
                    {
                        System.out.print("Codigo materia (E para volver): ");
                        String codA = sc.nextLine();

                        if (codA.equalsIgnoreCase("E"))
                        {
                            break;
                        }

                        inscA = alumno.getInscripcion(codA);

                        if (inscA == null)
                        {
                            System.out.println("Materia no encontrada.");
                        }

                    } while (inscA == null);

                    if (inscA == null)
                    {
                        break;
                    }

                    System.out.print("Presente? (s/n): ");
                    String asistencia = sc.nextLine().toLowerCase();

                    boolean presente;

                    if (asistencia.equals("s"))
                    {
                        presente = true;
                    }
                    else if (asistencia.equals("n"))
                    {
                        presente = false;
                    }
                    else
                    {
                        System.out.println("Opción inválida.");
                        break;
                    }

                    inscA.registrarAsistencia(presente);
                    System.out.println("Asistencia registrada.");

                    volverMenu(alumno, sc);

                    break;

                case 4:

                    InscripcionMateria inscN = null;

                    do {
                        System.out.print("Codigo materia (E para volver): ");
                        String codN = sc.nextLine().trim();

                        if (codN.equalsIgnoreCase("E")) {
                            break;
                        }

                        inscN = alumno.getInscripcion(codN);

                        if (inscN == null) {
                            System.out.println("Materia no encontrada.");
                        }

                    } while (inscN == null);

                    if (inscN == null) {
                        break;
                    }

                    double nota;

                    do {
                        System.out.print("Nota (0 a 10): ");

                        while (!sc.hasNextDouble()) {
                            System.out.println("Ingrese una nota válida.");
                            sc.nextLine();
                            System.out.print("Nota (0 a 10): ");
                        }

                        nota = sc.nextDouble();
                        sc.nextLine();

                        if (nota < 0 || nota > 10) {
                            System.out.println("La nota debe estar entre 0 y 10.");
                        }

                    } while (nota < 0 || nota > 10);

                    if (inscN.agregarNota(nota)) {
                        System.out.println("Nota registrada correctamente.");
                    } else {
                        System.out.println("No se pudo registrar la nota.");
                    }

                    volverMenu(alumno, sc);

                    break; 
                
                case 5:
                    System.out.println("\n=== VER REPORTES ===");
                    System.out.println("1. Reporte general");
                    System.out.println("2. Materias críticas");
                    System.out.println("0. Volver");
                    System.out.print("Opción: ");

                    int opcionReporte;

                    while (!sc.hasNextInt()) {
                        System.out.println("Opción inválida.");
                        sc.nextLine();
                        System.out.print("Opción: ");
                    }

                    opcionReporte = sc.nextInt();
                    sc.nextLine();

                    switch (opcionReporte) {
                        case 1:

                            System.out.println("\n=== REPORTE GENERAL ===");

                            if (alumno.getMaterias().isEmpty()) {
                                System.out.println("No hay materias inscriptas.");
                                volverMenu(alumno, sc);
                                break;
                            }

                            int regulares = 0;
                            int libres = 0;
                            int aprobadas = 0;

                            for (InscripcionMateria insc : alumno.getMaterias()) {
                                System.out.println("Materia: " + insc.getMateria().getNombre());
                                System.out.println("Código: " + insc.getMateria().getCodigo());
                                System.out.println("Asistencia: " + insc.getPorcentajeAsistencia() + "%");
                                System.out.println("Promedio: " + insc.getPromedio());
                                System.out.println("Condición: " + insc.getCondicion());

                                if (insc.getCondicion().equalsIgnoreCase("Regular")) {
                                    regulares++;
                                } else {
                                    libres++;
                                }

                                if (insc.estaAprobada()) {
                                    aprobadas++;
                                }

                                System.out.println("----------------------");
                            }

                            System.out.println("Promedio general: " + alumno.getPromedioGeneral());
                            System.out.println("Materias regulares: " + regulares);
                            System.out.println("Materias libres: " + libres);
                            System.out.println("Materias aprobadas: " + aprobadas);

                            // BONUS: RANKING
                            System.out.println("\n=== RANKING DE MATERIAS ===");

                            for (int i = 0; i < alumno.getMaterias().size(); i++) {
                                for (int j = i + 1; j < alumno.getMaterias().size(); j++) {
                                    if (alumno.getMaterias().get(i).getPuntajeRanking()
                                            < alumno.getMaterias().get(j).getPuntajeRanking()) {
                                        InscripcionMateria aux = alumno.getMaterias().get(i);
                                        alumno.getMaterias().set(i, alumno.getMaterias().get(j));
                                        alumno.getMaterias().set(j, aux);
                                    }
                                }

                                System.out.println(
                                        (i + 1) + ". "
                                        + alumno.getMaterias().get(i).getMateria().getNombre()
                                        + " | Puntaje: "
                                        + alumno.getMaterias().get(i).getPuntajeRanking()
                                );
                            }

                            volverMenu(alumno, sc);
                            break;

                        case 2:

                            System.out.println("\n=== MATERIAS CRÍTICAS ===");

                            if (alumno.getMateriasCriticas().isEmpty()) {
                                System.out.println("No hay materias críticas.");
                            } else {
                                for (InscripcionMateria critica : alumno.getMateriasCriticas()) {
                                    System.out.println("Materia: " + critica.getMateria().getNombre());
                                    System.out.println("Código: " + critica.getMateria().getCodigo());
                                    System.out.println("Asistencia: " + critica.getPorcentajeAsistencia() + "%");
                                    System.out.println("Promedio: " + critica.getPromedio());
                                    System.out.println("Condición: " + critica.getCondicion());
                                    System.out.println("----------------------");
                                }
                            }

                            volverMenu(alumno, sc);
                            break;

                        case 0:
                            break;

                        default:
                            System.out.println("Opción inválida.");
                            break;
                    }

                    break;
                    
                case 0:
                    System.out.println("Hasta luego!");
                    break;

                default:
                    System.out.println("Opción invalida. Intente nuevamente.");
                    break;
            }

        } while (opcion != 0);

        sc.close();
    }

    private static void menuMaterias(Estudiante alumno, Scanner sc) 
    {
        int opcionMateria;

        do {
            System.out.println("\n=== GESTIÓN DE MATERIAS ===");
            System.out.println("1. Inscribir materia");
            System.out.println("2. Darse de baja de una materia");
            System.out.println("3. Ver materias");
            System.out.println("4. Buscar materias");
            System.out.println("0. Volver");
            System.out.println("-------------");

            System.out.print("Opción: ");

            while (!sc.hasNextInt()) 
            {
                System.out.println("Opción inválida. Intente nuevamente.");
                sc.nextLine();
                System.out.print("Opción: ");
            }

            opcionMateria = sc.nextInt();
            sc.nextLine();
 
            switch (opcionMateria) 
            {
                
                case 1:

                    System.out.println("TIPO DE INSCRIPCIÓN:");
                    System.out.println("1. Materia cuatrimestral");
                    System.out.println("2. Materia anual");

                    int tipoMateria;

                    do {
                        System.out.print("Opción: ");

                        while (!sc.hasNextInt()) {
                            System.out.println("Ingrese una opción válida.");
                            sc.nextLine();
                            System.out.print("Opción: ");
                        }

                        tipoMateria = sc.nextInt();
                        sc.nextLine(); // LIMPIA BUFFER

                        if (tipoMateria == 1) {
                            System.out.println("Seleccionaste: Materia cuatrimestral");
                        } else if (tipoMateria == 2) {
                            System.out.println("Seleccionaste: Materia anual");
                        } else {
                            System.out.println("Debe elegir 1 o 2.");
                        }

                    } while (tipoMateria != 1 && tipoMateria != 2);

                    System.out.println("Inscribirse a una materia:");

                    System.out.print("NOMBRE: ");
                    String nombre = sc.nextLine().trim();

                    System.out.print("CODIGO: ");
                    String codigo = sc.nextLine().trim();

                    // Validación longitud código
                    if (codigo.length() < 3 || codigo.length() > 10) {
                        System.out.println("Error: El código debe tener entre 3 y 10 caracteres.");
                        break;
                    }

                    // Validación código repetido
                    if (alumno.getInscripcion(codigo) != null) {
                        System.out.println("Error: Ya estás inscripto en una materia con ese código.");
                        break;
                    }

                    int anio;

                    do {
                        System.out.print("AÑO: ");

                        while (!sc.hasNextInt()) {
                            System.out.println("Ingrese un año válido.");
                            sc.nextLine();
                            System.out.print("AÑO: ");
                        }

                        anio = sc.nextInt();
                        sc.nextLine(); // LIMPIA BUFFER

                        if (anio <= 0) {
                            System.out.println("El año debe ser mayor a 0.");
                        }

                    } while (anio <= 0);

                    Materia nuevaMateria;


                    // MATERIA ANUAL

                    if (tipoMateria == 2) {
                        MateriaAnual anual = new MateriaAnual(nombre, codigo, anio);

                        double nota1;
                        do {
                            System.out.print("Nota 1° cuatrimestre: ");

                            while (!sc.hasNextDouble()) {
                                System.out.println("Ingrese una nota válida.");
                                sc.nextLine();
                                System.out.print("Nota 1° cuatrimestre: ");
                            }

                            nota1 = sc.nextDouble();

                            if (nota1 < 0 || nota1 > 10) {
                                System.out.println("La nota debe estar entre 0 y 10.");
                            }

                        } while (nota1 < 0 || nota1 > 10);

                        sc.nextLine();

                        anual.setNotaPrimerCuatrimestre(nota1);

                        double nota2;
                        do {
                            System.out.print("Nota 2° cuatrimestre: ");

                            while (!sc.hasNextDouble()) {
                                System.out.println("Ingrese una nota válida.");
                                sc.nextLine();
                                System.out.print("Nota 2° cuatrimestre: ");
                            }

                            nota2 = sc.nextDouble();

                            if (nota2 < 0 || nota2 > 10) {
                                System.out.println("La nota debe estar entre 0 y 10.");
                            }

                        } while (nota2 < 0 || nota2 > 10);

                        sc.nextLine();

                        anual.setNotaSegundoCuatrimestre(nota2);

                        double asistencia1;
                        do {
                            System.out.print("Asistencia 1° cuatrimestre: ");

                            while (!sc.hasNextDouble()) {
                                System.out.println("Ingrese un porcentaje válido.");
                                sc.nextLine();
                                System.out.print("Asistencia 1° cuatrimestre: ");
                            }

                            asistencia1 = sc.nextDouble();

                            if (asistencia1 < 0 || asistencia1 > 100) {
                                System.out.println("La asistencia debe estar entre 0 y 100.");
                            }

                        } while (asistencia1 < 0 || asistencia1 > 100);

                        sc.nextLine();

                        anual.setAsistenciaPrimerCuatrimestre(asistencia1);

                        double asistencia2;
                        do {
                            System.out.print("Asistencia 2° cuatrimestre: ");

                            while (!sc.hasNextDouble()) {
                                System.out.println("Ingrese un porcentaje válido.");
                                sc.nextLine();
                                System.out.print("Asistencia 2° cuatrimestre: ");
                            }

                            asistencia2 = sc.nextDouble();

                            if (asistencia2 < 0 || asistencia2 > 100) {
                                System.out.println("La asistencia debe estar entre 0 y 100.");
                            }

                        } while (asistencia2 < 0 || asistencia2 > 100);

                        sc.nextLine();

                        anual.setAsistenciaSegundoCuatrimestre(asistencia2);

                        nuevaMateria = anual;
                    } 
                    // MATERIA CUATRIMESTRAL

                    else {
                        int cuatrimestre;

                        do {
                            System.out.print("CUATRIMESTRE (1 o 2): ");

                            while (!sc.hasNextInt()) {
                                System.out.println("Ingrese una opción válida.");
                                sc.nextLine();
                                System.out.print("CUATRIMESTRE (1 o 2): ");
                            }

                            cuatrimestre = sc.nextInt();
                            sc.nextLine(); // LIMPIA BUFFER

                            if (cuatrimestre != 1 && cuatrimestre != 2) {
                                System.out.println("Error: El cuatrimestre debe ser 1 o 2.");
                            }

                        } while (cuatrimestre != 1 && cuatrimestre != 2);

                        nuevaMateria = new Materia(
                                nombre,
                                codigo,
                                cuatrimestre,
                                anio
                        );
                    }

                    alumno.inscribirse(nuevaMateria);

                    System.out.println("Materia registrada correctamente:");
                    nuevaMateria.mostrarResumen();

                    break; 
                case 2:

                    System.out.println("Dar de baja de una materia:");

                    InscripcionMateria inscBaja = null;

                    do {
                        System.out.print("Ingrese el código de la materia (E para volver): ");
                        String codigoBaja = sc.nextLine().trim();

                        if (codigoBaja.equalsIgnoreCase("E")) {
                            break;
                        }

                        inscBaja = alumno.getInscripcion(codigoBaja);

                        if (inscBaja == null) {
                            System.out.println("Materia no encontrada.");
                        } else {
                            alumno.darDeBaja(codigoBaja);
                        }
 
                    } while (inscBaja == null);

                    break;
                    
                case 3:

                    if (alumno.getMaterias().isEmpty()) 
                    {
                        System.out.println("No hay materias inscriptas.");
                    } 
                    else 
                    {
                        System.out.println("=== MATERIAS INSCRIPTAS ===");

                        for (InscripcionMateria insc : alumno.getMaterias()) 
                        {
                            Materia mat = insc.getMateria();

                            System.out.println("Materia: " + mat.getNombre());
                            System.out.println("Código: " + mat.getCodigo());
                            
                            if (mat instanceof MateriaAnual)
                            {
                                System.out.println("Tipo: Materia anual");
                            }
                            else
                            {
                                System.out.println("Cuatrimestre: " + mat.getCuatrimestre());
}
                            System.out.println("Año: " + mat.getAnio());
                            System.out.println("Asistencia: " + insc.getPorcentajeAsistencia() + "%");
                            System.out.println("Promedio: " + insc.getPromedio());
                            System.out.println("Condición: " + insc.getCondicion());
                            System.out.println("----------------------");
                        }
                    }

                    break;

                case 4:

                    System.out.println("Buscar materia por:");
                    System.out.println("1. Código");
                    System.out.println("2. Nombre");
                    System.out.print("Opción: ");

                    while (!sc.hasNextInt()) 
                    {
                        System.out.println("Opción inválida. Intente nuevamente.");
                        sc.nextLine();
                        System.out.print("Opción: ");
                    }

                    int tipoBusqueda = sc.nextInt();
                    sc.nextLine();

                    if (tipoBusqueda == 1) 
                    {
                        System.out.print("Ingrese código: ");
                        String codigoBusqueda = sc.nextLine();
                        buscarMateria(alumno, codigoBusqueda, true);
                    } 
                    else if (tipoBusqueda == 2) 
                    {
                        System.out.print("Ingrese nombre: ");
                        String nombreBusqueda = sc.nextLine();
                        buscarMateria(alumno, nombreBusqueda);
                    } 
                    else 
                    {
                        System.out.println("Opción inválida.");
                    }

                    break;

                case 0:

                    System.out.println("Volviendo al menú principal...");
                    break;

                default:

                    System.out.println("Opción inválida.");
                    break;
            }

        } while (opcionMateria != 0);
    }

    //Buscar materia por nombre 
    public static void buscarMateria(Estudiante alumno, String nombre) {
        boolean encontrada = false;

        for (InscripcionMateria insc : alumno.getMaterias()) {
 
            if (insc.getMateria().getNombre().toLowerCase().contains(nombre.toLowerCase().trim())) {
                Materia mat = insc.getMateria();

                System.out.println("Materia encontrada:");
                System.out.println("Materia: " + mat.getNombre());
                System.out.println("Código: " + mat.getCodigo());
                if (mat instanceof MateriaAnual)
                {
                    System.out.println("Tipo: Materia anual");
                }
                else
                {
                    System.out.println("Cuatrimestre: " + mat.getCuatrimestre());
}
                System.out.println("Año: " + mat.getAnio());
                System.out.println("Asistencia: " + insc.getPorcentajeAsistencia() + "%");
                System.out.println("Promedio: " + insc.getPromedio());
                System.out.println("Condición: " + insc.getCondicion());
                System.out.println("----------------------");

                encontrada = true;
            }
        }

        if (!encontrada) {
            System.out.println("Materia no encontrada.");
        }
    }

    //Buscar materia por código (búsqueda parcial)
    public static void buscarMateria(Estudiante alumno, String codigo, boolean porCodigo) {
        boolean encontrada = false;

        for (InscripcionMateria insc : alumno.getMaterias()) {
            // Verifica si el código ingresado está contenido en el código de la materia
            if (insc.getMateria().getCodigo() != null &&
                insc.getMateria().getCodigo().toLowerCase().contains(codigo.toLowerCase().trim())){
                Materia mat = insc.getMateria();

                System.out.println("Materia encontrada:");
                System.out.println("Materia: " + mat.getNombre());
                System.out.println("Código: " + mat.getCodigo());
                if (mat instanceof MateriaAnual)
                {
                    System.out.println("Tipo: Materia anual");
                }
                else
                {
                    System.out.println("Cuatrimestre: " + mat.getCuatrimestre());
}
                System.out.println("Año: " + mat.getAnio());
                System.out.println("Asistencia: " + insc.getPorcentajeAsistencia() + "%");
                System.out.println("Promedio: " + insc.getPromedio());
                System.out.println("Condición: " + insc.getCondicion());
                System.out.println("----------------------");

                encontrada = true;
            }
        }

        if (!encontrada) {
            System.out.println("Materia no encontrada.");
        }
    }
    
    public static void volverMenu(Estudiante alumno, Scanner sc) 
    {
       
        System.out.println("Presione ENTER para volver...");
        sc.nextLine();
    }
    
    
} 

