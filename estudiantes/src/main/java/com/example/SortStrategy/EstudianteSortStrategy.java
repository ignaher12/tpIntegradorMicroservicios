package com.example.SortStrategy;

public class EstudianteSortStrategy {
    EstudianteCriterio criterio;

    public EstudianteSortStrategy(EstudianteCriterio criterio){
        this.criterio = criterio;
    }
    
    public String getOrden(String alias){
        return "ORDER BY " + alias + "." + criterio.getCriterio() + " ASC";
    }

    public enum EstudianteCriterio {
        NOMBRE("nombre"),
        APELLIDO("apellido"),
        EDAD("edad"),
        GENERO("genero"),
        CIUDAD_RESIDENCIA("ciudadResidencia"),
        NUMERO_DE_DOCUMENTO("numeroDeDocumento");
    
        private final String criterio;
    
        EstudianteCriterio(String criterio) {
            this.criterio = criterio;
        }
    
        public String getCriterio() {
            return criterio;
        }
    }
}
