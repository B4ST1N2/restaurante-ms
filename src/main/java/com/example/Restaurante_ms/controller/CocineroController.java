package com.example.Restaurante_ms.controller;

import com.example.Restaurante_ms.api.dto.CocineroDTO;
import com.example.Restaurante_ms.api.dto.CrearCocineroRequest;
import com.example.Restaurante_ms.model.Cocinero;
import com.example.Restaurante_ms.services.CocineroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/cocinero")
public class CocineroController {
    @Autowired
    private CocineroService cocineroService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CrearCocineroRequest> agregarCocinero(@RequestBody CrearCocineroRequest request) {
        CocineroDTO dto = request.getCocinero();

        Cocinero guardado = cocineroService.crearDesdeDTO(
                dto.getNombre(),
                dto.getSalario(),
                dto.getEspecialidad()
        );

        dto.setCodigoEmpleado(guardado.getCodigoEmpleado());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(guardado.getCodigoEmpleado())
                .toUri();

        return ResponseEntity.created(location).body(request);
    }

    @GetMapping("/all")
    public List<Cocinero> listarCocineros() {
        return cocineroService.listarCocineros();
    }

    @GetMapping("/{id}")
    public Cocinero obtenerCocineroPorId(@PathVariable Integer id) {
        return cocineroService.obtenerCocineroPorId(id);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Cocinero> actualizarCocinero(@RequestBody Cocinero cocinero) {
        Cocinero cocineroActualizado = cocineroService.actualizarCocinero(cocinero.getCodigoEmpleado(), cocinero);
        return ResponseEntity.ok(cocineroActualizado);
    }

    @DeleteMapping("/{id}")
    public void eliminarCocinero(@PathVariable Integer id) {
        cocineroService.eliminarCocinero(id);
    }
}
