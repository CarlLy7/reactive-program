CREATE TABLE t_user (
                        `id` INT NOT NULL AUTO_INCREMENT,
                        `username` VARCHAR ( 255 ) NOT NULL COMMENT '⽤户名',
                        `password` VARCHAR ( 255 ) NOT NULL COMMENT '密码',
                        `email` VARCHAR ( 255 ) NOT NULL COMMENT '邮箱',
                        `phone` CHAR ( 11 ) NOT NULL COMMENT '电话',
                        `create_time` DATETIME NOT NULL COMMENT '创
  建时间',
                        `update_time` DATETIME NOT NULL COMMENT '更
  新时间',
                        PRIMARY KEY ( `id` )
);
CREATE TABLE `t_roles` (
                           `id` INT NOT NULL AUTO_INCREMENT,
                           `name` VARCHAR ( 255 ) NOT NULL COMMENT '⻆⾊名',
                           `value` VARCHAR ( 255 ) NOT NULL COMMENT '⻆⾊的英⽂名',
                           `create_time` DATETIME NOT NULL,
                           `update_time` DATETIME NOT NULL,
                           PRIMARY KEY ( `id` )
);
CREATE TABLE `t_perm` (
                          `id` INT NOT NULL AUTO_INCREMENT,
                          `value` VARCHAR ( 255 ) NOT NULL COMMENT '权限字段',
                          `uri` VARCHAR ( 255 ) NOT NULL COMMENT '资源路径',
                          `description` VARCHAR ( 255 ) NOT NULL COMMENT '资源描述',
                          `create_time` DATETIME NOT NULL,
                          `update_time` DATETIME NOT NULL,
                          PRIMARY KEY ( `id` ));
CREATE TABLE `t_user_role` (
                               `id` INT NOT NULL AUTO_INCREMENT,
                               `user_id` INT NOT NULL,
                               `role_id` INT NOT NULL,
                               `create_time` DATETIME NOT NULL,
                               `update_time` DATETIME NOT NULL,
                               PRIMARY KEY ( `id` ));
CREATE TABLE `t_role_perm` (
                               `id` INT NOT NULL AUTO_INCREMENT,
                               `role_id` INT NOT NULL,
                               `perm_id` INT NOT NULL,
                               `create_time` DATETIME NOT NULL,
                               `update_time` DATETIME NOT NULL,
                               PRIMARY KEY ( `id` )
);
CREATE TABLE `t_book` (
                          `id` INT NOT NULL AUTO_INCREMENT,
                          `title` VARCHAR ( 255 ) NOT NULL,
                          `author_id` INT NOT NULL,
                          `publish_time` DATETIME NOT NULL,
                          PRIMARY KEY ( `id` )
);