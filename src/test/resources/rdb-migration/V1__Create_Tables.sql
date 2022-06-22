CREATE TABLE carts (
id              VARCHAR(255) NOT NULL,
user_account_id VARCHAR(255) NOT NULL,
upper_limit     INTEGER NOT NULL,
PRIMARY KEY (id)
);

CREATE TABLE cart_items (
id              VARCHAR(255) NOT NULL,
cart_id         VARCHAR(255) NOT NULL,
item_name       VARCHAR(255) NOT NULL,
quantity        INTEGER NOT NULL,
price           INTEGER NOT NULL,
PRIMARY KEY(id)
);