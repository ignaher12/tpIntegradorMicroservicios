package com.example.InscripcionService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.DAOFactory.CarreraDAO;
import com.example.DAOFactory.DAOFactory;
import com.example.DAOFactory.InscripcionDAO;
import com.example.DTO.ReporteCarrera;
import com.example.Entities.Carrera;
import com.example.Entities.Estudiante;
import com.example.Entities.Inscripcion;
import com.example.Services.ReporteService;

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
