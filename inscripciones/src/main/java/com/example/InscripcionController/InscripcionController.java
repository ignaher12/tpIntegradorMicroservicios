package com.example.InscripcionController;
import java.util.List;

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

import com.example.Entities.Inscripcion;
import com.example.InscripcionService.InscripcionService;


@SpringBootApplication
@RestController
public class InscripcionController {
	private static InscripcionService inscripcionService = new InscripcionService();

	public static void main(String[] args) {
		SpringApplication.run(InscripcionController.class, args);
	}
	
	@PostMapping("/inscribir")
    public String postEstudianteEnCarrera(@RequestParam(value = "estudianteId", defaultValue = "" ) Long estudianteId, @RequestParam(value = "carreraId", defaultValue = "" ) int carreraId) {
        return inscripcionService.inscribirEstudianteEnCarrera(estudianteId, carreraId);
    }
	// Endpoint para eliminar una inscripción
    @DeleteMapping("/eliminar")
    public String deleteInscripcion(@RequestParam(value = "estudianteId", defaultValue = "" ) Long libretaUniversitaria, @RequestParam(value = "carreraId", defaultValue = "" ) int carreraId) {
		return inscripcionService.eliminarInscripcion(libretaUniversitaria, carreraId);
	}

    // Endpoint para actualizar una inscripción
    @PutMapping("/actualizar")
    public String updateInscripcion(@RequestBody Inscripcion inscripcion) {
		return inscripcionService.actualizarInscripcion(inscripcion);
    }

    // Endpoint para obtener una inscripción específica
    @GetMapping("/{libretaUniversitaria}/obtener/{carreraId}")
    public Inscripcion getInscripcion(@PathVariable long libretaUniversitaria, @PathVariable int carreraId) {
		return inscripcionService.obtenerInscripcion(libretaUniversitaria, carreraId);
    }

	@GetMapping("/obtener/inscripciones")
	public List<Long> getInscripcionesByCarrera(@RequestParam(name = "carreraId", defaultValue = "-1") int carreraId){
		return inscripcionService.obtenerInscripcionesByCarrera(carreraId);
	}	

	// Endpoint para obtener inscripciones con filtros y orden
    @GetMapping("/buscar")
    public String getInscripcionesConFiltroOrdenadas(@RequestParam(name = "filtro", defaultValue = "-1") int filtro, @RequestParam(name = "orden", defaultValue = " ") String orden) {
		return inscripcionService.obtenerInscripcionesConFiltroOrdenadas(filtro, orden);
    }

	@GetMapping("/reportes")
	public String getReporte(){
		return inscripcionService.getReporte();
	}
}
