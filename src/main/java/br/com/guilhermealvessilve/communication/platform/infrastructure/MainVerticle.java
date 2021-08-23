package br.com.guilhermealvessilve.communication.platform.infrastructure;

import br.com.guilhermealvessilve.communication.platform.infrastructure.database.migration.MigrationManager;
import br.com.guilhermealvessilve.communication.platform.infrastructure.endpoint.SchedulerEndpoint;
import br.com.guilhermealvessilve.communication.platform.configuration.dependency.InjectionModules;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import lombok.extern.log4j.Log4j2;

/**
 * @author Guilherme Alves Silveira
 */
@Log4j2
public class MainVerticle extends AbstractVerticle {

    @Override
    public void start() {

        final var migration = InjectionModules.getInstance(MigrationManager.class);

        migration.migrate();

        final var router = Router.router(vertx);

        SchedulerEndpoint.configureEndpoint(vertx, router);

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(8888)
            .onSuccess(server -> LOGGER.info("HTTP server started on port {}", server.actualPort()));
    }
}
