create table Category
(
    cateId   int identity
        primary key,
    cateName nvarchar(100),
    status   int
)
go

create table Customer
(
    cid      varchar(32)  not null
        primary key,
    cname    nvarchar(50) not null,
    username varchar(30)
        unique,
    password varchar(32)  not null,
    address  nvarchar(max),
    phone    varchar(20),
    status   int
)
go

create table Bill
(
    bid        varchar(32) not null
        primary key,
    dateCreate datetime default getdate(),
    recAddress nvarchar(max),
    recPhone   varchar(20),
    note       nvarchar(max),
    totalMoney money,
    cid        varchar(32)
        references Customer,
    status     int
)
go

create table Product
(
    pid         varchar(32)   not null
        primary key,
    pname       nvarchar(200) not null,
    quantity    int
        check ([quantity] >= 0),
    price       money
        check ([price] >= 0),
    image       varchar(256),
    description nvarchar(max),
    status      int,
    cateID      int
        references Category
)
go

create table BillDetail
(
    bid         varchar(32) not null
        constraint BillDetail_Bill_bid_fk
            references Bill,
    pid         varchar(32) not null
        constraint BillDetail_Product_pid_fk
            references Product,
    buyQuantity int,
    buyPrice    money,
    subtotal    money,
    primary key (bid, pid)
)
go

create table Cart
(
    cid      varchar(32)
        references Customer,
    pid      varchar(32)
        references Product,
    quantity int
        check ([quantity] >= 0)
)
go

create table admin
(
    admin    char(32) not null
        primary key,
    password char(32) not null
)
go


