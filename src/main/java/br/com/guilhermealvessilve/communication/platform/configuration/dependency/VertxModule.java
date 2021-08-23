package br.com.guilhermealvessilve.communication.platform.configuration.dependency;

import br.com.guilhermealvessilve.communication.platform.infrastructure.database.migration.MigrationManager;
import com.google.inject.AbstractModule;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class VertxModule extends AbstractModule {

    private static final ValidatorFactory FACTORY = Validation.buildDefaultValidatorFactory();

    @Override
    protected void configure() {
        bind(Validator.class).toProvider(FACTORY::getValidator);
        bind(MigrationManager.class).toInstance(new MigrationManager());
    }
}
