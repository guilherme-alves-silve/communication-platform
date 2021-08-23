package br.com.guilhermealvessilve.communication.platform.application.usecase.validator;

import br.com.guilhermealvessilve.communication.platform.application.usecase.dto.RequestMessageDto;
import br.com.guilhermealvessilve.communication.platform.shared.dependency.InjectionModules;
import br.com.guilhermealvessilve.communication.platform.shared.exception.dto.ErrorDto;
import br.com.guilhermealvessilve.communication.platform.shared.exception.dto.ErrorsDto;
import br.com.guilhermealvessilve.communication.platform.shared.util.ErrorMessages;
import br.com.guilhermealvessilve.communication.platform.shared.util.HttpStatus;
import com.google.inject.Singleton;
import jakarta.validation.Validator;
import org.hibernate.validator.internal.engine.path.PathImpl;

import java.util.stream.Collectors;

@Singleton
public class MessageDtoValidator {

    public ErrorsDto validate(final RequestMessageDto request) {

        final var validator = InjectionModules.getInstance(Validator.class);
        final var constraints = validator.validate(request);
        final var errors = constraints.stream()
            .map(violation -> ErrorDto.withError(
                HttpStatus.BAD_REQUEST,
                ErrorMessages.INVALID_PARAMETER_CODE,
                String.format("%s - %s", ((PathImpl) violation.getPropertyPath()).getLeafNode().getName(), violation.getMessage())
            ))
            .collect(Collectors.toList());

        return ErrorsDto.withErrors(errors);
    }
}
