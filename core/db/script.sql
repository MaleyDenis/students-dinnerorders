CREATE DATABASE IF NOT EXISTS dinnerorders;

CREATE TABLE IF NOT EXISTS dinnerorders.order (
order_id  bigint(20) NOT NULL,
user_id  bigint(20) NOT NULL,
cost double NOT NULL,
date_payment  timestamp NULL DEFAULT NULL,
date_order  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (order_id)
)
ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS  dinnerorders.order_menuitem (
id bigint(20) NOT  NULL,
order_id bigint(20) NOT  NULL,
menu_item_id bigint(20) NOT NULL,
PRIMARY KEY (id)
)
ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS dinnerorders.menu (
menu_id  bigint(20) NOT NULL,
cafename varchar(45) NOT NULL,
date_start timestamp NOT NULL,
date_end timestamp NOT NULL,
PRIMARY KEY (menu_id)
)
ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS dinnerorders.menuitem (
menuitem_id bigint(20) NOT NULL,
weekday varchar(10) NOT NULL,
description varchar(100) NOT NULL,
cost double NOT NULL,
PRIMARY KEY (menuitem_id)
)
ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS dinnerorders.menu_menuitem (
id bigint(20) NOT  NULL,
menu_id  bigint(20) NOT NULL ,
menuitem_id bigint(20) NOT NULL,
PRIMARY KEY (id)
)
ENGINE=InnoDB;