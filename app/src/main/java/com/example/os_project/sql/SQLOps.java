package com.example.os_project.sql;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;

import com.example.os_project.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class SQLOps {
	
	/*
	 * This class does all of the SQL 
	 * operations, including login
	 * and modifications.
	 */
	private Connection con;
	
	
	private String[] p;
	private String table;
	
	public boolean isFinished = false;
	
	public boolean debug = false;

	public String ip;
	public String db;
	public String user;
	public String pass;
	
	public SQLOps() {
		
		try {
			getEntireTable(con,this.table);
		} catch(SQLException e) {
			e.printStackTrace();
		}
				
	}

	public SQLOps(String ip, String db, String user, String pass) {

		this.ip = ip;
		this.db = db;
		this.user = user;
		this.pass = pass;

	}
	
	/* This is essentially the UPDATE command in MySQL. */
	public void edit(Connection con, String table, String id, String field, String newValue) {
		
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM " + table);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
						
			if (!rsmd.getColumnName(1).isEmpty()) {
				
				String edit = "UPDATE " + table + " SET " + field + "='" + newValue + "' WHERE "
								+ rsmd.getColumnName(1) + "='" + id + "';";
				
				System.out.println(edit);
				stmt.executeUpdate(edit);
			} else {
			}
			
		} catch(SQLException e) {
			//Button.sendError(e.toString());
		}
	}

	public ArrayList<String> columnNames(String table) {
		ArrayList<String> names = new ArrayList<String>();

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM " + table);
			ResultSet rs = stmt.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();

			int colCount = rsmd.getColumnCount();

			for (int i = 1; i <= colCount; i++) {
				names.add(rsmd.getColumnName(i));
			}
		} catch(SQLException e) {

		}
		return names;
	}
	
	/* This is the function to remove a single row from a table. */
	public void delRow(Connection con, String table, String id) {
		
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM " + table);
			ResultSetMetaData rsmd = stmt.executeQuery().getMetaData();
			
			if(rsmd.isAutoIncrement(1) || rsmd.getColumnName(1).equalsIgnoreCase("contact_id")) {
				String del = ("DELETE FROM " + table + " WHERE " + rsmd.getColumnName(1) + "=" + id);
				
				if (del.length() > 0)
					stmt.executeUpdate(del);
			} 
		} catch(SQLException e) {
			//Button.sendError(e.toString());
		}
	}
	
	
	public void add(Connection con, String table, String[] inputs) {
		
		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM " + table);
			ResultSetMetaData rsmd = stmt.executeQuery().getMetaData();
						
			String ins = "INSERT INTO " + table + " (";
			
			for (int i = 0; i < inputs.length; i++) {
				
				if (!rsmd.isAutoIncrement(i+1)) {
					if (i == inputs.length - 1)
						ins += rsmd.getColumnName(i+1) + ") ";
					else ins += rsmd.getColumnName(i+1) + ",";
				}
			}
			
			ins += "VALUE (";
			for (int i = 0; i < inputs.length; i++) {
				if (!rsmd.isAutoIncrement(i+1) && i < inputs.length - 1) 
					ins += "'" + inputs[i] + "', ";
			}
			ins += "'" + inputs[inputs.length - 1] + "');";
			
			System.out.println(ins);
			stmt.executeUpdate(ins);
			
			//JOptionPane.showMessageDialog(null, "Addition successful");
			
		} catch(SQLIntegrityConstraintViolationException e) {
			//Button.sendError(e.toString());
		}catch(SQLException e) {
			//Button.sendError(e.toString());
		}
		
		
	}
	
	/* Establishes the connection to the server */
	public Connection startConnection(String ip, String db, String user, String pass) {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://" + ip + "/" + db;
			Connection con = DriverManager.getConnection(url,user,pass);
			return con;
		} catch(SQLException e) {
			Log.d(null, e.toString());
		} catch (Exception e) {

		}
		
		return null;
	}
	
	/* Gets data from column. */
	public ArrayList<String> get(Connection con, String table, String col) throws SQLException {
				
		ArrayList<String> arr = new ArrayList<String>();
		
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM " + table + ";");
		ResultSet rs = stmt.executeQuery();
		
		while (rs.next()) {
			arr.add(rs.getString(col));
		}
		
		return arr;
	}
	
	/* Retrieves every piece of data from a table */
	public ArrayList<String> getEntireTable(Connection con, String table) throws SQLException {
		
		ArrayList<String> arr = new ArrayList<String>();
		
		PreparedStatement stmt = con.prepareStatement("SELECT * FROM " + table);
		ResultSet rs = stmt.executeQuery();
		
		
		for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
			if (!rs.isClosed())
				rs.close();
			rs = stmt.executeQuery();
			while (rs.next()) {
				arr.add(rs.getString(i));
			}
		}
		return arr;
	}

	public ArrayList<String> getTableList(Connection con) throws SQLException {
		ArrayList<String> arr = new ArrayList<String>();

		PreparedStatement stmt = con.prepareStatement("SHOW TABLES");
		ResultSet rs = stmt.executeQuery();

		for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
			if (!rs.isClosed())
				rs.close();
			rs = stmt.executeQuery();
			while (rs.next()) {
				arr.add(rs.getString(i));
			}
		}
		return arr;
	}
	
	/* Changes Database and table */
	public void setDatabaseTable(String db, String table) {
		try {
			con.setCatalog(db);
		} catch(SQLException e) {
			e.printStackTrace();
		}
		setTable(table);
	}

	public Connection getConnection() {
		return con;
	}
	
	public String getIP() {
		return p[0];
	}
	
	public String getDB() {
		return p[1];
	}
	
	public String getTable() {
		return this.table;
	}
	
	public void setTable(String table) {
		this.table = table;
	}
}