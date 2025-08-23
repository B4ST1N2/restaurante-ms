package com.example.Restaurante_ms.services;

import com.example.Restaurante_ms.model.Mesero;
import com.example.Restaurante_ms.repository.MeseroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeseroService {

    @Autowired
    private MeseroRepository meseroRepository;

    public Mesero crearMesero(Mesero mesero) {
        if (meseroRepository.existsById(mesero.getCodigoEmpleado())) {
            throw new IllegalArgumentException("El mesero con código " + mesero.getCodigoEmpleado() + " ya existe.");
        }
        return meseroRepository.save(mesero);
    }

    public List<Mesero> obtenerTodos() {
        return meseroRepository.findAll();
    }

    public Mesero obtenerPorCodigo(Integer codigoEmpleado) {
        return meseroRepository.findById(codigoEmpleado)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el mesero con código: " + codigoEmpleado));
    }

    public Mesero actualizarMesero(Integer codigoEmpleado, Mesero mesero) {
        Mesero meseroExistente = meseroRepository.findById(codigoEmpleado)
                .orElseThrow(() -> new IllegalArgumentException("No se puede actualizar, mesero con código " + codigoEmpleado + " no existe."));

        meseroExistente.setNombre(mesero.getNombre());
        meseroExistente.setSalario(mesero.getSalario());
        meseroExistente.setCelular(mesero.getCelular());

        return meseroRepository.save(meseroExistente);
    }

    public void eliminarMesero(Integer codigoEmpleado) {
        if (!meseroRepository.existsById(codigoEmpleado)) {
            throw new IllegalArgumentException("No se puede eliminar, mesero con código " + codigoEmpleado + " no existe.");
        }
        meseroRepository.deleteById(codigoEmpleado);
    }
}
