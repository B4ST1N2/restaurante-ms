package com.example.Restaurante_ms.services;

import com.example.Restaurante_ms.model.Cocinero;
import com.example.Restaurante_ms.repository.CocineroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CocineroService {

    @Autowired
    private CocineroRepository cocineroRepository;

    public Cocinero crearCocinero(Cocinero cocinero) {
        cocinero.setCodigoEmpleado(null); // aseguramos autogenerado
        return cocineroRepository.save(cocinero);
    }


    public Cocinero crearDesdeDTO(String nombre, double salario, String especialidad) {
        Cocinero c = new Cocinero();
        c.setNombre(nombre);
        c.setSalario(salario);
        c.setEspecialidad(especialidad);
        return cocineroRepository.save(c);
    }

    public List<Cocinero> listarCocineros() {
        return cocineroRepository.findAll();
    }

    public Cocinero obtenerCocineroPorId(Integer id) {
        Optional<Cocinero> cocinero = cocineroRepository.findById(id);
        if (cocinero.isEmpty()) {
            throw new RuntimeException("Cocinero con id " + id + " no encontrado");
        }
        return cocinero.get();
    }

    public Cocinero actualizarCocinero(Integer id, Cocinero cocineroActualizado) {
        Cocinero cocinero = cocineroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cocinero con id " + id + " no encontrado"));

        cocinero.setNombre(cocineroActualizado.getNombre());
        cocinero.setSalario(cocineroActualizado.getSalario());
        cocinero.setEspecialidad(cocineroActualizado.getEspecialidad());

        return cocineroRepository.save(cocinero);
    }

    public void eliminarCocinero(Integer id) {
        if (!cocineroRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Cocinero con id " + id + " no existe");
        }
        cocineroRepository.deleteById(id);
    }

}
