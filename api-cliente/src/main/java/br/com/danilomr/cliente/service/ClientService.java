package br.com.danilomr.cliente.service;

import br.com.danilomr.cliente.entity.Client;
import br.com.danilomr.cliente.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@RequiredArgsConstructor
public class ClientService {

    private static final String CLIENT_NOT_FOUND = "Client not found with id=%d";
    private static final String CLIENT_EXISTS = "Client already exists with cpf=%s";

    private final ClientRepository clientRepository;

    public Client findById(final Long clientId) {

        final Optional<Client> existingClient = clientRepository.findById(clientId);
        return existingClient.orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, String.format(CLIENT_NOT_FOUND, clientId)));
    }

    public Page<Client> findAll(final int page, final int size) {
        return clientRepository.findAll(PageRequest.of(page, size));
    }

    public Client create(final Client client) {

        final Optional<Client> existingClient = clientRepository.findByCpf(client.getCpf());

        if (existingClient.isEmpty()) {
            return clientRepository.save(client);
        }

        if (isFalse(existingClient.get().getActive())) {
            existingClient.get().setActive(true);
            return clientRepository.save(existingClient.get());
        }

        throw new HttpClientErrorException(HttpStatus.CONFLICT, String.format(CLIENT_EXISTS, client.getCpf()));
    }

    public Client update(final Client client) {

        return clientRepository.save(client);
    }

    public Client update(final Long clientId, final Client client) {

        final Client existingClient = findById(clientId);

        if (nonNull(client.getBirthdate())) {
            existingClient.setBirthdate(client.getBirthdate());
        }

        if (isNotBlank(client.getName())) {
            existingClient.setName(client.getName());
        }
        return clientRepository.save(existingClient);
    }

    public void delete(final Long clientId) {

        Client client = findById(clientId);
        client.setActive(false);
        clientRepository.save(client);
    }
}
