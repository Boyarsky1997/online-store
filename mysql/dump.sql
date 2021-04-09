create database store;
USE store;
CREATE TABLE user
(
    id        INTEGER AUTO_INCREMENT,
    role      ENUM ('ADMIN','BUYER'),
    name      VARCHAR(25) not null,
    surname   VARCHAR(25) not null,
    login     VARCHAR(40) NOT NULL unique,
    password  VARCHAR(25) NOT NULL,
    blacklist BOOLEAN     not null default false,
    PRIMARY KEY (id)
);

CREATE TABLE `order`
(
    id       INTEGER AUTO_INCREMENT,
    buyer_id int,
    price    double not null,
    date     date   not null,
    status   ENUM ('PAID','NEW') default 'NEW',
    FOREIGN KEY (buyer_id) references user (id),
    PRIMARY KEY (id)
);

CREATE TABLE product
(
    id          INTEGER AUTO_INCREMENT,
    name        VARCHAR(40),
    price       double       not null,
    count       int          not null,
    description varchar(225) not null,
    PRIMARY KEY (id)
);

CREATE TABLE product_in_order
(
    id          INTEGER AUTO_INCREMENT,
    product_id int          not null,
    FOREIGN KEY (product_id) REFERENCES product (id),
    order_id   int          not null,
    FOREIGN KEY (order_id) REFERENCES `order` (id),
    PRIMARY KEY (id, product_id, order_id)
);