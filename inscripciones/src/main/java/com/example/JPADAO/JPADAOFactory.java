package com.example.JPADAO;

import com.example.DAOFactory.CarreraDAO;
import com.example.DAOFactory.DAOFactory;
import com.example.DAOFactory.InscripcionDAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPADAOFactory extends DAOFactory {
    private static EntityManagerFactory emf;
    private static EntityManager em;
    
    @Override
    public CarreraDAO getCarreraDAO() {
        return new JPACarreraDAO(em);
    }
    
    @Override
    public void createConnection(String name){
        emf = Persistence.createEntityManagerFactory(name);
        em = emf.createEntityManager();
    }

    @Override
    public InscripcionDAO getInscripcionDAO() {
        return new JPAInscripcionDAO(em);
    }
}
