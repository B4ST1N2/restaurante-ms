package com.example.Restaurante_ms.services;

import com.example.Restaurante_ms.model.Cliente;
import com.example.Restaurante_ms.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente saveCliente(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public List<Cliente> obtenerClientes(){
        return clienteRepository.findAll();
    }

    public Optional<Cliente> obtenerClientePorId(Integer id){
        return clienteRepository.findById(id);
    }

    public Cliente actualizarCliente(Integer id, Cliente clienteDetalles) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id " + id));

        cliente.setNombre(clienteDetalles.getNombre());
        cliente.setCelular(clienteDetalles.getCelular());
        cliente.setCorreo(clienteDetalles.getCorreo());
        return clienteRepository.save(cliente);
    }

    public void elimiarCliente(Integer id){
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente no encontrado con id " + id);
        }
        clienteRepository.deleteById(id);
    }

}
