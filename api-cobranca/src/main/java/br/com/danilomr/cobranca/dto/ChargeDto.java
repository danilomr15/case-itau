package br.com.danilomr.cobranca.dto;

import br.com.danilomr.cobranca.entity.Charge;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChargeDto {

    private String email;
    private String message;

    public Charge toCharge() {

        return Charge.builder()
                .message(this.message)
                .email(this.email)
                .chargeDate(LocalDateTime.now())
                .build();
    }
}
