package br.com.guilhermealvessilve.communication.platform.infrastructure.database.migration;

import lombok.experimental.UtilityClass;
import org.flywaydb.core.Flyway;

/**
 * @author Guilherme Alves Silveira
 */
@UtilityClass
public class FlywayConfiguration {

    public static void migrate(final String url,
                               final String user,
                               final String password) {
        final var flyway = Flyway.configure()
            .dataSource(url, user, password)
            .load();
        flyway.migrate();
    }
}
