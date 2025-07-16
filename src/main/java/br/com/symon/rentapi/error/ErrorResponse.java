package br.com.symon.rentapi.error;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Builder
@ToString
public class ErrorResponse {

    @Builder.Default
    private Collection<ApiError> errors = new ArrayList<>();
}
