package br.com.guilhermealvessilve.communication.platform.infrastructure.component;

import br.com.guilhermealvessilve.communication.platform.dependency.InjectionModules;
import lombok.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JsonComponentTest {

    private JsonComponent component;

    @BeforeEach
    void setupEach() {
        this.component = InjectionModules.getInstance(JsonComponent.class);
    }

    @Test
    void shouldConvertObjectToJson() {
        final var dto = new Dto(1, "Test");
        assertThat(component.toJson(dto)).isEqualTo("{\"id\":1,\"name\":\"Test\"}");
    }

    @Test
    void shouldConvertJsonToObject() {
        final var json = "{\"id\":1,\"name\":\"Test\"}";
        final var dto = new Dto(1, "Test");
        assertThat(component.fromJson(json, Dto.class)).isEqualTo(dto);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @EqualsAndHashCode
    private static class Dto {
        private Integer id;
        private String name;
    }
}
