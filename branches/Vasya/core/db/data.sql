INSERT INTO dinnerorders.identifier VALUES(1);

INSERT INTO dinnerorders.order VALUE(1,19,58900,'2012-07-13 10:12:00','2012-07-20 15:45:59');
INSERT INTO dinnerorders.order VALUE(2,20,120900,'2012-07-10 14:45:12','2012-07-15 17:15:14');
INSERT INTO dinnerorders.order VALUE(3,21,85500,'2012-07-01 08:12:34','2012-07-12 17:23:35');

INSERT INTO dinnerorders.menuitem VALUE(4,'MONDAY','mushroom soup',15250);
INSERT INTO dinnerorders.menuitem VALUE(5,'TUESDAY','borscht',13400);
INSERT INTO dinnerorders.menuitem VALUE(6,'WEDNESDAY','pudding',12500);
INSERT INTO dinnerorders.menuitem VALUE(7,'THURSDAY','pasta nautically',7000);
INSERT INTO dinnerorders.menuitem VALUE(8,'FRIDAY','pilaf',11200);
INSERT INTO dinnerorders.menuitem VALUE(9,'SATURDAY','French fries',5500);
INSERT INTO dinnerorders.menuitem VALUE(10,'SUNDAY','rice and fish cutlet',18900);
INSERT INTO dinnerorders.menuitem VALUE(11,'WEDNESDAY','cutlet with mashed potatoes',14350);

INSERT INTO dinnerorders.order_menuitem VALUE(12,1,4);
INSERT INTO dinnerorders.order_menuitem VALUE(13,2,6);
INSERT INTO dinnerorders.order_menuitem VALUE(14,1,8);
INSERT INTO dinnerorders.order_menuitem VALUE(15,2,11);
INSERT INTO dinnerorders.order_menuitem VALUE(16,2,5);
INSERT INTO dinnerorders.order_menuitem VALUE(17,3,6);
INSERT INTO dinnerorders.order_menuitem VALUE(18,3,7);

INSERT INTO dinnerorders.user VALUE(19,'atarasyuk','Tarasyuk Alexander','USER');
INSERT INTO dinnerorders.user VALUE(20,'aokunevich','Alexander Okunevich','USER');
INSERT INTO dinnerorders.user VALUE(21,'ojuravleva','Juravleva Olga','USER');

UPDATE dinnerorders.identifier set id=22;


