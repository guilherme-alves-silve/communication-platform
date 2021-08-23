package br.com.guilhermealvessilve.communication.platform.domain.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@Getter
@RequiredArgsConstructor
public class MessageDTO {

    private final Instant scheduleTime;
    private final String from;
    private final String to;
    private final Type type;

    public enum Type {
        EMAIL, SMS, PUSH, WHATSAPP;
    }
}
