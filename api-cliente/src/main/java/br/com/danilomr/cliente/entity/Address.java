package br.com.danilomr.cliente.entity;

import br.com.danilomr.cliente.dto.AddressDto;
import br.com.danilomr.cliente.enums.AddressType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "enderecos")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    @Column(name = "logradouro")
    private String street;

    @Column(name = "numero")
    private String number;

    @Column(name = "complemento")
    private String complement;

    @Column(name = "bairro")
    private String neighborhood;

    @Column(name = "cep")
    private String zipcode;

    @Column(name = "cidade")
    private String city;

    @Column(name = "estado")
    private String state;

    @Column(name = "pais")
    private String country;

    @Column(name = "ativo")
    private Boolean active;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Client client;

    public boolean sameOf(@NotNull final Address address) {

        return address.getStreet().equalsIgnoreCase(this.street) &&
                address.getNumber().equalsIgnoreCase(this.number) &&
                address.getComplement().equalsIgnoreCase(this.complement) &&
                address.getNeighborhood().equalsIgnoreCase(this.neighborhood) &&
                address.getZipcode().equalsIgnoreCase(this.zipcode) &&
                address.getCity().equalsIgnoreCase(this.city) &&
                address.getState().equalsIgnoreCase(this.state) &&
                address.getCountry().equalsIgnoreCase(this.country);
    }

    public AddressDto toAddressDto() {

        return AddressDto.builder()
                .id(this.id)
                .street(this.street)
                .number(this.number)
                .complement(this.complement)
                .neighborhood(this.neighborhood)
                .zipcode(this.zipcode)
                .city(this.city)
                .state(this.state)
                .country(this.country)
                .addressType(this.addressType)
                .active(this.active)
                .build();
    }
}
