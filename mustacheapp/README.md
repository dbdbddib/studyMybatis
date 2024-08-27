# mustacheajax

http://localhost:8088

# mysql
```
create database phonebook_db character set utf8mb4 collate utf8mb4_general_ci;
create user 'phonebook_user'@'%' identified by 'sangbong3!';
grant all privileges on phonebook_db.* to 'phonebook_user'@'%' with grant option;
flush privileges;

CREATE TABLE `category_tbl` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `category_tbl_uniqName` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `phonebook_tbl` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `phoneNumber` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `category_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `phonebook_tbl_uniqName` (`name`),
  KEY `phonebook_tbl_key_category_id` (`category_id`),
  CONSTRAINT `phonebook_tbl_key_category_id` FOREIGN KEY (`category_id`) REFERENCES `category_tbl` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `member_tbl` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `loginId` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(500) COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `nickname` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `role` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `active` tinyint(1) NOT NULL DEFAULT 0,
  `createDt` varchar(20) COLLATE utf8mb4_general_ci NULL,
  `createId` varchar(20) COLLATE utf8mb4_general_ci NULL,
  `updateDt` varchar(20) COLLATE utf8mb4_general_ci NULL,
  `updateId` varchar(20) COLLATE utf8mb4_general_ci NULL,
  `deleteDt` varchar(20) COLLATE utf8mb4_general_ci NULL,
  `deleteId` varchar(20) COLLATE utf8mb4_general_ci NULL,
  `deleteFlag` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY `member_tbl_uniq_loginId` (`loginId`),
  UNIQUE KEY `member_tbl_uniq_nickname` (`nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `board_tbl` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content` varchar(4000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `viewQty` int DEFAULT '0',
  `likeQty` int DEFAULT '0',
  `createDt` varchar(20) COLLATE utf8mb4_general_ci NULL,
  `createId` varchar(30) COLLATE utf8mb4_general_ci NULL,
  `updateDt` varchar(20) COLLATE utf8mb4_general_ci NULL,
  `updateId` varchar(30) COLLATE utf8mb4_general_ci NULL,
  `deleteDt` varchar(20) COLLATE utf8mb4_general_ci NULL,
  `deleteId` varchar(30) COLLATE utf8mb4_general_ci NULL,
  `deleteFlag` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `sblike_tbl` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `tbl` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `boardId` bigint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `sbfile_tbl` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ord` int unsigned NOT NULL DEFAULT '1',
  `fileType` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `uniqName` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `length` int unsigned NOT NULL DEFAULT '0',
  `description` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `tbl` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `boardId` bigint unsigned NOT NULL DEFAULT '0',
  `deleteFlag` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `comment_like_tbl` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `commentTbl` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `commentId` bigint unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

CREATE TABLE `board_comment_tbl` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `comment` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `likeQty` int DEFAULT '0',
  `boardId` bigint unsigned NOT NULL,
  `createDt` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `createId` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `updateDt` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `updateId` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `deleteDt` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `deleteId` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `deleteFlag` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `board_comment_tbl_board_tbl_FK` (`boardId`),
  CONSTRAINT `board_comment_tbl_board_tbl_FK` FOREIGN KEY (`boardId`) REFERENCES `board_tbl` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
```
