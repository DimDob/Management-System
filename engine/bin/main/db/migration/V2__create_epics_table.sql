DROP TABLE IF EXISTS epics cascade;

CREATE TABLE IF NOT EXISTS epics (
    id UUID PRIMARY KEY NOT NULL,
    name VARCHAR(64)
);

