package br.com.fiap.seguradora.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "TB_DOCUMENTO",
        uniqueConstraints = {
        @UniqueConstraint(name = "UK_DOCUMENTO_TIPO",columnNames = "TIPO_DOCUMENTO"),
        @UniqueConstraint(name = "UK_DOCUMENTO_NUMERO",columnNames = "NOME_DOCUMENTO")
})
public class Documento {

    @Id
    @SequenceGenerator(name = "SQ_DOCUMENTO", sequenceName = "SQ_DOCUMENTO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_DOCUMENTO")
    @Column(name = "ID_DOCUMENTO")
    private Long id;

    @Column(name = "NOME_DOCUMENTO")
    private String numero;

    @Enumerated(EnumType.STRING)
    @Column(name = "TIPO_DOCUMENTO")
    private TipoDocumento tipo;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "TB_DOCUMENTO_FOTOS",
            joinColumns = {
                    @JoinColumn(
                            name = "DOCUMENTO",
                            referencedColumnName = "ID_DOCUMENTO",
                            foreignKey = @ForeignKey(
                                    name = "FK_DOCUMENTO_FOTO"
                            )
                    )
            },
            inverseJoinColumns = {
                    @JoinColumn(
                            name = "FOTO",
                            referencedColumnName = "ID_FOTO",
                            foreignKey = @ForeignKey(
                                    name = "FK_FOTO_DOCUMENTO"
                            )
                    )
            }
    )
    private Set<Foto> fotos = new LinkedHashSet<>();
}