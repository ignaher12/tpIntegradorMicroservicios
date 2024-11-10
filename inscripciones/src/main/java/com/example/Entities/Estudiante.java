package com.example.Entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Estudiante {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long libretaUniversitaria;

    @Column(nullable = false, unique = true)
    private long numeroDeDocumento;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false)
    private int edad;

    @Column(nullable = false)
    private Genero genero;

    @Column
    private String ciudadResidencia;

    public static enum Genero {
        masculino,
        femenino
    }
    // Constructor sin argumentos. Necesario para Hybernate
    public Estudiante() {};

    public Estudiante(long numeroDeDocumento, String nombre, String apellido, int edad, String ciudadResidencia, String genero) {
        this.numeroDeDocumento = numeroDeDocumento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.ciudadResidencia = ciudadResidencia;
        if (genero.equals("masculino")){
            this.genero = Genero.masculino;
        }else if (genero.equals("femenino")){
            this.genero = Genero.femenino;
        }else{
            throw new Error("Error al ingresar usuario: genero invalido");
        }
    }
    public long getLibretaUniversitaria() {
        return libretaUniversitaria;
    }
    public long getNumeroDeDocumento() {
        return numeroDeDocumento;
    }
    public void setNumeroDeDocumento(long numeroDeDocumento) {
        this.numeroDeDocumento = numeroDeDocumento;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public Genero getGenero() {
        return this.genero;
    }
    public void setGenero(String genero) {
        if (genero.equals("masculino")){
            this.genero = Genero.masculino;
        }else if (genero.equals("femenino")){
            this.genero = Genero.femenino;
        }
    }
    public String getCiudadResidencia() {
        return ciudadResidencia;
    }
    public void setCiudadResidencia(String ciudadResidencia) {
        this.ciudadResidencia = ciudadResidencia;
    }
    public void setLibretaUniversitaria(long libretaUniversitaria) {
        this.libretaUniversitaria = libretaUniversitaria;
    }
    @Override
    public String toString() {
        return "Estudiante{" +
                "libretaUniversitaria=" + libretaUniversitaria +
                ", numeroDeDocumento=" + numeroDeDocumento +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad=" + edad +
                ", genero=" + genero +
                ", ciudadResidencia='" + ciudadResidencia + '\'' +
                '}';
    }
}
