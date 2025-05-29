package com.greenhouse.greenhouse_api.repository;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	public DataSource mysqlDataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:mem:greenhouse");
		dataSource.setUsername("lindtbravos");
		dataSource.setPassword("fory'all");
		return dataSource;
	}

	public Connection getConn() {
		String url = "jdbc:mysql://localhost:3306/greenhouse?useUnicode=yes&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true";
		Connection con = null;
		System.out.println("Can you reach this?");
		try {
			con = DriverManager.getConnection(url, "root", "psycho");
			System.out.println("Did you get to this?");
		}catch(SQLException ex) {
			System.out.println("Exception thrown: " + ex.getMessage());
		}
		return con;
	}
}