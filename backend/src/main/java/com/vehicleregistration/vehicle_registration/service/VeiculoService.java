package com.vehicleregistration.vehicle_registration.service;

import com.vehicleregistration.vehicle_registration.model.Veiculo;

import java.util.List;

public interface VeiculoService {
    List<Veiculo> findAll();
    Veiculo findById(Long id);
    Veiculo save(Veiculo veiculo);
    Veiculo update(Long id, Veiculo veiculo);
    void delete(Long id);
    Long countNaoVendidos();
    List<Object[]> countByDecada();
    List<Object[]> countByMarca();
    List<Veiculo> findCadastradosNaUltimaSemana();
    List<Veiculo> findByMarcaAnoCor(String marca, Integer ano, String cor);
}
