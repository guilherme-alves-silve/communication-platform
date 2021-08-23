package br.com.guilhermealvessilve.communication.platform.infrastructure.repository;

import br.com.guilhermealvessilve.communication.platform.domain.entity.MessageEntity;
import br.com.guilhermealvessilve.communication.platform.domain.repository.MessageRepository;
import io.vertx.core.Future;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.Tuple;
import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;

public class MessageRepositoryImpl implements MessageRepository {

    private final SqlClient client;

    MessageRepositoryImpl(@NonNull final SqlClient client) {
        this.client = client;
    }

    @Override
    public Future<Optional<MessageEntity>> findById(@NonNull final UUID id) {
        return client.preparedQuery(String.join(StringUtils.SPACE, StringUtils.EMPTY,
                "SELECT",
                    " id",
                    ",schedule_time",
                    ",from_sender",
                    ",to_destination",
                    ",type",
                    ",sent",
                    ",message",
                "FROM message_tbl",
                "WHERE",
                    "id = $1 AND",
                    "active = TRUE;"
            ))
            .execute(Tuple.of(id))
            .map(rows -> {

                final var row = getOneRow(rows);
                if (null == row) {
                    return Optional.empty();
                }

                final var message = getMessageEntity(row);
                return Optional.of(message);
            });
    }

    @Override
    public Future<List<MessageEntity>> findNotSentScheduledMessages(@NonNull final LocalDateTime scheduledDateTime,
                                                                    final int expectedCountElements) {
        return client.preparedQuery(String.join(StringUtils.SPACE, StringUtils.EMPTY,
            "SELECT",
                " id",
                ",schedule_time",
                ",from_sender",
                ",to_destination",
                ",type",
                ",sent",
                ",message",
            "FROM message_tbl",
            "WHERE",
                "schedule_time <= $1 AND",
                "sent = FALSE AND",
                "active = TRUE",
            "LIMIT $2;"
        ))
        .execute(Tuple.of(scheduledDateTime, expectedCountElements))
        .map(rows -> {

            if (rows.rowCount() == 0) {
                return Collections.emptyList();
            }

            final var messages = new ArrayList<MessageEntity>(rows.rowCount());
            for (final var row : rows) {
                final MessageEntity message = getMessageEntity(row);
                messages.add(message);
            }

            return messages;
        });
    }

    @Override
    public Future<Boolean> save(@NonNull final MessageEntity message) {
        return client.preparedQuery(String.join(StringUtils.SPACE, StringUtils.EMPTY,
            "INSERT INTO message_tbl(",
                " id",
                ",schedule_time ",
                ",from_sender ",
                ",to_destination ",
                ",type",
                ",message",
            ")",
            "VALUES ($1, $2, $3, $4, $5, $6);"
        ))
        .execute(Tuple.of(
            message.getId(),
            LocalDateTime.ofInstant(message.getScheduleTime(), ZoneId.systemDefault()),
            message.getFrom(),
            message.getTo(),
            message.getType().toString(),
            message.getText()
        ))
        .map(rows -> rows.rowCount() == 1);
    }

    @Override
    public Future<Boolean> deleteById(@NonNull final UUID id) {
        return client.preparedQuery(String.join(StringUtils.SPACE, StringUtils.EMPTY,
            "UPDATE message_tbl",
            "SET active = FALSE",
            "WHERE id = $1;"
        ))
        .execute(Tuple.of(id))
        .map(rows -> rows.rowCount() == 1);
    }

    private MessageEntity getMessageEntity(Row row) {
        return new MessageEntity(
            row.getUUID("id"),
            row.getLocalDateTime("schedule_time").toInstant(ZoneOffset.UTC),
            row.getString("from_sender"),
            row.getString("to_destination"),
            MessageEntity.Type.valueOf(row.getString("type")),
            row.getBoolean("sent"),
            row.getString("message")
        );
    }

    private Row getOneRow(final RowSet<Row> rows) {
        final var it = rows.iterator();
        if (!it.hasNext()) {
            return null;
        }

        if (rows.rowCount() > 1) {
            throw new IllegalArgumentException("The result returned more than one result!");
        }

        return it.next();
    }

    public static MessageRepository getInstance(final SqlClient client) {
        return new MessageRepositoryImpl(client);
    }
}
