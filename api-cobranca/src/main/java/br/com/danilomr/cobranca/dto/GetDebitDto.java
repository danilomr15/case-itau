package br.com.danilomr.cobranca.dto;

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
public class GetDebitDto {

    private Long id;
    private DebitType debitType;
    private String clientCpf;
    private String clientEmail;
    private String clientName;
    private BigDecimal debitValue;
    private DebitSituation debitSituation;
}
