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
-- Table structure for table `ProductS`
--

CREATE TABLE `ProductS` (
  `c_productS` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `fk_c_sessionS` varchar(200) DEFAULT NULL,
  `amount` int DEFAULT NULL,
  `buyPrice` double DEFAULT NULL,
  `salePrice` double DEFAULT NULL,
  `descr` varchar(250) NOT NULL,
  `statePhoto` tinyint DEFAULT NULL,
  `deficit` int NOT NULL,
  `size` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `brand` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `ProductS`
--

INSERT INTO `ProductS` (`c_productS`, `name`, `fk_c_sessionS`, `amount`, `buyPrice`, `salePrice`, `descr`, `statePhoto`, `deficit`, `size`, `brand`) VALUES
('100636', 'Cachimbas Criollas', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'Cachimbas'),
('109993', 'Retén ', 'Retenes', 4, 0, 1000, '', 0, 0, '120x150x10', 'Repisa'),
('113165', 'Retén ', 'Retenes', 4, 0, 650, '', 0, 0, '25x72x8', '70'),
('113480', '6301', 'Cajebolas', 104, 375, 650, 'Centros de moto eléctrica, ax100 trasera', 0, 10, '12x37x12', 'China'),
('117479', 'Retén ', 'Retenes', 4, 0, 650, '', 0, 0, '28x42x7', '46'),
('119425', 'Retén ', 'Retenes', 4, 0, 800, '', 0, 0, '48x73x10', '65'),
('120160', 'Conmutador Integral Negro ', 'Accesorios de Motos', 5, 0, 0, '', 0, 0, '', 'Negro'),
('127666', 'Reten', 'Retenes', 3, 250, 400, '', 0, 1, '7x16x7', '3'),
('134647', 'Retén ', 'Retenes', 1, 0, 600, '', 0, 0, '20x37x8', '9'),
('136655', 'Retén ', 'Retenes', 3, 0, 1000, '', 0, 0, '90x120x10', 'Repisa'),
('137589', 'Retén ', 'Retenes', 4, 0, 700, '', 1, 0, '28x47x8', '46'),
('139186', 'Retén ', 'Retenes', 1, 0, 550, '', 0, 0, '17x47x8', '59'),
('142167', 'Retén ', 'Retenes', 4, 0, 850, '', 0, 0, '70x92x10', 'Repisa'),
('142856', 'Retén ', 'Retenes', 1, 0, 500, '', 0, 0, '15x32x7', '12'),
('143534', 'Retén ', 'Retenes', 5, 0, 700, '', 0, 0, '35x8x58', 'nono'),
('163531', 'Retén ', 'Retenes', 6, 0, 750, '', 0, 0, '16x24x7', '5'),
('166634', 'Retén ', 'Retenes', 3, 0, 850, '', 0, 0, '53x68x10', '67'),
('172274', 'Cable Criollo', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'Cable Criollo'),
('181436', 'Calzo Espro Criollo ETZ', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'ETZ'),
('184609', 'Varilla Freno ETZ (Criolla', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'ETZ Criolla'),
('185396', 'Espejos de ETZ', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'ETZ'),
('187732', 'Retén ', 'Retenes', 4, 0, 850, '', 0, 0, '65x90x12', 'Repisa'),
('188179', 'Retén ', 'Retenes', 1, 0, 0, '', 0, 0, '17x47x10', '59'),
('199770', 'Conmutadores  AX100', 'Accesorios de Motos', 5, 0, 0, '', 0, 0, '', 'AX100'),
('213964', 'Tensores GN-125', 'Accesorios de Motos', 5, 0, 0, '', 0, 0, '', 'Tensores Cadena'),
('221862', 'Retén ', 'Retenes', 4, 0, 700, '', 0, 0, '35.8x68x12', '74'),
('223454', 'Retén ', 'Retenes', 1, 0, 500, '', 0, 0, '15x35x7', '14'),
('232507', 'Retén ', 'Retenes', 3, 0, 700, '', 0, 0, '30x62x8', '69'),
('240398', 'Retén ', 'Retenes', 4, 0, 900, '', 0, 0, '80x105x10', 'Repisa'),
('243150', 'Retén ', 'Retenes', 3, 0, 650, '', 0, 0, '25x50x12', '52'),
('254143', 'Retén ', 'Retenes', 4, 0, 750, '', 0, 0, '41x56x8', '54'),
('256208', 'Retén ', 'Retenes', 1, 0, 500, '', 0, 0, '16x47x10', '49'),
('263820', 'Retén ', 'Retenes', 1, 0, 500, '', 0, 0, '16x30x7', '6'),
('264282', 'Retén ', 'Retenes', 4, 0, 900, '', 0, 0, '75x100x10', '82'),
('271428', 'Retén ', 'Retenes', 1, 0, 400, '', 0, 0, '12x22x7', '20'),
('274910', 'Intermitentes ', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'GN125'),
('277412', 'Retén ', 'Retenes', 1, 0, 600, '', 0, 0, '22x35x7', '42'),
('279922', 'Retén ', 'Retenes', 4, 0, 700, '', 0, 0, '42x68x8', 'Repisa '),
('283311', '6206', 'Cajebolas', 13, 600, 100, 'Cajebola', 0, 5, '30x62x16', 'KFB'),
('283765', 'Retén ', 'Retenes', 9, 0, 700, '', 0, 0, '30x41x7', '27'),
('302570', '6202', 'Cajebolas', 285, 300, 550, 'Cajebola', 0, 10, '15x35x11', 'China'),
('309022', 'Retén ', 'Retenes', 1, 0, 0, '', 0, 0, '18x32x5.5', '35'),
('316744', 'Retén ', 'Retenes', 4, 0, 700, '', 0, 0, '31x41x7', '39'),
('333629', 'Correa A38', 'Correas', 0, 0, 0, '', 0, 0, '', 'A38'),
('336061', '6000', 'Cajebolas', 15, 250, 450, 'Cajebola', 0, 4, '10x26x8', 'China '),
('344668', 'Retén ', 'Retenes', 4, 0, 850, '', 0, 0, '55x82x10', '80'),
('364624', '6302', 'Cajebolas', 115, 450, 750, 'Rueda trasera suzuki,taeko,GN125', 0, 10, '15x42x13', 'China'),
('368314', 'Cable cloche de ax100 original', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'AX100 '),
('375166', 'Retén ', 'Retenes', 4, 0, 700, '', 0, 0, '31x43x10', '38'),
('376697', 'Retén ', 'Retenes', 3, 0, 700, '', 0, 0, '36x52x10', '51'),
('383143', 'Retén ', 'Retenes', 4, 0, 850, '', 0, 0, '60x75x8', '75'),
('385496', 'Vaper - CDI de AX00', 'Accesorios de Motos', 5, 0, 0, '', 0, 0, '', 'Con Cachimba'),
('388754', 'Correa A39', 'Correas', 0, 0, 0, '', 0, 0, '', 'A39'),
('389316', '6005', 'Cajebolas', 61, 520, 850, 'Caña de Suzuki ax100', 0, 19, '25x47x12', 'China'),
('394853', '6201', 'Cajebolas', 7, 500, 850, 'Cajebola', 0, 5, '12x32x10', 'SKF'),
('396305', 'Correa A37', 'Correas', 0, 0, 0, '', 0, 0, '', 'A37'),
('402558', 'Retén ', 'Retenes', 4, 0, 750, '', 0, 0, '42x68x10', 'Repisa '),
('405344', 'Retén ', 'Retenes', 1, 0, 450, '', 0, 0, '14.8x30x7', '21'),
('406054', 'Filtro aceite de GN125', 'Accesorios de Motos', 15, 300, 550, 'Filtro de aceite de GN125', 0, 5, '', 'Filtro aceite GN'),
('421325', 'Cachimbas Originales ', 'Accesorios de Motos', 5, 0, 0, '', 0, 0, '', 'Cachimbas '),
('424133', 'Retén ', 'Retenes', 4, 0, 650, '', 0, 0, '25x52x7', '34'),
('427435', 'Retén ', 'Retenes', 4, 0, 850, '', 0, 0, '60x60x10', 'Repisa'),
('438708', 'Retén ', 'Retenes', 1, 0, 600, '', 0, 0, '20x47x10', '9'),
('441538', 'Retén ', 'Retenes', 1, 0, 600, '', 0, 0, '20x32x7', '7'),
('441997', 'Retén ', 'Retenes', 3, 0, 750, '', 0, 0, '40x56x8', '55'),
('443158', '30305', 'Rolletes', 54, 600, 1500, 'Chiquito de Gazella y Borga', 0, 5, '', 'AGL'),
('445969', 'Retén ', 'Retenes', 4, 0, 700, '', 0, 0, '30x40.5x10.5', '25'),
('450987', 'Retén ', 'Retenes', 4, 0, 1000, '', 0, 0, '100x120x10', 'Repisa'),
('469883', 'Intermitentes Criollos Cuadrados', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'ETZ'),
('471931', 'Retén ', 'Retenes', 4, 0, 850, '', 0, 0, '68x95x10', '77'),
('476508', 'Retén ', 'Retenes', 4, 0, 850, '', 0, 0, '68x86x8', '1'),
('480430', 'Retén ', 'Retenes', 1, 0, 600, '', 0, 0, '20x36x7', '16'),
('487106', 'Retén ', 'Retenes', 2, 0, 700, '', 0, 0, '35x45x8', '48'),
('488529', 'Retén ', 'Retenes', 4, 0, 650, '', 0, 0, '26x52x8', '43'),
('494347', 'Retén ', 'Retenes', 4, 0, 700, '', 0, 0, '38x54x8', '60'),
('495546', 'Correa A36', 'Correas', 0, 0, 0, '', 0, 0, '', 'A36'),
('500113', 'Retén ', 'Retenes', 1, 0, 400, '', 0, 0, '10x20x7', '3'),
('505065', 'Filtro de aceite LADA(Grande)', 'Accesorios de Motos', 10, 1200, 1500, 'Filtro grande', 0, 2, '', ''),
('511469', ' 6304', 'Cajebolas', 15, 1400, 1800, 'Cigueñal de ax100', 0, 4, '20x52x15', 'SKF'),
('515591', 'Retén ', 'Retenes', 4, 0, 700, '', 0, 0, '30x52x7', '32'),
('516399', 'Retén ', 'Retenes', 5, 0, 850, '', 0, 0, '51x76x10', 'Repisa'),
('519840', 'Retén ', 'Retenes', 4, 0, 750, '', 0, 0, '42x58x9', '73'),
('523372', 'Retén ', 'Retenes', 1, 0, 600, '', 0, 0, '20x40x8', '45'),
('525699', 'Retén ', 'Retenes', 1, 0, 600, '', 0, 0, '22x36.5x6', '10'),
('532633', 'Anillas Tubo de Escape', 'Accesorios de Motos', 5, 0, 0, '', 0, 0, '', 'AX100'),
('550017', '6201', 'Cajebolas', 128, 300, 550, 'Centro delantero de suzuki ax100 (Banda)', 0, 10, '12x32x10', 'China'),
('556251', 'Retén ', 'Retenes', 1, 0, 550, '', 0, 0, '19.5x35x8', '8'),
('557767', 'Espejos de AX100', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'AX100'),
('558525', 'Retén ', 'Retenes', 2, 0, 700, '', 0, 0, '30x50x7', '50'),
('571812', 'Retén ', 'Retenes', 3, 0, 550, '', 0, 0, '18.9x30x7', '13'),
('584585', 'Retén ', 'Retenes', 2, 0, 500, '', 0, 0, '15x25x7', '4'),
('586761', 'Llave de Gasolina AX100', 'Accesorios de Motos', 5, 0, 0, '', 1, 0, '', ''),
('587038', 'Retén ', 'Retenes', 4, 0, 750, '', 0, 0, '44x60x7', '56'),
('591306', 'Retén ', 'Retenes', 4, 0, 650, '', 0, 0, '27x39x7', '30'),
('595561', 'Retén ', 'Retenes', 4, 0, 800, '', 0, 0, '50x68x7', '71'),
('600172', 'Retén ', 'Retenes', 4, 0, 700, '', 0, 0, '42x62x10', '61'),
('629865', 'Retén ', 'Retenes', 4, 0, 700, '', 0, 0, '32x44x10.5', '29'),
('636113', 'Retén ', 'Retenes', 1, 0, 600, '', 0, 0, '19x30x7', '15'),
('638751', 'Retén ', 'Retenes', 1, 0, 550, '', 0, 0, '18x47x7', '40'),
('639810', 'Retén ', 'Retenes', 1, 0, 550, '', 0, 0, '17x32x8', '1'),
('643749', 'Retén ', 'Retenes', 4, 0, 800, '', 0, 0, '50x65x10 ', '76'),
('646302', 'Cable de Freno AX100', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'AX100'),
('651897', 'Foco trasero', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'AX100'),
('656413', 'Retén ', 'Retenes', 3, 0, 700, '', 0, 0, '35x47x7', '2'),
('659517', 'Encendido de GN125', 'Accesorios de Motos', 3, 7200, 10000, 'GN125', 0, 2, '', 'GN125'),
('665009', 'Retén ', 'Retenes', 4, 0, 850, '', 0, 0, '52x65x8', '79'),
('665462', 'Foco delantero', 'Accesorios de Motos', 5, 0, 3500, '', 0, 2, '', 'AX100'),
('665466', 'Retén ', 'Retenes', 4, 0, 750, '', 0, 0, '42x75x10', '68'),
('666437', 'Tensores Cadena', 'Accesorios de Motos', 5, 0, 0, '', 1, 0, '', 'AX100'),
('679552', 'Conmutador Integral Gris', 'Accesorios de Motos', 5, 0, 0, '', 0, 0, '', 'Gris'),
('689119', 'Retén ', 'Retenes', 2, 0, 850, '', 0, 0, '60x85x10 ', '35'),
('699249', 'Retén ', 'Retenes', 4, 0, 850, '', 0, 0, '60x90x10', 'Repisa'),
('699812', 'Retén ', 'Retenes', 4, 0, 850, '', 0, 0, '52x68x8', 'Repisa'),
('701437', 'Retén ', 'Retenes', 4, 0, 0, '', 0, 0, '18x37x7', '19'),
('702769', 'Filtro de gasolina', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'Filtro Gasolina'),
('705749', 'Retén ', 'Retenes', 2, 0, 700, '', 0, 0, '33.4x63x10', '58'),
('710755', 'llave gasolina Karpaty ', 'Accesorios de Motos', 5, 0, 0, '', 0, 0, '', ''),
('711177', '6205', 'Cajebolas', 12, 700, 1000, 'Cigueñal Suzuki ', 0, 10, '25x52x15', 'China'),
('723126', 'Repisa', 'Retenes', 4, 0, 1000, '', 0, 0, '95x127x14', 'Repisa'),
('723591', 'Correa A40', 'Correas', 0, 0, 0, '', 0, 0, '', 'A40'),
('723788', 'Vaper - CDI de AX00', 'Accesorios de Motos', 5, 0, 0, '', 0, 0, '', 'Sin Cachimba'),
('728478', 'Retén ', 'Retenes', 3, 0, 650, '', 0, 0, '28x40x7', '41'),
('728534', '6004', 'Cajebolas', 33, 450, 700, 'Cajebola', 0, 5, '20x42x12', 'China'),
('733892', 'Retén ', 'Retenes', 3, 0, 850, '', 0, 0, '52x72x10', '66'),
('742058', ' 6203', 'Cajebolas', 68, 375, 650, 'Cajebola', 0, 10, '17x40x12', 'China'),
('747601', 'Intermitentes', 'Accesorios de Motos', 4, 2600, 3200, 'Ax100', 0, 2, '', 'AX100'),
('754135', 'Retén ', 'Retenes', 3, 0, 700, '', 0, 0, '32x56x10', '81'),
('755384', 'Retén ', 'Retenes', 2, 0, 850, '', 0, 0, '65x95x10 ', '36'),
('761358', 'Retén ', 'Retenes', 3, 0, 750, '', 0, 0, '40x50', '56'),
('763749', 'Manilla Cloche ETZ', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'Manilla ETZ'),
('764873', 'Retén ', 'Retenes', 3, 0, 1000, '', 0, 0, '110x135x10', '87'),
('767194', 'Retén ', 'Retenes', 3, 0, 700, '', 0, 0, '30x45x8', '28'),
('777104', 'Retén ', 'Retenes', 2, 0, 800, '', 0, 0, '45x65x10', '62'),
('786264', 'Retén ', 'Retenes', 3, 0, 600, '', 0, 0, '22x34.5x5.5', '24'),
('789949', 'Retén ', 'Retenes', 3, 0, 700, '', 0, 0, '32x43x8', '47'),
('791548', 'Bomba de freno GN125', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'GN125'),
('793425', 'Retén ', 'Retenes', 4, 0, 1000, '', 0, 0, '110x130x10', '86'),
('797277', 'Retén ', 'Retenes', 3, 0, 850, '', 0, 0, '70x90x10', 'Repisa'),
('798417', 'Intermitentes Criollos Redondos  ', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'ETZ'),
('799983', 'Retén ', 'Retenes', 1, 0, 600, '', 0, 0, '20x30x7 ', '17'),
('800415', 'Cable Cuentamillas GN125', 'Accesorios de Motos', 3, 700, 1000, 'Cuentamillas de GN125', 0, 0, '', 'GN125 '),
('804665', '6205', 'Cajebolas', 3, 1000, 1800, 'Cajebola', 0, 4, '25x52x15', 'KFB'),
('805783', 'Conmutadores AX100-2', 'Accesorios de Motos', 5, 0, 0, '', 0, 0, '', 'AX100 - 2'),
('810398', 'Retén ', 'Retenes', 4, 0, 650, '', 0, 0, '25x62x8', '63'),
('812400', 'Retén ', 'Retenes', 1, 0, 600, '', 0, 0, '20x35x10', '8'),
('813783', 'Cadena Reforzada 132', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', '132 eslabón '),
('818342', 'Correa A35', 'Correas', 0, 0, 0, '', 0, 0, '', 'A35'),
('820321', 'Comnumtador Integral Gris', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'Gris'),
('823224', 'Retén ', 'Retenes', 1, 0, 600, '', 0, 0, '22x47x7', '28'),
('829663', 'Retén ', 'Retenes', 4, 0, 700, '', 0, 0, '35x50x8', '33'),
('842640', 'Retén ', 'Retenes', 1, 0, 550, '', 0, 0, '17x30x7', '3'),
('851743', 'Retén ', 'Retenes', 4, 0, 850, '', 0, 0, '70x95x10', 'Repisa'),
('852849', 'Calzo Espro Criollo Suzuki ', 'Accesorios de Motos', 5, 150, 300, '', 0, 0, '', 'Suzuki '),
('855348', 'Retén ', 'Retenes', 4, 0, 750, '', 0, 0, '45x62x8', '57'),
('857225', 'Cable Criollo Colores', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'Cable colores'),
('864333', 'Cable de cloche de GN125', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'GN125'),
('867201', 'Retén ', 'Retenes', 4, 0, 650, '', 0, 0, '25x48x8', '44'),
('869483', 'Relay de Arranque 4t', 'Accesorios de Motos', 8, 800, 1500, 'Relay', 0, 3, '', 'Relay de Arranque '),
('876027', 'Retén ', 'Retenes', 1, 0, 550, '', 0, 0, '18x48x10', '41'),
('891855', 'Retén ', 'Retenes', 4, 0, 750, '', 0, 0, '42x64x8', 'Repisa '),
('891957', 'Retén ', 'Retenes', 1, 0, 500, '', 0, 0, '15x25x6', '4'),
('897161', 'Retén ', 'Retenes', 4, 0, 650, '', 0, 0, '25x40x8', '37'),
('901777', ' 6303', 'Cajebolas', 15, 500, 900, 'Bomba de agua Mosckovich', 0, 5, '17x47x14', 'China'),
('920822', 'Retén ', 'Retenes', 4, 0, 650, 'Piñón de salida del TS - ETZ (5ta)', 0, 0, '25x35x7', '8'),
('921993', 'Reten ', 'Retenes', 4, 250, 400, '', 0, 0, '8x16x7', '3'),
('924753', 'Retén ', 'Retenes', 1, 0, 500, '', 0, 0, '15x30x7', '18'),
('931335', 'Retén ', 'Retenes', 4, 0, 750, '', 0, 0, '39.5x52x10', '37'),
('934060', 'Retén ', 'Retenes', 4, 0, 850, '', 0, 0, '65x90x10', 'Repisa'),
('935674', 'Manifor de AX100', 'Accesorios de Motos', 1, 700, 1200, 'Manifor de AX100', 0, 0, '', 'AX100'),
('938949', '6006', 'Cajebolas', 17, 600, 900, 'Cajebola', 0, 5, '30x55x13', 'China'),
('940190', 'Retén ', 'Retenes', 4, 0, 700, '', 0, 0, '30x42x7', '26'),
('940399', 'Retén ', 'Retenes', 4, 0, 650, '', 0, 0, '27x40x6', '31'),
('950958', 'Retén ', 'Retenes', 1, 0, 600, '', 0, 0, '20x34x6', '36'),
('951644', 'Retén ', 'Retenes', 4, 0, 800, '', 0, 0, '50x70x10', '64'),
('961757', 'Retén ', 'Retenes', 4, 0, 600, '', 0, 0, '24x35x7', '8'),
('962143', 'Retén ', 'Retenes', 1, 0, 500, '', 0, 0, '15x25.5x7', '11'),
('962867', 'Retén ', 'Retenes', 1, 0, 0, '', 0, 0, '18x30x7', '23'),
('966089', 'Cadena Reforzada AX100', 'Accesorios de Motos', 0, 0, 0, '', 0, 0, '', 'Cadena AX100'),
('968284', 'Retén ', 'Retenes', 4, 0, 700, '', 0, 0, '42x62x8', '61'),
('977870', 'Retén ', 'Retenes', 3, 0, 1000, '', 0, 0, '80x100', '85'),
('991393', '6204', 'Cajebolas', 108, 700, 1200, 'Cajebola', 0, 10, '20x47x14', 'KFB'),
('997257', 'Retén ', 'Retenes', 4, 0, 750, '', 0, 0, '40x52x7', '72');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ProductS`
--
ALTER TABLE `ProductS`
  ADD PRIMARY KEY (`c_productS`),
  ADD KEY `fk_c_sessionS` (`fk_c_sessionS`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `ProductS`
--
ALTER TABLE `ProductS`
  ADD CONSTRAINT `ProductS_ibfk_1` FOREIGN KEY (`fk_c_sessionS`) REFERENCES `SessionS` (`c_sessionS`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
