DROP TABLE IF EXISTS feature CASCADE;

CREATE TABLE IF NOT EXISTS feature (
    name VARCHAR(255) PRIMARY KEY NOT NULL,
    description VARCHAR(255),
    owner VARCHAR(255),
    status VARCHAR(255),
    epic_id UUID REFERENCES epics(id)
);
