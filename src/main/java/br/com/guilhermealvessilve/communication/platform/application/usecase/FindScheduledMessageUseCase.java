package br.com.guilhermealvessilve.communication.platform.application.usecase;

import br.com.guilhermealvessilve.communication.platform.application.converter.MessageDtoToEntityConverter;
import br.com.guilhermealvessilve.communication.platform.application.usecase.dto.ResponseMessageDto;
import br.com.guilhermealvessilve.communication.platform.domain.repository.MessageRepository;
import br.com.guilhermealvessilve.communication.platform.infrastructure.repository.MessageRepositoryImpl;
import br.com.guilhermealvessilve.communication.platform.dependency.InjectionModules;
import io.vertx.core.Future;
import io.vertx.sqlclient.SqlClient;
import lombok.NonNull;

import java.util.Optional;
import java.util.UUID;

public final class FindScheduledMessageUseCase {

    private final MessageDtoToEntityConverter converter;
    private final MessageRepository repository;

    FindScheduledMessageUseCase(@NonNull final MessageDtoToEntityConverter converter,
                                @NonNull final MessageRepository repository) {
        this.converter = converter;
        this.repository = repository;
    }

    public Future<Optional<ResponseMessageDto>> findById(@NonNull final UUID id) {
        return repository.findById(id)
            .map(result -> result.map(converter::toResponseDto));
    }

    public static FindScheduledMessageUseCase getInstance(@NonNull final SqlClient client) {
        return new FindScheduledMessageUseCase(
            InjectionModules.getInstance(MessageDtoToEntityConverter.class),
            MessageRepositoryImpl.getInstance(client)
        );
    }
}
