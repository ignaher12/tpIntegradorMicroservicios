package com.example.EstudianteService;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.DAOFactory.DAOFactory;
import com.example.DAOFactory.EstudianteDAO;
import com.example.Entities.Estudiante;
import com.example.SearchStrategy.EstudianteSearchByCarrera;
import com.example.SearchStrategy.EstudianteSearchByCiudad;
import com.example.SearchStrategy.EstudianteSearchByGenero;
import com.example.SortStrategy.EstudianteSortStrategy;
import com.example.SortStrategy.EstudianteSortStrategy.EstudianteCriterio;

@Service
public class EstudianteService{

	private static DAOFactory jpaDAOFactory;
	private static EstudianteDAO estudianteDAO;

    private RestTemplate restTemplate = new RestTemplate();
	private static final String INSCRIPCIONES_SERVICE_URL = "http://inscripciones:4005/";

	public EstudianteService(){
		jpaDAOFactory = DAOFactory.getDAOFactory(1);
		jpaDAOFactory.createConnection("UnidadDePersistencia");
		estudianteDAO = jpaDAOFactory.getEstudianteDAO();
	}

	public Estudiante agregarEstudiante(Estudiante estudiante){
		try {
			return estudianteDAO.addEstudiante(estudiante);
		} catch (Exception e) {
			return null;
		}
	}
	
	public List<Estudiante> recuperarEstudiantes(){
		return estudianteDAO.getAllEstudiantes();
	}

	public Estudiante actualizarEstudiante(Estudiante estudiante){
		return estudianteDAO.updateEstudiante(estudiante);
	}

	public Boolean eliminarEstudiante(long libreta){
		try{
			estudianteDAO.deleteEstudiante(libreta);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	public Estudiante recuperarEstudianteLibreta(long libreta){
		return estudianteDAO.getEstudianteByLibreta(libreta);
	}

	public Estudiante recuperarEstudianteDocumento(long documento){
		return estudianteDAO.getEstudianteByNumeroDocumento(documento);
	}

	public List<Estudiante> recuperarEstudianteOrdenado(EstudianteCriterio orden){
		return estudianteDAO.getAllEstudiantes(new EstudianteSortStrategy(orden));
	}

	public List<Estudiante> recuperarEstudianteFiltradoGM(){
		EstudianteSearchByGenero strategy = new EstudianteSearchByGenero();
		strategy.setGeneroMasculino();
		return estudianteDAO.findEstudiantes(strategy);
	}

	public List<Estudiante> recuperarEstudianteFiltradoGF(){
		EstudianteSearchByGenero strategy = new EstudianteSearchByGenero();
		strategy.setGeneroFemenino();
		return estudianteDAO.findEstudiantes(strategy);
	}

	public List<Estudiante> recuperarEstudianteFiltradoCC(String ciudad, int carrera){
		ResponseEntity<List<Long>> carrerasResponse = restTemplate.exchange(
				INSCRIPCIONES_SERVICE_URL + "/obtener/inscripciones?carreraId=" + carrera,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Long>>() {}
		);
		if (carrerasResponse.getStatusCode() == HttpStatus.OK) {
			List<Long> carreras = carrerasResponse.getBody();
			if (carreras != null) {
				EstudianteSearchByCarrera strategy1 = new EstudianteSearchByCarrera(carreras);
				EstudianteSearchByCiudad strategy2 = new EstudianteSearchByCiudad(ciudad);
				return estudianteDAO.findEstudiantes(strategy1, strategy2);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	
	

	
}
