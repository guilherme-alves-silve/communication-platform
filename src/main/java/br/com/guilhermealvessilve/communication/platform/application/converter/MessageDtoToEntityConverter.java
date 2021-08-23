package br.com.guilhermealvessilve.communication.platform.application.converter;

import br.com.guilhermealvessilve.communication.platform.application.usecase.dto.RequestMessageDto;
import br.com.guilhermealvessilve.communication.platform.application.usecase.dto.ResponseMessageDto;
import br.com.guilhermealvessilve.communication.platform.application.usecase.dto.Type;
import br.com.guilhermealvessilve.communication.platform.domain.entity.MessageEntity;
import com.google.inject.Singleton;
import lombok.NonNull;

import java.util.UUID;

@Singleton
public class MessageDtoToEntityConverter {

    private static final boolean NOT_SENT = false;

    public MessageEntity toEntity(final RequestMessageDto dto) {
        return toEntity(UUID.randomUUID(), dto);
    }

    public MessageEntity toEntity(final UUID id, final RequestMessageDto dto) {
        return new MessageEntity(
            id,
            dto.getScheduleTime(),
            dto.getFrom(),
            dto.getTo(),
            MessageEntity.Type.valueOf(dto.getType().toString()),
            NOT_SENT,
            dto.getMessage()
        );
    }

    public ResponseMessageDto toResponseDto(@NonNull final MessageEntity entity) {
        return new ResponseMessageDto(
            entity.getId(),
            entity.getScheduleTime(),
            entity.getFrom(),
            entity.getTo(),
            Type.valueOf(entity.getType().toString())
        );
    }
}
