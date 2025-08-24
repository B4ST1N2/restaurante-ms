package com.example.Restaurante_ms.services;

import com.example.Restaurante_ms.model.Mesero;
import com.example.Restaurante_ms.repository.MeseroRepository;
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
class MeseroServiceTest {

    @Mock
    private MeseroRepository meseroRepository;

    @InjectMocks
    private MeseroService meseroService;

    private Mesero mesero;

    @BeforeEach
    void setUp() {
        mesero = new Mesero(1, "Carlos", 2000.0, "123456789");
    }

    @Test
    void testCrearMesero_Success() {
        when(meseroRepository.existsById(mesero.getCodigoEmpleado())).thenReturn(false);
        when(meseroRepository.save(mesero)).thenReturn(mesero);

        Mesero creado = meseroService.crearMesero(mesero);
        assertEquals(mesero, creado);

        verify(meseroRepository, times(1)).existsById(mesero.getCodigoEmpleado());
        verify(meseroRepository, times(1)).save(mesero);
    }

    @Test
    void testCrearMesero_AlreadyExists() {
        when(meseroRepository.existsById(mesero.getCodigoEmpleado())).thenReturn(true);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            meseroService.crearMesero(mesero);
        });
        assertTrue(ex.getMessage().contains("ya existe"));

        verify(meseroRepository, times(1)).existsById(mesero.getCodigoEmpleado());
        verify(meseroRepository, never()).save(any());
    }

    @Test
    void testObtenerTodos() {
        List<Mesero> meseros = Arrays.asList(mesero);
        when(meseroRepository.findAll()).thenReturn(meseros);

        List<Mesero> result = meseroService.obtenerTodos();
        assertEquals(1, result.size());
        verify(meseroRepository, times(1)).findAll();
    }

    @Test
    void testObtenerPorCodigo_Success() {
        when(meseroRepository.findById(1)).thenReturn(Optional.of(mesero));

        Mesero encontrado = meseroService.obtenerPorCodigo(1);
        assertEquals(mesero, encontrado);
        verify(meseroRepository, times(1)).findById(1);
    }

    @Test
    void testObtenerPorCodigo_NotFound() {
        when(meseroRepository.findById(2)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            meseroService.obtenerPorCodigo(2);
        });
        assertTrue(ex.getMessage().contains("No se encontró"));
        verify(meseroRepository, times(1)).findById(2);
    }

    @Test
    void testActualizarMesero_Success() {
        Mesero actualizado = new Mesero(null, "Luis", 2500.0, "987654321");
        when(meseroRepository.findById(1)).thenReturn(Optional.of(mesero));
        when(meseroRepository.save(any(Mesero.class))).thenAnswer(i -> i.getArgument(0));

        Mesero result = meseroService.actualizarMesero(1, actualizado);

        assertEquals("Luis", result.getNombre());
        assertEquals(2500.0, result.getSalario());
        assertEquals("987654321", result.getCelular());
        verify(meseroRepository, times(1)).findById(1);
        verify(meseroRepository, times(1)).save(mesero);
    }

    @Test
    void testActualizarMesero_NotFound() {
        when(meseroRepository.findById(2)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            meseroService.actualizarMesero(2, mesero);
        });
        assertTrue(ex.getMessage().contains("no existe"));
        verify(meseroRepository, times(1)).findById(2);
        verify(meseroRepository, never()).save(any());
    }

    @Test
    void testEliminarMesero_Success() {
        when(meseroRepository.existsById(1)).thenReturn(true);
        doNothing().when(meseroRepository).deleteById(1);

        meseroService.eliminarMesero(1);

        verify(meseroRepository, times(1)).existsById(1);
        verify(meseroRepository, times(1)).deleteById(1);
    }

    @Test
    void testEliminarMesero_NotFound() {
        when(meseroRepository.existsById(2)).thenReturn(false);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            meseroService.eliminarMesero(2);
        });
        assertTrue(ex.getMessage().contains("no existe"));
        verify(meseroRepository, times(1)).existsById(2);
        verify(meseroRepository, never()).deleteById(2);
    }
}
