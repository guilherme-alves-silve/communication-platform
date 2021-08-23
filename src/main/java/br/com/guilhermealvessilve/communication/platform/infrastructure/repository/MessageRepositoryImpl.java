package br.com.guilhermealvessilve.communication.platform.infrastructure.repository;

import br.com.guilhermealvessilve.communication.platform.domain.entity.MessageEntity;
import br.com.guilhermealvessilve.communication.platform.domain.repository.MessageRepository;
import io.vertx.core.Future;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.Tuple;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageRepository {

    private final SqlClient client;

    public Future<Optional<MessageEntity>> findById(UUID id) {
        return client.preparedQuery(String.join(StringUtils.SPACE, StringUtils.EMPTY,
                "SELECT",
                    " id",
                    ",schedule_time",
                    ",from_sender",
                    ",to_destination",
                    ",type",
                "FROM message_tbl",
                "WHERE id = $1;"
            ))
            .execute(Tuple.of(id))
            .map(rows -> {

                final var row = getOneRow(rows);
                if (null == row) {
                    return Optional.empty();
                }

                final var message = new MessageEntity(
                    row.getUUID("id"),
                    row.getLocalDateTime("schedule_time").toInstant(ZoneOffset.UTC),
                    row.getString("from_sender"),
                    row.getString("to_destination"),
                    MessageEntity.Type.valueOf(row.getString("type"))
                );

                return Optional.of(message);
            });
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
}
