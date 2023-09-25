create table account
(
    account_id  bigint auto_increment
        primary key,
    created_at  datetime(6)  null,
    update_at   datetime(6)  null,
    external_id varchar(255) null
);

create table coupon
(
    price         int                    not null,
    coupon_id     bigint auto_increment
        primary key,
    created_at    datetime(6)            null,
    update_at     datetime(6)            null,
    discount_type enum ('PRICE', 'RATE') null,
    external_id   varchar(255)           null,
    name          varchar(255)           null
);

create table coupon_code
(
    coupon_code_id    bigint auto_increment
        primary key,
    coupon_id         bigint       not null,
    created_at        datetime(6)  null,
    expiration_period datetime(6)  null,
    update_at         datetime(6)  null,
    code              varchar(255) null,
    external_id       varchar(255) null
);

create table issued_coupon
(
    is_used           bit          not null,
    account_id        bigint       not null,
    coupon_id         bigint       not null,
    created_at        datetime(6)  null,
    expiration_period datetime(6)  null,
    issued_coupon_id  bigint auto_increment
        primary key,
    update_at         datetime(6)  null,
    external_id       varchar(255) null
);