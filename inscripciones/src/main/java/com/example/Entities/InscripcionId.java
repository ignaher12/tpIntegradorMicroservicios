package com.example.Entities;
import jakarta.persistence.Embeddable;

@Embeddable
public class InscripcionId {
    private long estudiante;
    private int carrera;

    public InscripcionId() {}

    public InscripcionId(long estudiante, int carrera) {
        this.estudiante = estudiante;
        this.carrera = carrera;
    }

    // Getters and Setters
    public long getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(long estudiante) {
        this.estudiante = estudiante;
    }

    public long getCarrera() {
        return carrera;
    }

    public void setCarrera(int carrera) {
        this.carrera = carrera;
    }

    public boolean equals(Object objetoPasado) {
        if (this == objetoPasado) return true;
        if (objetoPasado == null || getClass() !=objetoPasado.getClass()) return false;
        
        InscripcionId objeto = (InscripcionId) objetoPasado;
        return estudiante == objeto.estudiante && carrera == objeto.carrera;
    }
}
