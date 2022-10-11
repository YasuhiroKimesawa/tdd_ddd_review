CREATE TABLE carts (
id              VARCHAR(255) NOT NULL,
user_account_id VARCHAR(255) NOT NULL,
upper_limit     BIGINT NOT NULL,
PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE cart_items (
id              VARCHAR(255) NOT NULL,
cart_id         VARCHAR(255) NOT NULL,
item_name       VARCHAR(255) NOT NULL,
quantity        BIGINT NOT NULL,
price           BIGINT NOT NULL,
PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;