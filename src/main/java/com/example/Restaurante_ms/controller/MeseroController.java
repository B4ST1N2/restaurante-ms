package com.example.Restaurante_ms.controller;


import com.example.Restaurante_ms.model.Mesero;
import com.example.Restaurante_ms.services.MeseroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mesero")
public class MeseroController {

    @Autowired
    private MeseroService meseroService;

    @PostMapping
    public Mesero agregarMesero(@RequestBody Mesero mesero){
        return meseroService.crearMesero(mesero);
    }

    @GetMapping("/all")
    public List<Mesero> listarMeseros(){
        return meseroService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Mesero obtenerMeseroPorId(@PathVariable Integer id){
        return meseroService.obtenerPorCodigo(id);
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Mesero> actualizarMesero(@RequestBody Mesero mesero) {
        Mesero meseroActualizado = meseroService.actualizarMesero(mesero.getCodigoEmpleado(), mesero);
        return ResponseEntity.ok(meseroActualizado);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id){
        meseroService.eliminarMesero(id);
    }


}
