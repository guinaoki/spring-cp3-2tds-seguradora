package br.com.fiap.seguradora.service;


import org.springframework.data.domain.Example;

import java.util.Collection;
import java.util.Optional;

/**
 * @param <Entity>    Entity
 * @param <Request>   RequestDTO
 * @param <Response>> ResponseDTO
 */
public interface ServiceDTO<Entity, Request, Response> {


    public Collection<Entity> findAll(Example<Entity> example);


    public Optional<Entity> findById(Long id);


    public Entity save(Entity e);


    public Entity toEntity(Request dto);

    /**
     * Transforma uma Entidade em um DTO de Resposta (ResponseDTO)
     *
     * @param e
     * @return
     */
    public Response toResponse(Entity e);


}
