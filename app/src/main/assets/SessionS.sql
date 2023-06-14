-- phpMyAdmin SQL Dump
-- version 4.9.5deb2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Jun 10, 2023 at 11:03 PM
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
-- Table structure for table `SessionS`
--

CREATE TABLE `SessionS` (
  `c_sessionS` varchar(200) NOT NULL,
  `fk_c_drawerS` varchar(200) DEFAULT NULL,
  `amount` int DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `SessionS`
--

INSERT INTO `SessionS` (`c_sessionS`, `fk_c_drawerS`, `amount`) VALUES
('Accesorios de Motos', 'Inventario', 41),
('Cajebolas', 'Inventario', 16),
('Correas', 'Inventario', 6),
('Cuarto_Accesorios de motos _AX100', 'Cuarto_Accesorios de motos ', 0),
('Cuarto_Accesorios de motos _Electricidad ', 'Cuarto_Accesorios de motos ', 0),
('Cuarto_Accesorios de motos _ETZ - MZ 250', 'Cuarto_Accesorios de motos ', 0),
('Cuarto_Accesorios de motos _GN - 125', 'Cuarto_Accesorios de motos ', 0),
('Cuarto_Accesorios de motos _Taeko ', 'Cuarto_Accesorios de motos ', 0),
('Inventario_Pqte de Retenes', 'Inventario', 0),
('Retenes', 'Inventario', 118),
('Rolletes', 'Inventario', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `SessionS`
--
ALTER TABLE `SessionS`
  ADD PRIMARY KEY (`c_sessionS`),
  ADD KEY `fk_c_drawerS` (`fk_c_drawerS`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `SessionS`
--
ALTER TABLE `SessionS`
  ADD CONSTRAINT `SessionS_ibfk_1` FOREIGN KEY (`fk_c_drawerS`) REFERENCES `DrawerS` (`c_drawerS`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
