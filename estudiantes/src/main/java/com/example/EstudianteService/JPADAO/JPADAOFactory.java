package com.example.EstudianteService.JPADAO;

import com.example.EstudianteService.DAOFactory.DAOFactory;
import com.example.EstudianteService.DAOFactory.EstudianteDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPADAOFactory extends DAOFactory {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    
    @Override
    public void createConnection(String name){
        emf = Persistence.createEntityManagerFactory(name);
        em = emf.createEntityManager();
    }

    @Override
    public EstudianteDAO getEstudianteDAO() {
        return new JPAEstudianteDAO(em);
    }

}
