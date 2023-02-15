-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.29-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for mydb
CREATE DATABASE IF NOT EXISTS `mydb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `mydb`;

-- Dumping structure for procedure mydb.GetJournal
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetJournal`(IN intype VARCHAR(1) )
BEGIN
	SELECT journalid,journalname,amount FROM tjournal WHERE journaltype = intype;
END//
DELIMITER ;

-- Dumping structure for table mydb.tjournal
CREATE TABLE IF NOT EXISTS `tjournal` (
  `journalid` varchar(50) NOT NULL COMMENT 'UUID',
  `journalname` varchar(100) NOT NULL,
  `journaltype` varchar(1) NOT NULL DEFAULT '0' COMMENT '0=Payment,1=Receive',
  `amount` decimal(20,4) NOT NULL DEFAULT '0.0000',
  `editdate` date DEFAULT NULL,
  `edittime` time DEFAULT NULL,
  PRIMARY KEY (`journalid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='table keep journal list';

-- Dumping data for table mydb.tjournal: ~7 rows (approximately)
/*!40000 ALTER TABLE `tjournal` DISABLE KEYS */;
INSERT INTO `tjournal` (`journalid`, `journalname`, `journaltype`, `amount`, `editdate`, `edittime`) VALUES
	('1001', 'Breakfast', '0', 100.0000, NULL, NULL),
	('1002', 'Lunch', '0', 100.0000, NULL, NULL),
	('1003', 'Dinner', '0', 100.0000, NULL, NULL),
	('1004', 'Fare', '0', 150.0000, NULL, NULL),
	('1005', 'Shopping', '0', 500.0000, NULL, NULL),
	('2001', 'Salary', '1', 25000.0000, NULL, NULL),
	('2002', 'Commission', '1', 1500.0000, NULL, NULL);
/*!40000 ALTER TABLE `tjournal` ENABLE KEYS */;

-- Dumping structure for table mydb.ttransaction
CREATE TABLE IF NOT EXISTS `ttransaction` (
  `transid` varchar(50) NOT NULL COMMENT 'UUID',
  `userid` varchar(50) NOT NULL COMMENT 'tuser.userid',
  `transdate` date NOT NULL,
  `journalid` varchar(50) NOT NULL COMMENT 'tjournal.journalid',
  `transno` int(11) NOT NULL COMMENT 'sequence number',
  `amount` decimal(20,4) NOT NULL,
  `remark` varchar(200) DEFAULT NULL,
  `transtime` datetime DEFAULT NULL,
  `editdate` date DEFAULT NULL,
  `edittime` time DEFAULT NULL,
  PRIMARY KEY (`transid`),
  UNIQUE KEY `userid_trxdate_journalid` (`userid`,`transdate`,`journalid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='table keep transaction';

-- Dumping data for table mydb.ttransaction: ~4 rows (approximately)
/*!40000 ALTER TABLE `ttransaction` DISABLE KEYS */;
INSERT INTO `ttransaction` (`transid`, `userid`, `transdate`, `journalid`, `transno`, `amount`, `remark`, `transtime`, `editdate`, `edittime`) VALUES
	('44d814de-ac10-11ed-809e-98fa9bd6bd8e', 'tester', '2023-02-01', '2002', 4, 1100.0000, NULL, NULL, NULL, NULL),
	('498e18b8-ac0f-11ed-809e-98fa9bd6bd8e', 'tester', '2023-02-01', '1001', 1, 120.0000, 'Noodle + Coffee', NULL, NULL, NULL),
	('5055f401-ac0f-11ed-809e-98fa9bd6bd8e', 'tester', '2023-02-01', '1002', 2, 100.0000, 'Fry Rice', NULL, NULL, NULL),
	('57781363-ac0f-11ed-809e-98fa9bd6bd8e', 'tester', '2023-02-01', '1003', 3, 250.0000, 'Steak', NULL, NULL, NULL);
/*!40000 ALTER TABLE `ttransaction` ENABLE KEYS */;

-- Dumping structure for table mydb.tuser
CREATE TABLE IF NOT EXISTS `tuser` (
  `userid` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `userpassword` varchar(50) NOT NULL,
  `usertitle` varchar(100) NOT NULL,
  PRIMARY KEY (`userid`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='table keep user';

-- Dumping data for table mydb.tuser: ~1 rows (approximately)
/*!40000 ALTER TABLE `tuser` DISABLE KEYS */;
INSERT INTO `tuser` (`userid`, `username`, `userpassword`, `usertitle`) VALUES
	('tester', 'tester', 'test', 'Tester Testing');
/*!40000 ALTER TABLE `tuser` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
