package br.com.guilhermealvessilve.communication.platform.infrastructure.util;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class ErrorMessages {

    private static final Map<String, String> CODE_AND_MESSAGE = new HashMap<>();

    static {
        CODE_AND_MESSAGE.put("001", "Invalid parameter");
        CODE_AND_MESSAGE.put("002", "Internal Server Error");
    }

    public static final String INVALID_PARAMETER_CODE = "001";
    public static final String INTERNAL_SERVER_ERROR_CODE = "002";

    public static String getMessage(final String code) {
        return CODE_AND_MESSAGE.get(code);
    }
}
