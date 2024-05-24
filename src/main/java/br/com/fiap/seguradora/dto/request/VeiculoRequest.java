package br.com.fiap.seguradora.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.Year;

public record VeiculoRequest(

        @NotNull(message = "Chassis é obrigatório")
        String chassis,

        @NotNull(message = "Placa é obrigatório")
        String placa,

        String modelo,

        String cor,

        String marca,

        @PastOrPresent
        Year ano,

        BigDecimal valor

) {
}