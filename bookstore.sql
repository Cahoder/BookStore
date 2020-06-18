/*
Navicat MySQL Data Transfer

Source Server         : cahoder
Source Server Version : 50728
Source Host           : 127.0.0.1:3306
Source Database       : gpnu_java_web

Target Server Type    : MYSQL
Target Server Version : 50728
File Encoding         : 65001

Date: 2020-06-08 14:15:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for book_cart
-- ----------------------------
DROP TABLE IF EXISTS `book_cart`;
CREATE TABLE `book_cart` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '购物车ID',
  `product_id` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品ID',
  `addnum` int(11) NOT NULL DEFAULT '0' COMMENT '此商品添加件数',
  `user_id` int(11) NOT NULL COMMENT '购物车所属用户ID',
  `is_del` int(11) NOT NULL DEFAULT '0' COMMENT '是否被用户删除状态（0/未删除,1/已删除）',
  `update_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`,`product_id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='BookStore用户购物车列表';

-- ----------------------------
-- Table structure for book_favor
-- ----------------------------
DROP TABLE IF EXISTS `book_favor`;
CREATE TABLE `book_favor` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '收藏夹ID',
  `product_id` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品ID',
  `user_id` int(11) NOT NULL COMMENT '收藏夹所属用户ID',
  `is_del` int(11) NOT NULL DEFAULT '0' COMMENT '是否被用户删除状态（0/未删除,1/已删除）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`,`product_id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='BookStore用户收藏夹列表';

-- ----------------------------
-- Table structure for book_notice
-- ----------------------------
DROP TABLE IF EXISTS `book_notice`;
CREATE TABLE `book_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '通知ID',
  `title` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '通知标题',
  `details` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '通知详情',
  `is_del` int(11) NOT NULL DEFAULT '0' COMMENT '通知删除状态（0/未删除,1/已删除）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='BookStore公告通知表';

-- ----------------------------
-- Table structure for book_orderitem
-- ----------------------------
DROP TABLE IF EXISTS `book_orderitem`;
CREATE TABLE `book_orderitem` (
  `order_id` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对应订单列表ID',
  `product_id` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '对应商品列表ID',
  `buynum` int(11) NOT NULL DEFAULT '0' COMMENT '此商品购买件数',
  PRIMARY KEY (`order_id`,`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='BookStore订单商品详情表';

-- ----------------------------
-- Table structure for book_orders
-- ----------------------------
DROP TABLE IF EXISTS `book_orders`;
CREATE TABLE `book_orders` (
  `id` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '订单ID',
  `money` double NOT NULL DEFAULT '0' COMMENT '订单金额',
  `receiverAddress` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '订单派送地址',
  `receiverName` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '订单签送人',
  `receiverPhone` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '订单签收人电话号码',
  `paystate` int(11) NOT NULL DEFAULT '0' COMMENT '订单支付状态（0/未支付,1/已支付）',
  `ordertime` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '订单创建时间',
  `user_id` int(11) NOT NULL COMMENT '订单用户ID',
  `is_del` int(11) NOT NULL DEFAULT '0' COMMENT '订单删除状态（0/未删除,1/已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='BookStore订单列表';

-- ----------------------------
-- Table structure for book_products
-- ----------------------------
DROP TABLE IF EXISTS `book_products`;
CREATE TABLE `book_products` (
  `id` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '商品ID',
  `name` varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '商品名称',
  `price` double DEFAULT NULL COMMENT '商品价格',
  `category` varchar(40) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '商品分类',
  `pnum` int(11) DEFAULT '0' COMMENT '商品库存',
  `imgurl` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '商品图片连接',
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '商品简介',
  `is_del` int(11) NOT NULL DEFAULT '0' COMMENT '产品删除状态（0/未删除,1/已删除）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='BookStore商品列表';

-- ----------------------------
-- Table structure for book_user
-- ----------------------------
DROP TABLE IF EXISTS `book_user`;
CREATE TABLE `book_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户姓名',
  `password` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户密码',
  `gender` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '性别(男,女)',
  `email` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '邮箱',
  `telephone` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '电话',
  `introduce` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '简介',
  `role` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '角色',
  `register_time` timestamp NOT NULL COMMENT '注册时间',
  `is_del` int(11) NOT NULL DEFAULT '0' COMMENT '用户删除状态（0/未删除,1/已删除）',
  `avatar` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '用户头像图片连接',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='BookStore用户信息表';

-- ----------------------------
-- Records of book_user
-- ----------------------------
INSERT INTO `book_user` VALUES ('1', 'admin', '123456', '男', '123456@email.com', '13333333333', '用户简介', '超级管理员', '2020-05-09 13:44:35', '0', '');


-- ----------------------------
-- Table structure for book_user_receipt
-- ----------------------------
DROP TABLE IF EXISTS `book_user_receipt`;
CREATE TABLE `book_user_receipt` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '信息ID',
  `user_id` int(11) NOT NULL COMMENT '信息所属用户ID',
  `name` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '签收人名字',
  `address` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '签收人地址',
  `phone` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '签收人电话',
  `is_del` int(11) NOT NULL DEFAULT '0' COMMENT '是否被用户删除状态（0/未删除,1/已删除）',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`,`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='BookStore用户签收信息列表';

