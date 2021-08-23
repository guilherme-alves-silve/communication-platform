package br.com.guilhermealvessilve.communication.platform.infrastructure.endpoint.validator;

import br.com.guilhermealvessilve.communication.platform.infrastructure.component.JsonComponent;
import com.google.inject.Inject;
import io.vertx.core.http.HttpServerResponse;

import java.util.regex.Pattern;

import static br.com.guilhermealvessilve.communication.platform.infrastructure.component.ErrorMessageComponent.INVALID_PARAMETER_CODE;
import static br.com.guilhermealvessilve.communication.platform.infrastructure.endpoint.exception.dto.ErrorsDto.withError;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;

public class SchedulerValidator {

    private static final Pattern UUID_PATTERN = Pattern.compile("[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}");

    private final JsonComponent jsonComponent;

    @Inject
    public SchedulerValidator(JsonComponent jsonComponent) {
        this.jsonComponent = jsonComponent;
    }

    public boolean validate(final String uuid, final HttpServerResponse response) {

        if (isValid(uuid)) {
            return true;
        }

        response.setStatusCode(HTTP_BAD_REQUEST)
            .end(jsonComponent.toJson(withError(HTTP_BAD_REQUEST, INVALID_PARAMETER_CODE)));

        return false;
    }

    private boolean isValid(final String id) {

        if (null == id) {
            return false;
        }

        return UUID_PATTERN.asMatchPredicate().test(id);
    }
}
