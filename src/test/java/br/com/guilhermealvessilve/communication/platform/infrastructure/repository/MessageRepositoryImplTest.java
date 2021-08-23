package br.com.guilhermealvessilve.communication.platform.infrastructure.repository;

import br.com.guilhermealvessilve.communication.platform.domain.entity.MessageEntity;
import br.com.guilhermealvessilve.communication.platform.fixture.MessageEntityFixture;
import br.com.guilhermealvessilve.communication.platform.test.specific.PostgreSQLTestcontainersExtension;
import br.com.guilhermealvessilve.communication.platform.test.specific.SQLTestHelper;
import io.vertx.core.Vertx;
import io.vertx.junit5.VertxExtension;
import io.vertx.junit5.VertxTestContext;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(VertxExtension.class)
@ExtendWith(PostgreSQLTestcontainersExtension.class)
class MessageRepositoryImplTest {

    @BeforeAll
    static void setupAll(final Vertx vertx, final VertxTestContext testContext) {
        SQLTestHelper.execute("/db/test/insert_messages.sql", vertx, testContext);
    }

    @Test
    @Order(1)
    void shouldFindMessage(final Vertx vertx, final VertxTestContext testContext) {

        final var expectedUUID = UUID.fromString("3308b0da-7e11-43aa-a88c-59a1366c057e");

        final var pool = PostgreSQLTestcontainersExtension.getTestcontainersPool(vertx);
        pool.withConnection(conn -> {
            final var repository = new MessageRepositoryImpl(conn);
            return repository.findById(expectedUUID);
        })
        .onSuccess(optional -> {
            assertAll(
                () -> assertThat(optional).isNotEmpty(),
                () -> assertThat(optional.orElseThrow().getId()).isEqualTo(expectedUUID),
                () -> assertThat(optional.orElseThrow().getFrom()).isEqualTo("nani@gmail.com"),
                () -> assertThat(optional.orElseThrow().getTo()).isEqualTo("jotaro@gmail.com"),
                () -> assertThat(optional.orElseThrow().getType()).isEqualTo(MessageEntity.Type.WHATSAPP)
            );

            testContext.completeNow();
        })
        .onComplete(testContext.succeedingThenComplete());
    }

    @Test
    @Order(2)
    void shouldDeleteMessage(final Vertx vertx, final VertxTestContext testContext) {

        final var uuidDelete = UUID.fromString("1ad9b240-c417-4ae4-bfc4-6f169bdf9a85");

        final var pool = PostgreSQLTestcontainersExtension.getTestcontainersPool(vertx);
        pool.withTransaction(conn -> {
            final var repository = new MessageRepositoryImpl(conn);
            return repository.deleteById(uuidDelete);
        })
        .onSuccess(result -> {
            assertThat(result).isTrue();
            testContext.completeNow();
        })
        .onComplete(testContext.succeedingThenComplete());
    }

    @Test
    @Order(3)
    void shouldFindAllScheduledMessages(final Vertx vertx, final VertxTestContext testContext) {

        final var past = LocalDateTime.now().minusDays(1);
        final int expectedCountElements = 2;
        final var pool = PostgreSQLTestcontainersExtension.getTestcontainersPool(vertx);
        pool.withConnection(conn -> {
            final var repository = new MessageRepositoryImpl(conn);
            return repository.findNotSentScheduledMessages(past, expectedCountElements);
        })
        .onSuccess(messages -> {
            assertAll(
                () -> assertThat(messages).hasSize(expectedCountElements),
                () -> assertThat(messages).contains(
                    MessageEntity.withId("812053b6-5cd3-4bd6-bfd5-8de9c1a7bf38"),
                    MessageEntity.withId("9b3e4039-420c-4505-8cf2-76e9ca7bfaad")
                )
            );

            testContext.completeNow();
        })
        .onComplete(testContext.succeedingThenComplete());
    }

    @Test
    @Order(4)
    void shouldSaveMessage(final Vertx vertx, final VertxTestContext testContext) {

        final var messageEntity = MessageEntityFixture.fixtureMessageEntity("wesley@gmail.com", "nappa@hotmail.com");

        final var pool = PostgreSQLTestcontainersExtension.getTestcontainersPool(vertx);
        pool.withTransaction(conn -> {
            final var repository = new MessageRepositoryImpl(conn);
            return repository.save(messageEntity);
        })
        .onSuccess(result -> {
            assertThat(result).isTrue();
            testContext.completeNow();
        })
        .onComplete(testContext.succeedingThenComplete());
    }
}
