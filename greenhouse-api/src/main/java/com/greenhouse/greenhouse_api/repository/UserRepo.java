package com.greenhouse.greenhouse_api.repository;

import com.greenhouse.greenhouse_api.model.User;
import com.greenhouse.greenhouse_api.validator.UtilValidator;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepo extends DatabaseConnection {

    private final JdbcTemplate jdbcTemplate;
    private final UtilValidator validate = new UtilValidator();

    public UserRepo() {
        jdbcTemplate = new JdbcTemplate(mysqlDataSource());
    }

    public JdbcTemplate getJdbc() {
        return jdbcTemplate;
    }

    static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return new User(resultSet.getInt(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), true);
        }
    }

    public List<User> findAll() {
        return this.getJdbc().query("select * from greenhouse.users", new UserRowMapper());
    }

    public User findUserWithEmail(String email) {
        List<User> users = new ArrayList<>();

        if(validate.verifyEmail(email).isEmpty())
            users = this.getJdbc().query("SELECT * FROM greenhouse.users WHERE emailAddress = ?;",
                    new UserRowMapper(), email);

        return users.isEmpty() ? null : users.get(0);
    }

    public User findUser(User user) {
        List<User> users = new ArrayList<>();

        if(validate.verifyEmail(user.getEmail()).isEmpty() &&
                validate.verifyPassword(user.getPassword()).isEmpty()) {
            users = this.getJdbc().query("SELECT *  FROM greenhouse.users WHERE emailAddress = ? AND password = ?;",
                    new UserRowMapper(), user.getEmail(), user.getPassword());
        }

        return users.isEmpty() ? null : users.get(0);
    }

    public ResponseEntity<String> insert(User user) {
        int result;
        String stringReturned;

        boolean nameValidated = validate.verifyString(user.getName()).isEmpty();
        boolean surnameValidated = validate.verifyString(user.getSurname()).isEmpty();
        boolean emailValidated = validate.verifyEmail(user.getEmail()).isEmpty();
        String passwordValidated = validate.verifyPassword(user.getPassword());

        if(nameValidated && surnameValidated && emailValidated && passwordValidated.isEmpty()) {
            try {

                result = this.getJdbc().update("INSERT INTO `greenhouse`.`users` (`name`, `surname`, `emailAddress`, `password`) " +
                        "VALUES (?, ?, ?, ?);", user.getName(), user.getSurname(), user.getEmail(), user.getPassword());
                stringReturned = result > 0 ? "User addition was a success!" : "Something went wrong during the user insertion process!";

            }catch(DataIntegrityViolationException e) {
                stringReturned = "User already exists, or constraint violated!";
            }catch(Exception e) {
                stringReturned = e.getMessage();
            }
        }else {
            if(!nameValidated) stringReturned = "Error on name validation! Please try again.";
            else if(!surnameValidated) stringReturned = "Error on last name validation! Please try again.";
            else if(!emailValidated) stringReturned = "Error on email validation! Please try again.";
            else stringReturned = passwordValidated;
        }

        return new ResponseEntity<>(stringReturned, stringReturned.contains("success") ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updateAll(User user) {

        String stringReturned;
        int result;

        boolean nameValidated = validate.verifyString(user.getName()).isEmpty();
        boolean surnameValidated = validate.verifyString(user.getSurname()).isEmpty();
        boolean emailValidated = validate.verifyEmail(user.getEmail()).isEmpty();
        String passwordValidated = validate.verifyPassword(user.getPassword());

        if(nameValidated && surnameValidated && emailValidated && passwordValidated.isEmpty()) {
            result = this.getJdbc().update("UPDATE `greenhouse`.`users` SET `name` = ?, `surname` = ?, `emailAddress` = ?, `password` = ? WHERE `ID` = ?;",
                    user.getName(), user.getSurname(), user.getEmail(), user.getPassword(), user.getID());

            stringReturned = result > 0 ? "The user's details have been updated! Count: " +result : "Something went wrong during the plan updating process!";
        }else {
            if(!nameValidated) stringReturned = "Error on name validation! Please try again.";
            else if(!surnameValidated) stringReturned = "Error on last name validation! Please try again.";
            else if(!emailValidated) stringReturned = "Error on email validation! Please try again.";
            else stringReturned = passwordValidated;
        }

        return new ResponseEntity<>(stringReturned, stringReturned.contains("updated") ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> updatePassword(String email, String password) {
        String stringReturned;
        int result;

        boolean emailValidated = validate.verifyEmail(email).isEmpty();
        String passwordValidated = validate.verifyPassword(password);

        if(emailValidated && passwordValidated.isEmpty()) {
            result = this.getJdbc().update("UPDATE `greenhouse`.`users` SET `password` = ? WHERE `emailAddress` = ?;",
                    password, email);

            stringReturned = result > 0 ? "Your password has been reset! Count: " +result : "Something went wrong during the plan updating process!";
        }else{
            if(!emailValidated) stringReturned = "Error on email validation! Please try again.";
            else stringReturned = passwordValidated;
        }

        return new ResponseEntity<>(stringReturned, stringReturned.contains("reset") ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> delete(int userID) {
        int result = this.getJdbc().update("DELETE FROM `greenhouse`.`users` WHERE ID = ?;", userID);

        String stringReturned = result > 0 ? "This user has now been deactivated!" : "Something went wrong during the plan's deletion process!";

        return new ResponseEntity<>(stringReturned, stringReturned.contains("deactivated") ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}