package ua.com.vertex.logic.interfaces;

import org.springframework.dao.DataAccessException;
import ua.com.vertex.beans.Role;
import ua.com.vertex.beans.User;

import java.sql.SQLException;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserLogic {

    List<String> getAllUserIds();

    Optional<User> getUserById(int id);

    Optional<User> getUserByEmail(String email);

    void saveImage(int userId, byte[] image, String imageType) throws Exception;

    Optional<byte[]> getImage(int userId, String imageType);

    List<User> getAllUsers() throws SQLException;

    Optional<User> getUserDetailsByID(int userId) throws SQLException;

    EnumMap<Role, Role> getAllRoles();

    int saveUserData(User user);

    int activateUser(String email);

    List<User> searchUser(String userData) throws Exception;

    Optional<User> userForRegistrationCheck(String userEmail) throws DataAccessException;

    String encryptPassword(String password);

    void registrationUserInsert(User user) throws DataAccessException;

    void registrationUserUpdate(User user) throws DataAccessException;

    Map<String, String> getTeachers () throws DataAccessException;

    List<User> getCourseUsers(int courseId) throws DataAccessException;
}
