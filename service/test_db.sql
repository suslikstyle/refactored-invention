-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Хост: localhost
-- Время создания: Мар 15 2020 г., 15:12
-- Версия сервера: 8.0.11
-- Версия PHP: 7.3.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `test_db`
--

-- --------------------------------------------------------

--
-- Структура таблицы `data_table`
--

CREATE TABLE `data_table` (
  `id` int(11) UNSIGNED NOT NULL,
  `company_name` char(32) COLLATE utf8_unicode_ci NOT NULL,
  `tax_number` char(12) COLLATE utf8_unicode_ci NOT NULL,
  `phone_number` char(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` char(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `first_name` char(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `surname` char(20) COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Дамп данных таблицы `data_table`
--

INSERT INTO `data_table` (`id`, `company_name`, `tax_number`, `phone_number`, `address`, `first_name`, `surname`) VALUES
(1, 'name', '000', NULL, NULL, NULL, NULL);

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `data_table`
--
ALTER TABLE `data_table`
  ADD UNIQUE KEY `tax` (`tax_number`),
  ADD KEY `id` (`id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `data_table`
--
ALTER TABLE `data_table`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
