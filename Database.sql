--CREATE DATABASE DUONG_SE1704
USE DUONG_SE1704

DROP TABLE Cart

DROP TABLE BillDetail

DROP TABLE Bill

DROP TABLE Customer

DROP TABLE Product

DROP TABLE Category

DROP TABLE admin

CREATE TABLE admin
(
    admin    char(32) PRIMARY KEY,
    password char(32) NOT NULL
)
CREATE TABLE Category
(
    cateId   int PRIMARY KEY IDENTITY (1,1),
    cateName nvarchar(100),
    status   int
)
CREATE TABLE Product
(
    pid         varchar(32) PRIMARY KEY,
    pname       nvarchar(200) NOT NULL,
    quantity    int CHECK (quantity >= 0),
    price       money CHECK (price >= 0),
    image       varchar(100),
    description nvarchar(max),
    status      int,
    cateID      int FOREIGN KEY REFERENCES Category (cateId)
)
CREATE TABLE Customer
(
    cid      varchar(32) PRIMARY KEY,
    cname    nvarchar(50) NOT NULL,
    username varchar(30) UNIQUE,
    password varchar(32)  NOT NULL,
    address  nvarchar(max),
    phone    varchar(20),
    status   int,
)

CREATE TABLE Cart
(
    cid      varchar(32) FOREIGN KEY REFERENCES Customer (cid),
    pid      varchar(32) FOREIGN KEY REFERENCES Product (pid),
    quantity int CHECK (quantity >= 0),
)

CREATE TABLE Bill
(
    bid        varchar(32) PRIMARY KEY,
    dateCreate datetime DEFAULT GETDATE(),
    recAddress nvarchar(max),
    recPhone   varchar(20),
    note       nvarchar(max),
    totalMoney money,
    cid        varchar(32) FOREIGN KEY REFERENCES Customer (cid),
    status     int,
)
CREATE TABLE BillDetail
(
    bid         varchar(32)
        CONSTRAINT BillDetail_Bill_bid_fk
            REFERENCES Bill (bid),
    pid         varchar(32)
        CONSTRAINT BillDetail_Product_pid_fk
            REFERENCES Product (pid),
    buyQuantity int,
    buyPrice    money,
    subtotal    money,
    PRIMARY KEY (bid, pid)
)

--dmin:

INSERT INTO admin (admin, password)
VALUES ('admin1', 'password123');
INSERT INTO admin (admin, password)
VALUES ('admin2', 'qwertyuiop');
INSERT INTO admin (admin, password)
VALUES ('admin3', 'letmein123');
INSERT INTO admin (admin, password)
VALUES ('admin4', 'password1234');
INSERT INTO admin (admin, password)
VALUES ('admin5', 'mypassword');
INSERT INTO admin (admin, password)
VALUES ('supersanta', '18273645');
--Category:

INSERT INTO Category (cateName, status)
VALUES ('Clothing', 1);
INSERT INTO Category (cateName, status)
VALUES ('Electronics', 1);
INSERT INTO Category (cateName, status)
VALUES ('Home & Garden', 1);
INSERT INTO Category (cateName, status)
VALUES ('Beauty & Personal Care', 1);
INSERT INTO Category (cateName, status)
VALUES ('Sports & Outdoors', 1);
--Product:

INSERT INTO Product (pid, pname, quantity, price, image, description, status, cateID)
VALUES ('P001', 'Men T-Shirt', 50, 19.99, 'b1.jpg', 'This is a comfortable and stylish t-shirt for men.', 1, 1);
INSERT INTO Product (pid, pname, quantity, price, image, description, status, cateID)
VALUES ('P002', 'Wireless Headphones', 100, 49.99, 'b2.jpg',
        'These headphones have great sound quality and are easy to connect to your devices.', 1, 2);
INSERT INTO Product (pid, pname, quantity, price, image, description, status, cateID)
VALUES ('P003', 'Coffee Maker', 20, 89.99, 'b3.jpg',
        'Make your morning coffee with ease using this high-quality coffee maker.', 1, 3);
INSERT INTO Product (pid, pname, quantity, price, image, description, status, cateID)
VALUES ('P004', 'Lipstick Set', 30, 14.99, 'b4.jpg', 'This set includes 5 different shades of long-lasting lipstick.',
        1, 4);
INSERT INTO Product (pid, pname, quantity, price, image, description, status, cateID)
VALUES ('P005', 'Yoga Mat', 15, 29.99, 'banner1.jpg',
        'Stay comfortable and focused during your yoga practice with this high-quality mat.', 1, 5);
INSERT INTO Product (pid, pname, quantity, price, image, description, status, cateID)
VALUES ('P006', 'Bluetooth Speaker', 50, 79.99, 'g1.jpg',
        'This compact and portable speaker delivers powerful sound and is perfect for outdoor activities.', 1, 2);
INSERT INTO Product (pid, pname, quantity, price, image, description, status, cateID)
VALUES ('P007', 'Running Shoes', 80, 109.99, 'g2.jpg',
        'These lightweight and comfortable shoes are designed for runners and provide excellent support.', 1, 3);
INSERT INTO Product (pid, pname, quantity, price, image, description, status, cateID)
VALUES ('P008', 'Wireless Mouse', 60, 34.99, 'g3.jpg',
        'This mouse is easy to use and connects wirelessly to your computer or laptop.', 1, 1);
INSERT INTO Product (pid, pname, quantity, price, image, description, status, cateID)
VALUES ('P009', 'LED Desk Lamp', 25, 49.99, 'banner4.jpg',
        'This lamp features adjustable brightness levels and color temperatures to suit your needs.', 1, 4);
INSERT INTO Product (pid, pname, quantity, price, image, description, status, cateID)
VALUES ('P010', 'Portable Charger', 70, 39.99, 'banner3.jpg',
        'Charge your devices on-the-go with this high-capacity portable charger.', 1, 5);
INSERT INTO Product (pid, pname, quantity, price, image, description, status, cateID)
VALUES ('P011', 'Water Bottle', 90, 24.99, 'banner2.jpg',
        'Stay hydrated throughout the day with this durable and leak-proof water bottle.', 1, 2);
INSERT INTO Product (pid, pname, quantity, price, image, description, status, cateID)
VALUES ('P012', 'Cookware Set', 10, 199.99, 'g4.jpg',
        'This set includes high-quality pots and pans that are perfect for cooking a variety of dishes.', 1, 1);
INSERT INTO Product (pid, pname, quantity, price, image, description, status, cateID)
VALUES ('P013', 'Fitness Tracker', 40, 89.99, 'g5.jpg',
        'Track your fitness goals with this feature-packed tracker that includes heart rate monitoring and GPS.', 1, 3);
INSERT INTO Product (pid, pname, quantity, price, image, description, status, cateID)
VALUES ('P014', 'Smart Thermostat', 15, 149.99, 'g6.jpg',
        'Save energy and control your homes temperature with this smart thermostat that can be controlled from your phone.',
        1, 4);
--ustomer:

INSERT INTO Customer (cid, cname, username, password, address, phone, status)
VALUES ('C001', 'supersanta', 'supersanta', '18273645', '123 Main St, Anytown USA', '555-555-5555', 1);
INSERT INTO Customer (cid, cname, username, password, address, phone, status)
VALUES ('C002', 'Samantha Johnson', 'sjohnson', 'letmein123', '456 Park Ave, Anytown USA', '555-555-5556', 1);
INSERT INTO Customer (cid, cname, username, password, address, phone, status)
VALUES ('C003', 'David Lee', 'dlee', 'mypassword', '789 Elm St, Anytown USA', '555-555-5557', 1);
INSERT INTO Customer (cid, cname, username, password, address, phone, status)
VALUES ('C004', 'Emily Chen', 'echen', 'qwertyuiop', '321 Maple Rd, Anytown USA', '555-555-5558', 1);
INSERT INTO Customer (cid, cname, username, password, address, phone, status)
VALUES ('C005', 'Daniel Kim', 'dkim', 'password1234', '654 Pine Ln, Anytown USA', '555-555-5559', 1);
--Bill:

select *
from bill