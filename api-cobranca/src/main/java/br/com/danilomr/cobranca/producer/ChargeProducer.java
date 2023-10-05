package br.com.danilomr.cobranca.producer;

import br.com.danilomr.cobranca.dto.ChargeDto;
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
public class ChargeProducer {

    private static final String JSON_PARSE_ERROR = "Error while trying to parse object in json; ERROR: [%s]";

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Value("${exchanges.charge-email-exchange}")
    private String chargeExchange;

    @Value("${routing-keys.charge-email-routing-key}")
    private String chargeRoutingKey;

    public void sendChargeEmail(final ChargeDto chargeDto) {

        try {
            rabbitTemplate.convertAndSend(chargeExchange, chargeRoutingKey, objectMapper.writeValueAsString(chargeDto));
        } catch (JsonProcessingException ex) {
            log.error(String.format(JSON_PARSE_ERROR, ex.getMessage()));
        }
    }
}
