INSERT INTO `item`(`cts`,`uts`,`name`,`originprice`,`realprice`,`discount`,`image`,`images`) VALUES (now(),now(),'公主玩偶套装',59.90,29.90,4.9,'http://p1.meituan.net/640.480/deal/__45629424__4842793.jpg','["http://p1.meituan.net/800.480/deal/__45629424__4842793.jpg","http://p1.meituan.net/700.480/deal/__45629424__4842793.jpg"]');

INSERT INTO `item_status`(`cts`,`uts`,`itemid`,`saletype`,`starttime`,`endtime`,`sales`,`units`,`status`) VALUES (now(),now(),1,2,'2014-08-08','2014-09-09',10000,'件',1);
INSERT INTO `item_recommend`(`cts`,`uts`,`itemid`,`uid`,`name`,`avatar`,`reason`) VALUES (now(),now(),1,1,'荷花','http://www.baidu.com/img/bdlogo.png','推荐理由');
INSERT INTO `item_detail`(`cts`,`uts`,`itemid`,`brand`,`texture`,`crowd`,`gender`,`image`,`imagetext`) VALUES (now(),now(),1,'荷花','[{"name":"质地","value":"棉"}]','3月至6月','男女适用','http://p1.meituan.net/800.400/deal/__45629424__4842793.jpg','公主玩偶套装<url>http://p1.meituan.net/800.400/deal/__45629424__4842793.jpg</url>');
INSERT INTO `item_attr`(`cts`,`uts`,`itemid`,`attributes`) VALUES (now(),now(),1,'[{"name":"颜色", "value":[{"id":1,"desc":"红色"},{"id":2,"desc":"蓝色"}]},{"name":"尺码", "value":[{"id":1,"desc":"XL"},{"id":2,"desc":"L"}]}]');
INSERT INTO `item_appraise`(`cts`,`uts`,`itemid`,`uid`,`name`,`appraise`,`offical`) VALUES (now(),now(),1,2,'荷花','平平测测', '1');
INSERT INTO `item_comment`(`cts`,`uts`,`itemid`,`uid`,`name`,`comment`) VALUES (now(),now(),1,3,'荷花官方','评价评价好');

