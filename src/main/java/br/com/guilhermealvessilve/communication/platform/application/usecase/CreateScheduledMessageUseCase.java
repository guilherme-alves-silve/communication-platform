package br.com.guilhermealvessilve.communication.platform.application.usecase;

import br.com.guilhermealvessilve.communication.platform.application.converter.MessageDtoToEntityConverter;
import br.com.guilhermealvessilve.communication.platform.application.usecase.dto.RequestMessageDto;
import br.com.guilhermealvessilve.communication.platform.application.usecase.dto.ResponseMessageDto;
import br.com.guilhermealvessilve.communication.platform.application.usecase.validator.MessageDtoValidator;
import br.com.guilhermealvessilve.communication.platform.domain.repository.MessageRepository;
import br.com.guilhermealvessilve.communication.platform.infrastructure.repository.MessageRepositoryImpl;
import br.com.guilhermealvessilve.communication.platform.dependency.InjectionModules;
import br.com.guilhermealvessilve.communication.platform.infrastructure.endpoint.exception.ErrorViolationException;
import io.vertx.core.Future;
import io.vertx.sqlclient.SqlClient;
import lombok.NonNull;
import org.apache.commons.lang3.BooleanUtils;

import java.util.Optional;

public final class CreateScheduledMessageUseCase {

    private final MessageDtoToEntityConverter converter;
    private final MessageRepository repository;
    private final MessageDtoValidator validator;

    CreateScheduledMessageUseCase(@NonNull final MessageDtoToEntityConverter converter,
                                  @NonNull final MessageRepository repository,
                                  @NonNull final MessageDtoValidator validator) {
        this.converter = converter;
        this.repository = repository;
        this.validator = validator;
    }

    public Future<Optional<ResponseMessageDto>> create(@NonNull final RequestMessageDto dto) {

        final var errors = validator.validate(dto);
        if (errors.hasError()) {
            return Future.failedFuture(new ErrorViolationException(errors));
        }

        final var entity = converter.toEntity(dto);
        return repository.save(entity)
            .map(result -> {
                if (BooleanUtils.isTrue(result)) {
                    final var response = converter.toResponseDto(entity);
                    return Optional.of(response);
                }

                return Optional.empty();
            });
    }

    public static CreateScheduledMessageUseCase getInstance(final SqlClient client) {
        return new CreateScheduledMessageUseCase(
            InjectionModules.getInstance(MessageDtoToEntityConverter.class),
            MessageRepositoryImpl.getInstance(client),
            InjectionModules.getInstance(MessageDtoValidator.class)
        );
    }
}
