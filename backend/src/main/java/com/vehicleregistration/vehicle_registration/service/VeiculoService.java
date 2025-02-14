package com.vehicleregistration.vehicle_registration.service;

import com.vehicleregistration.vehicle_registration.model.Veiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface VeiculoService {
    Page<Veiculo> findAll(Pageable pageable);
    Veiculo findById(Long id);
    Veiculo save(Veiculo veiculo);
    Veiculo update(Long id, Veiculo veiculo);
    Veiculo patchVeiculo(Long id, Map<String, Object> updates);
    void delete(Long id);
    Long countNaoVendidos();
    List<Object[]> countByDecada();
    List<Object[]> countByMarca();
    List<Veiculo> findCadastradosNaUltimaSemana();
    List<Veiculo> findByMarcaAnoCor(String marca, Integer ano, String cor);
}
