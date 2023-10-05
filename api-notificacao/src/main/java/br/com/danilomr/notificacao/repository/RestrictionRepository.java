package br.com.danilomr.notificacao.repository;

import br.com.danilomr.notificacao.entity.Restriction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestrictionRepository extends JpaRepository<Restriction, Long> {

    Page<Restriction> findAllByClientCpf(final String clientCpf, final Pageable pageable);
}
