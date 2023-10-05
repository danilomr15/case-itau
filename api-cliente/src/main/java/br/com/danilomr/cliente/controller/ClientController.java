package br.com.danilomr.cliente.controller;

import br.com.danilomr.cliente.dto.*;
import br.com.danilomr.cliente.entity.Client;
import br.com.danilomr.cliente.service.AddressService;
import br.com.danilomr.cliente.service.ClientService;
import br.com.danilomr.cliente.service.PhoneService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping(path = "/api/clientes")
public class ClientController {

    private final ClientService clientService;
    private final PhoneService phoneService;
    private final AddressService addressService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto create(@Valid @RequestBody final CreateClientDto createClientDto) {

        final Client client = clientService.create(createClientDto.toClient());
        return client.toClientDto();
    }

    @PutMapping("/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDto update(@PathVariable("clientId") final Long clientId,
                            @Valid @RequestBody final UpdateClientDto updateClientDto) {

        final Client client = clientService.update(clientId, updateClientDto.toClient());
        return client.toClientDto();
    }

    @DeleteMapping("/{clientId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("clientId") final Long clientId) {

        clientService.delete(clientId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<ClientDto> findAll(@RequestParam(name = "page", required = false, defaultValue = "0") final int page,
                                   @RequestParam(name = "size", required = false, defaultValue = "10") final int size) {

        final Page<Client> clientPage = clientService.findAll(page, size);
        final List<ClientDto> clientDtoList = clientPage.getContent().stream()
                .map(Client::toClientDto)
                .toList();
        return new PageImpl<>(clientDtoList, clientPage.getPageable(), clientPage.getTotalElements());
    }

    @GetMapping("/{clientId}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDto findById(@PathVariable("clientId") final Long clientId) {

        final Client client = clientService.findById(clientId);
        return client.toClientDto();
    }

    @PostMapping("/{clientId}/telefones")
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto addPhone(@PathVariable("clientId") final Long clientId,
                              @Valid @RequestBody final PhoneDto phoneDto) {

        final Client client = phoneService.addPhone(clientId, phoneDto.toPhone());
        return client.toClientDto();
    }

    @DeleteMapping("/{clientId}/telefones/{phoneId}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDto deletePhone(@PathVariable("clientId") final Long clientId,
                                 @PathVariable("phoneId") final Long phoneId) {

        final Client client = phoneService.deletePhone(clientId, phoneId);
        return client.toClientDto();
    }

    @PostMapping("/{clientId}/enderecos")
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto addAddress(@PathVariable("clientId") final Long clientId,
                                @Valid @RequestBody final AddressDto addressDto) {

        final Client client = addressService.addAddress(clientId, addressDto.toAddress());
        return client.toClientDto();
    }

    @DeleteMapping("/{clientId}/enderecos/{addressId}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDto deleteAddress(@PathVariable("clientId") final Long clientId,
                                   @PathVariable("addressId") final Long addressId) {

        final Client client = addressService.deleteAddress(clientId, addressId);
        return client.toClientDto();
    }
}
