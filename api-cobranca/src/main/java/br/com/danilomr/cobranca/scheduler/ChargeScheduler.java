package br.com.danilomr.cobranca.scheduler;

import br.com.danilomr.cobranca.dto.ChargeDto;
import br.com.danilomr.cobranca.entity.Debit;
import br.com.danilomr.cobranca.enums.DebitSituation;
import br.com.danilomr.cobranca.producer.ChargeProducer;
import br.com.danilomr.cobranca.producer.RestrictionProducer;
import br.com.danilomr.cobranca.service.DebitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

import static org.springframework.util.CollectionUtils.isEmpty;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChargeScheduler {

    public static final DecimalFormat VALUE_FORMAT = new DecimalFormat("#,###.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
    private static final String CHARGE_MESSAGE_PATTERN = "Prezado %s, \n" +
            "Você possui um débito no valor de %s. \n" +
            "Pedimos que entre em contato com nossa equipe, temos uma condição especial para você. \n" +
            "Evite a negativação do seu CPF.";

    private static final String CHARGE_RESTRICTED_MESSAGE_PATTERN = "Prezado %s, \n" +
            "Você possui uma restrição em seu CPF devido a um débito no valor de %s. \n" +
            "Pague hoje e limpe o seu nome.";

    private final DebitService debitService;
    private final ChargeProducer chargeProducer;
    private final RestrictionProducer restrictionProducer;

    @Value("${charges.attempts}")
    private Integer attempts;

    @Scheduled(fixedRate = 60000) // 1 minuto
    public void charge() {

        log.debug("Buscando débitos em aberto");
        final List<Debit> debits = debitService.getOpenDebits();
        if (isEmpty(debits)) {
            log.debug("0 registro encontrado");
            return;
        }
        log.debug(debits.size() + " registro(s) encontrado(s)");
        debits.forEach(debit -> {

            final String value = VALUE_FORMAT.format(debit.getDebitValue());

            if (DebitSituation.ABERTO.equals(debit.getDebitSituation()) && debit.getAmountOfCharges() < attempts) {
                final String message = String.format(CHARGE_MESSAGE_PATTERN, debit.getClientName(), value);
                final var chargeDto = saveCharge(debit, message);
                chargeProducer.sendChargeEmail(chargeDto);
                return;
            }

            if(DebitSituation.ABERTO.equals(debit.getDebitSituation()) && debit.getAmountOfCharges() == attempts) {
                log.warn("O cliente foi protestado");
                debit.setDebitSituation(DebitSituation.PROTESTADO);
                debitService.saveDebit(debit);
                restrictionProducer.addRestriction(debit);
            }

            final String message = String.format(CHARGE_RESTRICTED_MESSAGE_PATTERN, debit.getClientName(), value);
            final var chargeDto = saveCharge(debit, message);
            chargeProducer.sendChargeEmail(chargeDto);
        });
    }

    private ChargeDto saveCharge(final Debit debit, final String message) {

        final var chargeDto = ChargeDto.builder()
                .email(debit.getClientEmail())
                .message(message)
                .build();
        debit.addCharge(chargeDto.toCharge());
        debitService.saveDebit(debit);

        return chargeDto;
    }
}
