package br.com.guilhermealvessilve.communication.platform.infrastructure.handler.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class ErrorsDTO {

    private final List<ErrorDTO> errors = new ArrayList<>();

    public void add(@NonNull ErrorDTO error) {
        this.errors.add(error);
    }

    public void addAll(@NonNull List<ErrorDTO> errors) {
        errors.forEach(this::add);
    }
}
