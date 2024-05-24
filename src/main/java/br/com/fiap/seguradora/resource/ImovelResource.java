package br.com.fiap.seguradora.resource;

import br.com.fiap.seguradora.dto.request.ImovelRequest;
import br.com.fiap.seguradora.dto.response.ImovelResponse;
import br.com.fiap.seguradora.entity.Imovel;
import br.com.fiap.seguradora.service.ImovelService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.Collection;

@RestController
@RequestMapping(value = "/imoveis")
public class ImovelResource implements ResourceDTO<ImovelRequest, ImovelResponse> {

    @Autowired
    private ImovelService service;


    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<ImovelResponse> findById(@PathVariable Long id) {

        var entity = service.findById(id);
        if (entity.isEmpty()) return ResponseEntity.notFound().build();
        var response = service.toResponse(entity.orElse(null));
        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping
    @Override
    public ResponseEntity<ImovelResponse> save(@RequestBody @Valid ImovelRequest r) {
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
    public ResponseEntity<Collection<ImovelResponse>> findAll(
            @RequestParam(name = "descricao", required = false) String descricao
    ) {
        var imoveis = Imovel.builder()
                .descricao(descricao)
                .build();

        var matcher = ExampleMatcher.matching()
                .withIgnoreCase()
                .withIgnoreNullValues();

        Example<Imovel> example = Example.of(imoveis, matcher);

        var entities = service.findAll(example);

        if (entities.isEmpty()) return ResponseEntity.notFound().build();

        var response = entities.stream().map(service::toResponse).toList();

        return ResponseEntity.ok(response);
    }

}