package com.cavisson.ata.service;
/**
 * @author Vishal Singh
 *
 */

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.api.Database;
import com.lambdaworks.redis.RedisClient;
import com.lambdaworks.redis.RedisFuture;
import com.lambdaworks.redis.api.StatefulRedisConnection;
import com.lambdaworks.redis.api.async.RedisAsyncCommands;
import com.lambdaworks.redis.api.sync.RedisCommands;

public class DatabaseService {

	// cloudant db
	public static String cloudantconnect(String dbname) {
		String username = "0146e259-2907-42de-8d81-b9f9790e81b6-bluemix";
		String password = "8c505da4df4f82e7190cb8789ae0b4598854246742cf45834acb8d20c998e70c";

		CloudantClient client;
		try {
			client = ClientBuilder.url(new URL(
					"https://0146e259-2907-42de-8d81-b9f9790e81b6-bluemix:8c505da4df4f82e7190cb8789ae0b4598854246742cf45834acb8d20c998e70c@0146e259-2907-42de-8d81-b9f9790e81b6-bluemix.cloudantnosqldb.appdomain.cloud"))
					.username(username).password(password).build();

			System.out.println("instance created");

			// Show the server version
			System.out.print(", server version = " + client.serverVersion());

			// Create a new database.
			client.createDB(dbname);
			System.out.print(", database " + dbname + " created");

			// Get a List of all the databases this Cloudant account
			List<String> databases = client.getAllDbs();
			String datab = null;
			System.out.println("All my databases : ");
			for (String db : databases) {
				System.out.print(db);
				datab = db;
				break;
			}

			com.google.gson.JsonObject studentJson = new com.google.gson.JsonObject();
			studentJson.addProperty("_id", generateId());
			studentJson.addProperty("firstname", "Joe");
			studentJson.addProperty("lastname", "Doe");

			// Get a Database instance to interact with, but don't create it if it doesn't
			// already exist
			Database db = client.database(datab, false);

			com.cloudant.client.api.model.Response dbResponse = db.save(studentJson);
			com.google.gson.JsonObject output = new com.google.gson.JsonObject();
			// for success insertion
			if (dbResponse.getStatusCode() < 400) {
				output.add("document", studentJson);

				// dbResponse json data
				com.google.gson.JsonObject dbResponseJson = new com.google.gson.JsonObject();
				dbResponseJson.addProperty("status", dbResponse.getStatusCode() + " - " + dbResponse.getReason());
				dbResponseJson.addProperty("id", dbResponse.getId());
				dbResponseJson.addProperty("rev", dbResponse.getRev());

				output.add("data", dbResponseJson);
				System.out.println("You have inserted the document");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				db.find(dbResponse.getId());
				System.out.println(output);

				// Delete a database we created previously.
				client.deleteDB(datab);
				System.out.println("database " + datab + " deleted");
			} else {
				output.addProperty("err", dbResponse.getStatusCode() + " - " + dbResponse.getReason());
			}
			return "cloudant db instance created";

		} catch (Exception e) {
			System.err.println(e);
		}
		return "failed";
	}

	private static String generateId() {
		return "ID#" + new Double(Math.floor(Math.random() * 10000)).intValue();
	}

	// ibm db2
	public static String db2connect(String url, String dbname, String username, String password, String firstName,
			String lastName) {
		ArrayList<String> list = new ArrayList<String>();
		list.add("table before deletion");
		try {

			Class.forName("com.ibm.db2.jcc.DB2Driver");
			String str = "jdbc:db2://" + url + "/" + dbname;
			Connection con = DriverManager.getConnection(str, username, password);

			PreparedStatement stmt = con.prepareStatement("insert into people values(?,?)");
			stmt.setString(1, firstName);// 1 specifies the first parameter in the query
			stmt.setString(2, lastName);

			stmt.executeUpdate();

			String sql1 = "update people set firstname=? where lastname=?";

			PreparedStatement preparedStatement1 = con.prepareStatement(sql1);

			preparedStatement1.setString(1, "changed");
			preparedStatement1.setString(2, lastName);
			preparedStatement1.executeUpdate();

			String sql2 = "select * from people";
			PreparedStatement preparedStatement2 = con.prepareStatement(sql2);
			ResultSet rs2 = preparedStatement2.executeQuery();
			while (rs2.next()) {
				list.add(rs2.getString(1) + " " + rs2.getString(2));

			}
			list.add("table after deletion");

			String sql3 = "delete from people where firstname=?";
			PreparedStatement preparedStatement3 = con.prepareStatement(sql3);
			preparedStatement3.setString(1, firstName);
			preparedStatement3.executeUpdate();

			while (rs2.next()) {
				list.add(rs2.getString(1) + " " + rs2.getString(2));

			}
			System.out.println("connected to db2, url = " + str + " , executing queries, db successfully updated...");

			con.close();
		}

		catch (Exception e) {
			System.err.println(e);
		}

		return list.toString();
	}

	// mssql prepared
	public static String mssqlPreparedConnect(String host, String username, String password, String dbname, String port,
			String firstname, String lastname) {
		ArrayList<String> s = new ArrayList<String>();
		s.add("data before deletion : ");
		try {
			String connUrl = "jdbc:sqlserver://" + host + ":" + port + ";databaseName=" + dbname;
			Connection con = null;
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connUrl, username, password);

			PreparedStatement ps1 = con.prepareStatement("insert into people values(?,?)");
			ps1.setString(1, firstname);
			ps1.setString(2, lastname);
			ps1.executeUpdate();

			PreparedStatement ps2 = con.prepareStatement("SELECT * FROM people");
			ResultSet rs = ps2.executeQuery();
			while (rs.next()) {
				s.add(rs.getString("firstName") + " " + rs.getString("lastName"));
			}

			PreparedStatement ps3 = con.prepareStatement("delete from people where firstname=?");
			ps3.setString(1, firstname);
			ps3.executeUpdate();

			rs = ps2.executeQuery();
			while (rs.next()) {
				s.add(rs.getString("firstName") + " " + rs.getString("lastName"));
			}
			System.out.println("connected to mssql, url = " + connUrl
					+ " , executing prepared queries, db successfully updated...");
			con.close();

		}

		catch (Exception e) {
			System.err.println(e);
		}
		return s.toString();
	}

	// mssql non prepared
	public static String mssqlNonPrepConnect(String host, String username, String password, String dbname,
			String port) {
		ArrayList<String> list = new ArrayList<String>();
		list.add("data before deletion : ");
		try {
			String connUrl = "jdbc:sqlserver://" + host + ":" + port + ";databaseName=" + dbname;
			Connection con = null;
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(connUrl, username, password);

			Statement stm = con.createStatement();

			String SQL = "INSERT INTO people  " + "VALUES ('Sam','Wise')";
			stm.executeUpdate(SQL);

			String sql = "SELECT * from people";
			ResultSet rs2 = stm.executeQuery(sql);

			while (rs2.next()) {
				list.add(rs2.getString(1) + " " + rs2.getString(2));

			}

			String sql3 = "delete from student where firstname='Sam'";
			stm.executeUpdate(sql3);

			list.add("TABLE AFTER DELETION");

			ResultSet rs1 = stm.executeQuery(sql);

			while (rs1.next()) {
				list.add(rs1.getString(1) + " " + rs1.getString(2));

			}
			System.out.println("connected to mssql, url = " + connUrl
					+ " , executing non prepared queries, db successfully updated...");
			con.close();
		}

		catch (Exception e) {
			System.err.println(e);
		}
		return list.toString();
	}

	public static String mysqlNonPrepConnect(String dbname, String usernameDb, String passwordDb, String host) {
		ArrayList<String> list = new ArrayList<String>();
		list.add("TABLE BEFORE DELETION");
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String connUrl = "jdbc:mysql://" + host + ":3306/" + dbname;

			Connection con = DriverManager.getConnection(connUrl, usernameDb, passwordDb);

			Statement stm = con.createStatement();

			String SQL = "INSERT INTO people  " + "VALUES ('Sam','Wise')";
			stm.executeUpdate(SQL);

			String sql = "SELECT * from people";
			ResultSet rs2 = stm.executeQuery(sql);

			while (rs2.next()) {
				list.add(rs2.getString(1) + " " + rs2.getString(2));

			}
			String sql3 = "delete from people where firstname='Sam'";
			stm.executeUpdate(sql3);

			list.add("TABLE AFTER DELETION");

			ResultSet rs1 = stm.executeQuery(sql);

			while (rs1.next()) {
				list.add(rs1.getString(1) + " " + rs1.getString(2));
			}

			System.out.println("connected to mysql, url = " + connUrl
					+ " , executing non prepared queries, db successfully updated...");
			con.close();

		} catch (Exception e) {
			System.err.println(e);
		}
		return list.toString();
	}

	public static String mysqlPreparedConnect(String dbname, String username, String password, String firstname,
			String lastname, String host) {

		ArrayList<String> mylist = new ArrayList<String>();
		mylist.add("Table before Deletion : ");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String connUrl = "jdbc:mysql://" + host + ":3306/" + dbname;

			Connection con = DriverManager.getConnection(connUrl, username, password);

			PreparedStatement ps = con.prepareStatement("insert into people values(?,?)");

			ps.setString(1, firstname);
			ps.setString(2, lastname);
			ps.executeUpdate();

			PreparedStatement ps2 = con.prepareStatement("select * from people");
			ResultSet rs2 = ps2.executeQuery();
			while (rs2.next()) {

				mylist.add(rs2.getString(1) + "    " + rs2.getString(2));
			}

			PreparedStatement ps1 = con.prepareStatement("delete from people where firstname=?");
			ps1.setString(1, firstname);
			int i1 = ps1.executeUpdate();
			mylist.add("Table after Deletion : ");

			ResultSet rs3 = ps2.executeQuery();

			while (rs3.next()) {

				mylist.add(rs3.getString(1) + " " + rs3.getString(2));
			}

			if (i1 > 0)
				System.out.println("connected to mysql, url = " + connUrl
						+ " , executing prepared queries, db successfully updated...");

			con.close();
		} catch (Exception e) {
			System.err.println(e);
		}
		return mylist.toString();
	}

	public static String oraclePreparedConnect(String host, String port, String username, String password,
			String firstname, String lastname) {
		ArrayList<String> list = new ArrayList<String>();
		list.add("TABLE BEFORE DELETION");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");

			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":" + port + ":" + "XE",
					username, password);

			PreparedStatement stmt = con.prepareStatement("insert into student values(?,?)");
			stmt.setString(1, firstname);// 1 specifies the first parameter in the query
			stmt.setString(2, lastname);
			stmt.executeUpdate();

			String sql2 = "select * from student";

			PreparedStatement preparedStatement2 = con.prepareStatement(sql2);
			ResultSet rs2 = preparedStatement2.executeQuery();
			while (rs2.next()) {
				list.add(rs2.getString(1) + " " + rs2.getString(2));

			}

			list.add("TABLE AFTER DELETION");

			String sql3 = "delete from student where firstname=?";
			PreparedStatement preparedStatement3 = con.prepareStatement(sql3);
			preparedStatement3.setString(1, firstname);
			preparedStatement3.executeUpdate();

			rs2 = preparedStatement2.executeQuery();
			while (rs2.next()) {
				list.add(rs2.getString(1) + " " + rs2.getString(2));

			}
			System.out.println("connected to oracle xe db, url : jdbc:oracle:thin:@" + host + ":" + port + ":" + "XE"
					+ " , prepared insertion & deletion successful");
			con.close();

		} catch (Exception e) {
			System.err.println(e);

		}
		return list.toString();
	}

	public static String oracleNonPrepConnect(String host, String port, String username, String password) {
		ArrayList<String> list = new ArrayList<String>();
		list.add("TABLE BEFORE DELETION");
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":" + port + ":" + "XE",
					username, password);

			Statement stm = con.createStatement();

			String SQL = "INSERT INTO student  " + "VALUES ('Sam', 'Wise')";
			stm.executeUpdate(SQL);

			String sql = "SELECT * from student";

			ResultSet rs2 = stm.executeQuery(sql);

			while (rs2.next()) {
				list.add(rs2.getString(1) + " " + rs2.getString(2));

			}

			list.add("TABLE AFTER DELETION");

			String sql3 = "delete from student where firstname='Sam'";
			stm.executeUpdate(sql3);

			rs2 = stm.executeQuery(sql);

			while (rs2.next()) {
				list.add(rs2.getString(1) + " " + rs2.getString(2));
			}

			System.out.println("connected to oracle xe db, url : jdbc:oracle:thin:@" + host + ":" + port + ":" + "XE"
					+ " , non-prepared insertion & deletion successful");
			con.close();
		} catch (Exception e) {
			System.err.println(e);
		}

		return list.toString();
	}

	public static String redisCall(String host) {
		String str = "redis://" + host + ":6379";
		RedisClient redisClient = RedisClient.create(str);
		StatefulRedisConnection<String, String> connection = redisClient.connect();
		RedisCommands<String, String> syncCommands = connection.sync();
		RedisAsyncCommands<String, String> asyncCommands = redisClient.connect().async();

		syncCommands.set("key", "Hello, Redis!");

		RedisFuture<String> future = asyncCommands.get("key");
		String value = "";
		try {
			value = future.get();
		} catch (Exception e) {
			System.err.println(e);
		}

		System.out.println("using redis, url = " + str + " , value = " + value);

		String output = syncCommands.get("key");
		String info = syncCommands.info();
		syncCommands.exists("key");
		syncCommands.ttl("key");

		connection.close();
		redisClient.shutdown();
		return "done " + output + " INFO " + info;
	}

	public static String postgresConnect() {
		return null;
	}

	public static String redisDataConnect() {
		return null;
	}
}
