CREATE TABLE message_tbl (
    id UUID NOT NULL PRIMARY KEY,
    schedule_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    from_sender VARCHAR(255) NOT NULL,
    to_destination VARCHAR(255) NOT NULL,
    type VARCHAR(50) NOT NULL,
    message TEXT NOT NULL,
    sent BOOLEAN DEFAULT FALSE,
    active BOOLEAN DEFAULT TRUE
);

CREATE INDEX idx_message_tbl_schedule_time ON message_tbl(schedule_time);
