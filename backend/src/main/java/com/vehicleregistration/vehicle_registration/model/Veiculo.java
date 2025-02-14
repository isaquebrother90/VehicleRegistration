package com.vehicleregistration.vehicle_registration.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDateTime;

@Entity
public class Veiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @NotNull(message = "O nome do veículo não pode ser nulo")
    @Column(nullable = false)
    private String veiculo;

    @NotNull(message = "A marca não pode ser nula")
    @Column(nullable = false)
    private String marca;

    @NotNull(message = "O ano não pode ser nulo")
    @Min(value = 1000, message = "O ano deve ter pelo menos 4 dígitos")
    @Max(value = 9999, message = "O ano não pode ter mais de 4 dígitos")
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Integer ano;

    private String descricao;

    @NotNull(message = "O campo 'vendido' não pode ser nulo")
    @Column(nullable = false)
    private Boolean vendido;

    private LocalDateTime created;

    private LocalDateTime updated;

    @PrePersist
    protected void onCreate() {
        created = LocalDateTime.now();
        updated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = LocalDateTime.now();
    }

    public Veiculo() {
    }

    public Veiculo(Long id, String veiculo, String marca, Integer ano, String descricao, Boolean vendido, LocalDateTime created, LocalDateTime updated) {
        this.id = id;
        this.veiculo = veiculo;
        this.marca = marca;
        this.ano = ano;
        this.descricao = descricao;
        this.vendido = vendido;
        this.created = created;
        this.updated = updated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(String veiculo) {
        this.veiculo = veiculo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getVendido() {
        return vendido;
    }

    public void setVendido(Boolean vendido) {
        this.vendido = vendido;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
    }
}
