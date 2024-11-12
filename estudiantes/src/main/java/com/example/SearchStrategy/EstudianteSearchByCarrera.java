package com.example.SearchStrategy;
import java.util.List;
import java.util.stream.Collectors;

public class EstudianteSearchByCarrera implements EstudianteSearchStrategy{
    List<Long> carreras;

    public EstudianteSearchByCarrera(List<Long> carreras) {
        this.carreras = carreras;
    }

    @Override
    public String buildSearchQuery(String alias) {
        String carrerasString = carreras.stream()
            .map(String::valueOf)
            .collect(Collectors.joining(", "));
        return alias + ".libretaUniversitaria IN ("+ carrerasString + ")";
    }

    public List<Long> getCarreras() {
        return carreras;
    }

    public void setIdCarrera(List<Long> carreras) {
        this.carreras = carreras;
    }
}
