# Users schema

# --- !Ups

CREATE SEQUENCE job_id_seq;
CREATE TABLE Jobs(
    id bigint NOT NULL DEFAULT nextval('job_id_seq'),
    title text NOT NULL,
    description text,
    skills text,
    contract int,
    company_name text,
    company_website text,
    city text,
    country char(3) NOT NULL,
    created TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (id)
);

# --- !Downs

DROP TABLE Jobs;
DROP SEQUENCE job_id_seq;
