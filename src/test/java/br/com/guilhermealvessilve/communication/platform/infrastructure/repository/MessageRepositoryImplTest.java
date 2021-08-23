package br.com.guilhermealvessilve.communication.platform.infrastructure.repository;

import br.com.guilhermealvessilve.communication.platform.domain.entity.MessageEntity;
import br.com.guilhermealvessilve.communication.platform.test.specific.PostgreSQLTestcontainersExtension;
import br.com.guilhermealvessilve.communication.platform.test.specific.SQLTestHelper;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@ExtendWith(VertxExtension.class)
@ExtendWith(PostgreSQLTestcontainersExtension.class)
class MessageRepositoryImplTest {

    @BeforeAll
    static void setupAll(final Vertx vertx, final VertxTestContext testContext) {
        SQLTestHelper.execute("/db/test/insert_messages.sql", vertx, testContext);
    }

    @Test
    void shouldFindMessage(Vertx vertx, VertxTestContext testContext) {

        final var expectedUUID = UUID.fromString("3308b0da-7e11-43aa-a88c-59a1366c057e");

        final var pool = PostgreSQLTestcontainersExtension.getTestcontainersPool(vertx);
        pool.withConnection(conn -> {
            final var repository = new MessageRepositoryImpl(conn);
            return repository.findById(expectedUUID);
        })
        .onSuccess(optional -> assertAll(
            testContext::completeNow,
            () -> assertThat(optional).isNotEmpty(),
            () -> assertThat(optional.orElseThrow().getId()).isEqualTo(expectedUUID),
            () -> assertThat(optional.orElseThrow().getFrom()).isEqualTo("nani@gmail.com"),
            () -> assertThat(optional.orElseThrow().getTo()).isEqualTo("jotaro@gmail.com"),
            () -> assertThat(optional.orElseThrow().getType()).isEqualTo(MessageEntity.Type.WHATSAPP)
        ))
        .onComplete(testContext.succeedingThenComplete());
    }
}
