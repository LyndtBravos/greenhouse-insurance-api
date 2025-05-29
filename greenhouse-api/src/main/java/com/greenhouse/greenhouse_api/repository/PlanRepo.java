package com.greenhouse.greenhouse_api.repository;

import com.greenhouse.greenhouse_api.model.Plan;
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
public class PlanRepo extends DatabaseConnection {

    private final JdbcTemplate jdbcTemplate;
    private final UtilValidator validate = new UtilValidator();

    public PlanRepo() {
        jdbcTemplate = new JdbcTemplate(mysqlDataSource());
    }

    public JdbcTemplate getJdbc() {
        return jdbcTemplate;
    }

    static class PlanRowMapper implements RowMapper<Plan> {
        @Override
        public Plan mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return new Plan(resultSet.getInt(1), resultSet.getString(2), resultSet.getBoolean(3)
                    , resultSet.getString(4), resultSet.getInt(5), resultSet.getInt(6));
        }
    }

    public List<Plan> findAll() {
        return this.getJdbc().query("select * from greenhouse.plan", new PlanRowMapper());
    }

    public Plan findPlanWithPlanID(int planID) {
        List<Plan> plans = new ArrayList<>();
        String idValidated = validate.verifyNumbers(planID);

        if(idValidated.isEmpty()) {
            plans = this.getJdbc().query("SELECT * FROM greenhouse.plan WHERE planID = ?;",
                    new PlanRowMapper(), planID);
        }

        return plans.isEmpty() ? null : plans.get(0);
    }

    public List<Plan> findPlanWithClientID(int clientID) {
        if(validate.verifyNumbers(clientID).isEmpty()) {
            return this.getJdbc().query("SELECT * from greenhouse.plan WHERE clientID = ?;",
                    new PlanRowMapper(), clientID);
        }else return new ArrayList<>();
    }

    public ResponseEntity<String> insert(Plan plan) {

        int result;
        String stringReturned;

        String incomeValidated = validate.verifyNumbers(plan.getIncome());
        String dateValidated = validate.validateDate(plan.getDOB());
        String dependencyValidated = validate.verifyDependency(plan.getDOB());
        String genderValidated = validate.verifyGender(plan.getGender());

        if(incomeValidated.isEmpty() && dateValidated.isEmpty()
                && dependencyValidated.isEmpty() && genderValidated.isEmpty()) {
            try {
                result = this.getJdbc().update("INSERT INTO `greenhouse`.`plan` (`DOB`,`Dependency?`,`Gender`,`Income`,`clientID`)"
                        + " VALUES (?,?,?,?,?);", plan.getDOB(), plan.isDependency(), plan.getGender(), plan.getIncome(), plan.getUserID());
                stringReturned = result > 0 ? "Plan addition was a success!" : "Something went wrong during the plan insertion process!";

            }catch(DataIntegrityViolationException e) {
                stringReturned = "User already exists, or constraint violated!";
            }catch(Exception e) {
                stringReturned = e.getMessage();
            }
        }else {
            if(!incomeValidated.isEmpty())
                stringReturned = incomeValidated;
            else if(!dateValidated.isEmpty())
                stringReturned = dateValidated;
            else if(!dependencyValidated.isEmpty())
                stringReturned = dependencyValidated;
            else stringReturned = genderValidated;
        }

        return new ResponseEntity<>(stringReturned, stringReturned.contains("success") ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> update(Plan plan) {

        int result;
        String stringReturned;

        String incomeValidated = validate.verifyNumbers(plan.getIncome());
        String dateValidated = validate.validateDate(plan.getDOB());
        String dependencyValidated = validate.verifyDependency(plan.getDOB());
        String genderValidated = validate.verifyGender(plan.getGender());

        if(incomeValidated.isEmpty() && dateValidated.isEmpty()
                && dependencyValidated.isEmpty() && genderValidated.isEmpty()) {
            result = this.getJdbc().update("UPDATE `greenhouse`.`plan` SET `DOB` = ?, `Dependency?` = ?, " +
                            "`Gender` = ?, `Income` = ? WHERE `planID` = ?;",
                    plan.getDOB(), plan.isDependency(), plan.getGender(), plan.getIncome(), plan.getID());

            stringReturned = result > 0 ? "The plan's update was a success!" : "Something went wrong during the plan updating process!";
        }else {
            if(!incomeValidated.isEmpty())
                stringReturned = incomeValidated;
            else if(!dateValidated.isEmpty())
                stringReturned = dateValidated;
            else if(!dependencyValidated.isEmpty())
                stringReturned = dependencyValidated;
            else stringReturned = genderValidated;
        }

        return new ResponseEntity<>(stringReturned, stringReturned.contains("success") ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> delete(int planID) {
        int result;
        String stringReturned;

        String idValidated = validate.verifyNumbers(planID);

        if(idValidated.isEmpty()) {
            result = this.getJdbc().update("DELETE FROM `greenhouse`.`plan` WHERE planID = ?;", planID);
            stringReturned = result > 0 ? "Plan deletion was a success!" : "Something went wrong during the plan's deletion process!";
        }else {
            stringReturned = idValidated;
        }

        return new ResponseEntity<>(stringReturned, stringReturned.contains("success") ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }
}