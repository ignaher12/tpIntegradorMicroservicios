package com.example.JPADAO;

import com.example.DAOFactory.InscripcionDAO;
import com.example.Entities.Inscripcion;
import com.example.Entities.InscripcionId;
import com.example.SearchStrategy.InscripcionSearchStrategy;
import com.example.SortStrategy.InscripcionSortStrategy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class JPAInscripcionDAO implements InscripcionDAO{
    private EntityManager entityManager;

    public JPAInscripcionDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void addInscripcion(Inscripcion inscripcion) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(inscripcion);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al agregar inscripcion:" + e);
        }
    }

    @Override
    public void deleteInscripcion(long libretaUniversitaria, int carreraId) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(getInscripcion(libretaUniversitaria, carreraId));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al eliminar inscripcion:" + e);
        }
    }

    @Override
    public void updateInscripcion(Inscripcion inscripcion) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(inscripcion);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al actualizar inscripcion:" + e);
        }
    }

    @Override
    public Inscripcion getInscripcion(long libretaUniversitaria, int carreraId) {
        return entityManager.find(Inscripcion.class, new InscripcionId(libretaUniversitaria, carreraId));
    }

    public List<Inscripcion> getInscripcionByFilterOrdenadas(InscripcionSearchStrategy strategy1, InscripcionSortStrategy strategy2){
        String jpql = "SELECT i FROM Inscripcion i " +
                "WHERE "+ strategy1.searchQuery() + " " +
                strategy2.getOrden("i");

        TypedQuery<Inscripcion> query = entityManager.createQuery(jpql, Inscripcion.class);

        return query.getResultList();
    }



    
}
