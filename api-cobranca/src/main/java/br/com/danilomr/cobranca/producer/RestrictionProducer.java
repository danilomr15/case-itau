package br.com.danilomr.cobranca.producer;

import br.com.danilomr.cobranca.entity.Debit;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestrictionProducer {

    private static final String JSON_PARSE_ERROR = "Error while trying to parse object in json; ERROR: [%s]";

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Value("${exchanges.add-restriction-exchange}")
    private String addRestrictionExchange;

    @Value("${routing-keys.add-restriction-routing-key}")
    private String addRestrictionRoutingKey;

    @Value("${exchanges.delete-restriction-exchange}")
    private String deleteRestrictionExchange;

    @Value("${routing-keys.delete-restriction-routing-key}")
    private String deleteRestrictionRoutingKey;

    public void addRestriction(final Debit debit) {

        try {
            rabbitTemplate.convertAndSend(addRestrictionExchange, addRestrictionRoutingKey, objectMapper.writeValueAsString(debit.toAddRestrictionDto()));
        } catch (JsonProcessingException ex) {
            log.error(String.format(JSON_PARSE_ERROR, ex.getMessage()));
        }
    }

    public void deleteRestriction(final Debit debit) {

        try {
            rabbitTemplate.convertAndSend(deleteRestrictionExchange, deleteRestrictionRoutingKey, objectMapper.writeValueAsString(debit.toDeleteRestrictionDto()));
        } catch (JsonProcessingException ex) {
            log.error(String.format(JSON_PARSE_ERROR, ex.getMessage()));
        }
    }
}
