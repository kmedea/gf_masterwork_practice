CREATE TABLE IF NOT EXISTS orders
(
    id               int PRIMARY KEY AUTO_INCREMENT,
    basket_id        int,
    purchase_date    TIMESTAMP,
    status           varchar(50),
    total_price      int,
    is_paid          boolean,
    shipping_address varchar(250),
    shipping_date    TIMESTAMP
);