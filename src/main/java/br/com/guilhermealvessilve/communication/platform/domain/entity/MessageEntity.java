package br.com.guilhermealvessilve.communication.platform.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.Instant;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(of = "id")
public class MessageEntity {

    private final UUID id;
    private final Instant scheduleTime;
    private final String from;
    private final String to;
    private final Type type;
    private final boolean sent;
    private final String text;

    public enum Type {
        EMAIL, SMS, PUSH, WHATSAPP;
    }

    public static MessageEntity withId(@NonNull final String id) {
        return new MessageEntity(UUID.fromString(id), null, null, null, null,  false, null);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
            .append("id", id)
            .append("scheduleTime", scheduleTime)
            .append("from", from)
            .append("to", to)
            .append("type", type)
            .append("sent", sent)
            .toString();
    }
}
