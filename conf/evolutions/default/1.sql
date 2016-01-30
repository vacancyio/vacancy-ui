# --- !Ups

CREATE SEQUENCE job_id_seq;
CREATE TABLE Jobs(
    id bigint NOT NULL UNIQUE DEFAULT nextval('job_id_seq'),
    employer text NOT NULL,
    title text NOT NULL,
    description text NOT NULL,
    location text,
    application text,
    salary text,
    remote boolean default false,
    contract boolean default false,
    created TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (id)
);

# --- !Downs

DROP TABLE Jobs;
DROP SEQUENCE job_id_seq;
