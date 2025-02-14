package com.vehicleregistration.vehicle_registration.controller;

import com.vehicleregistration.vehicle_registration.model.Veiculo;
import com.vehicleregistration.vehicle_registration.service.VeiculoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Tag(name = "Veículos", description = "API para gerenciamento de veículos")
@RestController
@RequestMapping("/veiculos")
public class VeiculoControllerImpl {

    @Autowired
    private VeiculoService vehicleService;

    @GetMapping
    public ResponseEntity<Page<Veiculo>> findAll(
            @RequestParam(defaultValue = "0") int page, // Número da página (começa em 0)
            @RequestParam(defaultValue = "10") int size // Tamanho da página
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Veiculo> veiculos = vehicleService.findAll(pageable);

        if (veiculos.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content se a lista estiver vazia
        }
        return ResponseEntity.ok(veiculos); // 200 OK com a página de veículos
    }

    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> findById(@PathVariable Long id) {
        Veiculo veiculo = vehicleService.findById(id);
        if (veiculo == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        return ResponseEntity.ok(veiculo); // 200 OK
    }

    @PostMapping
    public ResponseEntity<Veiculo> save(@RequestBody Veiculo veiculo) {
        Veiculo savedVeiculo = vehicleService.save(veiculo);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedVeiculo.getId())
                .toUri();
        return ResponseEntity.created(location).body(savedVeiculo); // 201 Created
    }

    @PutMapping("/{id}")
    public ResponseEntity<Veiculo> update(@PathVariable Long id, @RequestBody Veiculo veiculo) {
        Veiculo updatedVeiculo = vehicleService.update(id, veiculo);
        if (updatedVeiculo == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        return ResponseEntity.ok(updatedVeiculo); // 200 OK
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Veiculo> patchVeiculo(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Veiculo patchedVeiculo = vehicleService.patchVeiculo(id, updates);
        if (patchedVeiculo == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
        return ResponseEntity.ok(patchedVeiculo); // 200 OK
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        vehicleService.delete(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @GetMapping("/nao-vendidos")
    public ResponseEntity<Long> countNaoVendidos() {
        Long count = vehicleService.countNaoVendidos();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/distribuicao-decada")
    public ResponseEntity<List<String>> countByDecada() {
        List<String> distribuicao = vehicleService.countByDecada();
        return ResponseEntity.ok(distribuicao);
    }

    @GetMapping("/distribuicao-fabricante")
    public ResponseEntity<List<String>> countByFabricante() {
        List<String> distribuicao = vehicleService.countByFabricante();
        return ResponseEntity.ok(distribuicao);
    }

    @GetMapping("/ultima-semana")
    public ResponseEntity<List<Veiculo>> findCadastradosNaUltimaSemana() {
        List<Veiculo> veiculos = vehicleService.findCadastradosNaUltimaSemana();
        return ResponseEntity.ok(veiculos);
    }

    @GetMapping("/filtro")
    public ResponseEntity<List<Veiculo>> findByMarcaAnoCor(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) String cor
    ) {
        List<Veiculo> veiculos = vehicleService.findByMarcaAnoCor(marca, ano, cor);
        return ResponseEntity.ok(veiculos);
    }
}