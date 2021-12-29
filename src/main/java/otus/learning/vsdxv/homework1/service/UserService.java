package otus.learning.vsdxv.homework1.service;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import otus.learning.vsdxv.homework1.mappers.UserMapper;
import otus.learning.vsdxv.homework1.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SqlSessionTemplate sqlSessionTemplate;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, SqlSessionTemplate sqlSessionTemplate) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public User findUserByUserName(String username) throws Exception {
        User user = null;
        try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            user = userMapper.findUserByUserName(username);

        }

        return user;
    }

    public List<User> findAllUsers() throws Exception {
        List<User> users = null;
        try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            users = userMapper.findAllUsers();

        }
        return users;
    }

    public int saveUser(User user) throws Exception{
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setEnabled(true);
        int result = 0;
        try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            result = userMapper.saveUser(user);

        }
        return result;
    }
    @Transactional
    public int saveUserTestData(List<User> users, int batchSize){
        logger.info("generated data");
        int result = 0;
        int i = 0;
        long startList = System.currentTimeMillis();
        try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false)) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            for(User user: users) {
                user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
                user.setEnabled(true);
                try {
                    result = userMapper.saveUser(user);
                }catch (DuplicateKeyException e) {
                        logger.warn("saveUserTestData insert DuplicateKeyException insert ignore: " + e);
                        continue;
                    }
//                logger.info("result insert"+result+"insert user: "+user);
          /*      i++;
                if (i % 100000 == 0 || i == 100000  - 1) {
//                if (i % batchSize == 0 || i == batchSize - 1) {
                    sqlSession.flushStatements();
                    sqlSession.commit();
                    sqlSession.clearCache();
                    i=0;
                }
*/
            }
            sqlSession.flushStatements();
            sqlSession.commit();
            long stopList = System.currentTimeMillis()-startList;
            logger.info("The time spent was:"+stopList/1000+" second");
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return result;
    }
    public int addFriendById(long userId, long friendId) throws Exception {
        int result;
        try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession();) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            result = userMapper.addFriendById(userId,friendId);

        }
        return result;
    }

    public List<User> findUserFriends(long userId) {
        List<User> users = null;
        try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            users = userMapper.findUserFriends(userId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public int deleteFriendById(long userId, long friendId) throws Exception {
        int result = 0;
        try (SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession()) {
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            result = userMapper.deleteFriendById(userId,friendId);

        }
        return result;
    }
}
