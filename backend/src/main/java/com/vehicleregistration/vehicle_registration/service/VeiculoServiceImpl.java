package com.vehicleregistration.vehicle_registration.service;

import com.vehicleregistration.vehicle_registration.model.Veiculo;
import com.vehicleregistration.vehicle_registration.repository.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class VeiculoServiceImpl implements VeiculoService {

    @Autowired
    private VeiculoRepository repository;

    @Override
    public Page<Veiculo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }public List<Veiculo> findAll() {
        return repository.findAll();
    }

    @Override
    public Veiculo findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Veículo não encontrado com ID: " + id));
    }

    @Transactional
    @Override
    public Veiculo save(Veiculo veiculo) {
        validateVeiculo(veiculo);
        return repository.save(veiculo);
    }

    private void validateVeiculo(Veiculo veiculo) {
        if (veiculo.getAno() == null) {
            log.error("Ano do veículo não pode ser nulo: {}", veiculo);
            throw new IllegalArgumentException("O ano do veículo é obrigatório");
        }
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
    public Veiculo patchVeiculo(Long id, Map<String, Object> updates) {
        Veiculo veiculo = findById(id); // Busca o veículo existente

        // Itera sobre os campos a serem atualizados
        updates.forEach((campo, valor) -> {
            switch (campo) {
                case "veiculo":
                    veiculo.setVeiculo((String) valor);
                    break;
                case "marca":
                    veiculo.setMarca((String) valor);
                    break;
                case "ano":
                    if (!(valor instanceof Integer)) {
                        throw new IllegalArgumentException("O campo 'ano' deve ser um número inteiro.");
                    }
                    veiculo.setAno((Integer) valor);
                    break;
                case "descricao":
                    veiculo.setDescricao((String) valor);
                    break;
                case "vendido":
                    if (!(valor instanceof Boolean)) {
                        throw new IllegalArgumentException("O campo 'vendido' deve ser um valor booleano.");
                    }
                    veiculo.setVendido((Boolean) valor);
                    break;
                default:
                    throw new IllegalArgumentException("Campo inválido: " + campo);
            }
        });

        veiculo.setUpdated(LocalDateTime.now()); // Atualiza a data de modificação
        return repository.save(veiculo); // Salva as alterações
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Veículo não encontrado com ID: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    public Long countNaoVendidos() {
        return repository.countByVendidoFalse();
    }

    @Override
     public List<String> countByDecada() {
        List<Object[]> distribuicao = repository.countByDecada();

        // Transforma a lista de arrays em uma lista de strings no formato desejado
        List<String> resultado = distribuicao.stream()
                .map(arr -> String.format("Década %d = %d veículos", (Integer) arr[0], (Long) arr[1]))
                .collect(Collectors.toList());
        return resultado;
    }

    @Override
    public List<String> countByFabricante() {
        List<Object[]> distribuicao = repository.countByFabricante();

        List<String> resultado = distribuicao.stream()
                .map(arr -> String.format("%s = %d veículos", arr[0], (Long) arr[1]))
                .toList();
        return resultado;
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
