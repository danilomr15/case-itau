package br.com.danilomr.notificacao.dto;

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
public class RestrictionDto {

    private Long id;
    private LocalDate date;
    private String clientCpf;
    private String name;
    private Long debitId;
    private String email;
    private String description;
    private String issuer;
    private BigDecimal debitValue;
}
