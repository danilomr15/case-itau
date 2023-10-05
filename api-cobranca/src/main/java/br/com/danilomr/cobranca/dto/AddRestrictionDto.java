package br.com.danilomr.cobranca.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

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
}
