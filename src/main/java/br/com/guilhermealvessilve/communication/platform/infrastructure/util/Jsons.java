package br.com.guilhermealvessilve.communication.platform.infrastructure.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Jsons {

    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .findAndRegisterModules();
    }

    @SneakyThrows
    public static String toJson(final Object object) {
        return MAPPER.writeValueAsString(object);
    }

    @SneakyThrows
    public static <T> T fromJson(String json, Class<T> clazz) {
        return MAPPER.readValue(json, clazz);
    }
}
