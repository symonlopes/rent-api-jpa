package br.com.symon.rentapi.error;

import lombok.Builder;

@Builder
public record ApiError(String code, String message, String detail) { }