# Sistema de Autogestión Estudiantil - IE2

Proyecto desarrollado en Java utilizando Swing, arquitectura MVC + DAO y persistencia de datos mediante archivos de texto.

Esta aplicación representa la evolución del Sistema de Autogestión Estudiantil desarrollado en la Primera Instancia Evaluativa (IE1), migrando desde una aplicación de consola hacia una aplicación de escritorio con interfaz gráfica.

---

## Integrantes

* Evelin Ferreyra
  DNI: 47993248

* Zaira Di Fini
  DNI: 47350879

---

## Tecnologías utilizadas

* Java JDK 21
* Apache NetBeans 28 y 29
* Java Swing
* GitHub
* GitHub Desktop
* Figma

---

## Arquitectura Implementada

El proyecto fue desarrollado siguiendo el patrón MVC + DAO solicitado en la consigna.

### Modelo (Model)

Contiene las clases reutilizadas de la IE1:

* Estudiante
* Materia
* InscripcionMateria
* Evaluable
* Consultable

Además, conserva toda la lógica de negocio:

* Cálculo de promedio
* Determinación de condición académica
* Verificación de aprobación
* Cálculo del porcentaje de asistencia
* Identificación de materias críticas
* Validaciones de inscripción

### Vista (View)

Desarrollada con Java Swing utilizando el diseñador visual de NetBeans.

Componentes implementados:

* JFrame
* JPanel
* JLabel
* JTable
* JList
* JButton
* JMenuBar
* JOptionPane
* JScrollPane
* CardLayout
* FlowLayout

### Controlador (Controller)

Responsable de:

* Gestionar eventos de la interfaz gráfica.
* Coordinar la comunicación entre Vista y Modelo.
* Aplicar la lógica de negocio.
* Actualizar la interfaz en tiempo real.
* Invocar los DAO para persistir los cambios.

### DAO (Data Access Object)

Responsable de la persistencia de datos.

Funciones:

* Lectura de archivos al iniciar la aplicación.
* Escritura automática cuando se agregan, modifican o eliminan datos.
* Manejo de archivos de texto sin rutas absolutas.

Archivos utilizados:

* materias.txt
* inscripciones.txt

---

## Funcionalidades Implementadas

### Perfil del estudiante

Visualización de:

* Nombre
* Carrera
* DNI
* Año de ingreso

### Gestión de materias

* Inscripción de materias.
* Dar de baja a materias.
* Validación de código único.
* Validación de cuatrimestre.
* Actualización automática de la tabla.
* Buscar materia por nombre o código.

### Gestión de asistencias

* Registro de asistencia.
* Registro de ausencias.
* Cálculo automático del porcentaje de asistencia.
* Actualización inmediata de la condición académica.

### Gestión de calificaciones

* Registro de notas.
* Validación de rango de notas (0 a 10).
* Máximo de 5 notas por materia.
* Cálculo automático del promedio.

### Reportes Académicos

* Situación general del estudiante.
* Materias aprobadas.
* Materias en riesgo.
* Estado académico.

---

## Componentes Gráficos Obligatorios Utilizados

| Componente | Implementación |
|------------|---------------|
| JLabel | Títulos, etiquetas y mensajes |
| JTable | Listado de materias |
| JList | Materias en riesgo |
| JButton | Acciones principales |
| JMenuBar | Navegación y reportes |
| JOptionPane | Confirmaciones y alertas |
| JScrollPane | Scroll de tabla y lista |
| CardLayout | Navegación entre pantallas |

---

## Layouts Utilizados

### BorderLayout

Utilizado como estructura principal de la ventana.

### FlowLayout

Utilizado para la organización de botones y controles de gestión materias.

---

## Persistencia de Datos

La aplicación implementa persistencia mediante archivos de texto.

Características:

* Carga automática al iniciar la aplicación.
* Guardado automático después de cada modificación.
* Creación automática de archivos si no existen.
* Almacenamiento local dentro del proyecto.

---

## Diseño UX/UI

El diseño de la interfaz fue previamente prototipado en Figma aplicando los conceptos vistos en la materia:

* Jerarquía visual.
* Consistencia.
* Navegación intuitiva.
* Retroalimentación al usuario.
* Organización de la información.

### Prototipo Figma

https://www.figma.com/proto/dkg1D2aedG48Bfvzi2KoR5/Dise%C3%B1o-de-Autogesti%C3%B3n-Estudiantil?node-id=0-1&t=X8ZrB4lmrzKspytE-1 

---

## Bonus Implementados

- Prototipo navegable en Figma.
- Búsqueda de materias por código o nombre.
- Reportes avanzados.
- Estadísticas académicas.

---

## Ejecución del Proyecto

### Requisitos

* Java JDK 21.
* Apache NetBeans 28 o superior.

### Pasos para ejecutar

1. Clonar el repositorio:

```bash
git clone [https://github.com/evefhsk/pryFerreyra_DiFini_IE2_IG.git]
```

2. Abrir el proyecto en NetBeans.

3. Ejecutar la clase principal:

```java
AutogestionEstudiantil.java
```

4. Comenzar a utilizar el sistema.

---

## Organización del Proyecto

```text
src
│
├── modelo
│   ├── Estudiante.java
│   ├── Materia.java
│   ├── InscripcionMateria.java
│   ├── Evaluable.java
│   └── Consultable.java
│
├── dao
│   ├── MateriaDAO.java
│   └── InscripcionDAO.java
│
├── controlador
│   ├── MateriaController.java
│   ├── InscripcionesController.java
│
├── vista
│   ├── FrmPrincipal.java
│   ├── PnlMaterias.java
│   ├── PnlPerfil.java
│   └── PnlReportes.java
│
└── main
    └── AutogestionEstudiantil.java
```

---

## Desarrollo Colaborativo

El proyecto fue desarrollado utilizando Git y GitHub mediante ramas para organizar el trabajo colaborativo.

Ramas principales:

* `dev`
* `feature/controller-materias`
* `feature/controller-inscripciones`
* `feature/dao-inscripciones`
* `feature/dao-materias`
* `feature/documentacion`
* `feature/integracion-mvc`
* `feature/interfaz-general`
* `feature/modelo-inscripciones`
* `feature/modelo-materias`
* `feature/perfil-estudiante`
* `feature/vista-inscripciones`
* `feature/vista-materias`
---

## Documentación Adicional

| Recurso | Enlace |
|----------|---------|
| Repositorio GitHub | https://github.com/evefhsk/pryFerreyra_DiFini_IE2_IG.git |
| Diseño Figma | https://www.figma.com/proto/dkg1D2aedG48Bfvzi2KoR5/Dise%C3%B1o-de-Autogesti%C3%B3n-Estudiantil?node-id=158-211&t=X8ZrB4lmrzKspytE-1  |
| Video explicativo |  |
| Conversaciones IA | https://docs.google.com/document/d/1AImyo5g_JzDFDcJhzfkO8-3BBhia3rdTO1botwDrLG0/edit?usp=sharing |
| Prueba y error | https://docs.google.com/document/d/1AImyo5g_JzDFDcJhzfkO8-3BBhia3rdTO1botwDrLG0/edit?usp=sharing |
| Organización |https://docs.google.com/document/d/1EHddp_ELIB-SvB9dtQ8pyK6g7zr67QqlOk3FhPDF05E/edit?usp=sharing |

---

## Desafíos Encontrados

Durante el desarrollo se presentaron distintos desafíos:

* Migrar la lógica de consola a una interfaz gráfica.
* Implementar correctamente la arquitectura MVC + DAO.
* Mantener la separación de responsabilidades entre capas.
* Persistir la información sin utilizar bases de datos.
* Sincronizar la actualización de JTable y JList en tiempo real.
* Diseñar una interfaz intuitiva y funcional.

---

## Uso de Inteligencia Artificial

Se utilizó inteligencia artificial como herramienta de apoyo para:

* Resolución de dudas técnicas.
* Comprensión de la arquitectura MVC + DAO.
* Implementación de componentes Swing.
* Diseño de la interfaz.
* Optimización y depuración del código.
* Generación de documentación.

La implementación final, adaptación, integración y validación del código fue realizada manualmente por las integrantes del proyecto.
