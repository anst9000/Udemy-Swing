package controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import gui.FormEvent;
import model.AgeCategory;
import model.Database;
import model.EmploymentCategory;
import model.Gender;
import model.Person;

public class Controller {

	Database db = new Database();

	public List<Person> getPeople() {
		return db.getPeople();
	}

  public void save() throws SQLException {
    db.save();
  }

  public void load() throws SQLException {
    db.load();
  }

  public void connect() throws Exception {
    db.connect();
  }

  public void disconnect() {
    db.disconnect();
  }

	public void addPerson( FormEvent event ) {
		String name = event.getName();
		String occupation = event.getOccupation();
		int ageCatId = event.getAgeCategory();
		String empCatString = event.getEmploymentCategory();
		boolean usCitizen = event.isUsCitizen();
		String taxId = event.getTaxId();
		String genderString = event.getGender();

		AgeCategory ageCategory;

		switch ( ageCatId ) {
			case 0:
				ageCategory = AgeCategory.child;
				break;

			case 1:
				ageCategory = AgeCategory.adult;
				break;

			case 2:
				ageCategory = AgeCategory.senior;
				break;

			default:
				ageCategory = null;
				break;
		}

		EmploymentCategory employmentCategory;

		switch ( empCatString ) {
			case "employed":
				employmentCategory = EmploymentCategory.employed;
				break;

			case "self-employed":
				employmentCategory = EmploymentCategory.selfEmployed;
				break;

			case "unemployed":
				employmentCategory = EmploymentCategory.unemployed;
				break;

			case "other":
				employmentCategory = EmploymentCategory.other;
				System.err.println( empCatString );
				break;

			default:
				employmentCategory = null;
		}

		Gender genderCategory;

		switch ( genderString ) {
			case "male":
				genderCategory = Gender.male;
				break;

			case "female":
				genderCategory = Gender.female;
				break;

			default:
				genderCategory = null;
				break;
		}

		Person person = new Person( name, occupation, ageCategory, employmentCategory, usCitizen, taxId,
				genderCategory );

		db.addPerson( person );
	}

	public void saveToFile( File file ) throws IOException {
		db.saveToFile( file );
	}

	public void loadFromFile( File file ) throws IOException {
		db.loadFromFile( file );
	}

	public void removePerson( int index ) {
		db.removePerson( index );
	}
}
