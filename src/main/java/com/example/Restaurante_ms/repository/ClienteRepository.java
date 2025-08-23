package com.example.Restaurante_ms.repository;

import com.example.Restaurante_ms.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
