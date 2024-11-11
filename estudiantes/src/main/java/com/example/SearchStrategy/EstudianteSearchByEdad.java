package com.example.SearchStrategy;

public class EstudianteSearchByEdad implements EstudianteSearchStrategy{
    private int edad;

    public EstudianteSearchByEdad(int edad){
        this.edad = edad;
    }

    @Override
    public String buildSearchQuery(String alias) {
        return alias +".edad ="+ edad;
    }

    public void setEdad(int edad){this.edad = edad;}
}
