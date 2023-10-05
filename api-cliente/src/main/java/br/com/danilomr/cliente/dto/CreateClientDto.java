package br.com.danilomr.cliente.dto;

import br.com.danilomr.cliente.entity.Client;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientDto {

    @NotBlank(message = "Field 'name' is mandatory")
    private String name;

    @NotBlank(message = "Field 'cpf' is mandatory")
    @Size(min = 11, max = 11, message = "Field 'cpf' should contain 11 numeric characters")
    @Pattern(regexp="^[0-9]{11}$", message = "Field 'cpf' should contain 11 numeric characters")
    private String cpf;

    @NotNull(message = "Field 'birthdate' is mandatory")
    private LocalDate birthdate;

    @Email(message = "Field 'email' is invalid")
    @NotBlank(message = "Field 'email' is mandatory")
    private String email;

    public Client toClient() {

        return Client.builder()
                .name(this.name)
                .cpf(this.cpf)
                .birthdate(this.birthdate)
                .email(this.email)
                .active(true)
                .build();
    }
}
