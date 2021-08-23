package br.com.guilhermealvessilve.communication.platform.infrastructure.endpoint.exception.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Guilherme Alves Silveira
 */
@Getter
public class ErrorsDto {

    private final List<ErrorDto> errors = new ArrayList<>();

    public ErrorsDto add(@NonNull ErrorDto error) {
        this.errors.add(error);
        return this;
    }

    public ErrorsDto addAll(@NonNull List<ErrorDto> errors) {
        errors.forEach(this::add);
        return this;
    }

    public boolean noError() {
        return errors.isEmpty();
    }

    public boolean hasError() {
        return !errors.isEmpty();
    }

    public static ErrorsDto withError(final int status, @NotNull final String code) {
        return new ErrorsDto().add(ErrorDto.withError(status, code));
    }

    public static ErrorsDto withError(final int status, @NotNull final String code, final String message) {
        return new ErrorsDto().add(ErrorDto.withError(status, code, message));
    }

    public static ErrorsDto withErrors(@NotNull List<ErrorDto> dtos) {
        return new ErrorsDto()
            .addAll(dtos);
    }

    public static ErrorsDto withErrors(@NotNull ErrorDto... dtos) {
        return new ErrorsDto()
            .addAll(Arrays.asList(dtos));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("errors", errors)
            .toString();
    }
}
