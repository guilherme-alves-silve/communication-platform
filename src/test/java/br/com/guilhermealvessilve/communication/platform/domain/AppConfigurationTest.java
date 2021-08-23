package br.com.guilhermealvessilve.communication.platform.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AppConfigurationTest {

    private AppConfiguration configuration;

    @BeforeEach
    void setupEach() {
        configuration = AppConfiguration.configuration();
    }

    @Test
    void shouldReturnCorrectValuesFromApplicationProperties() {

        final var properties = configuration.properties();
        assertAll(
            () -> assertThat(properties.get("app.port")).isEqualTo("8888"),
            () -> assertThat(properties.get("db.port")).isEqualTo("5432"),
            () -> assertThat(properties.get("db.host")).isEqualTo("localhost"),
            () -> assertThat(properties.get("db.name")).isEqualTo("postgres"),
            () -> assertThat(properties.get("db.user")).isEqualTo("postgres"),
            () -> assertThat(properties.get("db.password")).isEqualTo("postgres"),
            () -> assertThat(properties.get("db.pool.size")).isEqualTo("5"),
            () -> assertThat(configuration.properties()).isEqualTo(properties)
        );
    }

    @Test
    void shouldNotAllowModificationsToTheAppConfigurationProperties() {
        final var properties = configuration.properties();
        assertThrows(UnsupportedOperationException.class, () -> properties.put("AnotherKey", "AnotherValue"));
    }
}
