package com.example.Restaurante_ms.controller;

import com.example.Restaurante_ms.model.Cliente;
import com.example.Restaurante_ms.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public Cliente agregarCliente(@RequestBody Cliente cliente) {
        return clienteService.saveCliente(cliente);
    }

    @GetMapping("/all")
    public List<Cliente> listarClientes() {
        return clienteService.obtenerClientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerClientePorId(@PathVariable Integer id) {
        return clienteService.obtenerClientePorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/actualizar")
    public ResponseEntity<Cliente> actualizarCliente(@RequestBody Cliente cliente) {
        Cliente clienteActualizado = clienteService.actualizarCliente(cliente.getId(), cliente);
        return ResponseEntity.ok(clienteActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Integer id) {
        clienteService.elimiarCliente(id);
        return ResponseEntity.noContent().build();
    }
}