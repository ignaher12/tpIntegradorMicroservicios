package com.example.JPADAO;

import com.example.DAOFactory.InscripcionDAO;
import com.example.Entities.Estudiante;
import com.example.Entities.Inscripcion;
import com.example.Entities.InscripcionId;
import com.example.SearchStrategy.InscripcionSearchStrategy;
import com.example.SortStrategy.InscripcionSortStrategy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;


@Transactional
public class JPAInscripcionDAO implements InscripcionDAO{
    private EntityManager entityManager;

    public JPAInscripcionDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void addInscripcion(Inscripcion inscripcion) {
        try {
            // Merge the detached Estudiante entity
            Estudiante estudiante = inscripcion.getEstudiante();
            if (estudiante.getLibretaUniversitaria() > 0) {
                entityManager.merge(estudiante);  // Merges the Estudiante if it exists
            }
            entityManager.getTransaction().begin();
            entityManager.persist(inscripcion);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al agregar inscripcion:" + e);
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();  // Rollback the transaction if there's an error
            }
        }
    }
    @Override
    public void deleteInscripcion(long libretaUniversitaria, int carreraId) {
        try {
            entityManager.getTransaction().begin();
    
            String jpql = "DELETE FROM Inscripcion i WHERE i.estudiante.libretaUniversitaria = :libretaUniversitaria AND i.carrera.carreraId = :carreraId";
            int rowsAffected = entityManager.createQuery(jpql)
                .setParameter("libretaUniversitaria", libretaUniversitaria)
                .setParameter("carreraId", carreraId)
                .executeUpdate();
    
            if (rowsAffected > 0) {
                entityManager.getTransaction().commit();
            } else {
                throw new EntityNotFoundException("Inscripción no encontrada para los parámetros proporcionados.");
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar inscripción: " + e);
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();  // Rollback the transaction if there's an error
            }
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
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();  // Rollback the transaction if there's an error
            }
        }
    }

    @Override
    public Inscripcion getInscripcion(long libretaUniversitaria, int carreraId) {
        return entityManager.find(Inscripcion.class, new InscripcionId(libretaUniversitaria, carreraId));
    }
    
    @Override
    public List<Long> getInscripcionById(int carreraId){
        String jpql = "SELECT i.estudiante.libretaUniversitaria FROM Inscripcion i WHERE i.carrera.carreraId = :carreraId";
        return entityManager.createQuery(jpql, Long.class)
                .setParameter("carreraId", carreraId)
                .getResultList();
    }
    
    @Override
    public List<Inscripcion> getInscripcionByFilterOrdenadas(InscripcionSearchStrategy strategy1, InscripcionSortStrategy strategy2){
        String jpql = "SELECT i FROM Inscripcion i " +
                "WHERE "+ strategy1.searchQuery() + " " +
                strategy2.getOrden("i");

        TypedQuery<Inscripcion> query = entityManager.createQuery(jpql, Inscripcion.class);

        return query.getResultList();
    }   
}
