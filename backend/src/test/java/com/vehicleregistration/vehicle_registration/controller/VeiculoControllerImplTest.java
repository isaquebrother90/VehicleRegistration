package com.vehicleregistration.vehicle_registration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vehicleregistration.vehicle_registration.model.Veiculo;
import com.vehicleregistration.vehicle_registration.service.VeiculoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VeiculoControllerImpl.class)
class VeiculoControllerImplMockMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VeiculoService vehicleService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        Veiculo veiculo = new Veiculo();
        veiculo.setId(1L);
        veiculo.setMarca("Toyota");
        veiculo.setAno(2020);

        Page<Veiculo> veiculoPage = new PageImpl<>(List.of(veiculo));
        Mockito.when(vehicleService.findAll(PageRequest.of(0, 10))).thenReturn(veiculoPage);
        Mockito.when(vehicleService.findById(1L)).thenReturn(veiculo);
        Mockito.when(vehicleService.save(Mockito.any(Veiculo.class))).thenReturn(veiculo);
        Mockito.when(vehicleService.update(Mockito.anyLong(), Mockito.any(Veiculo.class))).thenReturn(veiculo);
        Mockito.when(vehicleService.patchVeiculo(Mockito.anyLong(), Mockito.anyMap())).thenReturn(veiculo);
    }

    @Test
    void shouldReturnAllVehicles() throws Exception {
        mockMvc.perform(get("/veiculos")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content[0].marca").value("Toyota"));
    }

    @Test
    void shouldReturnVehicleById() throws Exception {
        mockMvc.perform(get("/veiculos/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marca").value("Toyota"));
    }

    @Test
    void shouldCreateVehicle() throws Exception {
        Veiculo veiculo = new Veiculo();
        veiculo.setVeiculo("Corolla");
        veiculo.setMarca("Toyota");
        veiculo.setAno(2020);
        veiculo.setVendido(false);

        mockMvc.perform(post("/veiculos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(veiculo)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"));
    }

    @Test
    void shouldUpdateVehicle() throws Exception {
        Veiculo veiculo = new Veiculo();
        veiculo.setVeiculo("Corolla");
        veiculo.setMarca("Toyota");
        veiculo.setAno(2020);
        veiculo.setVendido(true);

        mockMvc.perform(put("/veiculos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(veiculo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marca").value("Toyota"));
    }

    @Test
    void shouldPatchVehicle() throws Exception {
        mockMvc.perform(patch("/veiculos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"marca\":\"Honda\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marca").value("Toyota"));
    }

    @Test
    void shouldDeleteVehicle() throws Exception {
        mockMvc.perform(delete("/veiculos/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnVehiclesNaoVendidos() throws Exception {
        Mockito.when(vehicleService.countNaoVendidos()).thenReturn(5L);

        mockMvc.perform(get("/veiculos/nao-vendidos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(5));
    }

    @Test
    void shouldReturnVehicleDistributionPorDecada() throws Exception {
        List<String> distribuicao = List.of("Década 1990 = 10", "Década 1980 = 20");
        Mockito.when(vehicleService.countByDecada()).thenReturn(distribuicao);

        mockMvc.perform(get("/veiculos/distribuicao-decada"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Década 1990 = 10"))
                .andExpect(jsonPath("$[1]").value("Década 1980 = 20"));
    }

    @Test
    void shouldReturnDistributionPorFabricante() throws Exception {
        List<String> distribuicao = List.of("Ford = 10", "Toyota = 20");
        Mockito.when(vehicleService.countByFabricante()).thenReturn(distribuicao);

        mockMvc.perform(get("/veiculos/distribuicao-fabricante"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Ford = 10"))
                .andExpect(jsonPath("$[1]").value("Toyota = 20"));
    }

    @Test
    void shouldReturnVehiclesCadastradosUltimaSemana() throws Exception {
        List<Veiculo> veiculos = List.of(new Veiculo());
        Mockito.when(vehicleService.findCadastradosNaUltimaSemana()).thenReturn(veiculos);

        mockMvc.perform(get("/veiculos/ultima-semana"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }

    @Test
    void shouldReturnFilteredVehiclesByAnoMarca() throws Exception {
        List<Veiculo> veiculos = List.of(new Veiculo());
        Mockito.when(vehicleService.findByMarcaAnoCor(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString())).thenReturn(veiculos);

        mockMvc.perform(get("/veiculos/filtro")
                        .param("marca", "Toyota")
                        .param("ano", "2020")
                        .param("cor", "Azul"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists());
    }
}
