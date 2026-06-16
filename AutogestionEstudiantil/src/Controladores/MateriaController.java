package Controladores;

import autogestionestudiantil.DAO.MateriaDAO;
import autogestionestudiantil.Modelos.Materia;
import java.util.ArrayList;

public class MateriaController {

    private MateriaDAO dao;
    private ArrayList<Materia> materias;

    public MateriaController() {
        dao = new MateriaDAO();
        materias = dao.cargarMaterias();
    }

    // INSCRIBIR MATERIA
    public boolean agregarMateria(String nombre,
            String codigo,
            int cuatrimestre,
            int anio) {

        // Código único
        if (buscarPorCodigo(codigo) != null) {
            return false;
        }

        // Código entre 3 y 10 caracteres
        if (codigo.length() < 3 || codigo.length() > 10) {
            return false;
        }

        // Cuatrimestre válido
        if (cuatrimestre != 1 && cuatrimestre != 2) {
            return false;
        }

        Materia nueva = new Materia(
                nombre,
                codigo,
                cuatrimestre,
                anio
        );

        materias.add(nueva);

        dao.guardarMaterias(materias);

        return true;
    }

    // BUSCAR POR CÓDIGO
    public Materia buscarPorCodigo(String codigo) {

        for (Materia materia : materias) {

            if (materia.getCodigo() != null
                    && materia.getCodigo().equalsIgnoreCase(codigo)) {

                return materia;
            }
        }

        return null;
    }

    // BUSCAR POR NOMBRE
    public ArrayList<Materia> buscarPorNombre(String nombre) {

        ArrayList<Materia> resultado = new ArrayList<>();

        for (Materia materia : materias) {

            if (materia.getNombre() != null
                    && materia.getNombre()
                            .toLowerCase()
                            .contains(nombre.toLowerCase())) {

                resultado.add(materia);
            }
        }

        return resultado;
    }

    // DAR DE BAJA
    public boolean darDeBajaMateria(String codigo) {

        Materia materia = buscarPorCodigo(codigo); //Por esto se deja el metodo buscar por codigo aca aunque ya este en el InscripcionesController

        if (materia != null) {

            materias.remove(materia);

            dao.guardarMaterias(materias);

            return true;
        }

        return false;
    }

    // LISTAR
    public ArrayList<Materia> listarMaterias() {

        return new ArrayList<>(materias);
    }
    
    
}
 