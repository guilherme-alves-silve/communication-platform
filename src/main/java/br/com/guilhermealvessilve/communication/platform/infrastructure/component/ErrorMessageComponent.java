package br.com.guilhermealvessilve.communication.platform.infrastructure.component;

import java.util.HashMap;
import java.util.Map;

public class ErrorMessageComponent {

    private static final Map<String, String> CODE_AND_MESSAGE = new HashMap<>();

    static {
        CODE_AND_MESSAGE.put("001", "Invalid parameter");
        CODE_AND_MESSAGE.put("002", "Internal Server Error");
        CODE_AND_MESSAGE.put("003", "Element with id {0} was not found");
    }

    public static final String INVALID_PARAMETER_CODE = "001";
    public static final String INTERNAL_SERVER_ERROR_CODE = "002";
    public static final String NOT_FOUND_CODE = "003";

    public String getMessage(final String code) {
        return CODE_AND_MESSAGE.get(code);
    }
}
