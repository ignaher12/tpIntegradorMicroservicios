package com.example.DAOFactory;


import com.example.Entities.Inscripcion;
import com.example.SearchStrategy.InscripcionSearchStrategy;
import com.example.SortStrategy.InscripcionSortStrategy;

import java.util.List;

public interface InscripcionDAO {
    public abstract void addInscripcion(Inscripcion inscripcion);
    public void deleteInscripcion(long libretaUniversitaria, int carreraId);
    public void updateInscripcion(Inscripcion inscripcion);
    public Inscripcion getInscripcion(long libretaUniversitaria, int carreraId);
    public List<Inscripcion> getInscripcionByFilterOrdenadas(InscripcionSearchStrategy strategy1, InscripcionSortStrategy strategy2);
}
