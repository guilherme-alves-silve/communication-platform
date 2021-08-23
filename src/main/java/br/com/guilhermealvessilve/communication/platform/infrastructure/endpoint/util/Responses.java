package br.com.guilhermealvessilve.communication.platform.infrastructure.endpoint.util;

import br.com.guilhermealvessilve.communication.platform.configuration.exception.ErrorViolationException;
import io.vertx.core.AsyncResult;
import io.vertx.ext.web.RoutingContext;
import lombok.experimental.UtilityClass;

import java.text.MessageFormat;

import static br.com.guilhermealvessilve.communication.platform.infrastructure.util.Jsons.toJson;
import static br.com.guilhermealvessilve.communication.platform.configuration.exception.dto.ErrorsDto.withError;
import static br.com.guilhermealvessilve.communication.platform.configuration.util.ErrorMessages.*;
import static java.net.HttpURLConnection.HTTP_INTERNAL_ERROR;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;

@UtilityClass
public class Responses {

    public static <T> boolean handleFailure(final AsyncResult<T> asyncResult, final RoutingContext ctx) {
        if (asyncResult.failed()) {
            if (asyncResult.cause() instanceof ErrorViolationException) {
                final var violation = (ErrorViolationException) asyncResult.cause();
                ctx.response()
                    .end(toJson(violation.getErrors()));
                return true;
            }

            handleFailure(ctx);
            return true;
        }

        return false;
    }

    public static void notFound(final RoutingContext ctx, final String id) {
        ctx.response()
            .end(toJson(withError(
                HTTP_NOT_FOUND,
                NOT_FOUND_CODE,
                MessageFormat.format(getMessage(NOT_FOUND_CODE), id)
            )));
    }

    public static void handleFailure(final RoutingContext ctx) {
        ctx.response()
            .setStatusCode(HTTP_INTERNAL_ERROR)
            .end(toJson(withError(HTTP_INTERNAL_ERROR, INTERNAL_SERVER_ERROR_CODE)));
    }
}
