package com.example.Restaurante_ms.services;

import com.example.Restaurante_ms.model.Cliente;
import com.example.Restaurante_ms.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1, "Sebastián", "123456789", "test@email.com");
    }



    @Test
    void testObtenerClientePorId_Found() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        Optional<Cliente> result = clienteService.obtenerClientePorId(1);
        assertTrue(result.isPresent());
        assertEquals(cliente, result.get());
        verify(clienteRepository, times(1)).findById(1);
    }


}
