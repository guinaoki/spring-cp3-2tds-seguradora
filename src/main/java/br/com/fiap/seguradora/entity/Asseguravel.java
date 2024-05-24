package br.com.fiap.seguradora.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "TB_ASSEGURAVEL")
public class Asseguravel {

    @Id
    @SequenceGenerator(name = "SQ_ASSEGURAVEL", sequenceName = "SQ_ASSEGURAVEL", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_ASSEGURAVEL")
    @Column(name = "ID_ASSEGURAVEL")
    private Long id;

    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    private TipoSeguro tipo;
}