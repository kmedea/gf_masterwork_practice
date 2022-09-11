CREATE TABLE IF NOT EXISTS baskets
(
    id          int PRIMARY KEY AUTO_INCREMENT,
    user_id     int,
    total_price int,
    basket_status varchar(50)
);