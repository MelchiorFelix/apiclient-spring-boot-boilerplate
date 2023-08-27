package com.diariodeumdev.apiclientspringbootboilerplate.application.controller;

import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request.ClientRequest;
import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.response.ErrorResponse;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.model.Client;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.diariodeumdev.apiclientspringbootboilerplate.infrastructure.utils.Constants.SECURITY_SCHEME;

@RestController
@RequestMapping("/api/clients")
@SecurityRequirement(name = SECURITY_SCHEME)
public class ClientController {

    private final ClientService _clientService;

    public ClientController(ClientService clientService) {
        _clientService = clientService;
    }

    @Operation(summary = "Get client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get client",
                    content = @Content(schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Server Error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        return _clientService.getClientById(id);
    }

    @Operation(summary = "Creates a new client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created client"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Server Error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<Client> insertClient(@Valid @RequestBody ClientRequest clientRequest) {
        return _clientService.save(clientRequest);
    }

    @Operation(summary = "Delete client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Delete client"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Server Error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteClientById(@PathVariable Long id) {
        return _clientService.deleteClientById(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @Valid @RequestBody Client client) {
        return _clientService.updateClient(id, client);
    }

    @Operation(summary = "List clients")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List Clients",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Client.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Server Error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping(consumes = {"application/json"})
    public ResponseEntity find(ClientRequest filter) {
        return _clientService.find(filter);
    }
}
