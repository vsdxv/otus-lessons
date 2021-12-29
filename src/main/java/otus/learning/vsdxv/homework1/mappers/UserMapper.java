package otus.learning.vsdxv.homework1.mappers;

import org.apache.ibatis.annotations.Mapper;
import otus.learning.vsdxv.homework1.model.User;

import java.util.List;

@Mapper
public interface UserMapper {
    public User findUserByUserName(String username) throws Exception;
    public List<User> findAllUsers() throws Exception;
    public int saveUser(User user) throws Exception;
    public int addFriendById(long userId, long friendId) throws Exception;
    public List<User> findUserFriends(long userId) throws Exception;
    public int deleteFriendById(long userId, long friendId) throws Exception;
}
