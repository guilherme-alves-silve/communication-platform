package br.com.guilhermealvessilve.communication.platform.application.usecase.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.Instant;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class ResponseMessageDto {

    private final UUID id;
    private final Instant scheduleTime;
    private final String from;
    private final String to;
    private final Type type;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", id)
            .append("scheduleTime", scheduleTime)
            .append("from", from)
            .append("to", to)
            .append("type", type)
            .toString();
    }
}
