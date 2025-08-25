package com.example.Restaurante_ms.controller;

import com.example.Restaurante_ms.model.Cliente;
import com.example.Restaurante_ms.services.ClienteService;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1);
        cliente.setNombre("Juan Perez");
        cliente.setCorreo("juan@example.com");
    }

    @Test
    void testAgregarCliente() throws Exception {
        Mockito.when(clienteService.saveCliente(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/cliente")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Perez"))
                .andExpect(jsonPath("$.correo").value("juan@example.com"));
    }

    @Test
    void testListarClientes() throws Exception {
        List<Cliente> clientes = Arrays.asList(cliente);
        Mockito.when(clienteService.obtenerClientes()).thenReturn(clientes);

        mockMvc.perform(get("/cliente/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Juan Perez"));
    }

    @Test
    void testObtenerClientePorId_Encontrado() throws Exception {
        Mockito.when(clienteService.obtenerClientePorId(1)).thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/cliente/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan Perez"));
    }

    @Test
    void testObtenerClientePorId_NoEncontrado() throws Exception {
        Mockito.when(clienteService.obtenerClientePorId(2)).thenReturn(Optional.empty());

        mockMvc.perform(get("/cliente/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testActualizarCliente() throws Exception {
        Cliente clienteActualizado = new Cliente();
        clienteActualizado.setId(1);
        clienteActualizado.setNombre("Carlos Lopez");
        clienteActualizado.setCorreo("carlos@example.com");

        Mockito.when(clienteService.actualizarCliente(eq(1), any(Cliente.class)))
                .thenReturn(clienteActualizado);

        mockMvc.perform(put("/cliente/actualizar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(clienteActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Carlos Lopez"));
    }

    @Test
    void testEliminarCliente() throws Exception {
        Mockito.doNothing().when(clienteService).elimiarCliente(1);

        mockMvc.perform(delete("/cliente/1"))
                .andExpect(status().isNoContent());
    }
}
