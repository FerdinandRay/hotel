-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2016-05-30 17:38:00
-- 服务器版本: 5.5.49-0ubuntu0.14.04.1
-- PHP 版本: 5.5.9-1ubuntu4.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `Hotel`
--
CREATE DATABASE IF NOT EXISTS `Hotel` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `Hotel`;

-- --------------------------------------------------------

--
-- 表的结构 `tbl_checkin`
--

CREATE TABLE IF NOT EXISTS `tbl_checkin` (
  `checkin_id` int(11) NOT NULL AUTO_INCREMENT,
  `checkin_user_id` int(11) NOT NULL,
  `checkin_cus_id` int(11) NOT NULL,
  `checkin_room_id` int(11) NOT NULL,
  `checkin_datetime` varchar(30) COLLATE utf8_bin NOT NULL,
  `checkin_day` int(11) NOT NULL,
  PRIMARY KEY (`checkin_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=5012 ;

--
-- 转存表中的数据 `tbl_checkin`
--

INSERT INTO `tbl_checkin` (`checkin_id`, `checkin_user_id`, `checkin_cus_id`, `checkin_room_id`, `checkin_datetime`, `checkin_day`) VALUES
(5011, 1000, 2002, 314, '2016-05-30', 1),
(5010, 1000, 2001, 313, '2016-05-30', 1);

-- --------------------------------------------------------

--
-- 表的结构 `tbl_checkout`
--

CREATE TABLE IF NOT EXISTS `tbl_checkout` (
  `checkout_id` int(11) NOT NULL AUTO_INCREMENT,
  `checkout_user_id` int(11) NOT NULL,
  `checkout_cus_id` int(11) NOT NULL,
  `checkout_room_id` int(11) NOT NULL,
  `checkout_datetime` varchar(30) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`checkout_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=7002 ;

--
-- 转存表中的数据 `tbl_checkout`
--

INSERT INTO `tbl_checkout` (`checkout_id`, `checkout_user_id`, `checkout_cus_id`, `checkout_room_id`, `checkout_datetime`) VALUES
(7000, 1, 2001, 336, '2016-5-30'),
(7001, 1, 2001, 336, '2015-5-30');

-- --------------------------------------------------------

--
-- 表的结构 `tbl_customer`
--

CREATE TABLE IF NOT EXISTS `tbl_customer` (
  `cus_id` int(11) NOT NULL AUTO_INCREMENT,
  `cus_name` varchar(20) COLLATE utf8_bin NOT NULL,
  `cus_sex` varchar(10) COLLATE utf8_bin NOT NULL,
  `cus_identity` varchar(20) COLLATE utf8_bin NOT NULL,
  `cus_phone` varchar(11) COLLATE utf8_bin NOT NULL,
  `cus_points` int(11) NOT NULL,
  `cus_level_id` int(11) NOT NULL,
  PRIMARY KEY (`cus_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=2004 ;

--
-- 转存表中的数据 `tbl_customer`
--

INSERT INTO `tbl_customer` (`cus_id`, `cus_name`, `cus_sex`, `cus_identity`, `cus_phone`, `cus_points`, `cus_level_id`) VALUES
(2001, '陈飞', '男', '331004100408090325', '15156912312', 400, 2),
(2002, '张三', '男', '33100421421783127', '15158715708', 449, 1),
(2003, '王五', '男', '331004199405090311', '15158715708', 700, 3);

-- --------------------------------------------------------

--
-- 表的结构 `tbl_level`
--

CREATE TABLE IF NOT EXISTS `tbl_level` (
  `level_id` int(11) NOT NULL AUTO_INCREMENT,
  `level_name` varchar(20) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`level_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=5 ;

--
-- 转存表中的数据 `tbl_level`
--

INSERT INTO `tbl_level` (`level_id`, `level_name`) VALUES
(1, '普通会员'),
(2, '高级会员'),
(3, '钻石会员');

-- --------------------------------------------------------

--
-- 表的结构 `tbl_room`
--

CREATE TABLE IF NOT EXISTS `tbl_room` (
  `room_id` int(11) NOT NULL AUTO_INCREMENT,
  `room_number` int(11) NOT NULL,
  `room_type_id` int(11) NOT NULL,
  `room_status` varchar(11) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`room_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=315 ;

--
-- 转存表中的数据 `tbl_room`
--

INSERT INTO `tbl_room` (`room_id`, `room_number`, `room_type_id`, `room_status`) VALUES
(313, 110, 3, '非空'),
(314, 111, 3, '非空');

-- --------------------------------------------------------

--
-- 表的结构 `tbl_roomtype`
--

CREATE TABLE IF NOT EXISTS `tbl_roomtype` (
  `roomtype_id` int(11) NOT NULL AUTO_INCREMENT,
  `roomtype_name` varchar(20) COLLATE utf8_bin NOT NULL,
  `roomtype_cost` int(11) NOT NULL,
  `roomtype_deposit` int(11) NOT NULL,
  `roomtype_total` int(11) NOT NULL,
  `roomtype_surplus` int(11) NOT NULL,
  PRIMARY KEY (`roomtype_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=6 ;

--
-- 转存表中的数据 `tbl_roomtype`
--

INSERT INTO `tbl_roomtype` (`roomtype_id`, `roomtype_name`, `roomtype_cost`, `roomtype_deposit`, `roomtype_total`, `roomtype_surplus`) VALUES
(1, '豪华双人间', 500, 300, 10, 10),
(3, '总统套房', 2000, 1000, 9, 7),
(4, '普通单人间', 300, 300, 10, 10),
(5, '普通双人间', 250, 200, 9, 9);

-- --------------------------------------------------------

--
-- 表的结构 `tbl_user`
--

CREATE TABLE IF NOT EXISTS `tbl_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_account` varchar(20) COLLATE utf8_bin NOT NULL,
  `user_password` varchar(50) COLLATE utf8_bin NOT NULL,
  `user_name` varchar(20) COLLATE utf8_bin NOT NULL,
  `user_sex` varchar(10) COLLATE utf8_bin NOT NULL,
  `user_phone` varchar(11) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1003 ;

--
-- 转存表中的数据 `tbl_user`
--

INSERT INTO `tbl_user` (`user_id`, `user_account`, `user_password`, `user_name`, `user_sex`, `user_phone`) VALUES
(1000, 'root', 'root', '方寸', '男', '15158715708'),
(1002, 'fang', '111', '张飞', '男', '123123');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
