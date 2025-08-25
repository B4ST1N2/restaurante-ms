package com.example.Restaurante_ms.controller;

import com.example.Restaurante_ms.model.Cocinero;
import com.example.Restaurante_ms.services.CocineroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CocineroController.class)
class CocineroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CocineroService cocineroService;

    @Autowired
    private ObjectMapper objectMapper;

    private Cocinero cocinero;

    @BeforeEach
    void setUp() {
        cocinero = new Cocinero();
        cocinero.setCodigoEmpleado(1);
        cocinero.setNombre("Pedro Gomez");
        cocinero.setEspecialidad("Comida Italiana");
        cocinero.setSalario(2500.0);
    }

    @Test
    void testAgregarCocinero() throws Exception {
        Mockito.when(cocineroService.crearCocinero(any(Cocinero.class))).thenReturn(cocinero);

        mockMvc.perform(post("/cocinero")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cocinero)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Pedro Gomez"))
                .andExpect(jsonPath("$.especialidad").value("Comida Italiana"));
    }

    @Test
    void testListarCocineros() throws Exception {
        List<Cocinero> cocineros = Arrays.asList(cocinero);
        Mockito.when(cocineroService.listarCocineros()).thenReturn(cocineros);

        mockMvc.perform(get("/cocinero/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Pedro Gomez"))
                .andExpect(jsonPath("$[0].especialidad").value("Comida Italiana"));
    }

    @Test
    void testObtenerCocineroPorId() throws Exception {
        Mockito.when(cocineroService.obtenerCocineroPorId(1)).thenReturn(cocinero);

        mockMvc.perform(get("/cocinero/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Pedro Gomez"));
    }

    @Test
    void testActualizarCocinero() throws Exception {
        Cocinero actualizado = new Cocinero();
        actualizado.setCodigoEmpleado(1);
        actualizado.setNombre("Carlos Lopez");
        actualizado.setEspecialidad("Parrilla");
        actualizado.setSalario(3000.0);

        Mockito.when(cocineroService.actualizarCocinero(eq(1), any(Cocinero.class)))
                .thenReturn(actualizado);

        mockMvc.perform(put("/cocinero/actualizar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Carlos Lopez"))
                .andExpect(jsonPath("$.especialidad").value("Parrilla"));
    }

    @Test
    void testEliminarCocinero() throws Exception {
        Mockito.doNothing().when(cocineroService).eliminarCocinero(1);

        mockMvc.perform(delete("/cocinero/1"))
                .andExpect(status().isOk());
    }
}
