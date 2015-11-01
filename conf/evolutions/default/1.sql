# --- !Ups

CREATE SEQUENCE employer_id_seq;
CREATE TABLE Employers(
    id bigint NOT NULL UNIQUE DEFAULT nextval('employer_id_seq'),
    name text NOT NULL UNIQUE,
    email text NOT NULL UNIQUE,
    password text NOT NULL,
    created TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (id)
);

CREATE SEQUENCE job_id_seq;
CREATE TABLE Jobs(
    id bigint NOT NULL UNIQUE DEFAULT nextval('job_id_seq'),
    title text NOT NULL,
    description text,
    skills text,
    contract int,
    remote boolean default false,
    city text,
    country char(2) NOT NULL,
    employer_id bigint NOT NULL,
    created TIMESTAMP WITH TIME ZONE NOT NULL,
    FOREIGN KEY (employer_id) REFERENCES Employers(id),
    PRIMARY KEY (id)
);

# --- !Downs

DROP TABLE Jobs;
DROP SEQUENCE job_id_seq;

DROP TABLE Employers;
DROP SEQUENCE employer_id_seq;
