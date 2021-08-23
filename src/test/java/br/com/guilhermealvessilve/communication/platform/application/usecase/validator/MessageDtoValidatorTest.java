package br.com.guilhermealvessilve.communication.platform.application.usecase.validator;

import br.com.guilhermealvessilve.communication.platform.fixture.RequestMessageDtoFixture;
import br.com.guilhermealvessilve.communication.platform.configuration.exception.dto.ErrorDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MessageDtoValidatorTest {

    private MessageDtoValidator validator;

    @BeforeEach
    void setupEach() {
        Locale.setDefault(Locale.US);
        validator = new MessageDtoValidator();
    }

    @Test
    void shouldValidateReturnNoErrorOfValidFields() {

        final var dto = RequestMessageDtoFixture.fixtureRequestMessageDto();
        final var errors = validator.validate(dto);
        assertAll(
            () -> assertThat(errors.noError()).isTrue(),
            () -> assertThat(errors.hasError()).isFalse(),
            () -> assertThat(errors.getErrors()).isEmpty()
        );
    }

    @Test
    void shouldValidateReturnErrorOfInvalidFields() {

        final var dto = RequestMessageDtoFixture.fixtureInvalidRequestMessageDto();
        final var errors = validator.validate(dto);
        assertAll(
            () -> assertThat(errors.noError()).isFalse(),
            () -> assertThat(errors.hasError()).isTrue(),
            () -> assertThat(errors.getErrors()).hasSize(5),
            () -> assertThat(errors.getErrors()).contains(
                ErrorDto.withError(400, "001", "message - must not be blank"),
                ErrorDto.withError(400, "001", "to - must not be blank"),
                ErrorDto.withError(400, "001", "scheduleTime - must not be null"),
                ErrorDto.withError(400, "001", "type - must not be null"),
                ErrorDto.withError(400, "001", "from - must not be blank")
            )
        );
    }
}
