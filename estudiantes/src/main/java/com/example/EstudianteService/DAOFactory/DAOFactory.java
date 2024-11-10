package com.example.EstudianteService.DAOFactory;

import com.example.EstudianteService.JPADAO.JPADAOFactory;

public abstract class DAOFactory {
    public static final int MYSQL_JDBC = 1;
    
    public static JPADAOFactory jpaDAOFactory = null;

    public abstract void createConnection(String name);

    public abstract EstudianteDAO getEstudianteDAO();

    public static DAOFactory getDAOFactory(int numFactory) {
        switch (numFactory) {
            case MYSQL_JDBC:
                if (jpaDAOFactory == null){
                    jpaDAOFactory = new JPADAOFactory();
                }
                return jpaDAOFactory;
            default:
                return null;
        }
    }
}
