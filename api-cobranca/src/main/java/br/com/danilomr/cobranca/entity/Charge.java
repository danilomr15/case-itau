package br.com.danilomr.cobranca.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cobrancas")
public class Charge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "data_cobranca")
    private LocalDateTime chargeDate;

    @Column(name = "mensagem")
    private String message;

    @Column(name = "email")
    private String email;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_debito", referencedColumnName = "id")
    private Debit debit;
}
