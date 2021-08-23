package br.com.guilhermealvessilve.communication.platform.infrastructure.database.migration;

import br.com.guilhermealvessilve.communication.platform.domain.CommunicationPlatformConfiguration;

/**
 * @author Guilherme Alves Silveira
 */
public class MigrationManager {

    public void migrate() {

        final var configuration = CommunicationPlatformConfiguration.configuration()
            .properties();

        final var jdbcUrl = String.format("jdbc:postgresql://%s:%s/%s",
            configuration.getOrDefault("db.host", "localhost"),
            configuration.getOrDefault("db.port", "5432"),
            configuration.getOrDefault("db.name", "postgres")
        );

        FlywayConfiguration.migrate(jdbcUrl, configuration.get("db.user"), configuration.get("db.password"));
    }
}
