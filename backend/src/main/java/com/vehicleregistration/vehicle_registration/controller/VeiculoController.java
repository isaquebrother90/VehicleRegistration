package com.vehicleregistration.vehicle_registration.controller;

import com.vehicleregistration.vehicle_registration.model.Veiculo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

public interface VeiculoController {

    @Operation(
            summary = "Listar todos os veículos",
            description = "Retorna uma lista paginada de veículos",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Veículos encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Veiculo.class),
                                    examples = @ExampleObject(value = "{\"content\":[\" +\n" +
                                            "                                        \"{\\\"id\\\":1,\\\"veiculo\\\":\\\"Fiat Uno\\\",\\\"marca\\\":\\\"Fiat\\\",\\\"ano\\\":2015,\\\"descricao\\\":\\\"Carro popular...\\\",\\\"vendido\\\":false,\\\"created\\\":\\\"2024-08-01T10:00:00\\\",\\\"updated\\\":\\\"2024-08-01T12:00:00\\\"},\" +\n" +
                                            "                                        \"{\\\"id\\\":2,\\\"veiculo\\\":\\\"Gol\\\",\\\"marca\\\":\\\"Volkswagen\\\",\\\"ano\\\":2020,\\\"descricao\\\":\\\"Carro completo...\\\",\\\"vendido\\\":true,\\\"created\\\":\\\"2024-07-31T09:00:00\\\",\\\"updated\\\":\\\"2024-07-31T11:00:00\\\"}],\"pageable\":\"INSTANCE\",\"totalPages\":1,\"totalElements\":1,\"last\":true,\"size\":20,\"number\":0,\"sort\":{\"sorted\":true,\"unsorted\":false,\"empty\":false},\"first\":true,\"numberOfElements\":1,\"empty\":false}"))),
            @ApiResponse(responseCode = "204", description = "Nenhum veículo encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    ResponseEntity<List<Veiculo>> findAll();

    @Operation(
            summary = "Buscar veículo por ID",
            description = "Retorna um veículo específico pelo ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Veículo encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Veiculo.class),
                                    examples = @ExampleObject(value = "{\"id\":1,\"veiculo\":\"Fiat Uno\",\"marca\":\"Fiat\",\"ano\":2015,\"descricao\":\"Carro popular...\",\"vendido\":false,\"created\":\"2024-08-01T10:00:00\",\"updated\":\"2024-08-01T12:00:00\"}"))),
                    @ApiResponse(responseCode = "404", description = "Veículo não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @GetMapping("/{id}")
    ResponseEntity<Veiculo> findById(@Parameter(description = "ID do veículo a ser buscado") @PathVariable Long id);

    @Operation(
            summary = "Salvar um novo veículo",
            description = "Salva um novo veículo",
            responses = {
                    @ApiResponse(
                            responseCode = "201", description = "Veículo salvo com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Veiculo.class),
                                    examples = @ExampleObject(value = "{\"veiculo\":\"Civic\",\"marca\":\"Honda\",\"ano\":2023,\"descricao\":\"Carro novo...\",\"vendido\":false,\"created\":\"2024-08-02T14:00:00\",\"updated\":\"2024-08-02T14:00:00\"}"))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos para salvar o veículo"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = { @Content(schema = @Schema()) })
            }
    )
    @PostMapping
    ResponseEntity<Veiculo> save(@Valid @RequestBody Veiculo veiculo);

    @Operation(
            summary = "Atualizar um veículo existente",
            description = "Atualiza um veículo existente pelo ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Veículo atualizado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Veiculo.class),
                                    examples = @ExampleObject(value = "{\"veiculo\":\"Fiat Uno Turbo\",\"marca\":\"Fiat\",\"ano\":2015,\"descricao\":\"Carro turbo...\",\"vendido\":false,\"created\":\"2024-08-01T10:00:00\",\"updated\":\"2024-08-02T15:00:00\"}"))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos para atualizar o veículo"),
                    @ApiResponse(responseCode = "404", description = "Veículo não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @PutMapping("/{id}")
    ResponseEntity<Veiculo> update(@Parameter(description = "ID do veículo a ser atualizado") @PathVariable Long id, @Valid @RequestBody Veiculo veiculo);

    @Operation(
            summary = "Atualização parcial de um veículo",
            description = "Atualiza parcialmente um veículo existente pelo ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Veículo parcialmente atualizado com sucesso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Veiculo.class),
                                    examples = @ExampleObject(value = "{\"descricao\":\"Carro com teto solar...\"}"))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização parcial"),
                    @ApiResponse(responseCode = "404", description = "Veículo não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @PatchMapping("/{id}")
    ResponseEntity<Veiculo> patchVeiculo(
            @Parameter(description = "ID do veículo a ser parcialmente atualizado") @PathVariable Long id,
            @Valid @RequestBody Map<String, Object> updates);

    @Operation(
            summary = "Excluir um veículo",
            description = "Exclui um veículo pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Veículo excluído com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Veículo não encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@Parameter(description = "ID do veículo a ser excluído") @PathVariable Long id);

    @Operation(
            summary = "Contar veículos não vendidos",
            description = "Retorna a contagem de veículos não vendidos",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Contagem retornada com sucesso",
                            content = @Content(schema = @Schema(type = "integer"),
                                    examples = @ExampleObject(value = "10"))),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @GetMapping("/nao-vendidos")
    ResponseEntity<Long> countNaoVendidos();

    @Operation(
            summary = "Contar veículos por década",
            description = "Retorna a distribuição de veículos por década",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Distribuição retornada com sucesso",
                            content = @Content(schema = @Schema(implementation = Object[].class),
                                    examples = @ExampleObject(value = "[{\"decada\":\"2010-2019\",\"quantidade\":5},{\"decada\":\"2020-2029\",\"quantidade\":10}]"))),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @GetMapping("/distribuicao-decada")
    ResponseEntity<List<String>> countByDecada();

    @Operation(
            summary = "Contar veículos por fabricante",
            description = "Retorna a distribuição de veículos por fabricantes",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Distribuição por fabricantes retornada com sucesso",
                            content = @Content(schema = @Schema(implementation = Object[].class),
                                    examples = @ExampleObject(value = "[{\"marca\":\"Fiat\",\"quantidade\":7},{\"marca\":\"Volkswagen\",\"quantidade\":8}]"))),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @GetMapping("/distribuicao-fabricante")
    ResponseEntity<List<String>> countByFabricantes();

    @Operation(
            summary = "Listar veículos cadastrados na última semana",
            description = "Retorna uma lista de veículos cadastrados na última semana",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Veículos encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Veiculo.class),
                                    examples = @ExampleObject(value = "{\"content\":[" +
                                            "{\"id\":4,\"veiculo\":\"Punto\",\"marca\":\"Fiat\",\"ano\":2018,\"descricao\":\"Carro usado...\",\"vendido\":false,\"created\":\"2024-08-05T10:00:00\",\"updated\":\"2024-08-05T11:00:00\"}," +
                                            "{\"id\":5,\"veiculo\":\"Cruze\",\"marca\":\"Chevrolet\",\"ano\":2021,\"descricao\":\"Carro seminovo...\",\"vendido\":true,\"created\":\"2024-08-04T09:00:00\",\"updated\":\"2024-08-04T10:00:00\"}]," +
                                            "\"pageable\":\"INSTANCE\",\"totalPages\":1,\"totalElements\":2,\"last\":true,\"size\":20,\"number\":0,\"sort\":{\"sorted\":true,\"unsorted\":false,\"empty\":false},\"first\":true,\"numberOfElements\":2,\"empty\":false}"))),
                    @ApiResponse(responseCode = "204", description = "Nenhum veículo encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @GetMapping("/ultima-semana")
    ResponseEntity<List<Veiculo>> findCadastradosNaUltimaSemana();

    @Operation(
            summary = "Filtrar veículos por marca, ano e cor",
            description = "Retorna uma lista de veículos filtrados por marca, ano e cor",
            responses = {
                    @ApiResponse(
                            responseCode = "200", description = "Veículos encontrados",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = Veiculo.class),
                                    examples = @ExampleObject(value = "{\"content\":[" +
                                            "{\"id\":1,\"veiculo\":\"Fiat Uno\",\"marca\":\"Fiat\",\"ano\":2015,\"descricao\":\"Carro popular...\",\"vendido\":false,\"created\":\"2024-08-01T10:00:00\",\"updated\":\"2024-08-01T12:00:00\"}]," +
                                            "\"pageable\":\"INSTANCE\",\"totalPages\":1,\"totalElements\":1,\"last\":true,\"size\":20,\"number\":0,\"sort\":{\"sorted\":true,\"unsorted\":false,\"empty\":false},\"first\":true,\"numberOfElements\":1,\"empty\":false}"))),
                    @ApiResponse(responseCode = "204", description = "Nenhum veículo encontrado"),
                    @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
            }
    )
    @GetMapping("/filtro")
    ResponseEntity<List<Veiculo>> findByMarcaAnoCor(
            @Parameter(description = "Marca do veículo (opcional)") @RequestParam(required = false) String marca,
            @Parameter(description = "Ano do veículo (opcional)") @RequestParam(required = false) Integer ano,
            @Parameter(description = "Cor do veículo (opcional)") @RequestParam(required = false) String cor
    );
}