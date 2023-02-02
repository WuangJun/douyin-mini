DROP TABLE IF EXISTS `dy_user`;
CREATE TABLE `dy_user` (
  `id` bigint(64) NOT NULL,
  `username` varchar(64) NOT NULL DEFAULT '' COMMENT '用户名称',
  `password` varchar(64) NOT NULL DEFAULT '' COMMENT '登录密码',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号，用于乐观锁处理',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除标识。1：此记录已经删除',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表，存储用户登录信息';dy_user

DROP TABLE IF EXISTS `dy_video`;
CREATE TABLE `dy_video` (
  `id` bigint(64) NOT NULL,
	`author_id` bigint(64) NOT NULL  DEFAULT '0' COMMENT '视频作者ID',
  `title` varchar(64) NOT NULL DEFAULT '' COMMENT '视频标题',
  `play_url` varchar(256) NOT NULL DEFAULT '' COMMENT '视频播放地址',
	`cover_url` varchar(256) NOT NULL DEFAULT '' COMMENT '视频封面地址',
	`favorite_count` int(64) NOT NULL  DEFAULT '0' COMMENT '视频的点赞总数',
	`comment_count` int(64) NOT NULL  DEFAULT '0' COMMENT '视频的评论总数',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号，用于乐观锁处理',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除标识。1：此记录已经删除',
  PRIMARY KEY (`id`),
  KEY `idx_author_id` (`author_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='视频，存储用户投稿的视频';

DROP TABLE IF EXISTS `dy_video_favorite`;
CREATE TABLE `dy_video_favorite` (
  `id` bigint(64) NOT NULL,
	`favorite_user_id` bigint(64) NOT NULL  DEFAULT '0' COMMENT '点赞用户ID',
	`video_id` bigint(64) NOT NULL  DEFAULT '0' COMMENT '视频ID',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号，用于乐观锁处理',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除标识。1：此记录已经删除',
  PRIMARY KEY (`id`),
  KEY `idx_favorite_user_id` (`favorite_user_id`) USING BTREE,
  KEY `idx_video_id` (`video_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='视频点赞';

DROP TABLE IF EXISTS `dy_video_comment`;
CREATE TABLE `dy_video_comment` (
  `id` bigint(64) NOT NULL,
	`comment_user_id` bigint(64) NOT NULL  DEFAULT '0' COMMENT '评论用户ID',
	`video_id` bigint(64) NOT NULL  DEFAULT '0' COMMENT '视频ID',
	`comment_text` varchar(512) NOT NULL DEFAULT '' COMMENT '评论内容',
  `created_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modified_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本号，用于乐观锁处理',
  `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除标识。1：此记录已经删除',
  PRIMARY KEY (`id`),
  KEY `idx_comment_user_id` (`comment_user_id`) USING BTREE,
  KEY `idx_video_id` (`video_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='视频评论';


