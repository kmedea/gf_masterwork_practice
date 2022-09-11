CREATE TABLE IF NOT EXISTS users (
    id         int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    first_name varchar(250),
    last_name  varchar(250),
    user_name  varchar(100) UNIQUE,
    email      varchar(250),
    password   varchar(250) NOT NULL,
    address    varchar(250),
    role       varchar(50),
    state      varchar(50),
    enabled    boolean
);