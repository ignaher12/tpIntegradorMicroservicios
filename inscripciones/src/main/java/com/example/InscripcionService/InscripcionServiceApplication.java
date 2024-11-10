package com.example.InscripcionService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
public class InscripcionServiceApplication {
	private static DAOFactory jpaDAOFactory = DAOFactory.getDAOFactory(1);
	private static InscripcionDAO inscripcionDAO;
    private static CarreraDAO carreraDAO;

	@Autowired
    private RestTemplate restTemplate;
	private static final String ESTUDIANTES_SERVICE_URL = "http://localhost:4000/";


	public static void main(String[] args) {
		inscripcionDAO = jpaDAOFactory.getInscripcionDAO();
        carreraDAO= jpaDAOFactory.getCarreraDAO();
		SpringApplication.run(InscripcionServiceApplication.class, args);
	}
	
	@PostMapping("/{estudianteId}/inscribir/{carreraId}")
    public String inscribirEstudianteEnCarrera(@PathVariable Long estudianteId, @PathVariable int carreraId) {
        // Hacer la solicitud GET al microservicio de Estudiantes
        Estudiante estudiante = restTemplate.getForObject(ESTUDIANTES_SERVICE_URL + estudianteId, Estudiante.class);
        
        if (estudiante == null) {
            return "Estudiante no encontrado";
        }

		Carrera carrera = carreraDAO.getCarrera(carreraId);		//SE DEBERIA HACER UN LLAMADO AL MICROSERVICIO DE CARRERA
		if (carrera == null) {
            return "Carrera no encontrado";
        }

		Inscripcion inscripcion = new Inscripcion(estudiante, carrera, false);
		inscripcionDAO.addInscripcion(inscripcion);

        return "Estudiante inscrito en la carrera " + inscripcion;
    }
	// Endpoint para eliminar una inscripción
    @DeleteMapping("/{libretaUniversitaria}/eliminar/{carreraId}")
    public String eliminarInscripcion(@PathVariable long libretaUniversitaria, @PathVariable int carreraId) {
        try {
			inscripcionDAO.deleteInscripcion(libretaUniversitaria, carreraId);
			return "Inscripción eliminada para el estudiante con libreta universitaria " + libretaUniversitaria + " en la carrera " + carreraId;
		} catch (Exception e) {
			return "error";
		}
	}

    // Endpoint para actualizar una inscripción
    @PutMapping("/actualizar")
    public String actualizarInscripcion(@RequestBody Inscripcion inscripcion) {
        try {
			inscripcionDAO.updateInscripcion(inscripcion);
        	return "Inscripción actualizada: " + inscripcion;
		} catch (Exception e) {
			return "error";
		}
    }

    // Endpoint para obtener una inscripción específica
    @GetMapping("/{libretaUniversitaria}/obtener/{carreraId}")
    public Inscripcion obtenerInscripcion(@PathVariable long libretaUniversitaria, @PathVariable int carreraId) {
        try{
			return inscripcionDAO.getInscripcion(libretaUniversitaria, carreraId);
		} catch (Exception e) {
			return null;
		}
    }

	// Endpoint para obtener inscripciones con filtros y orden
    @GetMapping("/buscar")
    public String obtenerInscripcionesConFiltroOrdenadas(@RequestParam(name = "filtro", defaultValue = "-1") int filtro, @RequestParam(name = "orden", defaultValue = " ") String orden) {
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

	@GetMapping("/reportes")
	public String getReporte(){
		List<ReporteCarrera> reportes = ReporteService.generarReporte(carreraDAO, inscripcionDAO);
		
		StringBuilder ret = new StringBuilder();
		for (ReporteCarrera reporteCarrera : reportes) {
			ret.append(reporteCarrera.printReporte()).append("\n");
        }
		return ret.toString();
	}
}
