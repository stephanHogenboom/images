CREATE TABLE IF NOT EXISTS named_group (
 id UUID DEFAULT uuid_generate_v4 (),
 name VARCHAR NOT NULL UNIQUE,
 PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS group_image (
    group_id UUID REFERENCES named_group(id),
    image_id UUID REFERENCES image(id),
    PRIMARY KEY (group_id, image_id)
)