package br.com.danilomr.cobranca.controller;

import br.com.danilomr.cobranca.dto.GetDebitDto;
import br.com.danilomr.cobranca.entity.Debit;
import br.com.danilomr.cobranca.service.DebitService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/cobranca")
public class DebitController {

    private final DebitService debitService;

    @GetMapping("/clientes/{cpf}/debitos")
    public Page<GetDebitDto> getDebitsByCpf(@PathVariable("cpf") final String cpf,
                                            @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
                                            @RequestParam(name = "size", required = false, defaultValue = "10") final int size) {

        final Page<Debit> debitPage = debitService.findAllByClientCpf(cpf, page, size);
        final List<GetDebitDto> debitList = debitPage.getContent().stream()
                .map(Debit::toGetDebitDto)
                .toList();
        return new PageImpl<>(debitList, debitPage.getPageable(), debitPage.getTotalElements());
    }
}
