package com.example.Restaurante_ms.services;

import com.example.Restaurante_ms.model.Cocinero;
import com.example.Restaurante_ms.repository.CocineroRepository;
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
class CocineroServiceTest {

    @Mock
    private CocineroRepository cocineroRepository;

    @InjectMocks
    private CocineroService cocineroService;

    private Cocinero cocinero;

    @BeforeEach
    void setUp() {
        cocinero = new Cocinero(1, "Juan", 2500.0, "Italiana");
    }

    @Test
    void testCrearCocinero_Success() {
        when(cocineroRepository.existsById(cocinero.getCodigoEmpleado())).thenReturn(false);
        when(cocineroRepository.save(cocinero)).thenReturn(cocinero);

        Cocinero creado = cocineroService.crearCocinero(cocinero);
        assertEquals(cocinero, creado);

        verify(cocineroRepository, times(1)).existsById(cocinero.getCodigoEmpleado());
        verify(cocineroRepository, times(1)).save(cocinero);
    }

    @Test
    void testCrearCocinero_AlreadyExists() {
        when(cocineroRepository.existsById(cocinero.getCodigoEmpleado())).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            cocineroService.crearCocinero(cocinero);
        });
        assertTrue(ex.getMessage().contains("Ya existe un cocinero"));

        verify(cocineroRepository, times(1)).existsById(cocinero.getCodigoEmpleado());
        verify(cocineroRepository, never()).save(any());
    }

    @Test
    void testListarCocineros() {
        List<Cocinero> cocineros = Arrays.asList(cocinero);
        when(cocineroRepository.findAll()).thenReturn(cocineros);

        List<Cocinero> result = cocineroService.listarCocineros();
        assertEquals(1, result.size());
        verify(cocineroRepository, times(1)).findAll();
    }

    @Test
    void testObtenerCocineroPorId_Success() {
        when(cocineroRepository.findById(1)).thenReturn(Optional.of(cocinero));

        Cocinero encontrado = cocineroService.obtenerCocineroPorId(1);
        assertEquals(cocinero, encontrado);
        verify(cocineroRepository, times(1)).findById(1);
    }

    @Test
    void testObtenerCocineroPorId_NotFound() {
        when(cocineroRepository.findById(2)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            cocineroService.obtenerCocineroPorId(2);
        });
        assertTrue(ex.getMessage().contains("no encontrado"));
        verify(cocineroRepository, times(1)).findById(2);
    }

    @Test
    void testActualizarCocinero_Success() {
        Cocinero actualizado = new Cocinero(null, "Pedro", 3000.0, "Mexicana");
        when(cocineroRepository.findById(1)).thenReturn(Optional.of(cocinero));
        when(cocineroRepository.save(any(Cocinero.class))).thenAnswer(i -> i.getArgument(0));

        Cocinero result = cocineroService.actualizarCocinero(1, actualizado);

        assertEquals("Pedro", result.getNombre());
        assertEquals("Mexicana", result.getEspecialidad());
        assertEquals(3000.0, result.getSalario());
        verify(cocineroRepository, times(1)).findById(1);
        verify(cocineroRepository, times(1)).save(cocinero);
    }

    @Test
    void testActualizarCocinero_NotFound() {
        when(cocineroRepository.findById(2)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            cocineroService.actualizarCocinero(2, cocinero);
        });
        assertTrue(ex.getMessage().contains("no encontrado"));
        verify(cocineroRepository, times(1)).findById(2);
        verify(cocineroRepository, never()).save(any());
    }

    @Test
    void testEliminarCocinero_Success() {
        when(cocineroRepository.existsById(1)).thenReturn(true);
        doNothing().when(cocineroRepository).deleteById(1);

        cocineroService.eliminarCocinero(1);

        verify(cocineroRepository, times(1)).existsById(1);
        verify(cocineroRepository, times(1)).deleteById(1);
    }

    @Test
    void testEliminarCocinero_NotFound() {
        when(cocineroRepository.existsById(2)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            cocineroService.eliminarCocinero(2);
        });
        assertTrue(ex.getMessage().contains("no existe"));
        verify(cocineroRepository, times(1)).existsById(2);
        verify(cocineroRepository, never()).deleteById(2);
    }
}
