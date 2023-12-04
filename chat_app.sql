-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: localhost
-- Thời gian đã tạo: Th10 07, 2023 lúc 01:58 PM
-- Phiên bản máy phục vụ: 10.4.28-MariaDB
-- Phiên bản PHP: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `chat_app`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `conversation`
--
USE chat_app;
CREATE TABLE `conversation` (
  `conversation_id` bigint(20) UNSIGNED NOT NULL,
  `conversation_name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `group_member`
--

CREATE TABLE `group_member` (
  `user_id` bigint(20) UNSIGNED NOT NULL,
  `conversation_id` bigint(20) UNSIGNED NOT NULL,
  `joined_datetime` datetime NOT NULL,
  `left_datetime` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;


CREATE TABLE `login` (
  `user_id` bigint(20) UNSIGNED NOT NULL,
  `token` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `message`
--

CREATE TABLE `message` (
  `message_id` bigint(20) UNSIGNED NOT NULL,
  `sender` varchar(255) NOT NULL,
  `receiver` varchar(255) NOT NULL,
  `message_text` varchar(255) NOT NULL,
  `sent_datetime` datetime NOT NULL,
  `conversation_id` bigint(20) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `users`
--

CREATE TABLE `users` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `user_name` varchar(55) NOT NULL,
  `email` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL,
  `phone` varchar(20) NOT NULL,
  `avatar_url` varchar(255) DEFAULT 'https://cdn-icons-png.flaticon.com/256/149/149071.png',
  `created_at` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Đang đổ dữ liệu cho bảng `users`
--

INSERT INTO `users` (`id`, `user_name`, `email`, `password`, `phone`, `avatar_url`, `created_at`) VALUES
(1, 'huy', 'nam@gmail.com', '123456', '0987654321', 'https://cdn-icons-png.flaticon.com/256/149/149071.png', '2023-11-02 10:16:51'),
(2, 'toan', 'huy@gmail.com', '123456', '123456789', 'https://cdn-icons-png.flaticon.com/256/149/149071.png', '2023-11-05 18:29:35'),
(3, 'nam', '123456', '123456', '[value-5]', '[value-6]', '2023-11-06 23:23:24');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `conversation`
--
ALTER TABLE `conversation`
  ADD PRIMARY KEY (`conversation_id`);

--
-- Chỉ mục cho bảng `group_member`
--
ALTER TABLE `group_member`
  ADD KEY `user_id` (`user_id`),
  ADD KEY `conversation_id` (`conversation_id`);

--
-- Chỉ mục cho bảng `login`
--
ALTER TABLE `login`
  ADD KEY `user_id` (`user_id`);

--
-- Chỉ mục cho bảng `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`message_id`),
  ADD KEY `conversation_id` (`conversation_id`);

--
-- Chỉ mục cho bảng `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UQ_Users_Email` (`email`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `conversation`
--
ALTER TABLE `conversation`
  MODIFY `conversation_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `message`
--
ALTER TABLE `message`
  MODIFY `message_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `group_member`
--
ALTER TABLE `group_member`
  ADD CONSTRAINT `group_member_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `group_member_ibfk_2` FOREIGN KEY (`conversation_id`) REFERENCES `conversation` (`conversation_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `login`
--
ALTER TABLE `login`
  ADD CONSTRAINT `login_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Các ràng buộc cho bảng `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `message_ibfk_1` FOREIGN KEY (`conversation_id`) REFERENCES `conversation` (`conversation_id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
