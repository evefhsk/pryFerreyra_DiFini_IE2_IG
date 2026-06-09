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

    // Buscar materia por código
    public Materia buscarPorCodigo(String codigo) {

        for (Materia materia : materias) {

            if (materia.getCodigo() != null
                    && materia.getCodigo().equalsIgnoreCase(codigo)) {

                return materia;
            }
        }

        return null;
    }

    // Buscar materias por nombre
    public ArrayList<Materia> buscarPorNombre(String nombre) {

        ArrayList<Materia> resultado = new ArrayList<>();

        for (Materia materia : materias) {

            if (materia.getNombre() != null
                    && materia.getNombre().toLowerCase()
                            .contains(nombre.toLowerCase())) {

                resultado.add(materia);
            }
        }

        return resultado;
    }

    // Dar de baja una materia
    public boolean darDeBajaMateria(String codigo) {

        Materia materia = buscarPorCodigo(codigo);

        if (materia != null) {

            materias.remove(materia);

            dao.guardarMaterias(materias);

            return true;
        }

        return false;
    }

    // Obtener todas las materias
    public ArrayList<Materia> listarMaterias() {

        return new ArrayList<>(materias);
    }

}
