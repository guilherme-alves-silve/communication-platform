package br.com.guilhermealvessilve.communication.platform.infrastructure;

import br.com.guilhermealvessilve.communication.platform.dependency.InjectionModules;
import br.com.guilhermealvessilve.communication.platform.domain.AppConfiguration;
import br.com.guilhermealvessilve.communication.platform.infrastructure.database.migration.MigrationManager;
import br.com.guilhermealvessilve.communication.platform.infrastructure.endpoint.SchedulerEndpoint;
import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

import java.util.Locale;

/**
 * @author Guilherme Alves Silveira
 */
@Log4j2
public class MainVerticle extends AbstractVerticle {

    private final MigrationManager migration;

    public MainVerticle() {
        this(InjectionModules.getInstance(MigrationManager.class));
    }

    MainVerticle(@NonNull final MigrationManager migration) {
        this.migration = migration;
        configureDefaultLanguage();
    }

    @Override
    public void start() {

        migration.migrate();

        final var router = Router.router(vertx);

        SchedulerEndpoint.configureEndpoint(vertx, router);

        final var properties = AppConfiguration.configuration().properties();

        vertx.createHttpServer()
            .requestHandler(router)
            .listen(Integer.parseInt(properties.getOrDefault("app.port", "8888")))
            .onSuccess(server -> LOGGER.info("HTTP server started on port {}", server.actualPort()));
    }

    private void configureDefaultLanguage() {
        Locale.setDefault(Locale.US);
    }
}
