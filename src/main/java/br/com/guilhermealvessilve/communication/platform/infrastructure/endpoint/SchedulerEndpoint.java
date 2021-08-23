package br.com.guilhermealvessilve.communication.platform.infrastructure.endpoint;

import br.com.guilhermealvessilve.communication.platform.application.usecase.CreateScheduledMessageUseCase;
import br.com.guilhermealvessilve.communication.platform.application.usecase.DeleteScheduledMessageUseCase;
import br.com.guilhermealvessilve.communication.platform.application.usecase.FindScheduledMessageUseCase;
import br.com.guilhermealvessilve.communication.platform.application.usecase.dto.RequestMessageDto;
import br.com.guilhermealvessilve.communication.platform.infrastructure.database.ConnectionPool;
import br.com.guilhermealvessilve.communication.platform.infrastructure.endpoint.util.Responses;
import br.com.guilhermealvessilve.communication.platform.infrastructure.endpoint.validator.SchedulerValidator;
import br.com.guilhermealvessilve.communication.platform.infrastructure.endpoint.validator.Validators;
import br.com.guilhermealvessilve.communication.platform.infrastructure.util.Jsons;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.sqlclient.SqlClient;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.UUID;

import static br.com.guilhermealvessilve.communication.platform.infrastructure.util.Jsons.toJson;
import static java.net.HttpURLConnection.*;

@Log4j2
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class SchedulerEndpoint {

    private final Vertx vertx;
    private final Router router;
    private final SchedulerValidator validator;
    private final SqlClient client;

    public static void configureEndpoint(@NonNull final Vertx vertx, @NonNull final Router router) {
        final var validator = new SchedulerValidator();
        final var client = ConnectionPool.getClient(vertx);
        final var endpoint = new SchedulerEndpoint(vertx, router, validator, client);
        endpoint.configure();
    }

    private void configure() {
        router.route(HttpMethod.GET, "/scheduler/:id")
            .produces("application/json")
            .handler(this::handleGet)
            .failureHandler(Responses::handleFailure);

        router.route(HttpMethod.POST, "/scheduler")
            .consumes("application/json")
            .produces("application/json")
            .handler(Validators.bodyRequiredHandler(vertx))
            .handler(BodyHandler.create())
            .handler(this::handlePost)
            .failureHandler(Responses::handleFailure);

        router.route(HttpMethod.DELETE, "/scheduler/:id")
            .consumes("application/json")
            .handler(this::handleDelete)
            .failureHandler(Responses::handleFailure);
    }

    private void handleGet(final RoutingContext ctx) {

        logInput(ctx);

        final var id = ctx.pathParam("id");

        if (!validator.validate(id, ctx.response())) {
            return;
        }

        final var uuid = UUID.fromString(id);

        final var findUseCase = FindScheduledMessageUseCase.getInstance(client);
        findUseCase.findById(uuid)
            .onComplete(asyncResult -> {

                if (Responses.handleFailure(asyncResult, ctx)) {
                    return;
                }

                if (asyncResult.result().isEmpty()) {
                    Responses.notFound(ctx, uuid.toString());
                    return;
                }

                ctx.response()
                    .setStatusCode(HTTP_OK)
                    .end(toJson(asyncResult.result().get()));
            });
    }

    private void handlePost(final RoutingContext ctx) {
        final var body = ctx.getBodyAsString();
        final var request = Jsons.fromJson(body, RequestMessageDto.class);

        final var createUseCase = CreateScheduledMessageUseCase.getInstance(client);
        createUseCase.create(request)
            .onComplete(asyncResult -> {

                if (Responses.handleFailure(asyncResult, ctx)) {
                    return;
                }

                ctx.response()
                    .setStatusCode(HTTP_ACCEPTED)
                    .end(toJson(asyncResult.result().orElseThrow()));
            });
    }

    private void handleDelete(final RoutingContext ctx) {

        logInput(ctx);

        final var id = ctx.pathParam("id");
        if (!validator.validate(id, ctx.response())) {
            return;
        }

        final var uuid = UUID.fromString(id);

        final var findUseCase = DeleteScheduledMessageUseCase.getInstance(client);
        findUseCase.deleteById(uuid)
            .onComplete(asyncResult -> {

                if (Responses.handleFailure(asyncResult, ctx)) {
                    return;
                }

                if (!asyncResult.result()) {
                    Responses.notFound(ctx, uuid.toString());
                    return;
                }

                ctx.response()
                    .setStatusCode(HTTP_NO_CONTENT)
                    .end();
            });
    }

    private void logInput(final RoutingContext ctx) {
        final var address = ctx.request()
            .connection()
            .remoteAddress()
            .toString();

        final var id = ctx.pathParam("id");
        LOGGER.info("Request from address {} with id {}", address, id);
    }
}
