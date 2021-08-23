package br.com.guilhermealvessilve.communication.platform.infrastructure.endpoint.exception;

import br.com.guilhermealvessilve.communication.platform.infrastructure.endpoint.exception.dto.ErrorsDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Guilherme Alves Silveira
 */
@Getter
@RequiredArgsConstructor
public class ErrorViolationException extends RuntimeException {

    private final ErrorsDto errors;
}
