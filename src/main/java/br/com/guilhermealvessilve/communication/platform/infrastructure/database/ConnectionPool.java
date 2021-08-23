package br.com.guilhermealvessilve.communication.platform.infrastructure.database;

import br.com.guilhermealvessilve.communication.platform.domain.CommunicationPlatformConfiguration;
import io.vertx.core.Vertx;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;
import io.vertx.sqlclient.SqlClient;
import lombok.experimental.UtilityClass;

/**
 * @author Guilherme Alves Silveira
 */
@UtilityClass
public class ConnectionPool {

    public static SqlClient getClient(final Vertx vertx) {

        final var configuration = CommunicationPlatformConfiguration
            .configuration()
            .properties();

        final var connectOptions = new PgConnectOptions()
            .setPort(Integer.parseInt(configuration.getOrDefault("db.port", "5432")))
            .setHost(configuration.getOrDefault("db.host", "localhost"))
            .setDatabase(configuration.getOrDefault("db.name", "postgres"))
            .setUser(configuration.get("db.user"))
            .setPassword(configuration.get("db.password"));

        final var poolOptions = new PoolOptions()
            .setMaxSize(Integer.parseInt(configuration.getOrDefault("db.pool.size", "5")));

        return PgPool.client(vertx, connectOptions, poolOptions);
    }
}
