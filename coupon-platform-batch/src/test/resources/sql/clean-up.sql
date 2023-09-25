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