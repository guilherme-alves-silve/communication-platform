package br.com.guilhermealvessilve.communication.platform.application.usecase;

import br.com.guilhermealvessilve.communication.platform.domain.repository.MessageRepository;
import io.vertx.core.Future;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class DeleteScheduledMessageUseCaseTest {

    private DeleteScheduledMessageUseCase useCase;
    private MessageRepository mockRepository;

    @BeforeEach
    void setupEach() {
        this.mockRepository = Mockito.mock(MessageRepository.class);
        this.useCase = new DeleteScheduledMessageUseCase(mockRepository);
    }

    @Test
    void shouldExecuteDeleteByIdRoutineWithSuccess() {

        when(mockRepository.deleteById(any()))
            .thenReturn(Future.succeededFuture(true));

        final var expectedUuid = UUID.randomUUID();
        final var future = useCase.deleteById(expectedUuid);

        assertAll(
            () -> assertThat(future.result()).isTrue(),
            () -> verify(mockRepository).deleteById(eq(expectedUuid)),
            () -> verify(mockRepository, never()).findById(any())
        );
    }
}
