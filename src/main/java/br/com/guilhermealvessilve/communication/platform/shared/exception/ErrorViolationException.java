package br.com.guilhermealvessilve.communication.platform.shared.exception;

import br.com.guilhermealvessilve.communication.platform.shared.exception.dto.ErrorsDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorViolationException extends RuntimeException {

    private final ErrorsDto errors;
}
