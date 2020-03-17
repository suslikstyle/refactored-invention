--
-- База данных: `test_db`
--
CREATE DATABASE IF NOT EXISTS `test_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `test_db`;

-- --------------------------------------------------------

--
-- Структура таблицы `data_table`
--

CREATE TABLE IF NOT EXISTS `data_table` (
  `id` int(11) UNSIGNED NOT NULL,
  `company_name` char(32) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `tax_number` char(12) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `phone_number` char(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` char(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `first_name` char(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `surname` char(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `data_table`
--
ALTER TABLE `data_table`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `tax_number` (`tax_number`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `data_table`
--
ALTER TABLE `data_table`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT;
COMMIT;
