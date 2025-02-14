package com.vehicleregistration.vehicle_registration.repository;

import com.vehicleregistration.vehicle_registration.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    @Query("SELECT COUNT(v) FROM Veiculo v WHERE v.vendido = false")
    Long countByVendidoFalse();

    @Query("SELECT v.marca, COUNT(v) FROM Veiculo v GROUP BY v.marca")
    List<Object[]> countByMarca();

    @Query("SELECT v FROM Veiculo v WHERE " +
            "(:marca IS NULL OR v.marca = :marca) AND " +
            "(:ano IS NULL OR v.ano = :ano) AND " +
            "(:cor IS NULL OR v.descricao LIKE %:cor%)")
    List<Veiculo> findByMarcaAnoCor(
            @Param("marca") String marca,
            @Param("ano") Integer ano,
            @Param("cor") String cor
    );

    @Query("SELECT FLOOR(v.ano / 10) * 10 AS decada, COUNT(v) FROM Veiculo v GROUP BY decada")
    List<Object[]> countByDecada();

    @Query("SELECT v FROM Veiculo v WHERE v.created >= :dataInicio")
    List<Veiculo> findVeiculosCadastradosNaUltimaSemana(@Param("dataInicio") LocalDateTime dataInicio);
}