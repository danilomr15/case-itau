package br.com.danilomr.cliente.repository;

import br.com.danilomr.cliente.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhoneRepository extends JpaRepository<Phone, Long> {

    Optional<Phone> findByDddAndNumberAndClientId(Integer ddd, String number, Long id);
}
