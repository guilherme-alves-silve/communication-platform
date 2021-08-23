package br.com.guilhermealvessilve.communication.platform.fixture;

import br.com.guilhermealvessilve.communication.platform.application.usecase.dto.RequestMessageDto;
import br.com.guilhermealvessilve.communication.platform.application.usecase.dto.Type;
import lombok.experimental.UtilityClass;

import java.time.Instant;

@UtilityClass
public class RequestMessageDtoFixture {

    public static RequestMessageDto fixtureRequestMessageDto() {
        return new RequestMessageDto(
            Instant.now().plusSeconds(60),
            "johndoe@live.com",
            "doejohn@outlook.com",
            "Nani?",
            Type.EMAIL
        );
    }

    public static RequestMessageDto fixtureInvalidRequestMessageDto() {
        return new RequestMessageDto(
            null,
            null,
            null,
            null,
            null
        );
    }
}
