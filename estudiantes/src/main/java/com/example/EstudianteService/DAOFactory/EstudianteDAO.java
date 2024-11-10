package com.example.EstudianteService.DAOFactory;

import com.example.EstudianteService.Entities.Estudiante;
import com.example.EstudianteService.SearchStrategy.EstudianteSearchStrategy;
import com.example.EstudianteService.SortStrategy.EstudianteSortStrategy;

import java.util.List;

public interface EstudianteDAO {
    public void addEstudiante(Estudiante estudiante);
    public void deleteEstudiante(long libretaUniversitaria);
    public void updateEstudiante(Estudiante estudiante);
    public List<Estudiante> getAllEstudiantes();
    public List<Estudiante> getAllEstudiantes(EstudianteSortStrategy orden);
    public Estudiante getEstudianteByLibreta(long libretaUniversitaria);
    public Estudiante getEstudianteByNumeroDocumento(long numeroDeDocumento);
    public List<Estudiante> findEstudiantes(EstudianteSearchStrategy strategy);
    public List<Estudiante> findEstudiantes(EstudianteSearchStrategy strategy, EstudianteSearchStrategy strategy2);
}