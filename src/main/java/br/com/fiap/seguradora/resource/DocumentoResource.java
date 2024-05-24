package br.com.fiap.seguradora.resource;

import br.com.fiap.seguradora.dto.request.DocumentoRequest;
import br.com.fiap.seguradora.dto.response.DocumentoResponse;
import br.com.fiap.seguradora.service.DocumentoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/documentos")
public class DocumentoResource implements ResourceDTO<DocumentoRequest, DocumentoResponse> {

    @Autowired
    private DocumentoService service;

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<DocumentoResponse> findById(@PathVariable Long id) {
        var entity = service.findById(id);
        if (entity.isEmpty()) return ResponseEntity.notFound().build();
        var response = service.toResponse(entity.orElse(null));
        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping
    @Override
    public ResponseEntity<DocumentoResponse> save(@RequestBody @Valid DocumentoRequest r) {
        var entity = service.toEntity(r);
        entity = service.save(entity);

        var response = service.toResponse(entity);

        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entity.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }
}