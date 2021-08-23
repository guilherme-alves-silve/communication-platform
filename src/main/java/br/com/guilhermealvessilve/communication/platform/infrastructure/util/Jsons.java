package br.com.guilhermealvessilve.communication.platform.infrastructure.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Jsons {

    private static final ObjectMapper MAPPER;

    static {
        MAPPER = new ObjectMapper();
    }

    @SneakyThrows
    public static String toJson(final Object object) {
        return MAPPER.writeValueAsString(object);
    }
}
