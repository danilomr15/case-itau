package br.com.danilomr.cliente.dto;

import br.com.danilomr.cliente.entity.Address;
import br.com.danilomr.cliente.enums.AddressType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    private Long id;

    @NotNull(message = "Field 'addressType' is mandatory")
    private AddressType addressType;

    @NotNull(message = "Field 'street' is mandatory")
    private String street;

    @NotNull(message = "Field 'number' is mandatory")
    private String number;

    private String complement;

    @NotNull(message = "Field 'neighborhood' is mandatory")
    private String neighborhood;

    @NotNull(message = "Field 'zipcode' is mandatory")
    private String zipcode;

    @NotNull(message = "Field 'city' is mandatory")
    private String city;

    @NotNull(message = "Field 'state' is mandatory")
    private String state;

    @NotNull(message = "Field 'country' is mandatory")
    private String country;

    private Boolean active;

    public Address toAddress() {

        return Address.builder()
                .addressType(this.addressType)
                .street(this.street)
                .number(this.number)
                .complement(this.complement)
                .neighborhood(this.neighborhood)
                .zipcode(this.zipcode)
                .city(this.city)
                .state(this.state)
                .country(this.country)
                .active(true)
                .build();
    }
}
