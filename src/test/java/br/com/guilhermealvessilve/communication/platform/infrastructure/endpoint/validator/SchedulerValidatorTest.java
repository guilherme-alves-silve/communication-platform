package br.com.guilhermealvessilve.communication.platform.infrastructure.endpoint.validator;

import br.com.guilhermealvessilve.communication.platform.dependency.InjectionModules;
import io.vertx.core.http.HttpServerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class SchedulerValidatorTest {

    private SchedulerValidator validator;

    @BeforeEach
    void setupEach() {
        this.validator = InjectionModules.getInstance(SchedulerValidator.class);
    }

    @Test
    void shouldRespondBadRequestToClient() {

        final var mockResponse = Mockito.mock(HttpServerResponse.class);
        when(mockResponse.setStatusCode(anyInt())).thenReturn(mockResponse);
        final var result = validator.validate("invalid uuid", mockResponse);
        assertAll(
            () -> assertThat(result).isFalse(),
            () -> verify(mockResponse).setStatusCode(eq(HTTP_BAD_REQUEST))
        );
    }

    @Test
    void shouldValidatorReturnTrueInValidUUID() {

        final var mockResponse = Mockito.mock(HttpServerResponse.class);
        final var result = validator.validate("46218075-20a2-4a16-8d92-f2595f6161bd", mockResponse);
        assertAll(
            () -> assertThat(result).isTrue(),
            () -> verify(mockResponse, never()).setStatusCode(anyInt())
        );
    }
}
