insert into account (external_id)
values ('abcdedfghj11');
insert into product (external_id)
values ('abcdedfghj23');
insert into coupon (external_id, name, apply_type, discount_type)
values ('abcdedfghj23', 'coupon1', 'ACCOUNT', 'PRICE');
insert into coupon (external_id, name, apply_type, discount_type)
values ('abcdedfghj23', 'coupon1', 'ACCOUNT', 'PRICE');
insert into coupon_code (external_id, coupon_id, expiration_period)
values ('abcdedfghj23', '1', '2030-12-31 09:00:00');
insert into issued_coupon (external_id, coupon_id, expiration_period)
values ('abcdedfghj23', '1', '2030-12-31 09:00:00');