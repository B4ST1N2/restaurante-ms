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
    void testSaveCliente() {
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        Cliente saved = clienteService.saveCliente(cliente);
        assertEquals(cliente, saved);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void testObtenerClientes() {
        List<Cliente> clientes = Arrays.asList(cliente);
        when(clienteRepository.findAll()).thenReturn(clientes);
        List<Cliente> result = clienteService.obtenerClientes();
        assertEquals(2, result.size());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    void testObtenerClientePorId_Found() {
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        Optional<Cliente> result = clienteService.obtenerClientePorId(1);
        assertTrue(result.isPresent());
        assertEquals(cliente, result.get());
        verify(clienteRepository, times(1)).findById(1);
    }

    @Test
    void testObtenerClientePorId_NotFound() {
        when(clienteRepository.findById(2)).thenReturn(Optional.empty());
        Optional<Cliente> result = clienteService.obtenerClientePorId(2);
        assertFalse(result.isPresent());
        verify(clienteRepository, times(1)).findById(2);
    }

    @Test
    void testActualizarCliente_Success() {
        Cliente detalles = new Cliente(null, "Juan", "987654321", "juan@email.com");
        when(clienteRepository.findById(1)).thenReturn(Optional.of(cliente));
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(i -> i.getArgument(0));

        Cliente updated = clienteService.actualizarCliente(1, detalles);

        assertEquals("Juan", updated.getNombre());
        assertEquals("987654321", updated.getCelular());
        assertEquals("juan@email.com", updated.getCorreo());
        verify(clienteRepository, times(1)).findById(1);
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    void testActualizarCliente_NotFound() {
        when(clienteRepository.findById(2)).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            clienteService.actualizarCliente(2, cliente);
        });
        assertTrue(ex.getMessage().contains("Cliente no encontrado"));
        verify(clienteRepository, times(1)).findById(2);
    }

    @Test
    void testElimiarCliente_Success() {
        when(clienteRepository.existsById(1)).thenReturn(true);
        doNothing().when(clienteRepository).deleteById(1);

        clienteService.elimiarCliente(1);

        verify(clienteRepository, times(1)).existsById(1);
        verify(clienteRepository, times(1)).deleteById(1);
    }

    @Test
    void testElimiarCliente_NotFound() {
        when(clienteRepository.existsById(2)).thenReturn(false);
        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            clienteService.elimiarCliente(2);
        });
        assertTrue(ex.getMessage().contains("Cliente no encontrado"));
        verify(clienteRepository, times(1)).existsById(2);
        verify(clienteRepository, never()).deleteById(2);
    }
}
