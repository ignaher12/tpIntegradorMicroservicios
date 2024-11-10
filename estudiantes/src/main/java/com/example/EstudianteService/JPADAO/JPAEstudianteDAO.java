package com.example.EstudianteService.JPADAO;

import java.util.List;

import com.example.EstudianteService.DAOFactory.EstudianteDAO;
import com.example.EstudianteService.Entities.Estudiante;
import com.example.EstudianteService.SearchStrategy.EstudianteSearchStrategy;
import com.example.EstudianteService.SortStrategy.EstudianteSortStrategy;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class JPAEstudianteDAO implements EstudianteDAO {
    private EntityManager entityManager;

    public JPAEstudianteDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Dar de alta un estudiante
    public Estudiante addEstudiante(Estudiante estudiante) {
        try {
            estudiante.setCiudadResidencia(estudiante.getCiudadResidencia().toLowerCase());
            entityManager.getTransaction().begin();
            entityManager.persist(estudiante);
            entityManager.getTransaction().commit();
            return estudiante;
        } catch (Exception e) {
            System.out.println("Error al agregar estudiante:" + e);
            return null;
        }
    }

    // Dar de baja un estudiante
    public void deleteEstudiante(long libretaUniversitaria) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(getEstudianteByLibreta(libretaUniversitaria));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al eliminar estudiante:" + e);
        }
    }

    public Estudiante updateEstudiante(Estudiante estudiante) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(estudiante);
            entityManager.getTransaction().commit();
            return estudiante;
        } catch (Exception e) {
            System.out.println("Error al eliminar estudiante:" + e);
            return null;
        }
    }

    public List<Estudiante> getAllEstudiantes() {
        String jpql = "SELECT e FROM Estudiante e ORDER BY e.nombre ASC";
        TypedQuery<Estudiante> aux = entityManager.createQuery(jpql, Estudiante.class);
        return aux.getResultList();
    }
    public List<Estudiante> getAllEstudiantes(EstudianteSortStrategy orden) {
        String alias = "e";
        String jpql = "SELECT " + alias + " FROM Estudiante " + alias + " " + orden.getOrden(alias);
        TypedQuery<Estudiante> aux = entityManager.createQuery(jpql, Estudiante.class);
        return aux.getResultList();
    }

    public Estudiante getEstudianteByLibreta(long libretaUniversitaria) { //recuperar un estudiante, en base a su número de libreta universitaria.
        return entityManager.find(Estudiante.class, libretaUniversitaria);
    }


    public Estudiante getEstudianteByNumeroDocumento(long numeroDeDocumento) {
        return entityManager.find(Estudiante.class, numeroDeDocumento);
    }

    public List<Estudiante> findEstudiantes(EstudianteSearchStrategy busqueda) {
        String alias = "e";
        String jpql= "SELECT " + alias + " FROM Estudiante " + alias + " WHERE " + busqueda.buildSearchQuery(alias);

        TypedQuery<Estudiante> query = entityManager.createQuery(jpql, Estudiante.class);

        return query.getResultList();
    }

    public List<Estudiante> findEstudiantes(EstudianteSearchStrategy strategy1, EstudianteSearchStrategy strategy2){
        String alias = "e";
        String jpql = "SELECT "+ alias +" FROM Estudiante "+ alias +" " +
                "WHERE "+ strategy1.buildSearchQuery(alias) +
                " AND " + strategy2.buildSearchQuery(alias);

        TypedQuery<Estudiante> query = entityManager.createQuery(jpql, Estudiante.class);

        return query.getResultList();
    }
}




