package br.com.guilhermealvessilve.communication.platform.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class MessageEntity {

    private final UUID id;
    private final Instant scheduleTime;
    private final String from;
    private final String to;
    private final Type type;

    public enum Type {
        EMAIL, SMS, PUSH, WHATSAPP;
    }
}
