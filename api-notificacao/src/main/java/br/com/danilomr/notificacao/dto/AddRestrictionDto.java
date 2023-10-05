package br.com.danilomr.notificacao.dto;

import br.com.danilomr.notificacao.entity.Restriction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddRestrictionDto {

    private String clientCpf;
    private String clientName;
    private Long debitId;
    private String description;
    private String issuer;
    private String email;
    private BigDecimal debitValue;

    public Restriction toRestriction() {

        return Restriction.builder()
                .date(LocalDate.now())
                .clientCpf(this.clientCpf)
                .debitId(this.debitId)
                .description(this.description)
                .issuer(this.issuer)
                .debitValue(this.debitValue)
                .email(this.email)
                .name(this.clientName)
                .build();
    }
}
