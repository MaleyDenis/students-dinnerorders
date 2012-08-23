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
id bigint(20) NOT NULL,
menu_id  bigint(20) NOT NULL ,
menuitem_id bigint(20) NOT NULL,
PRIMARY KEY (id)
)
ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS dinnerorders.user (
ID  BIGINT(20) NOT NULL,
LDAPLOGIN  VARCHAR(45) NOT NULL,
USERNAME  VARCHAR(45) NOT NULL,
ROLE  VARCHAR(45) NOT NULL,
PRIMARY KEY (ID) )
ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS dinnerorders.identifier (
ID  BIGINT(20) NOT NULL,
PRIMARY KEY (ID) )
ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS dinnerorders.message (
id  BIGINT(20) NOT NULL,
text VARCHAR (10000),
PRIMARY KEY (ID) )
ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS dinnerorders.content (
ID  BIGINT(20) NOT NULL,
url VARCHAR(10000),
PRIMARY KEY (ID) )
ENGINE=InnoDB;

DELIMITER $$
CREATE PROCEDURE dinnerorders.getID(OUT idOUT INT)
BEGIN
SELECT ID INTO idOut FROM identifier ;
TRUNCATE TABLE identifier;
INSERT into dinnerorders.identifier set ID = idOut + 1;
END$$

CREATE TABLE IF NOT EXISTS dinnerorders.topic (topic_id  BIGINT(20) NOT NULL,user_id  bigint(20) NOT NULL,name_topic varchar(100) NOT NULL,date_creation  timestamp NULL DEFAULT NULL,PRIMARY KEY (topic_id))
ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS dinnerorders.message (message_id  BIGINT(20) NOT NULL,date timestamp NOT NULL,text VARCHAR (10000),PRIMARY KEY (message_id) )
ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS dinnerorders.content (content_id  BIGINT(20) NOT NULL,url VARCHAR(10000),PRIMARY KEY (content_id) )
ENGINE=InnoDB;