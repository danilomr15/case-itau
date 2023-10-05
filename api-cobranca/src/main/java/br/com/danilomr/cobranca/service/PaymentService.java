package br.com.danilomr.cobranca.service;

import br.com.danilomr.cobranca.entity.Debit;
import br.com.danilomr.cobranca.entity.Payment;
import br.com.danilomr.cobranca.enums.DebitSituation;
import br.com.danilomr.cobranca.producer.RestrictionProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final DebitService debitService;
    private final RestrictionProducer restrictionProducer;

    public Payment pay(final Long debitId, final BigDecimal paidValue) {

        final Debit debit = debitService.findById(debitId);

        final Payment payment = Payment.builder()
                .paidValue(paidValue)
                .paymentDate(LocalDateTime.now())
                .debitSettlement(paidValue.compareTo(debit.getDebitValue()) >= 0)
                .build();

        if(payment.getDebitSettlement()) {
            if(DebitSituation.PROTESTADO.equals(debit.getDebitSituation())) {
                restrictionProducer.deleteRestriction(debit);
            }
            debit.setDebitSituation(DebitSituation.PAGO);
        }

        debit.addPayment(payment);
        debit.setDebitValue(debit.getDebitValue().subtract(paidValue));
        debitService.saveDebit(debit);
        return payment;
    }
}
