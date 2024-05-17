-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 17, 2024 at 05:57 AM
-- Server version: 10.4.21-MariaDB
-- PHP Version: 7.3.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `uas_bad`
--

-- --------------------------------------------------------

--
-- Table structure for table `products`
--

CREATE TABLE `products` (
  `id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `description` text NOT NULL,
  `price` int(11) NOT NULL,
  `qty` int(11) DEFAULT 0,
  `category` enum('DEVICE','ACC') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `products`
--

INSERT INTO `products` (`id`, `name`, `description`, `price`, `qty`, `category`) VALUES
(1, 'ASUS ROG G531GT', 'intel core i7 95430H', 12000000, 13, 'DEVICE'),
(2, 'ROBOT charger cable 1m ', 'ini adalah kabel cas', 10000, 12, 'ACC');

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `id` int(11) NOT NULL,
  `productId` int(11) NOT NULL,
  `qty` int(11) NOT NULL,
  `type` enum('SELL','BUY') NOT NULL,
  `timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`id`, `productId`, `qty`, `type`, `timestamp`) VALUES
(1, 1, 2, 'SELL', '2024-05-15 11:03:39'),
(2, 1, 1, 'SELL', '2024-05-15 12:27:05'),
(3, 1, 1, 'SELL', '2024-05-15 12:41:15'),
(4, 1, 1, 'SELL', '2024-05-15 13:10:47'),
(5, 1, 1, 'SELL', '2024-05-15 15:35:49'),
(6, 1, 1, 'SELL', '2024-05-15 16:00:49'),
(7, 1, 1, 'SELL', '2024-05-15 16:22:53'),
(8, 1, 1, 'SELL', '2024-05-15 16:26:34'),
(9, 1, 1, 'SELL', '2024-05-15 16:32:48'),
(10, 1, 1, 'SELL', '2024-05-16 11:07:17'),
(11, 1, 10, 'BUY', '2024-05-16 14:34:18'),
(12, 2, 12, 'BUY', '2024-05-16 17:58:46');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `products`
--
ALTER TABLE `products`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `products`
--
ALTER TABLE `products`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
