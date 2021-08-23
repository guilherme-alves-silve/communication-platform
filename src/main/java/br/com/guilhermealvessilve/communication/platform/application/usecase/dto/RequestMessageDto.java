package br.com.guilhermealvessilve.communication.platform.application.usecase.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.Instant;

@Getter
public class RequestMessageDto {

    @NotNull
    @Future
    private final Instant scheduleTime;

    @NotBlank
    private final String from;

    @NotBlank
    private final String to;

    @NotBlank
    private final String message;

    @NotNull
    private final Type type;

    @JsonCreator
    public RequestMessageDto(@JsonProperty("scheduleTime") Instant scheduleTime,
                             @JsonProperty("from") String from,
                             @JsonProperty("to") String to,
                             @JsonProperty("message") String message,
                             @JsonProperty("type") Type type) {
        this.scheduleTime = scheduleTime;
        this.from = from;
        this.to = to;
        this.message = message;
        this.type = type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("scheduleTime", scheduleTime)
            .append("from", from)
            .append("to", to)
            .append("type", type)
            .toString();
    }
}
