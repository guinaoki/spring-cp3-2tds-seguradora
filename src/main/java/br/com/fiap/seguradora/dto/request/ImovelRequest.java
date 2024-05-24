package br.com.fiap.seguradora.dto.request;


import br.com.fiap.seguradora.entity.TipoSeguro;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ImovelRequest(

        BigDecimal valor,

        @NotNull(message = "Descrição é obrigatório")
        String descricao,

        @Positive
        Float metrosQuadrados,

        @Positive
        Integer quartos,

        @Positive
        Integer banheiros,

        @Positive
        Integer vagasGaragem,

        TipoSeguro tipo,

        @Valid
        EnderecoRequest endereco,

        String matricula
) {
}