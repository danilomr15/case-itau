package br.com.danilomr.cliente.dto;

import br.com.danilomr.cliente.entity.Client;
import jakarta.validation.constraints.AssertTrue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClientDto {

    private String name;
    private String email;
    private LocalDate birthdate;

    @AssertTrue(message = "Please fill the client 'name', 'email' or 'cpf'")
    private boolean isValidInvestmentName() {

        return isFalse(isBlank(this.name) && isNull(this.birthdate) && isBlank(this.email));
    }

    public Client toClient() {

        return Client.builder()
                .name(this.name)
                .birthdate(this.birthdate)
                .email(this.email)
                .build();
    }
}
