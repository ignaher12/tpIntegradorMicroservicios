package com.example.SortStrategy;

public class InscripcionSortStrategy {
    
    InscripcionCriterio criterio;

    public InscripcionSortStrategy(InscripcionCriterio criterio){
        this.criterio = criterio;
    }

    public String getOrden(String alias){
        return "ORDER BY " + alias + "." + criterio.getCriterio() + " ASC";
    }


    public enum InscripcionCriterio {
        GRADUADO("graduado"),
        FECHA_INSCRIPCION("fecha_inscripcion");
    
        private final String criterio;
    
        InscripcionCriterio(String criterio) {
            this.criterio = criterio;
        }
    
        public String getCriterio() {
            return criterio;
        }
    }
}

