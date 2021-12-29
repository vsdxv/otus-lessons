# MySQL
CREATE TABLE IF NOT EXISTS education.users (
  id bigint unsigned NOT NULL AUTO_INCREMENT,
  username varchar(100) NOT NULL,
  password varchar(100) NOT NULL,
  enabled tinyint(1) NOT NULL,
  lastname varchar(100) NOT NULL,
  age int NOT NULL,
  interests varchar(1000) DEFAULT NULL,
  city varchar(50) NOT NULL,
  gender tinyint(1) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY username_unique (username)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS education.user_friends (
   user_id bigint NOT NULL,
   friend_id bigint NOT NULL,
   PRIMARY KEY (friend_id,user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci