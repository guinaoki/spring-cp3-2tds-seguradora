package br.com.fiap.seguradora.resource;

import br.com.fiap.seguradora.dto.request.SeguroRequest;
import br.com.fiap.seguradora.dto.response.SeguroResponse;
import br.com.fiap.seguradora.entity.Seguro;
import br.com.fiap.seguradora.service.SeguroService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.time.LocalDate;
import java.util.Collection;

@RestController
@RequestMapping(value = "/seguros")
public class SeguroResource implements ResourceDTO<SeguroRequest, SeguroResponse> {

    @Autowired
    private SeguroService service;

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<SeguroResponse> findById(@PathVariable Long id) {

        var entity = service.findById(id);
        if (entity.isEmpty()) return ResponseEntity.notFound().build();
        var response = service.toResponse(entity.orElse(null));
        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping
    @Override
    public ResponseEntity<SeguroResponse> save(@RequestBody @Valid SeguroRequest r) {
        var entity = service.toEntity(r);
        if(entity == null) return ResponseEntity.badRequest().build();
        entity = service.save(entity);

        var response = service.toResponse(entity);

        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entity.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }


    @GetMapping
    public ResponseEntity<Collection<SeguroResponse>> findAll(
            @RequestParam(name = "inicio", required = false) LocalDate inicio,
            @RequestParam(name = "fim", required = false) LocalDate fim

    ){
        var seguros = Seguro.builder()
                .inico(inicio)
                .fim(fim)
                .build();

        var matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues();

        Example<Seguro> example = Example.of(seguros, matcher);

        var entities = service.findAll(example);

        if (entities.isEmpty()) return ResponseEntity.notFound().build();

        var response = entities.stream().map(service::toResponse).toList();

        return ResponseEntity.ok(response);
    }
}