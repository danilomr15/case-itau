package br.com.danilomr.notificacao.entity;

import br.com.danilomr.notificacao.dto.RestrictionDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "restricoes")
public class Restriction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "data_restricao")
    private LocalDate date;

    @Column(name = "cpf_cliente")
    private String clientCpf;

    @Column(name = "nome_cliente")
    private String name;

    @Column(name = "id_debito")
    private Long debitId;

    @Column(name = "email")
    private String email;

    @Column(name = "descricao")
    private String description;

    @Column(name = "emissor")
    private String issuer;

    @Column(name = "valor_debito")
    private BigDecimal debitValue;

    public RestrictionDto toRestrictionDto() {

        return RestrictionDto.builder()
                .id(this.id)
                .date(this.date)
                .clientCpf(this.clientCpf)
                .name(this.name)
                .debitId(this.debitId)
                .email(this.email)
                .description(this.description)
                .issuer(this.issuer)
                .debitValue(this.debitValue)
                .build();
    }
}
