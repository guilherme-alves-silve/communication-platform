package br.com.guilhermealvessilve.communication.platform.dependency;

import br.com.guilhermealvessilve.communication.platform.application.usecase.validator.MessageDtoValidator;
import br.com.guilhermealvessilve.communication.platform.infrastructure.component.ErrorMessageComponent;
import br.com.guilhermealvessilve.communication.platform.infrastructure.component.JsonComponent;
import br.com.guilhermealvessilve.communication.platform.infrastructure.database.migration.MigrationManager;
import br.com.guilhermealvessilve.communication.platform.infrastructure.endpoint.component.ResponseComponent;
import com.google.inject.AbstractModule;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

/**
 * @author Guilherme Alves Silveira
 */
class VertxModule extends AbstractModule {

    private static final ValidatorFactory FACTORY = Validation.buildDefaultValidatorFactory();

    @Override
    protected void configure() {
        bind(Validator.class).toProvider(FACTORY::getValidator);
        bind(MigrationManager.class).toInstance(new MigrationManager());
        final var jsonComponent = new JsonComponent();
        final var errorMessageComponent = new ErrorMessageComponent();
        bind(JsonComponent.class).toInstance(jsonComponent);
        bind(ResponseComponent.class).toInstance(new ResponseComponent(jsonComponent, errorMessageComponent));
        bind(ErrorMessageComponent.class).toInstance(errorMessageComponent);
        bind(MessageDtoValidator.class).toInstance(new MessageDtoValidator());
    }
}
