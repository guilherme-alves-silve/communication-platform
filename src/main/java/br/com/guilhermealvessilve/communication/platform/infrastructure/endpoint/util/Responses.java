package br.com.guilhermealvessilve.communication.platform.infrastructure.endpoint.util;

import br.com.guilhermealvessilve.communication.platform.shared.exception.ErrorViolationException;
import io.vertx.core.AsyncResult;
import io.vertx.ext.web.RoutingContext;
import lombok.experimental.UtilityClass;

import static br.com.guilhermealvessilve.communication.platform.infrastructure.util.Jsons.toJson;
import static br.com.guilhermealvessilve.communication.platform.shared.exception.dto.ErrorsDto.withError;
import static br.com.guilhermealvessilve.communication.platform.shared.util.ErrorMessages.INTERNAL_SERVER_ERROR_CODE;
import static br.com.guilhermealvessilve.communication.platform.shared.util.HttpStatus.INTERNAL_SERVER_ERROR;

@UtilityClass
public class Responses {

    public static <T> boolean handleFailure(AsyncResult<T> asyncResult, RoutingContext ctx) {
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

    public static void handleFailure(final RoutingContext ctx) {
        ctx.response()
            .setStatusCode(INTERNAL_SERVER_ERROR)
            .end(toJson(withError(INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_CODE)));
    }
}
