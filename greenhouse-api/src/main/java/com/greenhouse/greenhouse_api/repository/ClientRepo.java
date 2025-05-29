package com.greenhouse.greenhouse_api.repository;

import com.greenhouse.greenhouse_api.model.Client;
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
public class ClientRepo extends DatabaseConnection {

    private final JdbcTemplate jdbcTemplate;
    private final UtilValidator validate = new UtilValidator();

    public ClientRepo() {
        jdbcTemplate = new JdbcTemplate(mysqlDataSource());
    }

    public JdbcTemplate getJdbc() {
        return jdbcTemplate;
    }

    static class ClientRowMapper implements RowMapper<Client> {
        @Override
        public Client mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return new Client(resultSet.getDouble(2),resultSet.getDouble(3), resultSet.getInt(4));
        }
    }

    public List<Client> findAll() {
        return this.getJdbc().query("select * from greenhouse.clients", new ClientRowMapper());
    }

    public List<Client> findClientsByUser(int userID) {
        if(validate.verifyNumbers(userID).isEmpty()) {
            return this.getJdbc().query("SELECT * FROM greenhouse.clients WHERE userID = ?;",
                    new ClientRowMapper(), userID);
        }else return new ArrayList<>();
    }

    public Client findClient(int id) {
        List<Client> clients = new ArrayList<>();

        if(validate.verifyNumbers(id).isEmpty())
            clients = this.getJdbc().query("SELECT * FROM greenhouse.clients WHERE clientID = ?;",
                    new ClientRowMapper(), id);

        return clients.isEmpty() ? null : clients.get(0);
    }

    public ResponseEntity<String> insert(Client client) {
        int result;
        String stringReturned;

        try {
            result = this.getJdbc().update("insert into greenhouse.clients(Cover, Premium, userID)" +
                    " values(?,?,?);", client.getCover(), client.getPremium(), client.getUserID());
            stringReturned = result > 0 ? "Client addition was a success!" : "Something went wrong during the client insertion process!";

        }catch(DataIntegrityViolationException e) {
            stringReturned = "User already exists, or constraint violated!";
        }catch(Exception e) {
            stringReturned = e.getMessage();
        }

        return new ResponseEntity<>(stringReturned, stringReturned.contains("success") ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> update(Client client) {
        int result = this.getJdbc().update("UPDATE `greenhouse`.`clients` SET `Premium` = ?, `Cover` = ? WHERE `clientID` = ?;",
                client.getPremium(), client.getCover(), client.getUserID());

        String stringReturned = result > 0 ? "Client update was a success!" : "Something went wrong during the client updating process!";

        return new ResponseEntity<>(stringReturned, stringReturned.contains("success") ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> delete(int clientID) {
        int result;
        String stringReturned;

        String idValidated = validate.verifyNumbers(clientID);

        if(idValidated.isEmpty()) {
            result = this.getJdbc().update("DELETE FROM `greenhouse`.`clients` WHERE clientID = ?;",
                    clientID);

            stringReturned = result > 0 ? "Client deletion was a success!" : "Something went wrong during the client deletion process!";
        }else stringReturned = idValidated;

        return new ResponseEntity<>(stringReturned, stringReturned.contains("success") ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}