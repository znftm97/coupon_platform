insert into account (external_id)
values ('abcdedfghj11');

insert into coupon (external_id, name, discount_type)
values ('abcdedfghj23', 'coupon1', 'PRICE');

insert into coupon_code (external_id, coupon_id, expiration_period)
values ('abcdedfghj23', '1', '2030-12-31 09:00:00');

insert into issued_coupon (external_id, account_id, coupon_id, expiration_period, is_used)
values ('abcdedfghj23', '1', '1', '2030-12-31 09:00:00', false);


delete from BATCH_JOB_EXECUTION_PARAMS;
delete from BATCH_JOB_EXECUTION_CONTEXT;
delete from BATCH_JOB_EXECUTION_SEQ;
delete from BATCH_JOB_SEQ;
delete from BATCH_STEP_EXECUTION_CONTEXT;
delete from BATCH_STEP_EXECUTION;
delete from BATCH_JOB_EXECUTION;
delete from BATCH_JOB_INSTANCE;
delete from BATCH_STEP_EXECUTION_SEQ;

INSERT INTO BATCH_STEP_EXECUTION_SEQ values(0, '0');
INSERT INTO BATCH_JOB_EXECUTION_SEQ values(0, '0');
INSERT INTO BATCH_JOB_SEQ values(0, '0');