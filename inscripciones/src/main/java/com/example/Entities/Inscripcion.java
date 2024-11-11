package com.example.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@IdClass(InscripcionId.class)
public class Inscripcion {
    
    @Id
    @ManyToOne
    @JoinColumn(name = "estudianteId", referencedColumnName = "libretaUniversitaria")
    private Estudiante estudiante;

    @Id
    @ManyToOne
    @JoinColumn(name = "carreraId", referencedColumnName = "carreraId")
    private Carrera carrera;

    @Column(nullable = false)
    private boolean graduado;

    @Column(nullable = false)
    private LocalDateTime fecha_inscripcion;

    public Inscripcion(){};
    public Inscripcion(Estudiante estudiante, Carrera carrera, boolean graduado, LocalDateTime fechaInscripcion) {
        this.estudiante = estudiante;
        this.carrera = carrera;
        this.graduado = graduado;
        this.fecha_inscripcion = fechaInscripcion;

    }
    public Inscripcion(Estudiante estudiante, Carrera carrera, boolean graduado) {
        this.estudiante = estudiante;
        this.carrera = carrera;
        this.graduado = graduado;
        this.fecha_inscripcion = LocalDateTime.now();

    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public boolean isGraduado() {
        return graduado;
    }

    public LocalDateTime getFecha_inscripcion() {
        return fecha_inscripcion;
    }

    public String toString(){
        return "[ Estudiante: " +  estudiante.getNumeroDeDocumento() + " - Carrera: " + carrera.getNombreCarrera() + " - Graduado: " + this.graduado  + " - Fecha: " + this.fecha_inscripcion +  " ]";
    }
}
