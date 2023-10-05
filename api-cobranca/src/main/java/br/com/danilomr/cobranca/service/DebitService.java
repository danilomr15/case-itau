package br.com.danilomr.cobranca.service;

import br.com.danilomr.cobranca.entity.Debit;
import br.com.danilomr.cobranca.enums.DebitSituation;
import br.com.danilomr.cobranca.repository.DebitRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DebitService {

    private static final String DEBIT_NOT_FOUND = "Debit not found with id=%d";

    private final DebitRepository debitRepository;

    public Debit findById(final Long debitId) {

        return debitRepository.findById(debitId)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format(DEBIT_NOT_FOUND, debitId)));
    }

    public void saveDebit(final Debit debit) {

        debitRepository.save(debit);
    }

    public List<Debit> getOpenDebits() {

        return debitRepository.findByDebitSituationIn(DebitSituation.ABERTO, DebitSituation.PROTESTADO);
    }

    public Page<Debit> findAllByClientCpf(final String clientCpf, final int page, final int size) {

        try {
            return debitRepository.findByClientCpf(clientCpf, PageRequest.of(page, size));
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }

        return Page.empty();
    }
}
