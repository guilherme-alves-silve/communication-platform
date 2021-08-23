package br.com.guilhermealvessilve.communication.platform.configuration.dependency;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.experimental.UtilityClass;

/**
 * Injector é thread-safe.
 * Referência:
 *  https://groups.google.com/g/google-guice/c/DfT0eHtYK1Y?pli=1
 */
@UtilityClass
public class InjectionModules {

    private static final Injector VERTX_INJECTOR = Guice.createInjector(new VertxModule());

    public static <T> T getInstance(Class<T> clazz) {
        return VERTX_INJECTOR.getInstance(clazz);
    }
}
