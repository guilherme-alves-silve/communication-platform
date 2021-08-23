package br.com.guilhermealvessilve.communication.platform.test.specific;

import io.vertx.core.Vertx;
import io.vertx.junit5.VertxTestContext;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class SQLTestHelper {

    @SneakyThrows
    public static void execute(@NonNull final String sqlFile,
                               @NonNull final Vertx vertx,
                               @NonNull final VertxTestContext testContext) {

        final var pool = PostgreSQLTestcontainersExtension.getTestcontainersPool(vertx);
        final StringBuilder sql = new StringBuilder();
        try (final var reader = new BufferedReader(new InputStreamReader(getInput(sqlFile)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sql.append(line);
            }
        }

        pool.withConnection(conn -> conn.query(sql.toString())
            .execute())
            .toCompletionStage()
            .toCompletableFuture()
            .get(5, TimeUnit.SECONDS);

        testContext.completeNow();
    }

    private static InputStream getInput(final String sqlFile) {
        var input = SQLTestHelper.class.getClassLoader()
            .getResourceAsStream(String.format("/%s", sqlFile));

        if (null == input) {
            input = SQLTestHelper.class.getResourceAsStream(sqlFile);
        }

        return Objects.requireNonNull(input, "input of the sqlFile cannot be null!");
    }
}
