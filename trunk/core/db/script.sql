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
COLLATE=utf8;

CREATE TABLE IF NOT EXISTS  dinnerorders.order_menuitem(
order_id bigint(20) NOT  NULL;
munu_item_id bigint(20) NOT NULL;
)
ENGINE=InnoDB;
COLLATE=utf8;

CREATE TABLE IF NOT EXISTS dinnerorders.menus (
id  bigint(20) NOT NULL ,
cafename varchar(45) NOT NULL ,
datestart timestamp NOT NULL ,
dateend timestamp NOT NULL ,
PRIMARY KEY (id)
)
ENGINE=InnoDB,
COLLATE=utf8;

CREATE TABLE IF NOT EXISTS dinnerorders.menuitems (
id  bigint(20) NOT NULL ,
weekday enum('MONDAY','TUESDAY','WEDNESDAY','THURSDAY','FRIDAY','SATURDAY','SUNDAY') NOT NULL ,
description varchar(45) NOT NULL ,
cost double NOT NULL ,
PRIMARY KEY (id)
)
ENGINE=InnoDB,
COLLATE=utf8;

CREATE TABLE IF NOT EXISTS dinnerorders.menu_menuitem (
idmenu  bigint(20) NOT NULL ,
idmenuitem bigint(20) NOT NULL
)
ENGINE=InnoDB,
COLLATE=utf8;