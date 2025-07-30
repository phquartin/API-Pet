CREATE TABLE pet (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    firstname VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    type ENUM("DOG", "CAT") NOT NULL,
    gender ENUM("MALE", "FEMALE") NOT NULL,
    age DOUBLE,
    weight DOUBLE,
    breed VARCHAR(100),
    address_id BIGINT NOT NULL,
    CONSTRAINT fk_pet_address FOREIGN KEY (address_id) REFERENCES address(id)
);