package com.example.EstudianteService.SearchStrategy;

import com.example.EstudianteService.Entities.Estudiante;
import com.example.EstudianteService.Entities.Estudiante.Genero;

public class EstudianteSearchByGenero implements EstudianteSearchStrategy{
    private Estudiante.Genero genero; 

    public EstudianteSearchByGenero(){
        this.genero = Genero.masculino;
    }

    @Override
    public String buildSearchQuery(String alias) {
        return alias +".genero =" + genero;
    }

    public void setGeneroMasculino(){this.genero = Genero.masculino;};
    public void setGeneroFemenino(){this.genero = Genero.femenino;};
}
