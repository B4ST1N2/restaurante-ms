package com.example.Restaurante_ms.controller;

import com.example.Restaurante_ms.model.Mesero;
import com.example.Restaurante_ms.services.MeseroService;
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

@WebMvcTest(MeseroController.class)
class MeseroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MeseroService meseroService;

    @Autowired
    private ObjectMapper objectMapper;

    private Mesero mesero;

    @BeforeEach
    void setUp() {
        mesero = new Mesero();
        mesero.setCodigoEmpleado(1);
        mesero.setNombre("Juan Pérez");
    }

    @Test
    void testAgregarMesero() throws Exception {
        Mockito.when(meseroService.crearMesero(any(Mesero.class))).thenReturn(mesero);

        mockMvc.perform(post("/mesero")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mesero)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigoEmpleado").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"));
    }

    @Test
    void testListarMeseros() throws Exception {
        List<Mesero> meseros = Arrays.asList(mesero);
        Mockito.when(meseroService.obtenerTodos()).thenReturn(meseros);

        mockMvc.perform(get("/mesero/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].codigoEmpleado").value(1))
                .andExpect(jsonPath("$[0].nombre").value("Juan Pérez"));
    }

    @Test
    void testObtenerMeseroPorId() throws Exception {
        Mockito.when(meseroService.obtenerPorCodigo(1)).thenReturn(mesero);

        mockMvc.perform(get("/mesero/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigoEmpleado").value(1))
                .andExpect(jsonPath("$.nombre").value("Juan Pérez"));
    }

    @Test
    void testActualizarMesero() throws Exception {
        Mesero actualizado = new Mesero();
        actualizado.setCodigoEmpleado(1);
        actualizado.setNombre("Carlos Gómez");

        Mockito.when(meseroService.actualizarMesero(eq(1), any(Mesero.class)))
                .thenReturn(actualizado);

        mockMvc.perform(put("/mesero/actualizar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(actualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Carlos Gómez"));
    }

    @Test
    void testEliminarMesero() throws Exception {
        Mockito.doNothing().when(meseroService).eliminarMesero(1);

        mockMvc.perform(delete("/mesero/1"))
                .andExpect(status().isOk());
    }
}
