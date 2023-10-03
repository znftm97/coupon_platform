insert into account (external_id)
values ('abcdedfghj11');

insert into coupon (external_id, name, discount_type, price)
values ('abcdedfghj23', 'coupon1', 'PRICE', 1000);

insert into coupon_code (external_id, coupon_id, expiration_period)
values ('abcdedfghj23', '1', '2030-12-31 09:00:00');

insert into issued_coupon (external_id, account_id, coupon_id, expiration_period, is_used)
values ('abcdedfghj23', '1', '1', '2030-12-31 09:00:00', false);