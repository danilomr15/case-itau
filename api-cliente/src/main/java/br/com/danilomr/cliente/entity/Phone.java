package br.com.danilomr.cliente.entity;

import br.com.danilomr.cliente.dto.PhoneDto;
import br.com.danilomr.cliente.enums.PhoneType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "telefones")
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private PhoneType phoneType;

    @Column(name = "ddd")
    private Integer ddd;

    @Column(name = "numero")
    private String number;

    @Column(name = "ativo")
    private Boolean active;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", referencedColumnName = "id")
    private Client client;

    public boolean sameOf(@NotNull final Phone phone) {

        return phone.getPhoneType().equals(this.phoneType) &&
                phone.getDdd().equals(this.ddd) &&
                phone.getNumber().equals(this.number);
    }

    public PhoneDto toPhoneDto() {

        return PhoneDto.builder()
                .id(this.id)
                .ddd(this.ddd)
                .number(this.number)
                .phoneType(this.phoneType)
                .active(this.active)
                .build();
    }
}
