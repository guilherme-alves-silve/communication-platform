package br.com.guilhermealvessilve.communication.platform.infrastructure.handler.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class ErrorDTO {

    private final int status;
    private final String code;
    private final String message;
}
