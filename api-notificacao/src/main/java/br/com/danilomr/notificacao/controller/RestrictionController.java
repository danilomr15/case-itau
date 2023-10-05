package br.com.danilomr.notificacao.controller;

import br.com.danilomr.notificacao.dto.RestrictionDto;
import br.com.danilomr.notificacao.entity.Restriction;
import br.com.danilomr.notificacao.service.RestrictionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/notificacoes")
public class RestrictionController {

    private final RestrictionService restrictionService;

    @GetMapping("/restricoes/{cpf}")
    @ResponseStatus(HttpStatus.OK)
    public Page<RestrictionDto> findRestrictions(@PathVariable("cpf") final String cpf,
                                                 @RequestParam(name = "page", required = false, defaultValue = "0") final int page,
                                                 @RequestParam(name = "size", required = false, defaultValue = "10") final int size) {

        final Page<Restriction> restrictionPage = restrictionService.findRestrictionsByCpf(cpf, page, size);
        final List<RestrictionDto> restrictionList = restrictionPage.getContent().stream()
                .map(Restriction::toRestrictionDto)
                .toList();
        return new PageImpl<>(restrictionList, restrictionPage.getPageable(), restrictionPage.getTotalElements());
    }
}
