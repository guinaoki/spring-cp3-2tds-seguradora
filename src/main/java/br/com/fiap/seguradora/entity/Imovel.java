package br.com.fiap.seguradora.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "TB_IMOVEL", uniqueConstraints = {
        @UniqueConstraint(name = "UK_IMOVEL_MATRICULA", columnNames = "MATRICULA")
})
public class Imovel extends Asseguravel {

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "METROS_QUADRADOS")
    private Float metrosQuadrados;

    @Column(name = "QUARTOS")
    private Integer quartos;

    @Column(name = "BANHEIROS")
    private Integer banheiros;

    @Column(name = "VAGAS_GARAGEM")
    private Integer vagasGaragem;

    @Column(name = "MATRICULA")
    private String matricula;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_IMOVEL")
    private TipoSeguro tipo;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "TB_IMOVEL_FOTO",
            joinColumns = {
                    @JoinColumn(
                            name = "IMOVEL",
                            referencedColumnName = "ID_ASSEGURAVEL",
                            foreignKey = @ForeignKey(
                                    name = "FK_IMOVEL_FOTO"
                            )
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "FOTO",
                            referencedColumnName = "ID_FOTO",
                            foreignKey = @ForeignKey(
                                    name = "FK_FOTO_IMOVEL"
                            )
                    )
            }
    )
    private Set<Foto> fotos = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(
            name = "ENDERECO",
            referencedColumnName = "ID_ENDERECO",
            foreignKey = @ForeignKey(
                    name = "FK_IMOVEL_DOCUMENTO"
            )
    )
    private Endereco endereco;
}