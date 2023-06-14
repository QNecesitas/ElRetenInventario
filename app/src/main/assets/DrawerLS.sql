-- phpMyAdmin SQL Dump
-- version 4.9.5deb2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 10, 2023 at 11:07 PM
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
-- Table structure for table `DrawerLS`
--

CREATE TABLE `DrawerLS` (
  `c_drawerLS` varchar(200) NOT NULL,
  `fk_c_shelfLS` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `amount` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `DrawerLS`
--

INSERT INTO `DrawerLS` (`c_drawerLS`, `fk_c_shelfLS`, `amount`) VALUES
('6000\n6001\n6002', 'Estante Metal', 3),
('6003\n6004', 'Estante Metal', 3),
('6005\n6006', 'Estante Metal', 3),
('6007', 'Estante Metal', 3),
('607\n608\n6200', 'Estante Metal', 3),
('6201', 'Estante Metal', 3),
('6202\n', 'Estante Metal', 3),
('6203', 'Estante Metal', 3),
('6204', 'Estante Metal', 3),
('6205', 'Estante Metal', 3),
('6206', 'Estante Metal', 3),
('6207', 'Estante Metal', 3),
('6300\n6301', 'Estante Metal', 3),
('6302', 'Estante Metal', 3),
('6303', 'Estante Metal', 3),
('6304', 'Estante Metal', 3),
('6305', 'Estante Metal', 3),
('6306', 'Estante Metal', 3),
('Correas_Tipos', 'Correas', 1),
('Estante Madera_Arriba', 'Estante Madera', 1),
('Estante Madera_Retenes', 'Estante Madera', 85),
('Estante Metal_6006', 'Estante Metal', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `DrawerLS`
--
ALTER TABLE `DrawerLS`
  ADD PRIMARY KEY (`c_drawerLS`),
  ADD KEY `fk_shelfLS` (`fk_c_shelfLS`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `DrawerLS`
--
ALTER TABLE `DrawerLS`
  ADD CONSTRAINT `DrawerLS_ibfk_1` FOREIGN KEY (`fk_c_shelfLS`) REFERENCES `ShelfLS` (`c_shelfLS`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
