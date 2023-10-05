package br.com.danilomr.notificacao.consumer;

import br.com.danilomr.notificacao.dto.AddRestrictionDto;
import br.com.danilomr.notificacao.dto.ChargeDto;
import br.com.danilomr.notificacao.dto.DeleteRestrictionDto;
import br.com.danilomr.notificacao.service.EmailService;
import br.com.danilomr.notificacao.service.RestrictionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestrictionConsumer {

    private static final String INVALID_MESSAGE_FORMAT = "The message format is invalid = [%s]";

    private final ObjectMapper objectMapper;
    private final RestrictionService restrictionService;
    private final EmailService emailService;

    @RabbitListener(queues = {"${queues.add-restriction-queue}"})
    public void addRestriction(@Payload final String message) {

        log.debug("Message reveived: " + message);
        final var addRestrictionDto = parse(message, AddRestrictionDto.class);
        if(isNull(addRestrictionDto)) {
            return;
        }
        restrictionService.addRestriction(addRestrictionDto.toRestriction());
    }

    @RabbitListener(queues = {"${queues.delete-restriction-queue}"})
    public void deleteRestriction(@Payload final String message) {

        log.debug("Message reveived: " + message);
        final var deleteRestrictionDto = parse(message, DeleteRestrictionDto.class);
        if(isNull(deleteRestrictionDto)) {
            return;
        }
        restrictionService.deleteRestriction(deleteRestrictionDto.getId());
    }

    @RabbitListener(queues = {"${queues.charge-queue}"})
    public void charge(@Payload final String message) {

        log.debug("Message reveived: " + message);
        final var chargeDto = parse(message, ChargeDto.class);
        if(isNull(chargeDto)) {
            return;
        }
        emailService.sendEmail(chargeDto.getEmail(), chargeDto.getMessage());
    }

    private <T> T parse(final String message, final Class<T> type) {

        try {
            return objectMapper.readValue(message, type);
        } catch (JsonProcessingException e) {
            log.error(String.format(INVALID_MESSAGE_FORMAT, message));
            return null;
        }
    }
}
