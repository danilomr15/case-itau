package br.com.danilomr.notificacao.service;

import br.com.danilomr.notificacao.entity.Restriction;
import br.com.danilomr.notificacao.repository.RestrictionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestrictionService {

    private final RestrictionRepository restrictionRepository;

    public void addRestriction(final Restriction restriction) {

        restrictionRepository.save(restriction);
    }

    public void deleteRestriction(final Long restrictionId) {

        final var existingRestriction = restrictionRepository.findById(restrictionId);

        if (existingRestriction.isEmpty()) {
            return;
        }

        restrictionRepository.delete(existingRestriction.get());
    }

    public Page<Restriction> findRestrictionsByCpf(final String cpf, final int page, final int size) {

        return restrictionRepository.findAllByClientCpf(cpf, PageRequest.of(page, size));
    }
}
