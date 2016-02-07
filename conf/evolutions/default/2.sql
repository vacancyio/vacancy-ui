# --- !Ups

CREATE SEQUENCE user_id_seq;
CREATE TABLE Users(
    id bigint NOT NULL UNIQUE DEFAULT nextval('user_id_seq'),
    email text NOT NULL UNIQUE,
    password text,
    created TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (id)
);

# --- !Downs

DROP TABLE Users;
DROP SEQUENCE user_id_seq;