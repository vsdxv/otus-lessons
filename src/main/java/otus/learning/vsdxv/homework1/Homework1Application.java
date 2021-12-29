package otus.learning.vsdxv.homework1;

import org.apache.commons.lang3.RandomStringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import otus.learning.vsdxv.homework1.model.User;
import otus.learning.vsdxv.homework1.service.UserService;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

@SpringBootApplication
@MapperScan("otus.learning.vsdxv.homework1")
public class Homework1Application implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(Homework1Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Homework1Application.class, args);
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Value("${db.schema}")
    private String dbSchema;
    @Autowired
    private UserService userService;


    @Override
    public void run(String... args) throws Exception {
//        createTestData();
        logger.debug("Creating database tables");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS "+dbSchema+"users (\n" +
                "\tid int8 NOT NULL GENERATED ALWAYS AS IDENTITY,\n" +
                "\tusername varchar(100) NOT NULL,\n" +
                "\tpassword varchar(100) NOT NULL,\n" +
                "\tenabled bool NOT NULL,\n" +
                "\tlastname varchar(100) NOT NULL,\n" +
                "\tage int4 NOT NULL,\n" +
                "\tinterests varchar(1000) NULL,\n" +
                "\tcity varchar(50) NOT NULL,\n" +
                "\tgender int2 NOT NULL,\n" +
                "\tCONSTRAINT users_pk PRIMARY KEY (id)\n" +
                ")");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS "+dbSchema+"user_friends (\n" +
                "\tuser_id int8 NOT NULL,\n" +
                "\tfriend_id int8 NOT NULL,\n" +
                "\tCONSTRAINT user_friends_pk PRIMARY KEY (user_id, friend_id)\n" +
                ")");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS education.users (\n" +
                "  id bigint unsigned NOT NULL AUTO_INCREMENT,\n" +
                "  username varchar(100) NOT NULL,\n" +
                "  password varchar(100) NOT NULL,\n" +
                "  enabled tinyint(1) NOT NULL,\n" +
                "  lastname varchar(100) NOT NULL,\n" +
                "  age int NOT NULL,\n" +
                "  interests varchar(1000) DEFAULT NULL,\n" +
                "  city varchar(50) NOT NULL,\n" +
                "  gender tinyint(1) NOT NULL,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  UNIQUE KEY `username_unique` (username)\n" +
                ") ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci" +
                "");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS education.user_friends (\n" +
                "   user_id bigint NOT NULL,\n" +
                "   friend_id bigint NOT NULL,\n" +
                "   PRIMARY KEY (friend_id,user_id)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci" +
                "");

    }

}
