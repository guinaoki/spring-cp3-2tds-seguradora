package br.com.fiap.seguradora.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record AbstractRequest(

        @Positive(message = "O Id tem que ser positivo")
        @NotNull(message = "O Id é obrigatório")
        Long id
) {
}