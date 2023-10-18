insert into account (external_id)
values ('abcdedfghj11');

insert into coupon (external_id, name, discount_type, price)
values ('abcdedfghj23', 'coupon1', 'PRICE', 1000);

insert into coupon_code (external_id, coupon_id, expiration_period)
values ('abcdedfghj23', '1', '2030-12-31 09:00:00');

insert into issued_coupon (external_id, account_id, coupon_id, expiration_period, is_used, created_at)
values ('abcdedfghj23', '1', '1', '2030-01-01 09:00:00', false, '2030-01-01 09:00:00');

insert into issued_coupon (external_id, account_id, coupon_id, expiration_period, is_used, created_at)
values ('abcdedfghj23', '1', '1', '2030-01-02 09:00:00', false, '2030-01-01 09:00:00');

insert into issued_coupon (external_id, account_id, coupon_id, expiration_period, is_used, created_at)
values ('abcdedfghj23', '1', '1', '2030-01-03 09:00:00', false, '2030-01-01 09:00:00');

insert into issued_coupon (external_id, account_id, coupon_id, expiration_period, is_used, created_at)
values ('abcdedfghj23', '1', '1', '2030-01-04 09:00:00', true, '2030-01-01 09:00:00');

insert into coupon_daily_stats (coupon_usage_rate, coupon_daily_stats_id, created_at, expired_coupon_count, issued_coupon_count, used_coupon_count, external_id, stats_date)
values ('0', '1', '2023-10-15 00:00:00.000000', '0', '1', '0', 'asdasddsa1', '2023-10-15');

insert into coupon_daily_stats (coupon_usage_rate, coupon_daily_stats_id, created_at, expired_coupon_count, issued_coupon_count, used_coupon_count, external_id, stats_date)
values ('0', '2', '2023-10-16 00:00:00.000000', '2', '10', '5', 'asdasddsa2', '2023-10-16');

insert into coupon_daily_stats (coupon_usage_rate, coupon_daily_stats_id, created_at, expired_coupon_count, issued_coupon_count, used_coupon_count, external_id, stats_date)
values ('100', '3', '2023-10-17 00:00:00.000000', '2', '10', '5', 'asdasddsa3', '2023-10-17');