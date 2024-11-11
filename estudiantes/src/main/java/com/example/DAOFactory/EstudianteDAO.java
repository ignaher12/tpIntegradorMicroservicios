package com.example.DAOFactory;

import com.example.Entities.Estudiante;
import com.example.SearchStrategy.EstudianteSearchStrategy;
import com.example.SortStrategy.EstudianteSortStrategy;

import java.util.List;

public interface EstudianteDAO {
    public Estudiante addEstudiante(Estudiante estudiante);
    public void deleteEstudiante(long libretaUniversitaria);
    public Estudiante updateEstudiante(Estudiante estudiante);
    public List<Estudiante> getAllEstudiantes();
    public List<Estudiante> getAllEstudiantes(EstudianteSortStrategy orden);
    public Estudiante getEstudianteByLibreta(long libretaUniversitaria);
    public Estudiante getEstudianteByNumeroDocumento(long numeroDeDocumento);
    public List<Estudiante> findEstudiantes(EstudianteSearchStrategy strategy);
    public List<Estudiante> findEstudiantes(EstudianteSearchStrategy strategy, EstudianteSearchStrategy strategy2);
}
