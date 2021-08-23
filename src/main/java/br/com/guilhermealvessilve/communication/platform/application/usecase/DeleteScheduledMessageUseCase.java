package br.com.guilhermealvessilve.communication.platform.application.usecase;

import br.com.guilhermealvessilve.communication.platform.domain.repository.MessageRepository;
import br.com.guilhermealvessilve.communication.platform.infrastructure.repository.MessageRepositoryImpl;
import io.vertx.core.Future;
import io.vertx.sqlclient.SqlClient;
import lombok.NonNull;

import java.util.UUID;

/**
 * @author Guilherme Alves Silveira
 */
public class DeleteScheduledMessageUseCase {

    private final MessageRepository repository;

    DeleteScheduledMessageUseCase(@NonNull final MessageRepository repository) {
        this.repository = repository;
    }

    public Future<Boolean> deleteById(@NonNull final UUID id) {
        return repository.deleteById(id);
    }

    public static DeleteScheduledMessageUseCase getInstance(@NonNull final SqlClient client) {
        return new DeleteScheduledMessageUseCase(MessageRepositoryImpl.getInstance(client));
    }
}
