package br.com.danilomr.cobranca.entity;

import br.com.danilomr.cobranca.dto.ReceiptDto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pagamentos")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "valor_pagto")
    private BigDecimal paidValue;

    @Column(name = "liquidacao")
    private Boolean debitSettlement;

    @Column(name = "data_pagamento")
    private LocalDateTime paymentDate;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_debito", referencedColumnName = "id")
    private Debit debit;

    public ReceiptDto toReceiptDto() {

        return ReceiptDto.builder()
                .paymentDate(this.paymentDate)
                .paidValue(this.paidValue)
                .clientCpf(this.debit.getClientCpf())
                .clientName(this.debit.getClientName())
                .build();
    }
}
