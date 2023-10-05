package br.com.danilomr.cobranca.consumer;

import br.com.danilomr.cobranca.dto.DebitDto;
import br.com.danilomr.cobranca.service.DebitService;
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
public class DebitConsumer {

    private static final String INVALID_MESSAGE_FORMAT = "The message format is invalid = [%s]";

    private final ObjectMapper objectMapper;
    private final DebitService debitService;

    @RabbitListener(queues = {"${queues.add-debit-queue}"})
    public void addDebit(@Payload final String message) {

        log.debug("Message reveived: " + message);
        final DebitDto debitDto = parse(message, DebitDto.class);
        if(isNull(debitDto)) {
            return;
        }
        debitService.saveDebit(debitDto.toDebit());
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
