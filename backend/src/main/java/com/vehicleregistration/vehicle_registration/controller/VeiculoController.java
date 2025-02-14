package com.vehicleregistration.vehicle_registration.controller;

import com.vehicleregistration.vehicle_registration.model.Veiculo;
import com.vehicleregistration.vehicle_registration.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {
    @Autowired
    private VeiculoService vehicleService;

    @GetMapping
    public List<Veiculo> findAll() {
        return vehicleService.findAll();
    }

    @GetMapping("/{id}")
    public Veiculo findById(@PathVariable Long id) {
        return vehicleService.findById(id);
    }

    @PostMapping
    public Veiculo save(@RequestBody Veiculo veiculo) {
        return vehicleService.save(veiculo);
    }

    @PutMapping("/{id}")
    public Veiculo update(@PathVariable Long id, @RequestBody Veiculo veiculo) {
        return vehicleService.update(id, veiculo);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        vehicleService.delete(id);
    }

    @GetMapping("/nao-vendidos")
    public Long countNaoVendidos() {
        return vehicleService.countNaoVendidos();
    }

    @GetMapping("/distribuicao-decada")
    public List<Object[]> countByDecada() {
        return vehicleService.countByDecada();
    }

    @GetMapping("/distribuicao-marca")
    public List<Object[]> countByMarca() {
        return vehicleService.countByMarca();
    }

    @GetMapping("/ultima-semana")
    public List<Veiculo> findCadastradosNaUltimaSemana() {
        return vehicleService.findCadastradosNaUltimaSemana();
    }

    @GetMapping("/filtro")
    public List<Veiculo> findByMarcaAnoCor(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) String cor
    ) {
        return vehicleService.findByMarcaAnoCor(marca, ano, cor);
    }
}
