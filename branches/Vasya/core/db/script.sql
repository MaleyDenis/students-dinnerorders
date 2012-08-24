CREATE DATABASE IF NOT EXISTS dinnerorders;

CREATE TABLE IF NOT EXISTS dinnerorders.order (
order_id  bigint(20) NOT NULL,
user_id  bigint(20) NOT NULL,
cost double NOT NULL,
date_payment  timestamp NULL DEFAULT NULL,
date_order  timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
PRIMARY KEY (order_id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS dinnerorders.menu (
menu_id  bigint(20) NOT NULL,
cafename varchar(45) NOT NULL,
date_start timestamp NOT NULL,
date_end timestamp NOT NULL,
PRIMARY KEY (menu_id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS dinnerorders.menuitem (
menuitem_id bigint(20) NOT NULL,
weekday varchar(10) NOT NULL,
description varchar(100) NOT NULL,
cost double NOT NULL,
PRIMARY KEY (menuitem_id)
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS  dinnerorders.order_menuitem (
id bigint(20) NOT NULL AUTO_INCREMENT,
order_id bigint(20) NOT NULL,
menuitem_id bigint(20) NOT NULL,
PRIMARY KEY (id),
CONSTRAINT order_id FOREIGN KEY (order_id) REFERENCES dinnerorders.order(order_id)
ON UPDATE CASCADE
ON DELETE CASCADE,
CONSTRAINT menuitem_id FOREIGN KEY (menuitem_id) REFERENCES menuitem(menuitem_id)
ON UPDATE CASCADE
ON DELETE CASCADE
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS dinnerorders.menu_menuitem (
id bigint(20) NOT NULL AUTO_INCREMENT,
menu_id bigint(20) NOT NULL,
menuitem_id bigint(20) NOT NULL,
PRIMARY KEY (id),
CONSTRAINT menu_item_id FOREIGN KEY (menuitem_id) REFERENCES menuitem(menuitem_id)
ON UPDATE CASCADE
ON DELETE CASCADE,
CONSTRAINT menu_id FOREIGN KEY (menu_id) REFERENCES menu(menu_id)
ON UPDATE CASCADE
ON DELETE CASCADE
)
ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS dinnerorders.user (
ID  INT NOT NULL AUTO_INCREMENT ,
LDAPLOGIN  VARCHAR(45) NOT NULL ,
USERNAME  VARCHAR(45) NOT NULL ,
ROLE  VARCHAR(45) NOT NULL,
PRIMARY KEY (ID) )
ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS dinnerorders.identifier (
ID  INT ,
PRIMARY KEY (ID) )
ENGINE=InnoDB DEFAULT CHARSET=utf8;

DELIMITER $$
CREATE PROCEDURE dinnerorders.getID(OUT idOUT INT)
BEGIN
SELECT ID INTO idOut FROM identifier ;
TRUNCATE TABLE identifier;
INSERT into dinnerorders.identifier set ID = idOut + 1;
END$$