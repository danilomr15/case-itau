package br.com.danilomr.cliente.dto;

import br.com.danilomr.cliente.entity.Phone;
import br.com.danilomr.cliente.enums.PhoneType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PhoneDto {

    private Long id;

    @NotNull(message = "Field 'phoneType' is mandatory")
    private PhoneType phoneType;

    @NotNull(message = "Field 'ddd' is mandatory")
    private Integer ddd;

    @NotBlank(message = "Field 'number' is mandatory")
    @Pattern(regexp="^[0-9]{8,}$", message = "Field 'number' should contain at least 8 numeric characters")
    private String number;

    private Boolean active;

    public Phone toPhone() {

        return Phone.builder()
                .phoneType(this.phoneType)
                .ddd(this.ddd)
                .number(this.number)
                .active(true)
                .build();
    }
}
