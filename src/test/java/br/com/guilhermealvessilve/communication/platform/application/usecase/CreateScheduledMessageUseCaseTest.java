package br.com.guilhermealvessilve.communication.platform.application.usecase;

import br.com.guilhermealvessilve.communication.platform.application.converter.MessageDtoToEntityConverter;
import br.com.guilhermealvessilve.communication.platform.application.usecase.validator.MessageDtoValidator;
import br.com.guilhermealvessilve.communication.platform.domain.repository.MessageRepository;
import br.com.guilhermealvessilve.communication.platform.fixture.MessageEntityFixture;
import br.com.guilhermealvessilve.communication.platform.fixture.RequestMessageDtoFixture;
import br.com.guilhermealvessilve.communication.platform.fixture.ResponseMessageDtoFixture;
import br.com.guilhermealvessilve.communication.platform.shared.exception.ErrorViolationException;
import br.com.guilhermealvessilve.communication.platform.shared.exception.dto.ErrorsDto;
import io.vertx.core.Future;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateScheduledMessageUseCaseTest {

    private CreateScheduledMessageUseCase useCase;
    private MessageDtoToEntityConverter mockConverter;
    private MessageRepository mockRepository;
    private MessageDtoValidator mockValidator;

    @BeforeEach
    void setupEach() {
        this.mockConverter = Mockito.mock(MessageDtoToEntityConverter.class);
        this.mockRepository = Mockito.mock(MessageRepository.class);
        this.mockValidator = Mockito.mock(MessageDtoValidator.class);
        this.useCase = new CreateScheduledMessageUseCase(
            mockConverter,
            mockRepository,
            mockValidator
        );
    }

    @Test
    void shouldExecuteSaveRoutineWithSuccess() {

        when(mockValidator.validate(any()))
            .thenReturn(ErrorsDto.withErrors());

        when(mockConverter.toEntity(any()))
            .thenReturn(MessageEntityFixture.fixtureMessageEntity());

        when(mockConverter.toResponseDto(any()))
            .thenReturn(ResponseMessageDtoFixture.fixtureResponseMessageDto());

        when(mockRepository.save(any()))
            .thenReturn(Future.succeededFuture(true));

        final var dto = RequestMessageDtoFixture.fixtureRequestMessageDto();
        final var future = useCase.create(dto);

        assertAll(
            () -> assertThat(future.result()).isNotEmpty(),
            () -> verify(mockValidator).validate(any()),
            () -> verify(mockConverter).toEntity(any()),
            () -> verify(mockConverter).toResponseDto(any()),
            () -> verify(mockRepository).save(any())
        );
    }

    @Test
    void shouldNotExecuteSaveRoutineWhenRequestDtoIsInvalid() {

        when(mockValidator.validate(any()))
            .thenReturn(ErrorsDto.withError(400, "001"));

        final var dto = RequestMessageDtoFixture.fixtureRequestMessageDto();
        final var future = useCase.create(dto);

        assertAll(
            () -> assertThat(future.failed()).isTrue(),
            () -> assertThat(future.cause()).isInstanceOf(ErrorViolationException.class),
            () -> verify(mockValidator).validate(any()),
            () -> verify(mockConverter, never()).toEntity(any()),
            () -> verify(mockConverter, never()).toResponseDto(any()),
            () -> verify(mockRepository, never()).save(any())
        );
    }
}
