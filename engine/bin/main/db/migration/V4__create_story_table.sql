DROP TABLE IF EXISTS user_story;

CREATE TABLE user_story (
    title VARCHAR(255) PRIMARY KEY NOT NULL,
    functionality VARCHAR(255),
    priority VARCHAR(255),
    feature_name VARCHAR(255) REFERENCES feature(name),
    stories VARCHAR(255)
);