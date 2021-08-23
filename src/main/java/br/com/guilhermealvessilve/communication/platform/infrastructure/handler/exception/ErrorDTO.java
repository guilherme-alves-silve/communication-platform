package br.com.guilhermealvessilve.communication.platform.infrastructure.handler.exception;

import br.com.guilhermealvessilve.communication.platform.infrastructure.util.ErrorMessages;
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

    public static ErrorDTO withError(final int status, final String code) {
        return new ErrorDTO(status, code, ErrorMessages.getMessage(code));
    }

    public static ErrorDTO withError(final int status, final String code, final String message) {
        return new ErrorDTO(status, code, message);
    }
}
