INSERT INTO dinnerorders.identifier VALUES(1);

INSERT INTO dinnerorders.order VALUE(1,1,58900,'2012-07-13 10:12:00','2012-07-20 15:45:59');
INSERT INTO dinnerorders.order VALUE(2,2,120900,'2012-07-10 14:45:12','2012-07-15 17:15:14');
INSERT INTO dinnerorders.order VALUE(3,3,85500,'2012-07-01 08:12:34','2012-07-12 17:23:35');

INSERT INTO dinnerorders.menuitem VALUE(1,'MONDAY','mushroom soup',15250);
INSERT INTO dinnerorders.menuitem VALUE(2,'TUESDAY','borscht',13400);
INSERT INTO dinnerorders.menuitem VALUE(3,'WEDNESDAY','pudding',12500);
INSERT INTO dinnerorders.menuitem VALUE(4,'THURSDAY','pasta nautically',7000);
INSERT INTO dinnerorders.menuitem VALUE(5,'FRIDAY','pilaf',11200);
INSERT INTO dinnerorders.menuitem VALUE(6,'SATURDAY','French fries',5500);
INSERT INTO dinnerorders.menuitem VALUE(7,'SUNDAY','rice and fish cutlet',18900);
INSERT INTO dinnerorders.menuitem VALUE(8,'WEDNESDAY','cutlet with mashed potatoes',14350);

INSERT INTO dinnerorders.order_menuitem VALUE(1,1,1);
INSERT INTO dinnerorders.order_menuitem VALUE(2,1,3);
INSERT INTO dinnerorders.order_menuitem VALUE(3,2,6);
INSERT INTO dinnerorders.order_menuitem VALUE(4,1,4);

INSERT INTO dinnerorders.order_menuitem VALUE(5,2,7);
INSERT INTO dinnerorders.order_menuitem VALUE(6,3,2);
INSERT INTO dinnerorders.order_menuitem VALUE(7,3,7);

INSERT INTO dinnerorders.user VALUE(1,'atarasyuk','Tarasyuk Alexander','user');
INSERT INTO dinnerorders.user VALUE(2,'aokunevich','Alexander Okunevich','user');
INSERT INTO dinnerorders.user VALUE(3,'ojuravleva','Juravleva Olga','user');


