package com.example.SortStrategy;

public class CarreraSortStrategy {
    CarreraCriterio criterio;

    public CarreraSortStrategy(CarreraCriterio criterio){
        this.criterio = criterio;
    }
    
    public String getOrden(String alias){
        return "ORDER BY " + alias + "." + criterio.getCriterio() + " ASC";
    }

    public enum CarreraCriterio {
        NOMBRE_CARRERA("nombreCarrera"),
        FECHA_CREACION("fechaCreacion");
    
        private final String criterio;
    
        CarreraCriterio(String criterio) {
            this.criterio = criterio;
        }
    
        public String getCriterio() {
            return criterio;
        }
    }
}
