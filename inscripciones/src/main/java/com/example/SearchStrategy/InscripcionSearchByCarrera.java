package com.example.SearchStrategy;

public class InscripcionSearchByCarrera implements InscripcionSearchStrategy {
    private int idCarrera;

    public InscripcionSearchByCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    @Override
    public String searchQuery() {
        return "i.carrera.carreraId = "+ idCarrera;
    }
}
