package br.com.guilhermealvessilve.communication.platform.infrastructure.component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

/**
 * @author Guilherme Alves Silveira
 */
public class JsonComponent {

    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .findAndRegisterModules();
    }

    @SneakyThrows
    public String toJson(final Object object) {
        return MAPPER.writeValueAsString(object);
    }

    @SneakyThrows
    public <T> T fromJson(String json, Class<T> clazz) {
        return MAPPER.readValue(json, clazz);
    }
}
