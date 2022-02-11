import java.sql.SQLException;

import model.AgeCategory;
import model.Gender;
import model.Database;
import model.EmploymentCategory;
import model.Person;

public class TestDatabase {

  public static void main(String[] args) {
      System.out.println("Running DB test");

      Database db = new Database();
      try {
        db.connect();
      } catch (Exception e) {
        e.printStackTrace();
      }

      db.addPerson(new Person("Sam", "lion tamer", AgeCategory.adult, EmploymentCategory.employed, true, "123", Gender.male));
      db.addPerson(new Person("Alice", "artist", AgeCategory.adult, EmploymentCategory.selfEmployed, false, null, Gender.female));

      try {
        db.save();
      } catch (SQLException e) {
        e.printStackTrace();
      }

      try {
        db.load();
      } catch (SQLException e) {
        e.printStackTrace();
      }

      db.disconnect();
  }

}
