/*
SQLyog v10.2 
MySQL - 5.0.27-community : Database - url_function
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`url_function` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `url_function`;

/*Table structure for table `t_function` */

DROP TABLE IF EXISTS `t_function`;

CREATE TABLE `t_function` (
  `funId` int(11) NOT NULL auto_increment,
  `funName` varchar(32) default NULL,
  `note` varchar(128) default NULL,
  `resource` varchar(64) default NULL,
  `stuffix` varchar(8) default NULL,
  `params` varchar(64) default NULL,
  `type` tinyint(4) default NULL,
  PRIMARY KEY  (`funId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_function` */

insert  into `t_function`(`funId`,`funName`,`note`,`resource`,`stuffix`,`params`,`type`) values (1,'后台登陆权限','能够进入系统后台的登陆权限','gf','','login',4),(3,'管理员修改密码权限','该权限是指，登陆后的管理员可以修改自己的登陆密码。','bclient/user/updatePwd','.jsp','',4),(8,'添加管理员权限','该权限拥有新增普通管理员的功能。','bclient/user/addAdmin','.jsp','',4),(9,'禁用管理员权限','该权限拥有禁用普通管理员的功能。','admin','.do','stopAdmin,userId',4),(10,'解锁管理员权限','该权限拥有激活/解锁已被禁用的普通管理员的功能。','admin','.do','activeAdmin,userId',4),(11,'查看管理员列表权限','该权限能够查看所有的后台管理员，也是禁用管理员，激活管理员，变更管理员角色的入口。','admin','.do','listUser,admin',4),(12,'变更用户角色权限','该权限能够变更用户拥有的角色列表，还可以修改用户的userType。','admin','.do','toChangeUserUI,userId,user',4),(13,'添加权限的权限','该权限能够增加系统中的权限。','bclient/user/addFun','.jsp','',1),(14,'删除权限的权限','该权限能够删除系统中的权限。','function','.do','deleteFunction,funId',1),(15,'修改权限的权限','该权限能够修改系统中的权限。','function','.do','toUpdateFunctionUI,funId',1),(16,'查看权限列表的权限','该权限能够查看系统中的所有权限，也是删除权限和修改权限的入口。','function','.do','listFunction',1),(17,'添加角色的权限','该权限能够向系统中加入一个新的角色。','bclient/user/addRole','.jsp','',2),(18,'删除角色的权限','该权限能够删除系统中的角色。','role','.do','deleteRole,roleId',2),(19,'变更角色的权限','该权限能够修改角色具有的属性，还能够变更角色下具有的权限列表。','role','.do','toChangeRoleUI,roleId',2),(20,'查看角色列表的权限','该权限能够查看系统中所有的角色，也是删除角色，变更角色的入口。','role','.do','listRole',2),(21,'屏蔽会员的权限','该权限能够屏蔽前台的会员。','admin','.do','stopUser,userId',3),(22,'解锁会员的权限','该权限能够解锁已被屏蔽的会员。','admin','.do','activeUser,userId',3),(23,'查看会员列表的权限','该权限能够查看系统中的所有会员，也是屏蔽会员，解锁会员及变更用户角色的入口。','admin','.do','listUser,user',3),(24,'变更管理员角色权限','可以变更管理员的角色的权限','admin','.do','toChangeUserUI,userId,admin',4);

/*Table structure for table `t_role` */

DROP TABLE IF EXISTS `t_role`;

CREATE TABLE `t_role` (
  `roleId` int(11) NOT NULL auto_increment,
  `roleName` varchar(32) default NULL,
  `note` varchar(255) default NULL,
  PRIMARY KEY  (`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_role` */

insert  into `t_role`(`roleId`,`roleName`,`note`) values (1,'超级管理员','最高管理角色，拥有所有的权限。'),(2,'用户管理员','拥有管理用户的相关权限。\r\n加入后台登陆权限；'),(7,'角色管理员','拥有角色管理的权限。'),(8,'权限管理员','拥有权限管理的角色。');

/*Table structure for table `t_role_function` */

DROP TABLE IF EXISTS `t_role_function`;

CREATE TABLE `t_role_function` (
  `roleFunId` int(11) NOT NULL auto_increment,
  `roleId` int(11) default NULL,
  `funId` int(11) default NULL,
  `have` tinyint(4) default NULL,
  PRIMARY KEY  (`roleFunId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_role_function` */

insert  into `t_role_function`(`roleFunId`,`roleId`,`funId`,`have`) values (1,1,1,1),(25,6,1,1),(26,6,3,1),(27,6,6,0),(28,6,7,0),(29,2,1,1),(30,7,25,1),(31,7,3,0),(32,7,10,0),(33,7,12,0),(34,2,3,1),(35,1,19,1),(36,1,20,1),(37,1,25,1),(38,1,16,1),(39,1,15,1),(40,1,14,1),(41,1,13,1),(42,1,18,1),(43,1,17,1),(44,1,23,1),(45,1,22,1),(46,1,21,1),(47,1,11,1),(48,1,3,1),(49,1,8,1),(50,1,9,1),(51,1,10,1),(52,1,12,1),(53,2,16,0),(54,2,15,0),(55,2,13,0),(56,2,20,0),(57,2,22,1),(58,2,21,1),(59,2,12,1),(60,2,14,0),(61,2,23,1),(62,2,11,1),(63,2,10,1),(64,2,9,1),(65,2,8,1),(66,7,19,1),(67,7,20,1),(68,7,17,1),(69,7,18,1),(70,7,1,1),(71,8,15,1),(72,8,14,1),(73,8,13,1),(74,8,16,1),(75,8,1,1),(76,1,24,1),(77,2,24,1);

/*Table structure for table `t_user` */

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `userId` int(11) NOT NULL auto_increment,
  `userName` varchar(16) default NULL,
  `password` varchar(32) default NULL,
  `userType` tinyint(4) default NULL,
  `score` int(11) default NULL,
  `companyName` varchar(32) default NULL,
  `companyAddress` varchar(128) default NULL,
  `publishedAdCount` tinyint(4) default NULL,
  `phone` char(11) default NULL,
  `email` varchar(64) default NULL,
  `regTime` datetime default NULL,
  `lastLoginTime` datetime default NULL,
  `loginCount` int(11) default NULL,
  `status` tinyint(4) default NULL,
  PRIMARY KEY  (`userId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_user` */

insert  into `t_user`(`userId`,`userName`,`password`,`userType`,`score`,`companyName`,`companyAddress`,`publishedAdCount`,`phone`,`email`,`regTime`,`lastLoginTime`,`loginCount`,`status`) values (1,'admin','be2596076ccbd141120a050d12e98548',1,1000,NULL,NULL,NULL,NULL,'admin@163.com','2014-11-04 14:28:58','2015-05-28 20:03:39',1189,1),(2,'tempuser','be2596076ccbd141120a050d12e98548',3,10,NULL,NULL,NULL,'18271263336','1092017732@qq.com','2014-11-07 16:03:12','2014-11-07 19:59:00',3,1),(3,'nomaladmin','6c7c969613e6a7175879801381ab3d9f',2,500,NULL,NULL,NULL,NULL,NULL,'2014-11-08 10:53:09','2014-11-08 10:53:13',0,0),(4,'manager','1d0258c2440a8d19e716292b231e3190',1,500,NULL,NULL,NULL,NULL,NULL,'2014-11-08 10:58:03','2014-12-18 18:09:13',75,1),(5,'meigang','be2596076ccbd141120a050d12e98548',2,500,NULL,NULL,NULL,NULL,NULL,'2015-04-22 23:24:48','2015-05-26 16:54:34',76,1),(6,'roleManager','be2596076ccbd141120a050d12e98548',2,500,NULL,NULL,NULL,NULL,NULL,'2015-05-14 16:02:25','2015-05-26 16:53:47',10,1),(7,'userManager','be2596076ccbd141120a050d12e98548',2,500,NULL,NULL,NULL,NULL,NULL,'2015-05-14 16:03:04','2015-05-14 16:20:26',1,1),(9,'funManager','be2596076ccbd141120a050d12e98548',2,500,NULL,NULL,NULL,NULL,NULL,'2015-05-14 16:09:33','2015-05-14 16:20:02',1,1);

/*Table structure for table `t_user_role` */

DROP TABLE IF EXISTS `t_user_role`;

CREATE TABLE `t_user_role` (
  `userRoleId` int(11) NOT NULL auto_increment,
  `userId` int(11) default NULL,
  `roleId` int(11) default NULL,
  `have` tinyint(4) default NULL,
  PRIMARY KEY  (`userRoleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `t_user_role` */

insert  into `t_user_role`(`userRoleId`,`userId`,`roleId`,`have`) values (1,1,1,1),(2,2,1,0),(3,2,6,0),(4,4,2,1),(5,5,1,0),(6,5,2,1),(7,1,2,0),(8,5,3,0),(9,4,1,1),(10,1,7,0),(11,6,7,1),(12,7,2,1),(13,9,8,1);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
