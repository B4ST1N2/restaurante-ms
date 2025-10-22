package com.example.Restaurante_ms.controller;


import com.example.Restaurante_ms.model.Cocinero;
import com.example.Restaurante_ms.services.CocineroService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
;
import java.util.List;

@RestController
@RequestMapping("/cocinero")
public class CocineroController {
    @Autowired
    private CocineroService cocineroService;

    @PostMapping
    public Cocinero agregarCocinero(@RequestBody Cocinero cocinero) {
        return cocineroService.crearCocinero(cocinero);
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
