CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
SELECT uuid_generate_v1();

CREATE TABLE IF NOT EXISTS person (
 id UUID DEFAULT uuid_generate_v4 (),
 name VARCHAR NOT NULL,
 family_name VARCHAR NOT NULL,
 birth_day date,
 PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS person_image(
    person_id UUID REFERENCES person(id),
    image_id UUID REFERENCES image(id),
    PRIMARY KEY (person_id, image_id)
)