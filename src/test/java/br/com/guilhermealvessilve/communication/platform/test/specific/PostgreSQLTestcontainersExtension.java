package br.com.guilhermealvessilve.communication.platform.test.specific;

import br.com.guilhermealvessilve.communication.platform.infrastructure.database.migration.FlywayConfiguration;
import io.vertx.core.Vertx;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.PoolOptions;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.PostgreSQLContainer;

@Log4j2
public class PostgreSQLTestcontainersExtension implements BeforeAllCallback, AfterAllCallback {

    private static volatile PostgreSQLContainer<?> container;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        start();
    }

    @Override
    public void afterAll(ExtensionContext extensionContext) throws Exception {
        stop();
    }

    public static Pool getTestcontainersPool(@NonNull final Vertx vertx) {
        if (null == container) {
            throw new IllegalStateException("The method start must be called!");
        }

        final var connectionOptions = new PgConnectOptions()
            .setHost(container.getHost())
            .setPort(container.getFirstMappedPort())
            .setUser(container.getUsername())
            .setPassword(container.getPassword())
            .setDatabase(container.getDatabaseName());

        final var poolOptions = new PoolOptions();
        return PgPool.pool(vertx, connectionOptions, poolOptions);
    }

    public static boolean isStarted() {
        return container.isRunning();
    }

    private void start() {
        stop();
        LOGGER.info("Starting PostgreSQLTestcontainers ...");
        container = new PostgreSQLContainer<>("postgres:13.4-alpine3.14");
        container.start();
        LOGGER.info("Migrating database ...");
        FlywayConfiguration.migrate(container.getJdbcUrl(), container.getUsername(), container.getPassword());
        LOGGER.info("Database migrated!");
    }

    private void stop() {
        if (null == container) {
            return;
        }

        LOGGER.info("Stopping PostgreSQLTestcontainers...");
        container.close();
        container = null;
        LOGGER.info("Stopped PostgreSQLTestcontainers!");
    }
}
