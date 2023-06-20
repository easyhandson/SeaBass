/* 建库 seabass_example */
DROP DATABASE IF EXISTS `seabass_example`;
CREATE DATABASE  `seabass_example` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE seabass_example;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

/* 活动表 */
DROP TABLE IF EXISTS `seabass_example_act`;
CREATE TABLE `seabass_example_act` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
    `create_time` datetime NOT NULL DEFAULT 0 COMMENT '创建时间',
    `update_time` datetime NOT NULL DEFAULT 0 COMMENT '更新时间',
    `act_name` varchar(50) NOT NULL DEFAULT '' COMMENT '活动名称',
    `begin_time` datetime NOT NULL DEFAULT 0 COMMENT '开始时间',
    `end_time` datetime NOT NULL DEFAULT 0 COMMENT '结束时间',
    `sequence` int NOT NULL DEFAULT 0 COMMENT '序号',
    PRIMARY KEY (`id`),
    KEY `idx_sequence_create_time` (`sequence`, `create_time`)
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COMMENT='活动表';

/* 测试表初始化语句 */
BEGIN;
/* 清空表数据并初始化问题 */
TRUNCATE TABLE `seabass_example_act`;
INSERT INTO `seabass_example_act` (create_time, update_time, act_name, begin_time, end_time, sequence) VALUES
(now(), now(), '活动1', UNIX_TIMESTAMP('2022-06-11 00:00:00'), UNIX_TIMESTAMP('2022-06-18 23:59:59'), 1),
(now(), now(), '活动2', UNIX_TIMESTAMP('2022-06-11 00:00:00'), UNIX_TIMESTAMP('2022-06-18 23:59:59'), 2),
(now(), now(), '活动3', UNIX_TIMESTAMP('2022-06-14 00:00:00'), UNIX_TIMESTAMP('2022-06-20 23:59:59'), 3),
(now(), now(), '活动4', UNIX_TIMESTAMP('2022-06-14 00:00:00'), UNIX_TIMESTAMP('2022-06-20 23:59:59'), 4),
(now(), now(), '活动5', UNIX_TIMESTAMP('2022-06-18 00:00:00'), UNIX_TIMESTAMP('2022-06-28 23:59:59'), 5),
(now(), now(), '活动6', UNIX_TIMESTAMP('2022-06-18 00:00:00'), UNIX_TIMESTAMP('2022-06-28 23:59:59'), 6);
COMMIT;


SET FOREIGN_KEY_CHECKS = 1;
