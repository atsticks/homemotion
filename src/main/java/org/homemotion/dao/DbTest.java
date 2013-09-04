package org.homemotion.dao;

import java.sql.SQLException;

import org.apache.derby.jdbc.EmbeddedDataSource;

public class DbTest {
	public static void main(String[] args) {
		EmbeddedDataSource ds = new EmbeddedDataSource();
		ds.setDataSourceName("homemotion");
		ds.setCreateDatabase("create");
		ds.setDatabaseName("./target/testdb");
		ds.setUser("sa");
		ds.setPassword("");
		try {
			 ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
