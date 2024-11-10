package com.example.JPADAO;
import java.util.List;

import com.example.DAOFactory.CarreraDAO;
import com.example.Entities.Carrera;

import com.example.SortStrategy.CarreraSortStrategy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class JPACarreraDAO implements CarreraDAO {
    private EntityManager entityManager;
    
    public JPACarreraDAO(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    
    @Override
    public void addCarrera(Carrera carrera) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(carrera);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al agregar carrera:" + e);
        }
    }
    
    @Override
    public void deleteCarrera(int carreraId) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(getCarrera(carreraId));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al eliminar carrera:" + e);
        }
    }
    
    @Override
    public void updateCarrera(Carrera carrera) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(carrera);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al actualizar carrera:" + e);
        }
    }

    public Carrera getCarrera(int carreraId){
        return entityManager.find(Carrera.class, carreraId);
    }

    public List<Carrera> getCarreras(){
        String query = "SELECT c FROM Carrera c";
        TypedQuery<Carrera> aux = entityManager.createQuery(query, Carrera.class);
        return aux.getResultList();
    }

    public List<Carrera> getCarrerasSorteadas(CarreraSortStrategy orden) {
        String jpql = "SELECT c FROM Carrera c " + orden.getOrden("c");
        TypedQuery<Carrera> aux = entityManager.createQuery(jpql, Carrera.class);
        return aux.getResultList();
    }

    public List<Carrera> getCarrerasConInscriptos() { //recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos
        String jpql = "SELECT c " +
                      "FROM Inscripcion i JOIN i.carrera c " +
                      "GROUP BY c " +
                      "ORDER BY COUNT(i) DESC"; //ordena por cantidad de inscriptos
        TypedQuery<Carrera> query = entityManager.createQuery(jpql, Carrera.class);
        return query.getResultList();
    }


}
