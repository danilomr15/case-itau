package br.com.danilomr.cobranca.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    @NotNull(message = "Field 'debitId' is mandatory")
    private Long debitId;

    @NotNull(message = "Field 'paidValue' is mandatory")
    private BigDecimal paidValue;

}
