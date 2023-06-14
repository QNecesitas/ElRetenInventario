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
-- Table structure for table `ProductLS`
--

CREATE TABLE `ProductLS` (
  `c_productLS` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `name` varchar(50) DEFAULT NULL,
  `fk_c_sessionLS` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `amount` int DEFAULT NULL,
  `buyPrice` double DEFAULT NULL,
  `salePrice` double DEFAULT NULL,
  `descr` varchar(100) NOT NULL,
  `statePhoto` tinyint NOT NULL,
  `deficit` int NOT NULL,
  `size` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `brand` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `ProductLS`
--

INSERT INTO `ProductLS` (`c_productLS`, `name`, `fk_c_sessionLS`, `amount`, `buyPrice`, `salePrice`, `descr`, `statePhoto`, `deficit`, `size`, `brand`) VALUES
('109993', 'Retén ', 'Estante Madera_Retenes_Repisa', 1, 0, 1000, '', 0, 0, '120x150x10', 'Repisa'),
('113165', 'Retén ', 'Estante Madera_Retenes_70', 1, 0, 650, '', 0, 0, '25x72x8', '70'),
('113480', '6301', '6300\n6301_Chinas', 5, 375, 650, 'Centros de moto eléctrica, ax100 trasera', 0, 10, '12x37x12', 'China'),
('117479', 'Retén ', 'Estante Madera_Retenes_46', 1, 0, 650, '', 0, 0, '28x42x7', '46'),
('119425', 'Retén ', 'Estante Madera_Retenes_65', 1, 0, 800, '', 0, 0, '48x73x10', '65'),
('127666', 'Reten', 'Estante Madera_Retenes_3', 1, 250, 400, '', 0, 1, '7x16x7', '3'),
('134647', 'Retén ', 'Estante Madera_Retenes_9', 1, 0, 600, '', 0, 0, '20x37x8', '9'),
('136655', 'Retén ', 'Estante Madera_Retenes_Repisa', 2, 0, 1000, '', 0, 0, '90x120x10', 'Repisa'),
('137589', 'Retén ', 'Estante Madera_Retenes_46', 1, 0, 700, '', 0, 0, '28x47x8', '46'),
('139186', 'Retén ', 'Estante Madera_Retenes_59', 1, 0, 550, '', 0, 0, '17x47x8', '59'),
('142167', 'Retén ', 'Estante Madera_Retenes_Repisa', 1, 0, 850, '', 0, 0, '70x92x10', 'Repisa'),
('142856', 'Retén ', 'Estante Madera_Retenes_12', 1, 0, 500, '', 0, 0, '15x32x7', '12'),
('163531', 'Retén ', 'Estante Madera_Retenes_5', 4, 0, 750, '', 0, 0, '16x24x7', '5'),
('166634', 'Retén ', 'Estante Madera_Retenes_67', 2, 0, 850, '', 0, 0, '53x68x10', '67'),
('187732', 'Retén ', 'Estante Madera_Retenes_Repisa', 1, 0, 850, '', 0, 0, '65x90x12', 'Repisa'),
('188179', 'Retén ', 'Estante Madera_Retenes_59', 1, 0, 0, '', 0, 0, '17x47x10', '59'),
('221862', 'Retén ', '74', 1, 0, 700, '', 0, 0, '35.8x68x12', '74'),
('223454', 'Retén ', 'Estante Madera_Retenes_14', 1, 0, 500, '', 0, 0, '15x35x7', '14'),
('232507', 'Retén ', 'Estante Madera_Retenes_69', 2, 0, 700, '', 0, 0, '30x62x8', '69'),
('240398', 'Retén ', 'Estante Madera_Retenes_Repisa', 1, 0, 900, '', 0, 0, '80x105x10', 'Repisa'),
('243150', 'Retén ', 'Estante Madera_Retenes_52', 2, 0, 650, '', 0, 0, '25x50x12', '52'),
('254143', 'Retén ', 'Estante Madera_Retenes_54', 1, 0, 750, '', 0, 0, '41x56x8', '54'),
('256208', 'Retén ', 'Estante Madera_Retenes_49', 1, 0, 500, '', 0, 0, '16x47x10', '49'),
('263820', 'Retén ', 'Estante Madera_Retenes_6', 1, 0, 500, '', 0, 0, '16x30x7', '6'),
('264282', 'Retén ', '82', 1, 0, 900, '', 0, 0, '75x100x10', '82'),
('271428', 'Retén ', 'Estante Madera_Retenes_20', 1, 0, 400, '', 0, 0, '12x22x7', '20'),
('277412', 'Retén ', 'Estante Madera_Retenes_42', 1, 0, 600, '', 0, 0, '22x35x7', '42'),
('279922', 'Retén ', 'Estante Madera_Retenes_Repisa', 1, 0, 700, '', 0, 0, '42x68x8', 'Repisa '),
('283311', '6206', '6206_KFB', 3, 600, 100, 'Cajebola', 0, 5, '30x62x16', 'KFB'),
('283765', 'Retén ', 'Estante Madera_Retenes_27', 1, 0, 700, '', 0, 0, '30x41x7', '27'),
('293527', 'Intermitentes ETZ (Criollo)', 'Accesorios  de motos', 0, 0, 0, '', 0, 0, '', ''),
('302570', '6202', '6202\n_Chinas', 5, 300, 550, 'Cajebola', 0, 10, '15x35x11', 'China'),
('309022', 'Retén ', 'Estante Madera_Retenes_35', 1, 0, 0, '', 0, 0, '18x32x5.5', '35'),
('316744', 'Retén ', 'Estante Madera_Retenes_39', 1, 0, 700, '', 0, 0, '31x41x7', '39'),
('336061', '6000', '6000\n6001\n6002_Chinas', 5, 250, 450, 'Cajebola', 0, 4, '10x26x8', 'China '),
('340502', 'A35', 'A', 0, 0, 0, '', 0, 0, 'A35', 'A35'),
('344668', 'Retén ', '80', 1, 0, 850, '', 0, 0, '55x82x10', '80'),
('364624', '6302', '6302_Chinas', 5, 450, 750, 'Rueda trasera suzuki,taeko,GN125', 0, 10, '15x42x13', 'China'),
('375166', 'Retén ', 'Estante Madera_Retenes_38', 1, 0, 700, '', 0, 0, '31x43x10', '38'),
('376697', 'Retén ', 'Estante Madera_Retenes_51', 2, 0, 700, '', 0, 0, '36x52x10', '51'),
('383143', 'Retén ', '75', 1, 0, 850, '', 0, 0, '60x75x8', '75'),
('389316', '6005', '6005\n6006_Chinas', 5, 520, 850, 'Caña de Suzuki ax100', 0, 19, '25x47x12', 'China'),
('391430', 'Correa', 'Correas_Tipos_A', 0, 0, 0, '', 0, 0, 'A34', 'A34'),
('394853', '6201', '6201_SKF', 5, 500, 850, 'Cajebola', 0, 5, '12x32x10', 'SKF'),
('402558', 'Retén ', 'Estante Madera_Retenes_Repisa', 1, 0, 750, '', 0, 0, '42x68x10', 'Repisa '),
('405344', 'Retén ', 'Estante Madera_Retenes_21', 1, 0, 450, '', 0, 0, '14.8x30x7', '21'),
('424133', 'Retén ', 'Estante Madera_Retenes_34', 1, 0, 650, '', 0, 0, '25x52x7', '34'),
('427435', 'Retén ', 'Estante Madera_Retenes_Repisa', 1, 0, 850, '', 0, 0, '60x60x10', 'Repisa'),
('436461', 'Correas A34', 'A', 0, 0, 0, '', 0, 0, '', 'A34'),
('438708', 'Retén ', 'Estante Madera_Retenes_9', 1, 0, 600, '', 0, 0, '20x47x10', '9'),
('441538', 'Retén ', 'Estante Madera_Retenes_7', 1, 0, 600, '', 0, 0, '20x32x7', '7'),
('441997', 'Retén ', 'Estante Madera_Retenes_55', 2, 0, 750, '', 0, 0, '40x56x8', '55'),
('445969', 'Retén ', 'Estante Madera_Retenes_25', 1, 0, 700, '', 0, 0, '30x40.5x10.5', '25'),
('450987', 'Retén ', 'Estante Madera_Retenes_Repisa', 1, 0, 1000, '', 0, 0, '100x120x10', 'Repisa'),
('471931', 'Retén ', '77', 1, 0, 850, '', 0, 0, '68x95x10', '77'),
('476508', 'Retén ', 'Estante Madera_Retenes_1', 1, 0, 850, '', 0, 0, '68x86x8', '1'),
('480430', 'Retén ', 'Estante Madera_Retenes_16', 1, 0, 600, '', 0, 0, '20x36x7', '16'),
('487106', 'Retén ', 'Estante Madera_Retenes_46', 3, 0, 700, '', 0, 0, '35x45x8', '48'),
('488529', 'Retén ', 'Estante Madera_Retenes_43', 1, 0, 650, '', 0, 0, '26x52x8', '43'),
('494347', 'Retén ', 'Estante Madera_Retenes_60', 1, 0, 700, '', 0, 0, '38x54x8', '60'),
('500113', 'Retén ', 'Estante Madera_Retenes_3', 1, 0, 400, '', 0, 0, '10x20x7', '3'),
('511469', ' 6304', '6304_SKF', 5, 1400, 1800, 'Cigueñal de ax100', 0, 4, '20x52x15', 'SKF'),
('515591', 'Retén ', 'Estante Madera_Retenes_32', 1, 0, 700, '', 0, 0, '30x52x7', '32'),
('519840', 'Retén ', '73', 1, 0, 750, '', 0, 0, '42x58x9', '73'),
('523372', 'Retén ', 'Estante Madera_Retenes_45', 1, 0, 600, '', 0, 0, '20x40x8', '45'),
('525699', 'Retén ', 'Estante Madera_Retenes_10', 1, 0, 600, '', 0, 0, '22x36.5x6', '10'),
('550017', '6201', '6201_Chinas', 5, 300, 550, 'Centro delantero de suzuki ax100 (Banda)', 0, 10, '12x32x10', 'China'),
('556251', 'Retén ', 'Estante Madera_Retenes_8', 1, 0, 550, '', 0, 0, '19.5x35x8', '8'),
('558525', 'Retén ', 'Estante Madera_Retenes_50', 3, 0, 700, '', 0, 0, '30x50x7', '50'),
('571812', 'Retén ', 'Estante Madera_Retenes_13', 1, 0, 550, '', 0, 0, '18.9x30x7', '13'),
('587038', 'Retén ', 'Estante Madera_Retenes_56', 1, 0, 750, '', 0, 0, '44x60x7', '56'),
('591306', 'Retén ', 'Estante Madera_Retenes_30', 1, 0, 650, '', 0, 0, '27x39x7', '30'),
('595561', 'Retén ', '71', 1, 0, 800, '', 0, 0, '50x68x7', '71'),
('600172', 'Retén ', 'Estante Madera_Retenes_61', 1, 0, 700, '', 0, 0, '42x62x10', '61'),
('615908', 'Foco trasero (Criollo)', 'Accesorios  de motos', 0, 0, 0, '', 0, 0, '', 'Largo'),
('629865', 'Retén ', 'Estante Madera_Retenes_29', 1, 0, 700, '', 0, 0, '32x44x10.5', '29'),
('636113', 'Retén ', 'Estante Madera_Retenes_15', 1, 0, 600, '', 0, 0, '19x30x7', '15'),
('638751', 'Retén ', 'Estante Madera_Retenes_40', 1, 0, 550, '', 0, 0, '18x47x7', '40'),
('639810', 'Retén ', 'Estante Madera_Retenes_1', 1, 0, 550, '', 0, 0, '17x32x8', '1'),
('643749', 'Retén ', '76', 1, 0, 800, '', 0, 0, '50x65x10 ', '76'),
('644435', 'Intermitentes ETZ (Criollo)', 'Accesorios  de motos', 0, 0, 0, '', 0, 0, '', ''),
('656413', 'Retén ', 'Estante Madera_Retenes_2', 2, 0, 700, '', 0, 0, '35x47x7', '2'),
('665009', 'Retén ', '79', 1, 0, 850, '', 0, 0, '52x65x8', '79'),
('665466', 'Retén ', 'Estante Madera_Retenes_68', 1, 0, 750, '', 0, 0, '42x75x10', '68'),
('689119', 'Retén ', 'Estante Madera_Retenes_35', 1, 0, 850, '', 0, 0, '60x85x10 ', '35'),
('699249', 'Retén ', 'Estante Madera_Retenes_Repisa', 1, 0, 850, '', 0, 0, '60x90x10', 'Repisa'),
('699812', 'Retén ', 'Estante Madera_Retenes_Repisa', 1, 0, 850, '', 0, 0, '52x68x8', 'Repisa'),
('701437', 'Retén ', 'Estante Madera_Retenes_19', 1, 0, 0, '', 0, 0, '18x37x7', '19'),
('705749', 'Retén ', 'Estante Madera_Retenes_58', 1, 0, 700, '', 0, 0, '33.4x63x10', '58'),
('711177', '6205', '6205_Chinas', 2, 700, 1000, 'Cigueñal Suzuki ', 0, 10, '25x52x15', 'China'),
('719908', 'Foco trasero ETZ (Criollo)', 'Accesorios  de motos', 0, 0, 0, '', 0, 0, '', 'Corto'),
('723126', 'Repisa', 'Estante Madera_Retenes_Repisa', 1, 0, 1000, '', 0, 0, '95x127x14', 'Repisa'),
('728478', 'Retén ', 'Estante Madera_Retenes_41', 1, 0, 650, '', 0, 0, '28x40x7', '41'),
('728534', '6004', '6003\n6004_Chinas', 9, 450, 700, 'Cajebola', 0, 5, '20x42x12', 'China'),
('733892', 'Retén ', 'Estante Madera_Retenes_66', 1, 0, 850, '', 0, 0, '52x72x10', '66'),
('742058', ' 6203', '6203_Chinas', 5, 375, 650, 'Cajebola', 0, 10, '17x40x12', 'China'),
('754135', 'Retén ', '81', 2, 0, 700, '', 0, 0, '32x56x10', '81'),
('755384', 'Retén ', 'Estante Madera_Retenes_36', 1, 0, 850, '', 0, 0, '65x95x10 ', '36'),
('761358', 'Retén ', 'Estante Madera_Retenes_56', 1, 0, 750, '', 0, 0, '40x50', '56'),
('764873', 'Retén ', '87', 2, 0, 1000, '', 0, 0, '110x135x10', '87'),
('767194', 'Retén ', 'Estante Madera_Retenes_28', 1, 0, 700, '', 0, 0, '30x45x8', '28'),
('777104', 'Retén ', 'Estante Madera_Retenes_62', 1, 0, 800, '', 0, 0, '45x65x10', '62'),
('786264', 'Retén ', 'Estante Madera_Retenes_24', 2, 0, 600, '', 0, 0, '22x34.5x5.5', '24'),
('789949', 'Retén ', 'Estante Madera_Retenes_47', 1, 0, 700, '', 0, 0, '32x43x8', '47'),
('793425', 'Retén ', '86', 1, 0, 1000, '', 0, 0, '110x130x10', '86'),
('797277', 'Retén ', 'Estante Madera_Retenes_Repisa', 2, 0, 850, '', 0, 0, '70x90x10', 'Repisa'),
('799983', 'Retén ', 'Estante Madera_Retenes_17', 1, 0, 600, '', 0, 0, '20x30x7 ', '17'),
('804665', '6205', '6205_KFB', 12, 1000, 1800, 'Cajebola', 0, 4, '', 'KFB'),
('810398', 'Retén ', 'Estante Madera_Retenes_63', 1, 0, 650, '', 0, 0, '25x62x8', '63'),
('812400', 'Retén ', 'Estante Madera_Retenes_8', 1, 0, 600, '', 0, 0, '20x35x10', '8'),
('823224', 'Retén ', 'Estante Madera_Retenes_28', 1, 0, 600, '', 0, 0, '22x47x7', '28'),
('829663', 'Retén ', 'Estante Madera_Retenes_33', 1, 0, 700, '', 0, 0, '35x50x8', '33'),
('839821', 'Bomba de Freno ', 'Accesorios  de motos', 0, 0, 0, '', 0, 0, '', 'GN125'),
('842640', 'Retén ', 'Estante Madera_Retenes_3', 1, 0, 550, '', 0, 0, '17x30x7', '3'),
('851743', 'Retén ', 'Estante Madera_Retenes_Repisa', 1, 0, 850, '', 0, 0, '70x95x10', 'Repisa'),
('855348', 'Retén ', 'Estante Madera_Retenes_57', 1, 0, 750, '', 0, 0, '45x62x8', '57'),
('867201', 'Retén ', 'Estante Madera_Retenes_44', 1, 0, 650, '', 0, 0, '25x48x8', '44'),
('876027', 'Retén ', 'Estante Madera_Retenes_41', 1, 0, 550, '', 0, 0, '18x48x10', '41'),
('891855', 'Retén ', 'Estante Madera_Retenes_Repisa', 1, 0, 750, '', 0, 0, '42x64x8', 'Repisa '),
('891957', 'Retén ', 'Estante Madera_Retenes_4', 1, 0, 500, '', 0, 0, '15x25x6', '4'),
('897161', 'Retén ', 'Estante Madera_Retenes_37', 1, 0, 650, '', 0, 0, '25x40x8', '37'),
('901777', ' 6303', '6303_Chinas', 5, 500, 900, 'Bomba de agua Mosckovich', 0, 5, '17x47x14', 'China'),
('920822', 'Retén ', 'Estante Madera_Retenes_8', 1, 0, 650, 'Piñón de salida del TS - ETZ (5ta)', 0, 0, '25x35x7', '8'),
('921993', 'Reten ', 'Estante Madera_Retenes_3', 1, 250, 400, '', 0, 0, '8x16x7', '3'),
('924753', 'Retén ', 'Estante Madera_Retenes_18', 1, 0, 500, '', 0, 0, '15x30x7', '18'),
('931335', 'Retén ', 'Estante Madera_Retenes_37', 1, 0, 750, '', 0, 0, '39.5x52x10', '37'),
('934060', 'Retén ', 'Repisa', 1, 0, 850, '', 0, 0, '65x90x10', 'Repisa'),
('940190', 'Retén ', 'Estante Madera_Retenes_26', 1, 0, 700, '', 0, 0, '30x42x7', '26'),
('940399', 'Retén ', 'Estante Madera_Retenes_31', 1, 0, 650, '', 0, 0, '27x40x6', '31'),
('948276', 'Correa A36', 'A', 0, 0, 0, '', 0, 0, 'A36', 'A36'),
('950958', 'Retén ', 'Estante Madera_Retenes_36', 1, 0, 600, '', 0, 0, '20x34x6', '36'),
('951644', 'Retén ', 'Estante Madera_Retenes_64', 1, 0, 800, '', 0, 0, '50x70x10', '64'),
('961757', 'Retén ', 'Estante Madera_Retenes_8', 1, 0, 600, '', 0, 0, '24x35x7', '8'),
('962143', 'Retén ', 'Estante Madera_Retenes_11', 1, 0, 500, '', 0, 0, '15x25.5x7', '11'),
('962867', 'Retén ', 'Estante Madera_Retenes_23', 1, 0, 0, '', 0, 0, '18x30x7', '23'),
('964405', 'A35', 'Correas_Tipos_A', 0, 0, 0, '', 0, 0, 'A35', 'Correa'),
('968284', 'Retén ', 'Estante Madera_Retenes_61', 1, 0, 700, '', 0, 0, '42x62x8', '61'),
('977870', 'Retén ', '85', 2, 0, 1000, '', 0, 0, '80x100', '85'),
('991393', '6204', '6204_KFB', 5, 700, 1200, 'Cajebola', 0, 10, '20x47x14', 'KFB'),
('997257', 'Retén ', '72', 1, 0, 750, '', 0, 0, '40x52x7', '72');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ProductLS`
--
ALTER TABLE `ProductLS`
  ADD PRIMARY KEY (`c_productLS`),
  ADD KEY `fk_c_sessionLS` (`fk_c_sessionLS`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
