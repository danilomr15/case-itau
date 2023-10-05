package br.com.danilomr.cliente.service;

import br.com.danilomr.cliente.entity.Client;
import br.com.danilomr.cliente.entity.Phone;
import br.com.danilomr.cliente.repository.PhoneRepository;
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
public class PhoneService {

    private static final String PHONE_EXISTS = "Phone with ddd=%d and number=%s already exists and it is already active";
    private static final String PHONE_NOT_FOUND = "Phone not found with id=%d";
    private static final String CLIENT_INACTIVE = "Cannot add a phone for an inactive client";

    private final PhoneRepository phoneRepository;
    private final ClientService clientService;

    public Client addPhone(final Long clientId, final Phone phone) {

        final Client client = clientService.findById(clientId);
        if (isFalse(client.getActive())) {
            throw new HttpClientErrorException(HttpStatus.PRECONDITION_FAILED, CLIENT_INACTIVE);
        }
        final Optional<Phone> existingPhone = phoneRepository.findByDddAndNumberAndClientId(phone.getDdd(), phone.getNumber(), clientId);

        if (existingPhone.isEmpty()) {

            if (isEmpty(client.getPhoneList())) {
                client.setPhoneList(new ArrayList<>());
            }

            client.getPhoneList().add(phone);
            phone.setClient(client);
            return clientService.update(client);
        }

        if (isFalse(existingPhone.get().getActive())) {
            client.getPhoneList().stream()
                    .filter(ph -> ph.sameOf(phone))
                    .findFirst()
                    .ifPresent(ph -> ph.setActive(true));
            return clientService.update(client);
        }

        throw new HttpClientErrorException(HttpStatus.CONFLICT, String.format(PHONE_EXISTS, phone.getDdd(), phone.getNumber()));
    }

    public Client deletePhone(final Long clientId, final Long phoneId) {

        final Client client = clientService.findById(clientId);
        client.getPhoneList().stream()
                .filter(ph -> ph.getId().equals(phoneId))
                .findFirst()
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format(PHONE_NOT_FOUND, phoneId)))
                .setActive(false);

        return clientService.update(client);
    }
}
