package com.example.InscripcionService;
import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.DAOFactory.CarreraDAO;
import com.example.DAOFactory.DAOFactory;
import com.example.DAOFactory.InscripcionDAO;
import com.example.DTO.ReporteCarrera;
import com.example.Entities.Carrera;
import com.example.Entities.Estudiante;
import com.example.Entities.Inscripcion;
import com.example.SearchStrategy.InscripcionSearchByCarrera;
import com.example.SearchStrategy.InscripcionSearchStrategy;
import com.example.Services.ReporteService;
import com.example.SortStrategy.InscripcionSortStrategy;

@SpringBootApplication
@RestController
public class InscripcionService{
	private static DAOFactory jpaDAOFactory = DAOFactory.getDAOFactory(1);
	private static InscripcionDAO inscripcionDAO;
    private static CarreraDAO carreraDAO;
    private RestTemplate restTemplate = new RestTemplate();
	private static final String ESTUDIANTES_SERVICE_URL = "http://estudiantes:4004/";

	public InscripcionService(){
		jpaDAOFactory.createConnection("UnidadDePersistencia");
		inscripcionDAO = jpaDAOFactory.getInscripcionDAO();
		carreraDAO= jpaDAOFactory.getCarreraDAO();
	}
	
    public String inscribirEstudianteEnCarrera(Long estudianteId, int carreraId) {
        
		Carrera carrera = carreraDAO.getCarrera(carreraId);		//SE DEBERIA HACER UN LLAMADO AL MICROSERVICIO DE CARRERA
		if (carrera == null) {
            return "Carrera no encontrado";
        }

		// Hacer la solicitud GET al microservicio de Estudiantes
		ResponseEntity<Estudiante> estudiante = restTemplate.getForEntity(ESTUDIANTES_SERVICE_URL + "/estudiante/libreta?libreta=" + estudianteId, Estudiante.class);
        if (estudiante.getBody() == null) {
            return "Estudiante no encontrado";
        }else{
			System.out.println(estudiante.getBody());
		}
		Inscripcion inscripcion = new Inscripcion(estudiante.getBody(), carrera, false);
		inscripcionDAO.addInscripcion(inscripcion);

        return "Estudiante inscripto en la carrera " + inscripcion;
    }
	// Endpoint para eliminar una inscripción
    public String eliminarInscripcion(Long libretaUniversitaria, int carreraId) {
        try {
			inscripcionDAO.deleteInscripcion(libretaUniversitaria, carreraId);
			return "Inscripción eliminada para el estudiante con libreta universitaria " + libretaUniversitaria + " en la carrera " + carreraId;
		} catch (Exception e) {
			return "error" + e;
		}
	}

    // Endpoint para actualizar una inscripción
    public String actualizarInscripcion(Inscripcion inscripcion) {
        try {
			inscripcionDAO.updateInscripcion(inscripcion);
        	return "Inscripción actualizada: " + inscripcion;
		} catch (Exception e) {
			return "error" + e;
		}
    }

    // Endpoint para obtener una inscripción específica
    public Inscripcion obtenerInscripcion(long libretaUniversitaria, int carreraId) {
        try{
			return inscripcionDAO.getInscripcion(libretaUniversitaria, carreraId);
		} catch (Exception e) {
			return null;
		}
    }

	public List<Long> obtenerInscripcionesByCarrera(int carreraId){
		try{
			List<Long> aux = inscripcionDAO.getInscripcionById(carreraId);
			return aux;

		} catch (Exception e){
			return null;
		}
	}

	// Endpoint para obtener inscripciones con filtros y orden
    public String obtenerInscripcionesConFiltroOrdenadas(int filtro, String orden) {
		if ((filtro != -1) && (orden != " ")){
			try {
				// Crear instancia de InscripcionSearchStrategy basada en el filtro recibido
				InscripcionSearchStrategy searchStrategy = new InscripcionSearchByCarrera(filtro);
				// Crear instancia de InscripcionSortStrategy basada en el orden recibido
				InscripcionSortStrategy sortStrategy = new InscripcionSortStrategy(InscripcionSortStrategy.InscripcionCriterio.valueOf(orden.toUpperCase()));
				
				List<Inscripcion> inscripciones = inscripcionDAO.getInscripcionByFilterOrdenadas(searchStrategy, sortStrategy);
				StringBuilder ret = new StringBuilder();
				for (Inscripcion inscripcion : inscripciones) {
					ret.append(inscripcion).append("\n");
				}
				return ret.toString();
			} catch (Exception e) {
				return "Error con el filtro y/o el ordenamiento";
			}
		}
		return "Faltaron los filtro y/o ordenamiento";
    }

	public String getReporte(){
		List<ReporteCarrera> reportes = ReporteService.generarReporte(carreraDAO, inscripcionDAO);
		
		StringBuilder ret = new StringBuilder();
		for (ReporteCarrera reporteCarrera : reportes) {
			ret.append(reporteCarrera.printReporte()).append("\n");
        }
		return ret.toString();
	}
}
