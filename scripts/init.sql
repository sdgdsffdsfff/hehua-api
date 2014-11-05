INSERT INTO `brand` (`id`, `name`, `desc`) VALUES ('1', '荷花', '荷花品牌');
INSERT INTO `category` (`id`, `parentid`, `name`, `status`, `sort`) VALUES ('1', '0', '奶粉', '0', '1');
INSERT INTO `category` (`id`, `parentid`, `name`, `status`, `sort`) VALUES ('2', '1', '婴幼儿营养品', '0', '1');
INSERT INTO `brand_category` (`brandid`, `cateid`) VALUES ('1', '2');

INSERT INTO `crowd` (`id`, `name`, `starttime`, `endtime`) VALUES (1, '备孕中', '-100000', '0');
INSERT INTO `crowd` (`id`, `name`, `starttime`, `endtime`) VALUES (2,'宝宝0-1岁', '0', '13');
INSERT INTO `crowd` (`id`, `name`, `starttime`, `endtime`) VALUES (3,'宝宝1-2岁', '13', '25');
INSERT INTO `crowd` (`id`, `name`, `starttime`, `endtime`) VALUES (4,'宝宝2-3岁', '25', '37');

INSERT INTO `gender` (`id`,`name`, `value`) VALUES (0,'男女皆可', '0');
INSERT INTO `gender` (`id`,`name`, `value`) VALUES (1,'男', '1');
INSERT INTO `gender` (`id`,`name`, `value`) VALUES (2,'女', '2');

INSERT INTO `property` (`name`, `parentid`) VALUES ('颜色分类', '0');
INSERT INTO `property` (`name`, `parentid`) VALUES ('参考身高', '0');
INSERT INTO `property` (`name`, `parentid`) VALUES ('适用年龄', '0');
INSERT INTO `property` (`name`, `parentid`) VALUES ('尺码', '0');
INSERT INTO `property` (`name`, `parentid`) VALUES ('鞋码', '0');

INSERT INTO `property_value` (`propertyid`, `name`) VALUES ('1', '军绿色');
INSERT INTO `property_value` (`propertyid`, `name`) VALUES ('1', '天蓝色');
INSERT INTO `property_value` (`propertyid`, `name`) VALUES ('1', '巧克力色');
INSERT INTO `property_value` (`propertyid`, `name`) VALUES ('1', '桔色');
INSERT INTO `property_value` (`propertyid`, `name`) VALUES ('1', '浅灰色');
INSERT INTO `property_value` (`propertyid`, `name`) VALUES ('2', '48cm');
INSERT INTO `property_value` (`propertyid`, `name`) VALUES ('2', '52cm');
INSERT INTO `property_value` (`propertyid`, `name`) VALUES ('2', '59cm');
INSERT INTO `property_value` (`propertyid`, `name`) VALUES ('3', '0-3月');
INSERT INTO `property_value` (`propertyid`, `name`) VALUES ('3', '3-6月');
INSERT INTO `property_value` (`propertyid`, `name`) VALUES ('3', '6-12月');
INSERT INTO `property_value` (`propertyid`, `name`) VALUES ('4', 'L');
INSERT INTO `property_value` (`propertyid`, `name`) VALUES ('4', 'M');
INSERT INTO `property_value` (`propertyid`, `name`) VALUES ('4', 'S');
INSERT INTO `property_value` (`propertyid`, `name`) VALUES ('5', '11cm以下');
INSERT INTO `property_value` (`propertyid`, `name`) VALUES ('5', '12码/11cm');
INSERT INTO `property_value` (`propertyid`, `name`) VALUES ('5', '13码/11.5cm');

INSERT INTO `property` (`name`, `parentid`) VALUES ('材质', '0');
INSERT INTO `property_value` (`propertyid`, `name`, `cateid`) VALUES ('6', '棉', '0');

INSERT INTO `item_category` (`itemid`, `category`) VALUES ('1', '2');

INSERT INTO `item_sku` (`itemid`, `quantity`, `pvids`) VALUES ('1', '100', '1,10');
INSERT INTO `item_sku` (`itemid`, `quantity`, `pvids`) VALUES ('1', '50', '2,11');
INSERT INTO `item_sku` (`itemid`, `quantity`, `pvids`) VALUES ('1', '30', '3,10');

INSERT INTO `item_property` (`itemid`, `pid`, `pvid`, `issku`) VALUES ('1', '6', '18', '0');
INSERT INTO `item_property` (`itemid`, `pid`, `pvid`, `issku`, `skuid`) VALUES ('1', '1', '1', '1', '1');
INSERT INTO `item_property` (`itemid`, `pid`, `pvid`, `issku`, `skuid`) VALUES ('1', '3', '10', '1', '1');
INSERT INTO `item_property` (`itemid`, `pid`, `pvid`, `issku`, `skuid`) VALUES ('1', '1', '2', '1', '2');
INSERT INTO `item_property` (`itemid`, `pid`, `pvid`, `issku`, `skuid`) VALUES ('1', '3', '11', '1', '2');
INSERT INTO `item_property` (`itemid`, `pid`, `pvid`, `issku`, `skuid`) VALUES ('1', '1', '3', '1', '3');
INSERT INTO `item_property` (`itemid`, `pid`, `pvid`, `issku`, `skuid`) VALUES ('1', '3', '10', '1', '3');

INSERT INTO `item_image` (`itemid`, `imageid`, `imagelocation`, `ismaster`) VALUES ('1', '1', 'http://www.hehua.com/image/', '1');
