package br.com.danilomr.cliente.service;

import br.com.danilomr.cliente.entity.Address;
import br.com.danilomr.cliente.entity.Client;
import br.com.danilomr.cliente.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Optional;

import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@RequiredArgsConstructor
public class AddressService {

    private static final String ADDRESS_EXISTS = "Address with street=%s, number=%s and complement=%s exists and it is already active";
    private static final String ADDRESS_NOT_FOUND = "Address not found with id=%d";
    private static final String CLIENT_INACTIVE = "Cannot add an address for an inactive client";

    private final AddressRepository addressRepository;
    private final ClientService clientService;

    public Client addAddress(final Long clientId, final Address address) {

        final Client client = clientService.findById(clientId);
        if (isFalse(client.getActive())) {
            throw new HttpClientErrorException(HttpStatus.PRECONDITION_FAILED, CLIENT_INACTIVE);
        }
        final Optional<Address> existingAddress = addressRepository.findByStreetAndNumberAndComplementAndClientId(address.getStreet(), address.getNumber(), address.getComplement(), clientId);

        if (existingAddress.isEmpty()) {

            if (isEmpty(client.getAddressList())) {
                client.setAddressList(new ArrayList<>());
            }

            client.getAddressList().add(address);
            address.setClient(client);
            return clientService.update(client);
        }

        if (isFalse(existingAddress.get().getActive())) {
            client.getAddressList().stream()
                    .filter(add -> add.sameOf(address))
                    .findFirst()
                    .ifPresent(ph -> ph.setActive(true));
            return clientService.update(client);
        }

        throw new HttpClientErrorException(HttpStatus.CONFLICT, String.format(ADDRESS_EXISTS, address.getStreet(), address.getNumber(), address.getComplement()));
    }

    public Client deleteAddress(final Long clientId, final Long addressId) {

        final Client client = clientService.findById(clientId);
        client.getAddressList().stream()
                .filter(ph -> ph.getId().equals(addressId))
                .findFirst()
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format(ADDRESS_NOT_FOUND, addressId)))
                .setActive(false);

        return clientService.update(client);
    }
}
