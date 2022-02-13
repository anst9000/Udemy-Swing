package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Database {

	private List<Person> people;
  private Connection conn;

  private int port;
  private String user;
  private String password;

	public Database() {
		people = new LinkedList<>();
	}

  public void configure(int port, String user, String password) throws Exception {
    this.port = port;
    this.user = user;
    this.password = password;

    if (conn != null) {
      disconnect();
      connect();
    }
  }

  public void connect() throws Exception {
    if (conn != null) {
      return;
    }

    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      throw new Exception("Driver not found");
    }

    String url = "jdbc:mysql://localhost:3306/swingtest";
    String username = "root";
    String password = "password";

    conn = DriverManager.getConnection(url, username, password);

    System.out.println("Connected to DB " + conn);
  }

  public void disconnect() {
    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException e) {
        System.out.println("Can't close connection");
        e.printStackTrace();
      }
    }
  }

  public void save() throws SQLException {
    String checkQuery = "SELECT count(*) AS count FROM people WHERE id=?";

    PreparedStatement checkStatement = conn.prepareStatement(checkQuery);

    String insertSql = "INSERT INTO people (id, name, age, employment_status, tax_id, us_citizen, gender, occupation) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    PreparedStatement insertStatement = conn.prepareStatement(insertSql);

    String updateSql = "UPDATE people SET name=?, age=?, employment_status=?, tax_id=?, us_citizen=?, gender=?, occupation=? WHERE id=?";
    PreparedStatement updateStatement = conn.prepareStatement(updateSql);

    for (Person person : people) {

      int id = person.getId();
      String name = person.getName();
      String occupation = person.getOccupation();
      AgeCategory age = person.getAgeCategory();
      EmploymentCategory employment = person.getEmploymentCategory();
      boolean isUs = person.isUsCitizen();
      String taxId = person.getTaxId();
      Gender gender = person.getGender();

      checkStatement.setInt(1, id);
      ResultSet checkResult = checkStatement.executeQuery();

      checkResult.next();
      int count = checkResult.getInt(1);
      int col = 0;

      if (count == 0) {
        System.out.println("Inserting person with " + id);
        col = 0;

        insertStatement.setInt(++col, id);
        insertStatement.setString(++col, name);
        insertStatement.setString(++col, age.name());
        insertStatement.setString(++col, employment.name());
        insertStatement.setString(++col, taxId);
        insertStatement.setBoolean(++col, isUs);
        insertStatement.setString(++col, gender.name());
        insertStatement.setString(++col, occupation);

        insertStatement.executeUpdate();
      } else {
        System.out.println("Updating person with " + id);
        col = 0;

        updateStatement.setString(++col, name);
        updateStatement.setString(++col, age.name());
        updateStatement.setString(++col, employment.name());
        updateStatement.setString(++col, taxId);
        updateStatement.setBoolean(++col, isUs);
        updateStatement.setString(++col, gender.name());
        updateStatement.setString(++col, occupation);

        updateStatement.setInt(++col, id);

        updateStatement.executeUpdate();
      }
    }

    insertStatement.close();
    updateStatement.close();
    checkStatement.close();
  }

  public void load() throws SQLException {
    people.clear();

    String selectQuery = "SELECT id, name, age, employment_status, tax_id, us_citizen, gender, occupation FROM people ORDER BY name";
    Statement selectStatement = conn.createStatement();

    ResultSet results = selectStatement.executeQuery(selectQuery);
    while (results.next()) {
      int id = results.getInt("id");
      String name = results.getString("name");
      String age = results.getString("age");
      String occupation = results.getString("occupation");
      String employment = results.getString("employment_status");
      boolean isUs = results.getBoolean("us_citizen");
      String taxId = results.getString("tax_id");
      String gender = results.getString("gender");

      Person person = new Person(id, name, occupation, AgeCategory.valueOf(age), EmploymentCategory.valueOf(employment), isUs, taxId, Gender.valueOf(gender));
      people.add( person );
    }

    results.close();
    selectStatement.close();
  }

	public void addPerson( Person person ) {
		people.add( person );
	}

	public List<Person> getPeople() {
		return Collections.unmodifiableList( people );
	}

	public void saveToFile( File file ) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream( file );
		ObjectOutputStream objectOutputStream = new ObjectOutputStream( fileOutputStream );

		Person[] personArray = people.toArray( new Person[people.size()] );

		objectOutputStream.writeObject( personArray );

		objectOutputStream.close();
	}

	public void loadFromFile( File file ) throws IOException {
		FileInputStream fileInputStream = new FileInputStream( file );
		ObjectInputStream objectInputStream = new ObjectInputStream( fileInputStream );

		try {
			Person[] personArray = (Person[]) objectInputStream.readObject();
			people.clear();
			people.addAll( Arrays.asList( personArray ) );
		} catch ( ClassNotFoundException | IOException e ) {
			e.printStackTrace();
		}

		objectInputStream.close();
	}

	public void removePerson( int index ) {
		people.remove( index );
	}
}
