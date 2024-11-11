package com.example.JPADAO;

import com.example.DAOFactory.DAOFactory;
import com.example.DAOFactory.EstudianteDAO;

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
