package br.com.fiap.seguradora.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record EnderecoRequest(

        @NotNull(message = "Logradouro é obrigatório")
        String logradouro,

        @NotNull(message = "Número é obrigatório")
        String numero,

        @NotNull(message = "Complemento é obrigatório")
        String complemento,

        @NotNull(message = "CEP é obrigatório")
        @Pattern(regexp = "\\d{5}-\\d{3}", message = "O CEP deve ser assim = XXXXX-XXX")
        String cep
) {
}