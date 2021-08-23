package br.com.guilhermealvessilve.communication.platform.infrastructure.endpoint.component;

import br.com.guilhermealvessilve.communication.platform.infrastructure.component.ErrorMessageComponent;
import br.com.guilhermealvessilve.communication.platform.infrastructure.component.JsonComponent;
import br.com.guilhermealvessilve.communication.platform.infrastructure.endpoint.exception.ErrorViolationException;
import com.google.inject.Inject;
import io.vertx.core.AsyncResult;
import io.vertx.ext.web.RoutingContext;

import java.text.MessageFormat;

import static br.com.guilhermealvessilve.communication.platform.infrastructure.component.ErrorMessageComponent.*;
import static br.com.guilhermealvessilve.communication.platform.infrastructure.endpoint.exception.dto.ErrorsDto.withError;
import static java.net.HttpURLConnection.*;

public class ResponseComponent {

    private final JsonComponent jsonComponent;
    private final ErrorMessageComponent errorMessageComponent;

    @Inject
    public ResponseComponent(final JsonComponent jsonComponent,
                             final ErrorMessageComponent errorMessageComponent) {
        this.jsonComponent = jsonComponent;
        this.errorMessageComponent = errorMessageComponent;
    }

    public <T> boolean handleFailure(final AsyncResult<T> asyncResult, final RoutingContext ctx) {
        if (asyncResult.failed()) {
            if (asyncResult.cause() instanceof ErrorViolationException) {
                final var violation = (ErrorViolationException) asyncResult.cause();
                ctx.response()
                    .setStatusCode(HTTP_BAD_REQUEST)
                    .end(jsonComponent.toJson(violation.getErrors()));
                return true;
            }

            handleFailure(ctx);
            return true;
        }

        return false;
    }

    public void notFound(final RoutingContext ctx, final String id) {
        ctx.response()
            .setStatusCode(HTTP_NOT_FOUND)
            .end(jsonComponent.toJson(withError(
                HTTP_NOT_FOUND,
                NOT_FOUND_CODE,
                MessageFormat.format(errorMessageComponent.getMessage(NOT_FOUND_CODE), id)
            )));
    }

    public void handleFailure(final RoutingContext ctx) {
        ctx.response()
            .setStatusCode(HTTP_INTERNAL_ERROR)
            .end(jsonComponent.toJson(withError(
                HTTP_INTERNAL_ERROR,
                INTERNAL_SERVER_ERROR_CODE
            )));
    }
}
