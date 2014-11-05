CREATE DATABASE `hehua_item` DEFAULT CHARSET='utf8';

use `hehua_item`;

CREATE TABLE `category` (
  `id`            INT(11)     NOT NULL  AUTO_INCREMENT COMMENT '品类ID',
  `cts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '创建时间',
  `uts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '修改时间',
  `parentid`      INT(6)    	NOT NULL  COMMENT '品类父节点',
  `name`          VARCHAR(32) NOT NULL  COMMENT '品类名称',
  `status`        INT(4)      DEFAULT 0 COMMENT '状态', -- 0 正常 1 删除
  PRIMARY KEY (`id`),
  KEY `idx_parentid` (`parentid`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='品类信息';

CREATE TABLE `item` (
	`id`            INT(11)     NOT NULL  AUTO_INCREMENT,
	`cts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '创建时间',
	`uts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '修改时间',
	`name`          VARCHAR(64) NOT NULL  COMMENT '商品名称',
	`originprice`   decimal(11,2)     	NOT NULL  COMMENT '原价',
	`realprice`     decimal(11,2)      NOT NULL  COMMENT '现价',
	`brandid`         INT(11)          COMMENT '品牌',
	`sales`         INT(6)      DEFAULT 0 COMMENT '销量',
	`units`         VARCHAR(8)            COMMENT '单位',
	`image`         bigint(20)          COMMENT '头图',
	`images`         VARCHAR(1024)          COMMENT '图片列表',
	`crowedid` varchar(255) NOT NULL COMMENT '适用人群',
    `genderid` INT(11) NOT NULL COMMENT '性别',
	`materialid` INT(11) NOT NULL COMMENT '材料编号',
	`flashid` INT(11) NOT NULL COMMENT '当前闪购编号',
	`purchaseid` int(11) NOT NULL DEFAULT 0 COMMENT '采购地',
  	`warehouseid` int(11) NOT NULL DEFAULT 0 COMMENT '仓库',
	`desc`     mediumtext         COMMENT '图文详情',
	`status` int(11) NOT NULL DEFAULT 0 COMMENT '商品状态，0-代表正常，1-删除',
	`postagetype` int(11) not null default 1 COMMENT '邮费类型',
	PRIMARY KEY (`id`),
  KEY `idx_name` (`name`),
  KEY `idx_realprice` (`realprice`),
  KEY `idx_discount` (`discount`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品基本信息';

CREATE TABLE `item_category` (
	`id`            INT(11)     NOT NULL  AUTO_INCREMENT,
	`cts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '创建时间',
	`uts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '修改时间',
	`itemid`        INT(11)     NOT NULL  COMMENT '商品ID',
	`category`      INT(6)      NOT NULL  COMMENT '品类ID',
	PRIMARY KEY (`id`),
	UNIQUE KEY `uidx_itemid_category` (`itemid`, `category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品品类信息';

CREATE TABLE `item_status` (
	`id`            INT(11)     NOT NULL  AUTO_INCREMENT,
	`cts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '创建时间',
	`uts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '修改时间',
	`itemid`        INT(11)     NOT NULL  COMMENT '商品ID',
	`saletype`      INT(6)      DEFAULT 1 COMMENT '销售类型',  -- 1 普通 2 闪购
	`starttime`     DATETIME              COMMENT '开始时间',
	`endtime`       DATETIME              COMMENT '结束时间',
	`sales`         INT(6)      DEFAULT 0 COMMENT '销量',
	`units`         VARCHAR(8)            COMMENT '单位',
	`status`        INT(4)      DEFAULT 0 COMMENT '状态', -- 0 仓库 1 上架 2 待上架 4 下架
	PRIMARY KEY (`id`),
  UNIQUE KEY `idx_itemid` (`itemid`),
  KEY `idx_saletype` (`saletype`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品动态信息';

CREATE TABLE `item_detail` (
	`id`            INT(11)     NOT NULL  AUTO_INCREMENT,
	`cts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '创建时间',
	`uts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '修改时间',
	`itemid`        INT(11)     NOT NULL  COMMENT '商品ID',
	`brandid`         INT(11)          COMMENT '品牌',
	`image`         bigint(20)          COMMENT '头图',
	`images`         VARCHAR(1024)          COMMENT '图片列表',
	`crowedid` varchar(255) NOT NULL COMMENT '适用人群',
    `genderid` INT(11) NOT NULL COMMENT '性别',
	`desc`     mediumtext         COMMENT '图文详情',
	PRIMARY KEY (`id`),
  UNIQUE KEY `idx_itemid` (`itemid`),
  KEY `idx_brand` (`brand`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品细节信息';

-- item_attr废弃，迁移至 item_property
CREATE TABLE `item_attr` (
	`id`            INT(11)     NOT NULL  AUTO_INCREMENT,
	`cts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '创建时间',
	`uts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '修改时间',
	`itemid`        INT(11)     NOT NULL  COMMENT '商品ID',
  `attributes`    VARCHAR(4096)         COMMENT '规格信息',
	PRIMARY KEY (`id`),
  UNIQUE KEY `idx_itemid` (`itemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品规格信息';

CREATE TABLE `item_recommend` (
	`id`            INT(11)     NOT NULL  AUTO_INCREMENT,
	`cts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '创建时间',
	`uts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '修改时间',
	`itemid`        INT(11)     NOT NULL  COMMENT '商品ID',
	`uid`           INT(11)     NOT NULL  COMMENT '推荐人ID',
	`name`          VARCHAR(32) NOT NULL  COMMENT '推荐人名称', -- 改名字后需要更新
	`type`          VARCHAR(32)           COMMENT '推荐人类型', -- 改类型后需要更新
	`avatar`        VARCHAR(128)          COMMENT '推荐人头像', -- 改头像后需要更新
	`reason`        VARCHAR(2048)         COMMENT '推荐理由',
	PRIMARY KEY (`id`),
  KEY `idx_itemid` (`itemid`),
  KEY `idx_uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品推荐信息';


-- 废弃，迁移至feedback
CREATE TABLE `item_comment` (
	`id`            INT(11)     NOT NULL  AUTO_INCREMENT,
	`cts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '创建时间',
	`uts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '修改时间',
	`itemid`        INT(11)     NOT NULL  COMMENT '商品ID',
	`uid`           INT(11)     NOT NULL  COMMENT '评价人ID',
	`name`          VARCHAR(32) NOT NULL  COMMENT '评价人名称',
	`comment`       VARCHAR(2048)         COMMENT '评价',
	PRIMARY KEY (`id`),
  KEY `idx_itemid` (`itemid`),
  KEY `idx_uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品评价信息';

-- 这个属性废弃，迁移至item_property
-- ALTER TABLE `item_detail` CHANGE COLUMN `texture` `property` VARCHAR(1024) DEFAULT NULL COMMENT '属性' ;

ALTER TABLE `category` ADD COLUMN `sort` INT(6) NOT NULL DEFAULT 0 COMMENT '排序';

ALTER TABLE `item_status` ADD COLUMN `onlinetime` DATETIME  COMMENT '上线时间';

CREATE TABLE `property` (
  `id`            INT(11)     NOT NULL  AUTO_INCREMENT COMMENT '品类ID',
  `cts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '创建时间',
  `uts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '修改时间',
  `name`          VARCHAR(32) NOT NULL  COMMENT '属性名称',
  `parentid`      INT(6)    	NOT NULL  COMMENT '品类父节点',
  `cateid`        INT(11)     NOT NULL  DEFAULT 0     COMMENT '所属品类ID',
  `status`        INT(4)      DEFAULT 0 COMMENT '状态', -- 0 正常 1 删除
  `isshow`        INT(4)      DEFAULT 1 COMMENT '是否可见',
  PRIMARY KEY (`id`),
  KEY `idx_parentid` (`parentid`),
  KEY `idx_cateid` (`cateid`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='属性信息';

CREATE TABLE `property_value` (
	`id`            INT(11)     NOT NULL  AUTO_INCREMENT,
	`cts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '创建时间',
	`uts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '修改时间',
  `propertyid`    INT(11)     NOT NULL  COMMENT '属性ID',
  `name`          VARCHAR(32) NOT NULL  COMMENT '属性值名称',
  `cateid`        INT(11)     NOT NULL  DEFAULT 0     COMMENT '所属品类ID',
  `sort`          INT(6)      NOT NULL  DEFAULT 0     COMMENT '排序',
  `extend`        VARCHAR(128)          COMMENT '拓展属性',
	PRIMARY KEY (`id`),
  KEY `idx_propertyid` (`propertyid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品属性信息';

CREATE TABLE `item_sku` (
  `id`            INT(11)       NOT NULL  AUTO_INCREMENT COMMENT 'SKUID',
  `cts`           DATETIME      NOT NULL  DEFAULT now() COMMENT '创建时间',
  `uts`           DATETIME      NOT NULL  DEFAULT now() COMMENT '修改时间',
  `itemid`        INT(11)       NOT NULL                COMMENT '商品ID',
  `originprice`   decimal(11,2)     	NOT NULL  COMMENT '原价',
	`realprice`     decimal(11,2)      NOT NULL  COMMENT '现价',
  `quantity`      INT(6)        NOT NULL  DEFAULT 0     COMMENT '数量',
  `pvids`         VARCHAR(1024) NOT NULL  DEFAULT ''    COMMENT '属性值ID列表',
  `status`		  TINYINT(4) NOT NULL COMMENT '状态',
  `barcode`	      VARCHAR(255) NOT NULL COMMENT '条形码',
  PRIMARY KEY (`id`),
  KEY `idx_itemid` (`itemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品SKU信息';

CREATE TABLE `item_property` (
	`id`            INT(11)     NOT NULL  AUTO_INCREMENT,
	`cts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '创建时间',
	`uts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '修改时间',
  `itemid`        INT(11)     NOT NULL  COMMENT '商品ID',
  `pid`           INT(11)     NOT NULL  COMMENT '属性ID',
  `pvid`          INT(11)     NOT NULL  COMMENT '属性值ID',
  `issku`         INT(11)     NOT NULL  DEFAULT 0     COMMENT '是否是SKU',
  `skuid`         INT(11)               COMMENT 'SKUID',
	PRIMARY KEY (`id`),
  KEY `idx_itemid` (`itemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品基本属性信息';

-- 废弃
/*CREATE TABLE `item_image` (
	`id`            INT(11)       NOT NULL  AUTO_INCREMENT,
	`cts`           DATETIME      NOT NULL  DEFAULT now() COMMENT '创建时间',
	`uts`           DATETIME      NOT NULL  DEFAULT now() COMMENT '修改时间',
	`itemid`        INT(11)       NOT NULL  COMMENT '商品ID',
	`imageid`       INT(20)       NOT NULL  COMMENT '图片ID',
  `imagelocation` VARCHAR(128)            COMMENT '图片存储位置',
  `ismaster`      INT(3)        NOT NULL  DEFAULT 0     COMMENT '是否主图',
  PRIMARY KEY (`id`),
  KEY `idx_itemid` (`itemid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品图片信息';
*/

CREATE TABLE `brand` (
  `id`            INT(11)     NOT NULL  AUTO_INCREMENT COMMENT '品牌ID',
  `cts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '创建时间',
  `uts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '修改时间',
  `name`          VARCHAR(32) NOT NULL  COMMENT '名称',
  `desc`          VARCHAR(4096)         COMMENT '详情',
  PRIMARY KEY (`id`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='品牌信息';

CREATE TABLE `brand_category` (
  `id`            INT(11)     NOT NULL  AUTO_INCREMENT COMMENT 'ID',
  `cts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '创建时间',
  `uts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '修改时间',
  `brandid`       INT(11)     NOT NULL                COMMENT '品牌ID',
  `cateid`        INT(11)     NOT NULL  DEFAULT 0     COMMENT '所属品类ID',
  PRIMARY KEY (`id`),
  KEY `idx_brandid` (`brandid`),
  KEY `idx_cateid` (`cateid`)
 ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='品牌品类';

CREATE TABLE `purchase_address` (
  `id`            INT(11)     NOT NULL  AUTO_INCREMENT COMMENT 'ID',
  `cts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '创建时间',
  `uts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '修改时间',
  `name`          VARCHAR(32) NOT NULL                COMMENT '采购地名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品采购地';

CREATE TABLE `warehouse` (
  `id`            INT(11)     NOT NULL  AUTO_INCREMENT COMMENT 'ID',
  `cts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '创建时间',
  `uts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '修改时间',
  `name`          VARCHAR(32) NOT NULL                COMMENT '仓库名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='仓库';

CREATE TABLE `free_flash` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `onlinedate` date NOT NULL COMMENT '众测日期',
  `itemid` int(11) NOT NULL COMMENT '商品ID',
  `skuid` int(11) NOT NULL COMMENT '商品skuID',
  `applynum` int(11) NOT NULL COMMENT '众测商品人数目',
  `freequantity` int(11) NOT NULL COMMENT '商品数量',
  `starttime` datetime DEFAULT NULL COMMENT '众测开始时间',
  `endtime` datetime DEFAULT NULL COMMENT '众测结束时间',
  `status` tinyint(4) DEFAULT '0' COMMENT '状态 1：上线  0：下线',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_itemid` (`itemid`),
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='众测商品'

CREATE TABLE `item_appraise` (
	`id`            INT(11)     NOT NULL  AUTO_INCREMENT,
	`freeflashid`   INT(11) DEFAULT '0' COMMENT '众测id',
  `freeorderid`   INT(11) DEFAULT '0' COMMENT '众测订单号',
  `score`         INT(11) DEFAULT '5' COMMENT '评测打分',
	`cts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '创建时间',
	`uts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '修改时间',
	`itemid`        INT(11)     NOT NULL  COMMENT '商品ID',
	`uid`           INT(11)     NOT NULL  COMMENT '评测人ID',
	`name`          VARCHAR(32) NOT NULL  COMMENT '评测人名称', -- 改名字后需要更新
	`appraise`      TEXT         COMMENT '评测信息',
	`offical`       TINYINT(1)            COMMENT '官方',
	`summary`		VARCHAR(2048)	COMMENT '评测缩略',
	PRIMARY KEY (`id`),
  KEY `idx_itemid` (`itemid`),
  KEY `idx_uid` (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品评测信息';
/*
CREATE TABLE `crowd` (
  `id`            INT(11)     NOT NULL  AUTO_INCREMENT COMMENT 'ID',
  `cts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '创建时间',
  `uts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '修改时间',
  `name`          VARCHAR(32) NOT NULL                COMMENT '适用人群',
  `starttime`     INT(11)     NOT NULL                COMMENT '开始时间',
  `endtime`       INT(11)     NOT NULL                COMMENT '结束时间',
  PRIMARY KEY (`id`),
  KEY `idx_starttime_endtime` (`starttime`, `endtime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='适用人群';

CREATE TABLE `gender` (
  `id`            INT(11)     NOT NULL  AUTO_INCREMENT COMMENT 'ID',
  `cts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '创建时间',
  `uts`           DATETIME    NOT NULL  DEFAULT now() COMMENT '修改时间',
  `name`          VARCHAR(32) NOT NULL                COMMENT '名称',
  `value`         INT(11)     NOT NULL                COMMENT '', -- 1 男 2 女 4 男女皆可
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='性别';

-- 采购地、仓库、适用人群、适用性别、品牌本都属于属性（SKU）
*/

-- ALTER TABLE `item_detail` ADD COLUMN `brandid` INT(11) NOT NULL DEFAULT 0 COMMENT '品牌ID' AFTER `itemid`,
-- ADD COLUMN `crowdid` INT(11) NOT NULL COMMENT '适用人群' AFTER `property`,
-- ADD COLUMN `genderid` INT(11) NOT NULL COMMENT '性别' AFTER `crowd`,
ALTER TABLE `item_detail` ADD COLUMN `purchaseid` INT(11) NOT NULL DEFAULT 0 COMMENT '采购地',
  ADD COLUMN `warehouseid` INT(11) NOT NULL DEFAULT 0 COMMENT '仓库';

ALTER TABLE `item_status` ADD KEY `idx_starttime_endtime` (`starttime`, `endtime`),
ADD KEY `idx_status_onlinetime` (`status`, `onlinetime`);

ALTER TABLE `item_detail` ADD KEY `idx_crowdid_genderid` (`crowdid`, `genderid`),
ADD KEY `idx_brandid` (`brandid`);

ALTER TABLE `item_property` ADD COLUMN `sort` INT(3) NULL DEFAULT 0 AFTER `skuid`;

ALTER TABLE `property` ADD COLUMN `sort` INT(3) NULL DEFAULT 0;

ALTER TABLE `item_property` ADD COLUMN `pext` VARCHAR(200)  NULL DEFAULT "" COMMENT '属性名称的扩展json字段',
  ADD COLUMN `pvext` VARCHAR(200)  NULL DEFAULT "" COMMENT '属性value值的扩展json字段';
