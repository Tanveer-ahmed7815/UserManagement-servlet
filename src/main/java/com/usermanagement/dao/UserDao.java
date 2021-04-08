package com.usermanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.usermanagement.bean.User;

public class UserDao {

	private static final String INSERT_USERS_SQL = "insert into emp_details" + "values (name,email,country) "
			+ " (?,?,?);";

	private static final String SELECT_USER_BY_ID = "select * from emp_details" + " where id=?";
	private static final String SELECT_ALL_USERS = "select * from emp_detials";
	private static final String DELETE_USER_BY_ID = "delete from emp_details " + "where id=?";
	private static final String UPDATE_USER_BY_ID = "update emp_details set name=?,email=?,country=? " + "where id=?";

	protected Connection getConnection() {

		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.cj.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/servlet_db", "root", "slk@SOFT123");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}

	public void insertUser(User user) throws SQLException {

		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getCountry());
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	public User fetchUserById(int id) {
		User user = null;
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
			preparedStatement.setInt(1, id);
			ResultSet res = preparedStatement.executeQuery();
			while (res.next()) {
				String name = res.getString("name");
				String email = res.getString("email");
				String country = res.getString("country");
				user = new User(name, email, country);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public List<User> fetchAllUsers() {

		List<User> list = new ArrayList<>();
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
			ResultSet res = preparedStatement.executeQuery();

			while (res.next()) {
				int id = res.getInt("id");
				String name = res.getString("name");
				String email = res.getString("email");
				String country = res.getString("country");

				list.add(new User(id, name, email, country));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public User updateUser(User user) {

		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_USER_BY_ID);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getCountry());
			preparedStatement.setInt(4, user.getId());

			if (preparedStatement.executeUpdate() > 0) {

				return user;
			} else {

				return null;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public boolean deleteUser(int id) {
		boolean flag = true;
		try {
			Connection connection = getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_USER_BY_ID);
			preparedStatement.setInt(1, id);
			flag = preparedStatement.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
}
