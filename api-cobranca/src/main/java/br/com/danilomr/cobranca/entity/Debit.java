package br.com.danilomr.cobranca.entity;

import br.com.danilomr.cobranca.dto.AddRestrictionDto;
import br.com.danilomr.cobranca.dto.DeleteRestrictionDto;
import br.com.danilomr.cobranca.dto.GetDebitDto;
import br.com.danilomr.cobranca.enums.DebitSituation;
import br.com.danilomr.cobranca.enums.DebitType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static org.springframework.util.CollectionUtils.isEmpty;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "debitos")
public class Debit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tipo")
    @Enumerated(EnumType.STRING)
    private DebitType debitType;

    @Column(name = "cpf_cliente")
    private String clientCpf;

    @Column(name = "email_cliente")
    private String clientEmail;

    @Column(name = "nome_cliente")
    private String clientName;

    @Column(name = "valor_debito")
    private BigDecimal debitValue;

    @Column(name = "situacao")
    @Enumerated(EnumType.STRING)
    private DebitSituation debitSituation;

    @OneToMany(mappedBy = "debit", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Payment> payments;

    @OneToMany(mappedBy = "debit", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Charge> charges;

    public int getAmountOfCharges() {

        return isEmpty(getCharges()) ? 0 : getCharges().size();
    }

    public GetDebitDto toGetDebitDto() {

        return GetDebitDto.builder()
                .id(this.id)
                .debitType(this.debitType)
                .clientCpf(this.clientCpf)
                .clientEmail(this.clientEmail)
                .clientName(this.clientName)
                .debitValue(this.debitValue)
                .debitSituation(this.debitSituation)
                .build();
    }

    public AddRestrictionDto toAddRestrictionDto() {

        return AddRestrictionDto.builder()
                .clientCpf(this.clientCpf)
                .clientName(this.clientName)
                .debitId(this.id)
                .description(this.debitType.toString())
                .issuer("Itaú Unibanco SA")
                .email(this.clientEmail)
                .debitValue(this.debitValue)
                .build();
    }

    public DeleteRestrictionDto toDeleteRestrictionDto() {

        return DeleteRestrictionDto.builder()
                .id(this.id)
                .build();
    }

    public void addPayment(final Payment payment) {

        if(isNull(getPayments())) {
            setPayments(new ArrayList<>());
        }

        payment.setDebit(this);
        getPayments().add(payment);
    }

    public void addCharge(final Charge charge) {

        if(isNull(getCharges())) {
            setCharges(new ArrayList<>());
        }

        charge.setDebit(this);
        getCharges().add(charge);
    }
}
