package br.com.danilomr.cobranca.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptDto {

    private LocalDateTime paymentDate;
    private BigDecimal paidValue;
    private String clientCpf;
    private String clientName;

}
