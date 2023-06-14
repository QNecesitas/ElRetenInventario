-- phpMyAdmin SQL Dump
-- version 4.9.5deb2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 10, 2023 at 11:06 PM
-- Server version: 8.0.33-0ubuntu0.20.04.2
-- PHP Version: 7.4.3-4ubuntu2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ElReten`
--

-- --------------------------------------------------------

--
-- Table structure for table `SessionLS`
--

CREATE TABLE `SessionLS` (
  `c_sessionLS` varchar(200) NOT NULL,
  `fk_c_drawerLS` varchar(200) DEFAULT NULL,
  `amount` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `SessionLS`
--

INSERT INTO `SessionLS` (`c_sessionLS`, `fk_c_drawerLS`, `amount`) VALUES
('6000\n6001\n6002_Chinas', '6000\n6001\n6002', 1),
('6000\n6001\n6002_KFB', '6000\n6001\n6002', 0),
('6000\n6001\n6002_SKF', '6000\n6001\n6002', 0),
('6003\n6004_Chinas', '6003\n6004', 1),
('6003\n6004_KFB', '6003\n6004', 0),
('6003\n6004_SKF', '6003\n6004', 0),
('6005\n6006_Chinas', '6005\n6006', 1),
('6005\n6006_KFB', '6005\n6006', 0),
('6005\n6006_SKF', '6005\n6006', 0),
('6007_Chinas', '6007', 0),
('6007_KFB', '6007', 0),
('6007_SKF', '6007', 0),
('607\n608\n6200_Chinas', '607\n608\n6200', 0),
('607\n608\n6200_KFB', '607\n608\n6200', 0),
('607\n608\n6200_SKF', '607\n608\n6200', 0),
('6201_Chinas', '6201', 1),
('6201_KFB', '6201', 0),
('6201_SKF', '6201', 1),
('6202\n_Chinas', '6202\n', 1),
('6202\n_KFB', '6202\n', 0),
('6202\n_SKF', '6202\n', 0),
('6203_Chinas', '6203', 1),
('6203_KFB', '6203', 0),
('6203_SKF', '6203', 0),
('6204_Chinas', '6204', 0),
('6204_KFB', '6204', 1),
('6205_Chinas', '6205', 1),
('6205_KFB', '6205', 1),
('6205_SKF', '6205', 0),
('6206_Chinas', '6206', 0),
('6206_KFB', '6206', 1),
('6206_SKF', '6206', 0),
('6207_Chinas', '6207', 0),
('6207_KFB', '6207', 0),
('6207_SKF', '6207', 0),
('6300\n6301_Chinas', '6300\n6301', 1),
('6300\n6301_KFB', '6300\n6301', 0),
('6300\n6301_SKF', '6300\n6301', 0),
('6302_Chinas', '6302', 1),
('6302_KFB', '6302', 0),
('6302_SKF', '6302', 0),
('6303_Chinas', '6303', 1),
('6303_KFB', '6303', 0),
('6303_SKF', '6303', 0),
('6304_Chinas', '6304', 0),
('6304_KFB', '6304', 0),
('6304_SKF', '6304', 1),
('6305_Chinas', '6305', 0),
('6305_KFB', '6305', 0),
('6305_SKF', '6305', 0),
('6306_Chinas', '6306', 0),
('6306_KFB', '6306', 0),
('6306_SKF', '6306', 0),
('Correas_Tipos_A', 'Correas_Tipos', 2),
('Estante Madera_Arriba_Accesorios  de motos', 'Estante Madera_Arriba', 0),
('Estante Madera_Retenes_1', 'Estante Madera_Retenes', 2),
('Estante Madera_Retenes_10', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_11', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_12', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_13', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_14', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_15', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_16', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_17', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_18', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_19', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_2', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_20', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_21', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_22', 'Estante Madera_Retenes', 0),
('Estante Madera_Retenes_23', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_24', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_25', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_26', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_27', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_28', 'Estante Madera_Retenes', 2),
('Estante Madera_Retenes_29', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_3', 'Estante Madera_Retenes', 4),
('Estante Madera_Retenes_30', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_31', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_32', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_33', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_34', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_35', 'Estante Madera_Retenes', 2),
('Estante Madera_Retenes_36', 'Estante Madera_Retenes', 2),
('Estante Madera_Retenes_37', 'Estante Madera_Retenes', 2),
('Estante Madera_Retenes_38', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_39', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_4', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_40', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_41', 'Estante Madera_Retenes', 2),
('Estante Madera_Retenes_42', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_43', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_44', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_45', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_46', 'Estante Madera_Retenes', 3),
('Estante Madera_Retenes_47', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_48', 'Estante Madera_Retenes', 0),
('Estante Madera_Retenes_49', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_5', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_50', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_51', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_52', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_53', 'Estante Madera_Retenes', 0),
('Estante Madera_Retenes_54', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_55', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_56', 'Estante Madera_Retenes', 2),
('Estante Madera_Retenes_57', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_58', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_59', 'Estante Madera_Retenes', 2),
('Estante Madera_Retenes_6', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_60', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_61', 'Estante Madera_Retenes', 2),
('Estante Madera_Retenes_62', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_63', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_64', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_65', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_66', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_67', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_68', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_69', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_7', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_70', 'Estante Madera_Retenes', 1),
('Estante Madera_Retenes_71', 'Estante Madera_Retenes', 0),
('Estante Madera_Retenes_72', 'Estante Madera_Retenes', 0),
('Estante Madera_Retenes_73', 'Estante Madera_Retenes', 0),
('Estante Madera_Retenes_74', 'Estante Madera_Retenes', 0),
('Estante Madera_Retenes_75', 'Estante Madera_Retenes', 0),
('Estante Madera_Retenes_76', 'Estante Madera_Retenes', 0),
('Estante Madera_Retenes_77', 'Estante Madera_Retenes', 0),
('Estante Madera_Retenes_79', 'Estante Madera_Retenes', 0),
('Estante Madera_Retenes_8', 'Estante Madera_Retenes', 4),
('Estante Madera_Retenes_80', 'Estante Madera_Retenes', 0),
('Estante Madera_Retenes_81', 'Estante Madera_Retenes', 0),
('Estante Madera_Retenes_82', 'Estante Madera_Retenes', 0),
('Estante Madera_Retenes_85', 'Estante Madera_Retenes', 0),
('Estante Madera_Retenes_86', 'Estante Madera_Retenes', 0),
('Estante Madera_Retenes_87', 'Estante Madera_Retenes', 0),
('Estante Madera_Retenes_9', 'Estante Madera_Retenes', 2),
('Estante Madera_Retenes_Repisa', 'Estante Madera_Retenes', 15),
('SKF', '6204', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `SessionLS`
--
ALTER TABLE `SessionLS`
  ADD PRIMARY KEY (`c_sessionLS`),
  ADD KEY `fk_c_drawerLS` (`fk_c_drawerLS`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `SessionLS`
--
ALTER TABLE `SessionLS`
  ADD CONSTRAINT `SessionLS_ibfk_1` FOREIGN KEY (`fk_c_drawerLS`) REFERENCES `DrawerLS` (`c_drawerLS`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
