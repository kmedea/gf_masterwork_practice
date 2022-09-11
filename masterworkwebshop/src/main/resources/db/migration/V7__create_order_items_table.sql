CREATE TABLE IF NOT EXISTS order_items
(
    id         int PRIMARY KEY AUTO_INCREMENT,
    product_id int,
    quantity   int,
    price      int,
    order_id  int,
    CONSTRAINT fk_orders_order_items FOREIGN KEY (order_id) REFERENCES orders(id)
);