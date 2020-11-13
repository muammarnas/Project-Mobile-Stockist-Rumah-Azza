-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 16, 2020 at 09:38 AM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 7.0.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ms_rumahazza`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `Username` text NOT NULL,
  `Password` text NOT NULL,
  `Nama` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`Username`, `Password`, `Nama`) VALUES
('admin', 'admin', 'Owner MS Rumah Azza');

-- --------------------------------------------------------

--
-- Table structure for table `barang`
--

CREATE TABLE `barang` (
  `Tanggal` text NOT NULL,
  `Kode_Barang` text NOT NULL,
  `Nama` text NOT NULL,
  `Harga` text NOT NULL,
  `Jumlah` text NOT NULL,
  `ID_User` text NOT NULL,
  `Nama_User` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `barang`
--

INSERT INTO `barang` (`Tanggal`, `Kode_Barang`, `Nama`, `Harga`, `Jumlah`, `ID_User`, `Nama_User`) VALUES
('13/07/2020', 'B0001', 'Tespen', '30000', '93', '', ''),
('13/07/2020', 'B0002', 'Saklar Broco', '20000', '195', '', ''),
('16/07/2020', 'B0003', 'Gergaji', '26000', '29', 'K0001', 'Muammar Nasution');

-- --------------------------------------------------------

--
-- Table structure for table `customers`
--

CREATE TABLE `customers` (
  `ID_Cust` text NOT NULL,
  `Nama` text NOT NULL,
  `No_HP` text NOT NULL,
  `Email` text NOT NULL,
  `Jenis_Kelamin` text NOT NULL,
  `Alamat` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customers`
--

INSERT INTO `customers` (`ID_Cust`, `Nama`, `No_HP`, `Email`, `Jenis_Kelamin`, `Alamat`) VALUES
('C0001', 'Muammar Nasution', '081299647451', 'muammarnasyd@gmail.com', 'Laki-Laki', 'Jakarta'),
('C0002', 'Anton', '123456', 'test@test.com', 'Laki-Laki', 'Jakarta'),
('C0003', 'Andi Firmansyah', '08998909321', 'aloha@plus.com', 'Laki-Laki', 'Jakarta'),
('C0004', 'Aldi', '555', '555@gmail.com', 'Laki-Laki', 'jakarta'),
('C0005', 'demo', '999', 'demo@gmail.com', 'Laki-Laki', 'Jakarta');

-- --------------------------------------------------------

--
-- Table structure for table `laporan_transaksi`
--

CREATE TABLE `laporan_transaksi` (
  `Tanggal` text NOT NULL,
  `Nomor_Transaksi` text NOT NULL,
  `ID_Customers` text NOT NULL,
  `Total` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `laporan_transaksi`
--

INSERT INTO `laporan_transaksi` (`Tanggal`, `Nomor_Transaksi`, `ID_Customers`, `Total`) VALUES
('13/07/2020', 'NJ-000001', 'C0001', '240000'),
('13/07/2020', 'NJ-000002', 'C0003', '230000'),
('13/07/2020', 'NJ-000003', 'C0003', '230000'),
('13/07/2020', 'NJ-000004', 'C0004', '350000'),
('13/07/2020', 'NJ-000005', 'C0003', '230000'),
('13/07/2020', 'NJ-000006', 'C0001', '100000'),
('13/07/2020', 'NJ-000007', 'C0001', '60000'),
('15/07/2020', 'NJ-000008', 'C0005', '70000'),
('16/07/2020', 'NJ-000009', 'C0001', '336000');

-- --------------------------------------------------------

--
-- Table structure for table `pembelian`
--

CREATE TABLE `pembelian` (
  `Tanggal` text NOT NULL,
  `Nama_Barang` text NOT NULL,
  `Kode_Barang` text NOT NULL,
  `Harga` text NOT NULL,
  `QTY` text NOT NULL,
  `Total_Pembayaran` text NOT NULL,
  `ID_Customers` text NOT NULL,
  `Nama_Cust` text NOT NULL,
  `No_HP` text NOT NULL,
  `Nomor_Transaksi` text NOT NULL,
  `ID_User` text NOT NULL,
  `Nama_User` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pembelian`
--

INSERT INTO `pembelian` (`Tanggal`, `Nama_Barang`, `Kode_Barang`, `Harga`, `QTY`, `Total_Pembayaran`, `ID_Customers`, `Nama_Cust`, `No_HP`, `Nomor_Transaksi`, `ID_User`, `Nama_User`) VALUES
('13/07/2020', 'Saklar Broco', 'B0002', '20000', '3', '60000', 'C0001', 'Muammar Nasution', '081299647451', 'NJ-000001', '', ''),
('13/07/2020', 'Tespen', 'B0001', '30000', '3', '90000', 'C0001', 'Muammar Nasution', '081299647451', 'NJ-000001', '', ''),
('13/07/2020', 'Tespen', 'B0001', '30000', '3', '90000', 'C0001', 'Muammar Nasution', '081299647451', 'NJ-000001', '', ''),
('13/07/2020', 'Saklar Broco', 'B0002', '20000', '5', '100000', 'C0003', 'Andi Firmansyah', '08998909321', 'NJ-000002', '', ''),
('13/07/2020', 'Saklar Broco', 'B0002', '20000', '2', '40000', 'C0003', 'Andi Firmansyah', '08998909321', 'NJ-000002', '', ''),
('13/07/2020', 'Tespen', 'B0001', '30000', '3', '90000', 'C0003', 'Andi Firmansyah', '08998909321', 'NJ-000002', '', ''),
('13/07/2020', 'Tespen', 'B0001', '30000', '5', '150000', 'C0004', 'Aldi', '555', 'NJ-000004', '', ''),
('13/07/2020', 'Saklar Broco', 'B0002', '20000', '10', '200000', 'C0004', 'Aldi', '555', 'NJ-000004', '', ''),
('13/07/2020', 'Saklar Broco', 'B0002', '20000', '7', '140000', 'C0003', 'Andi Firmansyah', '08998909321', 'NJ-000005', '', ''),
('13/07/2020', 'Saklar Broco', 'B0002', '20000', '3', '60000', 'C0003', 'Andi Firmansyah', '08998909321', 'NJ-000005', '', ''),
('13/07/2020', 'Tespen', 'B0001', '30000', '1', '30000', 'C0003', 'Andi Firmansyah', '08998909321', 'NJ-000005', '', ''),
('13/07/2020', 'Saklar Broco', 'B0002', '20000', '5', '100000', 'C0001', 'Muammar Nasution', '081299647451', 'NJ-000006', '', ''),
('13/07/2020', 'Saklar Broco', 'B0002', '20000', '3', '60000', 'C0001', 'Muammar Nasution', '081299647451', 'NJ-000007', '', ''),
('15/07/2020', 'Saklar Broco', 'B0002', '20000', '2', '40000', 'C0005', 'demo', '999', 'NJ-000008', '', ''),
('15/07/2020', 'Tespen', 'B0001', '30000', '1', '30000', 'C0005', 'demo', '999', 'NJ-000008', '', ''),
('16/07/2020', 'Gergaji', 'B0003', '26000', '1', '26000', 'C0001', 'Muammar Nasution', '081299647451', 'NJ-000009', 'K0001', 'Muammar Nasution'),
('16/07/2020', 'Saklar Broco', 'B0002', '20000', '5', '100000', 'C0001', 'Muammar Nasution', '081299647451', 'NJ-000009', 'K0001', 'Muammar Nasution'),
('16/07/2020', 'Tespen', 'B0001', '30000', '7', '210000', 'C0001', 'Muammar Nasution', '081299647451', 'NJ-000009', 'K0001', 'Muammar Nasution');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `ID_User` text NOT NULL,
  `Nama` text NOT NULL,
  `No_HP` text NOT NULL,
  `Username` text NOT NULL,
  `Password` text NOT NULL,
  `Jenis_Kelamin` text NOT NULL,
  `Alamat` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`ID_User`, `Nama`, `No_HP`, `Username`, `Password`, `Jenis_Kelamin`, `Alamat`) VALUES
('K0001', 'Muammar Nasution', '081299647451', 'member', 'member', 'Laki-Laki', 'Jakarta');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `barang`
--
ALTER TABLE `barang`
  ADD PRIMARY KEY (`Kode_Barang`(12));

--
-- Indexes for table `customers`
--
ALTER TABLE `customers`
  ADD PRIMARY KEY (`ID_Cust`(12));

--
-- Indexes for table `laporan_transaksi`
--
ALTER TABLE `laporan_transaksi`
  ADD PRIMARY KEY (`Nomor_Transaksi`(12));

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`ID_User`(12));

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
