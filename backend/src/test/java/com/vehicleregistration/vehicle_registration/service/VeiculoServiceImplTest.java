package com.vehicleregistration.vehicle_registration.service;

import com.vehicleregistration.vehicle_registration.model.Veiculo;
import com.vehicleregistration.vehicle_registration.repository.VeiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VeiculoServiceImplTest {

    @Mock
    private VeiculoRepository repository;

    @InjectMocks
    private VeiculoServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnAllPageOfVeiculos() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Veiculo> veiculoPage = new PageImpl<>(List.of(new Veiculo()));
        when(repository.findAll(pageable)).thenReturn(veiculoPage);

        Page<Veiculo> result = service.findAll(pageable);

        assertEquals(veiculoPage, result);
        verify(repository, times(1)).findAll(pageable);
    }

    @Test
    void shouldReturnVeiculoWhenFound() {
        Veiculo veiculo = new Veiculo();
        when(repository.findById(1L)).thenReturn(Optional.of(veiculo));

        Veiculo result = service.findById(1L);

        assertEquals(veiculo, result);
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            service.findById(1L);
        });

        assertEquals("Veículo não encontrado com ID: 1", exception.getMessage());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void shouldSaveVeiculo() {
        Veiculo veiculo = new Veiculo();
        veiculo.setAno(2020);
        when(repository.save(veiculo)).thenReturn(veiculo);

        Veiculo result = service.save(veiculo);

        assertEquals(veiculo, result);
        verify(repository, times(1)).save(veiculo);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenAnoIsNull() {
        Veiculo veiculo = new Veiculo();
        veiculo.setAno(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.save(veiculo);
        });

        assertEquals("O ano do veículo é obrigatório", exception.getMessage());
        verify(repository, never()).save(any(Veiculo.class));
    }

    @Test
    void shouldUpdateVeiculo() {
        Veiculo existingVeiculo = new Veiculo();
        existingVeiculo.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(existingVeiculo));

        Veiculo veiculo = new Veiculo();
        veiculo.setVeiculo("Carro");
        veiculo.setMarca("Toyota");
        veiculo.setAno(2020);
        veiculo.setDescricao("Descrição");
        veiculo.setVendido(true);

        when(repository.save(any(Veiculo.class))).thenReturn(veiculo);

        Veiculo result = service.update(1L, veiculo);

        assertEquals("Carro", result.getVeiculo());
        assertEquals("Toyota", result.getMarca());
        assertEquals(2020, result.getAno());
        assertEquals("Descrição", result.getDescricao());
        assertTrue(result.getVendido());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Veiculo.class));
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenVeiculoNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            service.update(1L, new Veiculo());
        });

        assertEquals("Veículo não encontrado com ID: 1", exception.getMessage());
        verify(repository, times(1)).findById(1L);
        verify(repository, never()).save(any(Veiculo.class));
    }

    @Test
    void shouldUpdateFields() {
        Veiculo existingVeiculo = new Veiculo();
        existingVeiculo.setId(1L);
        existingVeiculo.setVeiculo("Carro");
        existingVeiculo.setMarca("Toyota");
        existingVeiculo.setAno(2020);
        existingVeiculo.setDescricao("Descrição");
        existingVeiculo.setVendido(false);

        when(repository.findById(1L)).thenReturn(Optional.of(existingVeiculo));

        Map<String, Object> updates = Map.of(
                "marca", "Honda",
                "vendido", true
        );

        Veiculo updatedVeiculo = existingVeiculo;
        updatedVeiculo.setMarca("Honda");
        updatedVeiculo.setVendido(true);

        when(repository.save(existingVeiculo)).thenReturn(updatedVeiculo);

        Veiculo result = service.patchVeiculo(1L, updates);

        assertEquals("Honda", result.getMarca());
        assertTrue(result.getVendido());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(existingVeiculo);
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenInvalidField() {
        Veiculo existingVeiculo = new Veiculo();
        existingVeiculo.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(existingVeiculo));

        Map<String, Object> updates = Map.of(
                "invalidField", "value"
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            service.patchVeiculo(1L, updates);
        });

        assertEquals("Campo inválido: invalidField", exception.getMessage());
        verify(repository, times(1)).findById(1L);
        verify(repository, never()).save(any(Veiculo.class));
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenIdNotFound() {
        when(repository.existsById(1L)).thenReturn(false);

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            service.delete(1L);
        });

        assertEquals("Veículo não encontrado com ID: 1", exception.getMessage());
        verify(repository, times(1)).existsById(1L);
        verify(repository, never()).deleteById(anyLong());
    }

    @Test
    void shouldCallRepositoryDeleteByIdWhenIdFound() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        service.delete(1L);

        verify(repository, times(1)).existsById(1L);
        verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void shouldReturnVeiculosNaoVendidos() {
        when(repository.countByVendidoFalse()).thenReturn(5L);

        Long count = service.countNaoVendidos();

        assertEquals(5L, count);
        verify(repository, times(1)).countByVendidoFalse();
    }

    @Test
    void shouldReturnDistributionPorDecada() {
        List<Object[]> distribuicao = List.of(new Object[]{1980, 10L}, new Object[]{1990, 20L});
        when(repository.countByDecada()).thenReturn(distribuicao);

        List<String> result = service.countByDecada();

        assertEquals("Década 1980 = 10 veículos", result.get(0));
        assertEquals("Década 1990 = 20 veículos", result.get(1));
        verify(repository, times(1)).countByDecada();
    }

    @Test
    void shouldReturnDistributionPorFabricante() {
        List<Object[]> distribuicao = List.of(new Object[]{"Ford", 10L}, new Object[]{"Toyota", 20L});
        when(repository.countByFabricante()).thenReturn(distribuicao);

        List<String> result = service.countByFabricante();

        assertEquals("Ford = 10 veículos", result.get(0));
        assertEquals("Toyota = 20 veículos", result.get(1));
        verify(repository, times(1)).countByFabricante();
    }

    @Test
    void shouldReturnRecentVehicles() {
        List<Veiculo> veiculos = List.of(new Veiculo());
        when(repository.findVeiculosCadastradosNaUltimaSemana(any(LocalDateTime.class))).thenReturn(veiculos);

        List<Veiculo> result = service.findCadastradosNaUltimaSemana();

        assertEquals(veiculos, result);
        verify(repository, times(1)).findVeiculosCadastradosNaUltimaSemana(any(LocalDateTime.class));
    }

    @Test
    void shouldReturnFilteredVehiclesPorMarcaEAno() {
        List<Veiculo> veiculos = List.of(new Veiculo());
        when(repository.findByMarcaAnoCor(anyString(), anyInt(), anyString())).thenReturn(veiculos);

        List<Veiculo> result = service.findByMarcaAnoCor("Toyota", 2020, "Azul");

        assertEquals(veiculos, result);
        verify(repository, times(1)).findByMarcaAnoCor(anyString(), anyInt(), anyString());
    }
}
