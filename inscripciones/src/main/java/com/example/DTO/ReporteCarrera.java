package com.example.DTO;


import com.example.Entities.Estudiante;

import java.util.ArrayList;
import java.util.List;

public class ReporteCarrera {
    public class ReporteAnual {
        private int anio;
        private List<Estudiante> inscriptos;
        private List<Estudiante> graduados;

        public ReporteAnual(int anio, List<Estudiante> inscriptos, List<Estudiante> graduados) {
            this.anio = anio;
            this.inscriptos = inscriptos;
            this.graduados = graduados;
        }

        public int getAnio() {
            return anio;
        }

        public void setAnio(int anio) {
            this.anio = anio;
        }

        public List<Estudiante> getInscriptos() {
            return inscriptos;
        }

        public void setInscriptos(List<Estudiante> inscriptos) {
            this.inscriptos = inscriptos;
        }

        public List<Estudiante> getGraduados() {
            return graduados;
        }

        public void setGraduados(List<Estudiante> graduados) {
            this.graduados = graduados;
        }
        public String printReporteAnual() {
            StringBuilder ret = new StringBuilder();
            ret.append("Reporte Anual del Año: ").append(anio).append("\n");
            ret.append("Inscriptos:\n");

            // Verificar si hay estudiantes inscriptos
            if (inscriptos.isEmpty()) {
                ret.append("No hay inscriptos.\n");
            } else {
                for (Estudiante estudiante : inscriptos) {
                    ret.append(estudiante.toString())
                            .append("\n");
                }
            }

            ret.append("Graduados:\n");
            if (graduados.isEmpty()) {
                ret.append("No hay graduados.\n");
            } else {
                for (Estudiante estudiante : graduados) {
                    ret.append(estudiante.toString())
                            .append("\n");
                }
            }
            return ret.toString();
        }


    }

    private int idCarrera;
    private List<ReporteAnual> inscripciones;

    public ReporteCarrera(int idCarrera){
        this.idCarrera = idCarrera;
        this.inscripciones = new ArrayList<ReporteAnual>();
    }

    public void addReporteAnual(int anio, List<Estudiante> inscriptos, List<Estudiante> graduados) {
        this.inscripciones.add(new ReporteAnual(anio, inscriptos, graduados));
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public String printReporte() {
        StringBuilder ret = new StringBuilder();
        ret.append("Reporte de Carrera ID: ").append(idCarrera).append("\n");

        for (ReporteAnual reporteAnual : inscripciones) {
            ret.append(reporteAnual.printReporteAnual()).append("\n");
        }

        return ret.toString();
    }

}
