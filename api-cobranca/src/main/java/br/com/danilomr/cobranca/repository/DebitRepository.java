package br.com.danilomr.cobranca.repository;

import br.com.danilomr.cobranca.entity.Debit;
import br.com.danilomr.cobranca.enums.DebitSituation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DebitRepository extends JpaRepository<Debit, Long> {

    List<Debit> findByDebitSituationIn(DebitSituation... situations);

    Page<Debit> findByClientCpf(String clientCpf, Pageable pageable);
}
