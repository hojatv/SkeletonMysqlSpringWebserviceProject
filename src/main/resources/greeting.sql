-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.9-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for greeting
DROP DATABASE IF EXISTS `greeting`;
CREATE DATABASE IF NOT EXISTS `greeting` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `greeting`;

-- Dumping structure for table greeting.account
DROP TABLE IF EXISTS `account`;
CREATE TABLE IF NOT EXISTS `account` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(200) NOT NULL,
  `enabled` tinyint(4) NOT NULL DEFAULT '1',
  `credentialsExpired` tinyint(4) NOT NULL DEFAULT '0',
  `expired` tinyint(4) NOT NULL DEFAULT '0',
  `locked` tinyint(4) NOT NULL DEFAULT '0',
  `credentials_expired` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- Dumping data for table greeting.account: ~2 rows (approximately)
DELETE FROM `account`;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` (`id`, `username`, `password`, `enabled`, `credentialsExpired`, `expired`, `locked`, `credentials_expired`) VALUES
	(1, 'user', '$2a$10$UyteuUfareQ7xfXW2CaVnuqDLgykpJbxBAb0VLFdIZfrpJyRK9L3e', 1, 0, 0, 0, b'0'),
	(2, 'operations', '$2a$10$eInav.MMWkJdVEctoR/aY.cJUJMRWw6HURp8vUJT5FegImdISJisW', 1, 0, 0, 0, b'0');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;

-- Dumping structure for table greeting.account_role
DROP TABLE IF EXISTS `account_role`;
CREATE TABLE IF NOT EXISTS `account_role` (
  `account_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`account_id`,`role_id`),
  KEY `FK_p2jpuvn8yll7x96rae4hvw3sj` (`role_id`),
  CONSTRAINT `FK_ibmw1g5w37bmuh5fc0db7wn10` FOREIGN KEY (`account_id`) REFERENCES `account` (`id`),
  CONSTRAINT `FK_p2jpuvn8yll7x96rae4hvw3sj` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table greeting.account_role: ~2 rows (approximately)
DELETE FROM `account_role`;
/*!40000 ALTER TABLE `account_role` DISABLE KEYS */;
INSERT INTO `account_role` (`account_id`, `role_id`) VALUES
	(1, 1),
	(2, 3);
/*!40000 ALTER TABLE `account_role` ENABLE KEYS */;

-- Dumping structure for table greeting.greeting
DROP TABLE IF EXISTS `greeting`;
CREATE TABLE IF NOT EXISTS `greeting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `text` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- Dumping data for table greeting.greeting: ~4 rows (approximately)
DELETE FROM `greeting`;
/*!40000 ALTER TABLE `greeting` DISABLE KEYS */;
INSERT INTO `greeting` (`id`, `text`) VALUES
	(1, 'Fourth Greeting11'),
	(6, 'Fifth Greeting'),
	(7, 'Sixth Greeting'),
	(13, 'Eight Greeting');
/*!40000 ALTER TABLE `greeting` ENABLE KEYS */;

-- Dumping structure for table greeting.role
DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint(20) NOT NULL,
  `code` varchar(50) NOT NULL,
  `label` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table greeting.role: ~3 rows (approximately)
DELETE FROM `role`;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` (`id`, `code`, `label`) VALUES
	(1, 'ROLE_USER', 'user'),
	(2, 'ROLE_ADMIN', 'admin'),
	(3, 'ROLE_SYSADMIN', 'sysadmin');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
