package com.example.EstudianteController;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Entities.Estudiante;
import com.example.EstudianteService.EstudianteService;

import com.example.SortStrategy.EstudianteSortStrategy.EstudianteCriterio;


@SpringBootApplication
@RestController
public class EstudianteController {

    private static EstudianteService estudianteService = new EstudianteService();

	public static void main(String[] args) {

		SpringApplication.run(EstudianteController.class, args);

	}
	@GetMapping("/")
    public String hello() {
      return String.format("Servicio activo");
    }

	@PostMapping("/estudiante")
	public Estudiante postEstudiante(@RequestBody Estudiante estudiante){
		return estudianteService.agregarEstudiante(estudiante);
	}
	
	@GetMapping("/estudiante")
	public List<Estudiante> getAllEstudiantes(){
		return estudianteService.recuperarEstudiantes();
	}

	@PutMapping("/estudiante")
	public Estudiante updateEstudiante(@RequestBody Estudiante estudiante){
		return estudianteService.actualizarEstudiante(estudiante);
	}

	@DeleteMapping("/estudiante")
	public Boolean deleteEstudiante(@RequestParam(value = "libreta", defaultValue = "null") long libreta){
		return estudianteService.eliminarEstudiante(libreta);
	}

	@GetMapping("/estudiante/libreta")
	public Estudiante getEstudianteLibreta(@RequestParam(value = "libreta", defaultValue = "0" ) long libreta){
		return estudianteService.recuperarEstudianteLibreta(libreta);
	}

	@GetMapping("/estudiante/documento")
	public Estudiante getEstudianteDocumento(@RequestParam(value = "documento", defaultValue = "0" ) long documento){
		return estudianteService.recuperarEstudianteDocumento(documento);
	}

	@GetMapping("/estudiante/orden")
	public List<Estudiante> getEstudianteOrdenado(@RequestParam(value = "orden", defaultValue = "" ) EstudianteCriterio orden){
		return estudianteService.recuperarEstudianteOrdenado(orden);
	}

	@GetMapping("/estudiante/masculinos")
	public List<Estudiante> getEstudianteFiltradoGM(){
		return estudianteService.recuperarEstudianteFiltradoGM();
	}

	@GetMapping("/estudiante/femeninos")
	public List<Estudiante> getEstudianteFiltradoGF(){
		return estudianteService.recuperarEstudianteFiltradoGF();

	}

	@GetMapping("estudiante/ciudad_carrera")
	public List<Estudiante> getEstudianteFiltradoCC(@RequestParam(value = "ciudad", defaultValue = "" ) String ciudad, @RequestParam(value = "carrera", defaultValue = "" ) int carrera){
		return estudianteService.recuperarEstudianteFiltradoCC(ciudad, carrera);
	}

	
	

	
}
