package br.com.guilhermealvessilve.communication.platform.configuration.exception.dto;

import br.com.guilhermealvessilve.communication.platform.configuration.util.ErrorMessages;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Getter
@Builder
@EqualsAndHashCode
@RequiredArgsConstructor
public class ErrorDto {

    private final int status;
    private final String code;
    private final String message;

    public static ErrorDto withError(final int status, final String code) {
        return new ErrorDto(status, code, ErrorMessages.getMessage(code));
    }

    public static ErrorDto withError(final int status, final String code, final String message) {
        return new ErrorDto(status, code, message);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("status", status)
            .append("code", code)
            .append("message", message)
            .toString();
    }
}
