DELETE
FROM user_favourite_products;
DELETE
FROM product_category;
DELETE
FROM basket_items;
DELETE
FROM baskets;
DELETE
FROM order_items;
DELETE
FROM orders;
DELETE
FROM stock;
DELETE
FROM products;
DELETE
FROM product_categories;
DELETE
FROM users;



INSERT INTO users (id, first_name, last_name, user_name, email, password, address, role, state, enabled)
VALUES (100, 'John', 'Doe', 'JohnD', 'johnd@test.hu', '$2a$10$BywHMcCLULUbnrTIhaG0pefepFWOA3YDQx/QTnCjETRPqqvTwIt7C',
        'Budapest', 'ROLE_ADMIN', 'ACTIVE', '1'),
       (200, 'Jack', 'Doe', 'JackD', 'jackd@test.hu', '$2a$10$BywHMcCLULUbnrTIhaG0pefepFWOA3YDQx/QTnCjETRPqqvTwIt7C',
        'Pécs', 'ROLE_USER', 'ACTIVE', '1'),
       (300, 'Manci', 'Doe', 'ManciD', 'mancid@test.hu', '$2a$10$BywHMcCLULUbnrTIhaG0pefepFWOA3YDQx/QTnCjETRPqqvTwIt7C',
        'Miskolc', 'ROLE_USER', 'ACTIVE', '1'),
       (999, 'Jane', 'Doe', 'JaneD', 'janed@test.hu', '$2a$10$BywHMcCLULUbnrTIhaG0pefepFWOA3YDQx/QTnCjETRPqqvTwIt7C',
        'Debrecen', 'ROLE_ADMIN', 'ACTIVE', '1');

INSERT INTO product_categories (id, name)
VALUES (100, 'A'),
       (200, 'B'),
       (300, 'C');


INSERT INTO products (id, code, name, status)
VALUES (100, 'asztal123', 'asztal', 'ACTIVE'),
       (200, 'szek123', 'szek', 'ACTIVE'),
       (300, 'ablak123', 'ablak', 'ACTIVE'),
       (400, 'tabla123', 'tabla', 'ACTIVE'),
       (500, 'asztal83', 'asztal', 'ACTIVE'),
       (600, 'szek183', 'szek', 'ACTIVE'),
       (700, 'ablak83', 'ablak', 'ACTIVE'),
       (800, 'tabla83', 'tabla', 'ACTIVE');


INSERT INTO product_category(product_id, product_category_id)
VALUES (100, 100),
       (200, 300),
       (300, 200),
       (400, 200);

INSERT INTO stock(id, product_id, quantity)
VALUES (10, 100, 100),
       (20, 200, 100),
       (30, 300, 100),
       (50, 500, 100),
       (60, 600, 100),
       (70, 700, 100);

INSERT INTO baskets (id, user_id, total_price, basket_status)
VALUES (100, 100, 4000, 'ACTIVE'),
       (200, 200, 4000, 'ACTIVE'),
       (300, 999, 4000, 'ACTIVE'),
       (400, 300, 4000, 'ACTIVE');


INSERT INTO basket_items(id, product_id, quantity, price, basket_id)
VALUES (100, 100, 2, 1000, 100),
       (200, 200, 1, 1000, 100),
       (300, 300, 1, 1000, 100),
       (400, 100, 2, 1000, 200),
       (500, 200, 1, 1000, 200),
       (600, 300, 1, 1000, 200),
       (700, 100, 2, 1000, 300),
       (800, 200, 1, 1000, 300),
       (900, 300, 1, 1000, 300),
       (1000, 100, 2, 1000, 400),
       (1100, 200, 1, 1000, 400),
       (1200, 300, 1, 1000, 400);

INSERT INTO orders(id, basket_id, purchase_date, status, total_price, is_paid, shipping_address, shipping_date)
VALUES (100, 100, '2022-06-15 23:16:37', 'ACTIVE', 4000, true, 'Budapest', '2022-06-17 23:16:37'),
       (200, 100, '2022-06-15 23:16:37', 'DELIVERED', 5000, true, 'Budapest', '2022-06-16 23:16:37'),
       (300, 200, '2022-06-15 23:16:37', 'ACTIVE', 7000, true, 'Pécs', '2022-06-17 23:16:37'),
       (400, 200, '2022-06-15 23:16:37', 'DELIVERED', 6000, true, 'Pécs', '2022-06-16 23:16:37'),
       (500, 300, '2022-06-15 23:16:37', 'ACTIVE', 8000, true, 'Debrecen', '2022-06-17 23:16:37'),
       (600, 300, '2022-06-15 23:16:37', 'DELIVERED', 6000, true, 'Debrecen', '2022-06-16 23:16:37'),
       (700, 400, '2022-06-15 23:16:37', 'ACTIVE', 8000, true, 'Miskolc', '2022-06-17 23:16:37'),
       (800, 400, '2022-06-15 23:16:37', 'ACTIVE', 6000, true, 'Miskolc', '2022-06-16 23:16:37');;


INSERT INTO order_items(id, product_id, quantity, price, order_id)
VALUES (100, 100, 2, 1000, 100),
       (200, 200, 1, 1000, 100),
       (300, 300, 1, 1000, 100),
       (400, 100, 4, 1000, 300),
       (500, 200, 1, 1000, 300),
       (600, 300, 1, 1000, 300),
       (700, 300, 1, 1000, 300),
       (800, 100, 1, 1000, 400),
       (900, 300, 1, 1000, 800),
       (1000, 300, 1, 1000, 800),
       (1100, 100, 1, 1000, 800);

INSERT INTO user_favourite_products(user_id, product_id)
VALUES (100, 100),
       (100, 200);
