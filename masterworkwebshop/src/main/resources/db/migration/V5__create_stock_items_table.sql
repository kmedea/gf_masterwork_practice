CREATE TABLE IF NOT EXISTS stock
(
    id          int PRIMARY KEY AUTO_INCREMENT,
    product_id  int UNIQUE,
    quantity    int
);