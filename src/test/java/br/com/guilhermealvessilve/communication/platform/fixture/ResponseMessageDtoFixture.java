package br.com.guilhermealvessilve.communication.platform.fixture;

import br.com.guilhermealvessilve.communication.platform.application.usecase.dto.ResponseMessageDto;
import br.com.guilhermealvessilve.communication.platform.application.usecase.dto.Type;
import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

@UtilityClass
public class ResponseMessageDtoFixture {

    public static ResponseMessageDto fixtureResponseMessageDto() {
        return fixtureResponseMessageDto("test1@gmail.com", "test2@hotmail.com");
    }

    public static ResponseMessageDto fixtureResponseMessageDto(final String from,
                                                               final String to) {
        final var types = Type.values();
        return new ResponseMessageDto(
            UUID.randomUUID(),
            Instant.now(),
            from,
            to,
            types[new Random().nextInt(types.length)]
        );
    }
}
