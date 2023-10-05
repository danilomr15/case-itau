package br.com.danilomr.cliente.repository;

import br.com.danilomr.cliente.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByStreetAndNumberAndComplementAndClientId(String street, String number, String complement, Long clientId);
}
