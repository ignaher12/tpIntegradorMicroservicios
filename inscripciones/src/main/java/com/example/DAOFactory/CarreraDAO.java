package com.example.DAOFactory;

import java.util.List;

import com.example.Entities.Carrera;
import com.example.SortStrategy.CarreraSortStrategy;

public interface CarreraDAO {
    public void addCarrera(Carrera carrera);
    public void deleteCarrera(int carreraId);
    public void updateCarrera(Carrera carrera);
    public Carrera getCarrera(int carreraId);
    public List<Carrera> getCarreras();
    public List<Carrera> getCarrerasSorteadas(CarreraSortStrategy orden);
    public List<Carrera> getCarrerasConInscriptos();
}
