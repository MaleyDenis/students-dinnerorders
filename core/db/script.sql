CREATE DATABASE IF NOT EXISTS dinnerorders;

CREATE TABLE IF NOT EXISTS dinnerorders.order (
order_id  bigint(20) NOT NULL ,
user_id  bigint(20) NOT NULL ,
cost double NOT NULL ,
date_payment  timestamp NULL DEFAULT NULL ,
date_order  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (id)
)
ENGINE=InnoDB;

