package br.com.guilhermealvessilve.communication.platform.infrastructure.endpoint.validator;

import io.vertx.core.Vertx;
import io.vertx.ext.web.validation.RequestPredicate;
import io.vertx.ext.web.validation.ValidationHandler;
import io.vertx.json.schema.SchemaParser;
import io.vertx.json.schema.SchemaRouter;
import io.vertx.json.schema.SchemaRouterOptions;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

/**
 * Referência:
 *  https://github.com/vert-x3/vertx-examples/blob/4.x/web-examples/src/main/java/io/vertx/example/web/validation/ValidationExampleServer.java
 * @author Guilherme Alves Silveira
 */
@UtilityClass
public class Validators {

    public static ValidationHandler bodyRequiredHandler(@NonNull final Vertx vertx) {
        final var schemaParser = SchemaParser.createDraft7SchemaParser(
            SchemaRouter.create(vertx, new SchemaRouterOptions())
        );

        return ValidationHandler.builder(schemaParser)
            .predicate(RequestPredicate.BODY_REQUIRED)
            .build();
    }
}
