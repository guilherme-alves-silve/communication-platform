package br.com.guilhermealvessilve.communication.platform.domain;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Referência:
 *  https://stackoverflow.com/questions/1464291/how-to-really-read-text-file-from-classpath-in-java
 * @author Guilherme Alves Silveira
 */
@Log4j2
public class AppConfiguration {

    private final Map<String, String> properties = new HashMap<>();

    private AppConfiguration() {
        //Must be singleton!
    }

    public Map<String, String> properties() {

        if (!properties.isEmpty()) {
            return Collections.unmodifiableMap(properties);
        }

        synchronized(this) {

            if (!properties.isEmpty()) {
                return Collections.unmodifiableMap(properties);
            }

            LOGGER.info("Loading application.properties");
            try (final var input = new BufferedInputStream(getResourceAsStream())) {
                final var temp = new Properties();
                temp.load(input);
                temp.forEach((key, value) -> properties.put(
                    ObjectUtils.toString(key, () -> StringUtils.EMPTY),
                    ObjectUtils.toString(value, () -> StringUtils.EMPTY)));

                LOGGER.info("Loaded application.properties!");
                return Collections.unmodifiableMap(properties);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

    private InputStream getResourceAsStream() {
        var input = this.getClass().getClassLoader()
            .getResourceAsStream("application.properties");

        if (null == input) {
            input = this.getClass().getResourceAsStream("/application.properties");
        }

        return Objects.requireNonNull(input, "application.properties must exists!");
    }

    public static AppConfiguration configuration() {
        return Factory.CONFIGURATION;
    }

    private static class Factory {
        private static final AppConfiguration CONFIGURATION = new AppConfiguration();
    }
}
