package br.com.danilomr.cliente.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto {

    private Long id;
    private String name;
    private String cpf;
    private LocalDate birthdate;
    private Boolean active;
    private List<PhoneDto> phoneList;
    private List<AddressDto> addressList;
}
