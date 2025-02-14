package com.vehicleregistration.vehicle_registration.service;

import com.vehicleregistration.vehicle_registration.model.Veiculo;
import com.vehicleregistration.vehicle_registration.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VeiculoServiceImpl implements VeiculoService {

    @Autowired
    private VeiculoRepository repository;

    @Override
    public List<Veiculo> findAll() {
        return repository.findAll();
    }

    @Override
    public Veiculo findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
    }

    @Override
    public Veiculo save(Veiculo veiculo) {
        return repository.save(veiculo);
    }

    @Override
    public Veiculo update(Long id, Veiculo veiculo) {
        Veiculo existingVeiculo = findById(id);
        existingVeiculo.setVeiculo(veiculo.getVeiculo());
        existingVeiculo.setMarca(veiculo.getMarca());
        existingVeiculo.setAno(veiculo.getAno());
        existingVeiculo.setDescricao(veiculo.getDescricao());
        existingVeiculo.setVendido(veiculo.getVendido());
        return repository.save(existingVeiculo);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Long countNaoVendidos() {
        return repository.countByVendidoFalse();
    }

    @Override
    public List<Object[]> countByDecada() {
        return repository.countByDecada();
    }

    @Override
    public List<Object[]> countByMarca() {
        return repository.countByMarca();
    }

    @Override
    public List<Veiculo> findCadastradosNaUltimaSemana() {
        LocalDateTime dataInicio = LocalDateTime.now().minusWeeks(1);
        return repository.findVeiculosCadastradosNaUltimaSemana(dataInicio);
    }

    @Override
    public List<Veiculo> findByMarcaAnoCor(String marca, Integer ano, String cor) {
        return repository.findByMarcaAnoCor(marca, ano, cor);
    }
}
