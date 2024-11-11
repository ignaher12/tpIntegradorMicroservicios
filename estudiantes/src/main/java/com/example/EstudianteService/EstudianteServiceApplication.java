package com.example.EstudianteService;
import java.util.List;

import org.aspectj.lang.annotation.RequiredTypes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.PutExchange;

import com.example.DAOFactory.DAOFactory;
import com.example.DAOFactory.EstudianteDAO;
import com.example.Entities.Estudiante;
import com.example.SearchStrategy.EstudianteSearchByCarrera;
import com.example.SearchStrategy.EstudianteSearchByCiudad;
import com.example.SearchStrategy.EstudianteSearchByGenero;
import com.example.SearchStrategy.EstudianteSearchStrategy;
import com.example.SortStrategy.EstudianteSortStrategy;
import com.example.SortStrategy.EstudianteSortStrategy.EstudianteCriterio;


@SpringBootApplication
@RestController
public class EstudianteServiceApplication {

	private static DAOFactory jpaDAOFactory;
	private static EstudianteDAO estudianteDAO;

	public static void main(String[] args) {
		jpaDAOFactory = DAOFactory.getDAOFactory(1);
		jpaDAOFactory.createConnection("UnidadDePersistencia");
		estudianteDAO = jpaDAOFactory.getEstudianteDAO();
		SpringApplication.run(EstudianteServiceApplication.class, args);

	}
	@GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
      return String.format("Hello %s!", name);
    }

	@PostMapping("/estudiante")
	public Estudiante agregarEstudiante(@RequestBody Estudiante estudiante){
		return estudianteDAO.addEstudiante(estudiante);
	}
	
	@GetMapping("/estudiante")
	public List<Estudiante> recuperarEstudiantes(){
		return estudianteDAO.getAllEstudiantes();
	}

	@PutMapping("/estudiante")
	public Estudiante actualizarEstudiante(@RequestBody Estudiante estudiante){
		return estudianteDAO.updateEstudiante(estudiante);
	}

	@DeleteMapping("/estudiante")
	public Boolean eliminarEstudiante(@RequestParam(value = "libreta", defaultValue = "null") long libreta){
		try{
			estudianteDAO.deleteEstudiante(libreta);
			return true;
		}catch(Exception e){
			return false;
		}
	}

	@GetMapping("/estudiante/libreta")
	public Estudiante recuperarEstudianteLibreta(@RequestParam(value = "libreta", defaultValue = "0" ) long libreta){
		return estudianteDAO.getEstudianteByLibreta(libreta);
	}

	@GetMapping("/estudiante/documento")
	public Estudiante recuperarEstudianteDocumento(@RequestParam(value = "documento", defaultValue = "0" ) long documento){
		return estudianteDAO.getEstudianteByNumeroDocumento(documento);
	}

	@GetMapping("/estudiante/orden")
	public List<Estudiante> recuperarEstudianteOrdenado(@RequestParam(value = "orden", defaultValue = "" ) EstudianteCriterio orden){
		return estudianteDAO.getAllEstudiantes(new EstudianteSortStrategy(orden));
	}

	@GetMapping("/estudiante/masculinos")
	public List<Estudiante> recuperarEstudianteFiltradoGM(){
		EstudianteSearchByGenero strategy = new EstudianteSearchByGenero();
		strategy.setGeneroMasculino();
		return estudianteDAO.findEstudiantes(strategy);
	}

	@GetMapping("/estudiante/femeninos")
	public List<Estudiante> recuperarEstudianteFiltradoGF(){
		EstudianteSearchByGenero strategy = new EstudianteSearchByGenero();
		strategy.setGeneroFemenino();
		return estudianteDAO.findEstudiantes(strategy);
	}

	@GetMapping("estudiante/ciudad_carrera")
	public List<Estudiante> recuperarEstudianteFiltradoCC(@RequestParam(value = "ciudad", defaultValue = "" ) String ciudad, @RequestParam(value = "carrera", defaultValue = "" ) int carrera){
		EstudianteSearchByCarrera strategy1 = new EstudianteSearchByCarrera(carrera);
		EstudianteSearchByCiudad strategy2 = new EstudianteSearchByCiudad(ciudad);
		return estudianteDAO.findEstudiantes(strategy1, strategy2);
	}

	
	

	
}
