package br.com.guilhermealvessilve.communication.platform.infrastructure.component;

import java.util.ResourceBundle;

/**
 * Classe que pode ser usada em projetos multi-linguas.
 * @author Guilherme Alves Silveira
 */
public class ErrorMessageComponent {

    public static final String INVALID_PARAMETER_CODE = "001";
    public static final String INTERNAL_SERVER_ERROR_CODE = "002";
    public static final String NOT_FOUND_CODE = "003";

    private final ResourceBundle resourceBundle;

    public ErrorMessageComponent() {
        this.resourceBundle = ResourceBundle.getBundle("error-messages");
    }

    public String getMessage(final String code) {
        return resourceBundle.getString(code);
    }
}
