CREATE TABLE IF NOT EXISTS products
(
    id     int PRIMARY KEY AUTO_INCREMENT,
    code   varchar(250) UNIQUE,
    name   varchar(250),
    status varchar(50)
);

CREATE TABLE IF NOT EXISTS product_categories
(
    id   int PRIMARY KEY AUTO_INCREMENT,
    name varchar(250)
);

CREATE TABLE IF NOT EXISTS product_category
(
    product_id          int,
    product_category_id int,
    CONSTRAINT pk_product_category PRIMARY KEY (product_id, product_category_id),
    CONSTRAINT fk_product_category_products FOREIGN KEY (product_id) REFERENCES products(id),
    CONSTRAINT fk_product_category_product_categories FOREIGN KEY (product_category_id) REFERENCES product_categories(id)
);