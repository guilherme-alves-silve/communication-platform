package br.com.guilhermealvessilve.communication.platform.application.usecase;

import br.com.guilhermealvessilve.communication.platform.application.converter.MessageDtoToEntityConverter;
import br.com.guilhermealvessilve.communication.platform.domain.repository.MessageRepository;
import br.com.guilhermealvessilve.communication.platform.fixture.MessageEntityFixture;
import br.com.guilhermealvessilve.communication.platform.fixture.ResponseMessageDtoFixture;
import io.vertx.core.Future;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FindScheduledMessageUseCaseTest {

    private FindScheduledMessageUseCase useCase;
    private MessageDtoToEntityConverter mockConverter;
    private MessageRepository mockRepository;

    @BeforeEach
    void setupEach() {
        this.mockConverter = Mockito.mock(MessageDtoToEntityConverter.class);
        this.mockRepository = Mockito.mock(MessageRepository.class);
        this.useCase = new FindScheduledMessageUseCase(
            mockConverter,
            mockRepository
        );
    }

    @Test
    void shouldExecuteFindByIdRoutineWithSuccess() {

        when(mockConverter.toResponseDto(any()))
            .thenReturn(ResponseMessageDtoFixture.fixtureResponseMessageDto());

        when(mockRepository.findById(any()))
            .thenReturn(Future.succeededFuture(Optional.of(MessageEntityFixture.fixtureMessageEntity())));

        final var future = useCase.findById(UUID.randomUUID());

        assertAll(
            () -> assertThat(future.result()).isNotEmpty(),
            () -> verify(mockConverter, never()).toEntity(any()),
            () -> verify(mockConverter).toResponseDto(any()),
            () -> verify(mockRepository).findById(any())
        );
    }

    @Test
    void shouldExecuteFindByIdRoutineEvenItTheElementWasNotFound() {

        when(mockConverter.toResponseDto(any()))
            .thenReturn(ResponseMessageDtoFixture.fixtureResponseMessageDto());

        when(mockRepository.findById(any()))
            .thenReturn(Future.succeededFuture(Optional.empty()));

        final var future = useCase.findById(UUID.randomUUID());
        assertAll(
            () -> assertThat(future.result()).isEmpty(),
            () -> verify(mockConverter, never()).toEntity(any()),
            () -> verify(mockConverter, never()).toResponseDto(any()),
            () -> verify(mockRepository).findById(any())
        );
    }
}
