package com.diariodeumdev.apiclientspringbootboilerplate.application.controller;

import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request.AuthenticationRequest;
import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request.UpdateRoleRequest;
import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.response.ErrorResponse;
import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.response.TokenResponse;
import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.response.UserResponse;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.service.impl.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.diariodeumdev.apiclientspringbootboilerplate.infrastructure.utils.Constants.ROLE_ADMIN;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserDetailsServiceImpl userService;

    @Operation(summary = "Authenticate a user and generate a jwt token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful login",
                    content = @Content(schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Server Error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationRequest request) {
        return userService.authenticate(request);
    }

    @Operation(summary = "Creates a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created user"),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Server Error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("register")
    public ResponseEntity register(@RequestBody @Valid AuthenticationRequest request) throws BindException {
        return userService.register(request);
    }

    @Operation(summary = "Update the role of a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User role updated successfully",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Server Error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("update-role")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity updateRole(@RequestBody @Valid UpdateRoleRequest request) {
        return userService.updateRole(request);
    }

    @Operation(summary = "Retrieve a list of all users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully",
                    content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UserResponse.class)))),
            @ApiResponse(responseCode = "400", description = "Bad request",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Server Error",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<List<UserResponse>> getAll() {
        return userService.getAll();
    }
}
