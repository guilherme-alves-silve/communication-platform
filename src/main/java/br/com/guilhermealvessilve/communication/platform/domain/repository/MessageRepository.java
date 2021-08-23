package br.com.guilhermealvessilve.communication.platform.domain.repository;

import br.com.guilhermealvessilve.communication.platform.domain.entity.MessageEntity;
import io.vertx.core.Future;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageRepository {

    Future<Optional<MessageEntity>> findById(final UUID id);

    Future<List<MessageEntity>> findNotSentScheduledMessages(final LocalDateTime scheduledDateTime,
                                                             final int expectedCountElements);

    Future<Boolean> save(final MessageEntity message);

    Future<Boolean> deleteById(final UUID id);
}
