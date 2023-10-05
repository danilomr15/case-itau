package br.com.danilomr.cobranca.controller;

import br.com.danilomr.cobranca.dto.PaymentDto;
import br.com.danilomr.cobranca.dto.ReceiptDto;
import br.com.danilomr.cobranca.entity.Payment;
import br.com.danilomr.cobranca.service.PaymentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/cobranca")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payments")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ReceiptDto pay(@Valid @RequestBody final PaymentDto paymentDto) {

        final Payment payment = paymentService.pay(paymentDto.getDebitId(), paymentDto.getPaidValue());
        return payment.toReceiptDto();
    }
}
