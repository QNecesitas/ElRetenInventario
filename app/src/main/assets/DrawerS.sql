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
-- Table structure for table `DrawerS`
--

CREATE TABLE `DrawerS` (
  `c_drawerS` varchar(200) NOT NULL,
  `fk_c_shelfS` varchar(200) DEFAULT NULL,
  `amount` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `DrawerS`
--

INSERT INTO `DrawerS` (`c_drawerS`, `fk_c_shelfS`, `amount`) VALUES
('Cuarto_Accesorios de motos ', 'Cuarto', 5),
('Inventario', 'Cuarto', 6);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `DrawerS`
--
ALTER TABLE `DrawerS`
  ADD PRIMARY KEY (`c_drawerS`),
  ADD KEY `fk_c_shelfS` (`fk_c_shelfS`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `DrawerS`
--
ALTER TABLE `DrawerS`
  ADD CONSTRAINT `DrawerS_ibfk_1` FOREIGN KEY (`fk_c_shelfS`) REFERENCES `ShelfS` (`c_shelfS`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
