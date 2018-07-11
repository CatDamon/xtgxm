-- 账簿表
DROP TABLE IF EXISTS yw_orders;
CREATE TABLE yw_account_book (
  account_book_id	VARCHAR(32) NOT NULL COMMENT	'账簿id',
  account_book_id	VARCHAR(32) NOT NULL COMMENT	'账簿id',
  	VARCHAR(32) DEFAULT null COMMENT	'',

  PRIMARY KEY (ordersid)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT '账簿表';