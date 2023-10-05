package br.com.danilomr.cobranca.dto;

import br.com.danilomr.cobranca.entity.Debit;
import br.com.danilomr.cobranca.enums.DebitSituation;
import br.com.danilomr.cobranca.enums.DebitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DebitDto {

    private DebitType debitType;
    private String clientCpf;
    private String clientEmail;
    private String clientName;
    private BigDecimal debitValue;

    public Debit toDebit() {

        return Debit.builder()
                .debitType(this.debitType)
                .clientCpf(this.clientCpf)
                .debitValue(this.debitValue)
                .clientEmail(this.clientEmail)
                .clientName(this.clientName)
                .debitSituation(DebitSituation.ABERTO)
                .build();
    }
}
