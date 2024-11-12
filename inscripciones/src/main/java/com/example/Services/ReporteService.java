package com.example.Services;

import com.example.DTO.ReporteCarrera;
import com.example.Entities.Carrera;
import com.example.Entities.Inscripcion;
import com.example.SearchStrategy.InscripcionSearchByCarrera;
import com.example.SortStrategy.CarreraSortStrategy;
import com.example.SortStrategy.InscripcionSortStrategy;
import com.example.SortStrategy.CarreraSortStrategy.CarreraCriterio;
import com.example.SortStrategy.InscripcionSortStrategy.InscripcionCriterio;

import java.util.ArrayList;
import java.util.List;

import com.example.DAOFactory.CarreraDAO;
import com.example.DAOFactory.InscripcionDAO;
import com.example.Entities.Estudiante;


public class ReporteService {
    private static List<ReporteCarrera> reportes;

    public static List<ReporteCarrera> generarReporte(CarreraDAO carreraDAO, InscripcionDAO inscripcionDAO) {
        reportes = new ArrayList<ReporteCarrera>();
        CarreraSortStrategy strategySortCarrera = new CarreraSortStrategy(CarreraCriterio.NOMBRE_CARRERA);

        //sort de carreras por nombre
        List<Carrera> carreras = carreraDAO.getCarrerasSorteadas(strategySortCarrera);

        InscripcionSortStrategy strategySortInscripcion = new InscripcionSortStrategy(InscripcionCriterio.FECHA_INSCRIPCION);

        for (Carrera c : carreras) {
            List<Estudiante> inscriptos = new ArrayList<Estudiante>();
            List<Estudiante> graduados = new ArrayList<Estudiante>();

            InscripcionSearchByCarrera strategySearchCarrera = new InscripcionSearchByCarrera(c.getCarreraId());
            ReporteCarrera reporte = new ReporteCarrera(c.getCarreraId());

            //agarra las inscripciones de c ordenadas por fecha de inscripcion.
            List<Inscripcion> inscripciones = inscripcionDAO.getInscripcionByFilterOrdenadas(strategySearchCarrera, strategySortInscripcion);
            if (!inscripciones.isEmpty()) {
                int indice = inscripciones.get(0).getFecha_inscripcion().getYear();

                for (Inscripcion inscripcion : inscripciones) {
                    if (indice == inscripcion.getFecha_inscripcion().getYear()) {
                        if (inscripcion.isGraduado()) {
                            graduados.add(inscripcion.getEstudiante());
                        } else {
                            inscriptos.add(inscripcion.getEstudiante());
                        }
                    } else {
                        reporte.addReporteAnual(indice, inscriptos, graduados);
                        indice = inscripcion.getFecha_inscripcion().getYear();
                        inscriptos.clear();
                        graduados.clear();
                        if (inscripcion.isGraduado()) {
                            graduados.add(inscripcion.getEstudiante());
                        } else {
                            inscriptos.add(inscripcion.getEstudiante());
                        }
                    }
                }
                reporte.addReporteAnual(indice, inscriptos, graduados);
                reportes.add(reporte);
            } else {
                // Manejo del caso en que la lista esté vacía
                System.out.println("No se encontraron inscripciones.");
            }
        }
            return reportes;
    }

    public void printReporte() {
        for (ReporteCarrera reporteCarrera : reportes) {
            System.out.println(reporteCarrera.printReporte());
        }
    }
}