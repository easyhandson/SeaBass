/* -------------------------------- 建库 seabass_user -------------------------------- */
DROP DATABASE IF EXISTS `seabass_user`;
CREATE DATABASE  `seabass_user` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE `seabass_user`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

/* 用户表 */
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime NOT NULL COMMENT '更新时间',
    `nickname` VARCHAR(100) NOT NULL COMMENT '昵称',
    `balance` DECIMAL(10, 0) NOT NULL DEFAULT 0 COMMENT '余额',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COMMENT='用户表';

/* 清空表数据并初始化 */
BEGIN;
TRUNCATE TABLE `user`;
INSERT INTO `user` (create_time, update_time, nickname, balance) VALUES
(NOW(), NOW(), '小明', 10);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;


/* -------------------------------- 建库 seabass_product -------------------------------- */
DROP DATABASE IF EXISTS `seabass_product`;
CREATE DATABASE  `seabass_product` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE `seabass_product`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

/* 产品表 */
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime NOT NULL COMMENT '更新时间',
    `stock` int DEFAULT NULL COMMENT '库存',
    `price` DECIMAL(10, 0) NOT NULL DEFAULT 0 COMMENT '价格',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COMMENT='产品表';

/* 清空表数据并初始化 */
BEGIN;
TRUNCATE TABLE `product`;
INSERT INTO `product` (create_time, update_time, stock, price) VALUES
(NOW(), NOW(), 10, 5);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;


/* -------------------------------- 建库 seabass_order -------------------------------- */
DROP DATABASE IF EXISTS `seabass_order`;
CREATE DATABASE  `seabass_order` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE `seabass_order`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

/* 订单表 */
DROP TABLE IF EXISTS `order`;
CREATE TABLE `order` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `update_time` datetime NOT NULL COMMENT '更新时间',
    `user_id` bigint NOT NULL COMMENT '用户Id',
    `product_id` bigint NOT NULL COMMENT '产品Id',
    `add_time` datetime NOT NULL COMMENT '下单时间',
    `pay_amount` DECIMAL(10, 0) NOT NULL DEFAULT 0 COMMENT '支付金额',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COMMENT='订单表';

SET FOREIGN_KEY_CHECKS = 1;
