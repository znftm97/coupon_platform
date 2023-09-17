delete
from account;
alter table account
    auto_increment = 1;

delete
from coupon;
alter table coupon
    auto_increment = 1;

delete
from coupon_code;
alter table coupon_code
    auto_increment = 1;

delete
from issued_coupon;
alter table issued_coupon
    auto_increment = 1;

delete
from product;
alter table product
    auto_increment = 1;
