package br.com.guilhermealvessilve.communication.platform.application.usecase.validator;

import br.com.guilhermealvessilve.communication.platform.application.usecase.dto.RequestMessageDto;
import br.com.guilhermealvessilve.communication.platform.dependency.InjectionModules;
import br.com.guilhermealvessilve.communication.platform.infrastructure.endpoint.exception.dto.ErrorDto;
import br.com.guilhermealvessilve.communication.platform.infrastructure.endpoint.exception.dto.ErrorsDto;
import br.com.guilhermealvessilve.communication.platform.infrastructure.component.ErrorMessageComponent;
import com.google.inject.Singleton;
import jakarta.validation.Validator;
import org.hibernate.validator.internal.engine.path.PathImpl;

import java.util.stream.Collectors;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;

@Singleton
public class MessageDtoValidator {

    public ErrorsDto validate(final RequestMessageDto request) {

        final var validator = InjectionModules.getInstance(Validator.class);
        final var constraints = validator.validate(request);
        final var errors = constraints.stream()
            .map(violation -> ErrorDto.withError(
                HTTP_BAD_REQUEST,
                ErrorMessageComponent.INVALID_PARAMETER_CODE,
                String.format("%s - %s", ((PathImpl) violation.getPropertyPath()).getLeafNode().getName(), violation.getMessage())
            ))
            .collect(Collectors.toList());

        return ErrorsDto.withErrors(errors);
    }
}
