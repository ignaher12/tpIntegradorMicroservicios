package com.example.SearchStrategy;

public class EstudianteSearchByCiudad implements EstudianteSearchStrategy{
    private String ciudadResidencia;

    public EstudianteSearchByCiudad(String ciudadResidencia){
        this.ciudadResidencia = ciudadResidencia;
    }

    @Override
    public String buildSearchQuery(String alias) {
        return alias +".ciudadResidencia = '"+ ciudadResidencia.toLowerCase() + "'";
    }

    public String getCiudadDeResidencia() {
        return ciudadResidencia;
    }

    public void setCiudadDeResidencia(String ciudadDeResidencia) {
        this.ciudadResidencia = ciudadDeResidencia;
    }
}
