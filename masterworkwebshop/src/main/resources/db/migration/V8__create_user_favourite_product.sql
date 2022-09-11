CREATE TABLE IF NOT EXISTS user_favourite_products
(
    user_id    int,
    product_id int,

    CONSTRAINT pk_user_favourite_products PRIMARY KEY (user_id, product_id),
    CONSTRAINT fk_user_favourite_products_products FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT fk_user_favourite_products_users FOREIGN KEY (user_id) REFERENCES users(id)
);