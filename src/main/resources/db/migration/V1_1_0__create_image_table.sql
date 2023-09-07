CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
SELECT uuid_generate_v1();

CREATE TABLE IF NOT EXISTS image (
 id UUID DEFAULT uuid_generate_v4 (),
 name VARCHAR NOT NULL,
 path VARCHAR NOT NULL UNIQUE,
 year int,
 day int,
 month int,
 nick_name VARCHAR,
 PRIMARY KEY (id)

);