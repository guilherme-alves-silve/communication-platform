package br.com.guilhermealvessilve.communication.platform.infrastructure.component;

import br.com.guilhermealvessilve.communication.platform.dependency.InjectionModules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class ErrorMessageComponentTest {

    private ErrorMessageComponent component;

    @BeforeEach
    void setupEach() {
        Locale.setDefault(Locale.US);
        this.component = InjectionModules.getInstance(ErrorMessageComponent.class);
    }

    @Test
    void shouldGetTheRightMessageOfTheParamCode() {
        assertAll(
            () -> assertThat(component.getMessage(ErrorMessageComponent.INVALID_PARAMETER_CODE)).isEqualTo("Invalid parameter"),
            () -> assertThat(component.getMessage(ErrorMessageComponent.INTERNAL_SERVER_ERROR_CODE)).isEqualTo("Internal Server Error"),
            () -> assertThat(component.getMessage(ErrorMessageComponent.NOT_FOUND_CODE)).isEqualTo("Element with id {0} was not found")
        );
    }
}
