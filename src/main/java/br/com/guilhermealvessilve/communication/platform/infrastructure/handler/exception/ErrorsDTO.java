package br.com.guilhermealvessilve.communication.platform.infrastructure.handler.exception;

import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class ErrorsDTO {

    private final List<ErrorDTO> errors = new ArrayList<>();

    public ErrorsDTO add(@NonNull ErrorDTO error) {
        this.errors.add(error);
        return this;
    }

    public ErrorsDTO addAll(@NonNull List<ErrorDTO> errors) {
        errors.forEach(this::add);
        return this;
    }

    public static ErrorsDTO withError(final int status, final String code) {
        return new ErrorsDTO().add(ErrorDTO.withError(status, code));
    }

    public static ErrorsDTO withErrors(ErrorDTO... dtos) {
        return new ErrorsDTO()
            .addAll(Arrays.asList(dtos));
    }
}
