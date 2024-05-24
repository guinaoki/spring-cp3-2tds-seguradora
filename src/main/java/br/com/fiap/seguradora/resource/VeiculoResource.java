package br.com.fiap.seguradora.resource;

import br.com.fiap.seguradora.dto.request.VeiculoRequest;
import br.com.fiap.seguradora.dto.response.VeiculoResponse;
import br.com.fiap.seguradora.entity.Veiculo;
import br.com.fiap.seguradora.service.VeiculoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.time.Year;
import java.util.Collection;

@RestController
@RequestMapping(value = "/veiculos")
public class VeiculoResource implements ResourceDTO<VeiculoRequest, VeiculoResponse> {

    @Autowired
    private VeiculoService service;


    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<VeiculoResponse> findById(@PathVariable Long id) {

        var entity = service.findById(id);
        if (entity.isEmpty()) return ResponseEntity.notFound().build();
        var response = service.toResponse(entity.orElse(null));
        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping
    @Override
    public ResponseEntity<VeiculoResponse> save(@RequestBody @Valid VeiculoRequest r) {
        var entity = service.toEntity(r);
        entity = service.save(entity);

        var response = service.toResponse(entity);

        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entity.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Collection<VeiculoResponse>> findAll(
            @RequestParam(name = "placa", required = false) String placa,
            @RequestParam(name = "modelo", required = false) String modelo,
            @RequestParam(name = "cor", required = false) String cor,
            @RequestParam(name = "marca", required = false) String marca,
            @RequestParam(name = "ano", required = false) Year ano
    ) {
        var veiculos = Veiculo.builder()
                .placa(placa)
                .modelo(modelo)
                .cor(cor)
                .marca(marca)
                .ano(ano)
                .build();

        var matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues();

        Example<Veiculo> example = Example.of(veiculos, matcher);

        var entities = service.findAll(example);

        if (entities.isEmpty()) return ResponseEntity.notFound().build();

        var response = entities.stream().map(service::toResponse).toList();

        return ResponseEntity.ok(response);
    }

}