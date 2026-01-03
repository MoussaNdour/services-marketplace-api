
create table image(
    id serial primary key,
    path text,
    name varchar(255)
);

ALTER TABLE service
ADD COLUMN image_id INT;

ALTER TABLE service
ADD CONSTRAINT fk_service_image
FOREIGN KEY (image_id)
REFERENCES image(id);
