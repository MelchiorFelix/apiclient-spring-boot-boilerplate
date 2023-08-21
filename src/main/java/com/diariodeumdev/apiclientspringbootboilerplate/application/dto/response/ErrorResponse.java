package com.diariodeumdev.apiclientspringbootboilerplate.application.dto.response;

public record ErrorResponse(String error, String message, int code) {
}
