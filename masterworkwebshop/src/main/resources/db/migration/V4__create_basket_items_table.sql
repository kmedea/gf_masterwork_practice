CREATE TABLE IF NOT EXISTS basket_items
(
    id         int PRIMARY KEY AUTO_INCREMENT,
    product_id int,
    quantity   int,
    price      int,
    basket_id  int,
    CONSTRAINT fk_baskets_basket_items FOREIGN KEY (basket_id) REFERENCES baskets(id)
);