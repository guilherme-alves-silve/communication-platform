package br.com.guilhermealvessilve.communication.platform.infrastructure.fixture;

import br.com.guilhermealvessilve.communication.platform.domain.entity.MessageEntity;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

@UtilityClass
public class MessageEntityFixture {

    public static MessageEntity fixtureMessageEntity(final String from,
                                                     final String to) {
        final var types = MessageEntity.Type.values();
        return new MessageEntity(
            UUID.randomUUID(),
            Instant.now(),
            from,
            to,
            types[new Random().nextInt(types.length)],
            false
        );
    }
}
