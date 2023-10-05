package br.com.danilomr.cliente.entity;

import br.com.danilomr.cliente.dto.AddressDto;
import br.com.danilomr.cliente.dto.ClientDto;
import br.com.danilomr.cliente.dto.PhoneDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static org.apache.commons.lang3.BooleanUtils.isTrue;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clientes")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String name;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "data_nasc")
    private LocalDate birthdate;

    @Column(name = "email")
    private String email;

    @Column(name = "ativo")
    private Boolean active;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Phone> phoneList;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Address> addressList;

    public ClientDto toClientDto() {

        final List<PhoneDto> phoneListDto = isNull(this.phoneList) ? new ArrayList<>() :
                this.phoneList.stream()
                        .filter(phone -> isTrue(phone.getActive()))
                        .map(Phone::toPhoneDto)
                        .toList();
        final List<AddressDto> addressListDto = isNull(this.addressList) ? new ArrayList<>() :
                this.addressList.stream()
                        .filter(address -> isTrue(address.getActive()))
                        .map(Address::toAddressDto)
                        .toList();

        return ClientDto.builder()
                .id(this.id)
                .name(this.name)
                .cpf(this.cpf)
                .birthdate(this.birthdate)
                .active(this.active)
                .phoneList(phoneListDto)
                .addressList(addressListDto)
                .build();
    }
}
