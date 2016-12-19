package ua.com.vertex.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.com.vertex.beans.User;
import ua.com.vertex.controllers.UserController;
import ua.com.vertex.dao.UserDaoRealizationInf;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDaoRealization implements UserDaoRealizationInf {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public User getUser(long id) {
        String query = "SELECT user_id, email, password, first_name, " +
                "last_name, passport_scan, photo, discount, phone FROM Users WHERE user_id=:id";
        return jdbcTemplate.queryForObject(query, new MapSqlParameterSource("id", id), new UserRowMapping());
    }

    public void deleteUser(long id) {
        String query = "DELETE FROM Users WHERE user_id=:id";
        jdbcTemplate.update(query, new MapSqlParameterSource("id", id));
    }

    @Override
    public List<Integer> getAllUserIds() {
        String query = "SELECT user_id FROM Users order by user_id";
        return jdbcTemplate.query(query, (resultSet, i) -> resultSet.getInt("user_id"));
    }

    @Override
    public int isRegisteredEmail(String email) {
        LOGGER.info("Running queries existence test E-mail to a database");

        String sql = "SELECT count(*) FROM Users WHERE email = :email";
        SqlParameterSource namedParameters = new MapSqlParameterSource("email", email);
        return this.jdbcTemplate.queryForObject(sql, namedParameters, Integer.class);
    }

    @Override
    public int registrationUser(User user) throws SQLException {

        LOGGER.info("Adding a new user in the database");

        String query = "INSERT INTO Users (email, password, first_name, last_name, phone) VALUES (:email, :password, :first_name, :last_name, :phone)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("email", user.getEmail());
        namedParameters.addValue("password", user.getPassword());
        namedParameters.addValue("first_name", user.getFirstName());
        namedParameters.addValue("last_name", user.getLastName());
        namedParameters.addValue("phone", user.getPhone());
        jdbcTemplate.update(query, namedParameters, keyHolder);

        Number id = keyHolder.getKey();

        return id.intValue();
    }

    private static final class UserRowMapping implements RowMapper<User> {
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            return new User.Builder().
                    setUserId(resultSet.getLong("user_id")).
                    setEmail(resultSet.getString("email")).
                    setPassword(resultSet.getString("password")).
                    setFirstName(resultSet.getString("first_name")).
                    setLastName(resultSet.getString("last_name")).
                    setPassportScan(resultSet.getBlob("passport_scan")).
                    setPhoto(resultSet.getBlob("photo")).
                    setDiscount(resultSet.getInt("discount")).
                    setPhone(resultSet.getString("phone")).getInstance();
        }
    }
}

