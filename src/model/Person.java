package model;

import java.io.Serializable;

public class Person implements Serializable {

	private static final long serialVersionUID = -8784555231751676465L;

	private static int count = 1;

	private int id;
	private String name;
	private String occupation;
	private AgeCategory ageCategory;
	private EmploymentCategory employmentCategory;
	private boolean usCitizen;
	private String taxId;
	private Gender gender;

	public Person() {

	}

	public Person( String name, String occupation, AgeCategory ageCategory,
			EmploymentCategory employmentCategory, boolean usCitizen, String taxId, Gender gender ) {
		super();

		this.name = name;
		this.occupation = occupation;
		this.ageCategory = ageCategory;
		this.employmentCategory = employmentCategory;
		this.usCitizen = usCitizen;
		this.taxId = taxId;
		this.gender = gender;

		this.id = count;
		count++;
	}

	public Person( int id, String name, String occupation, AgeCategory ageCategory,
			EmploymentCategory employmentCategory, boolean usCitizen, String taxId, Gender gender ) {

    this(name, occupation, ageCategory, employmentCategory, usCitizen, taxId, gender);

    this.id = id;
	}

  public int getId() {
		return id;
	}

	public void setId( int id ) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation( String occupation ) {
		this.occupation = occupation;
	}

	public AgeCategory getAgeCategory() {
		return ageCategory;
	}

	public void setAgeCategory( AgeCategory ageCategory ) {
		this.ageCategory = ageCategory;
	}

	public EmploymentCategory getEmploymentCategory() {
		return employmentCategory;
	}

	public void setEmploymentCategory( EmploymentCategory employmentCategory ) {
		this.employmentCategory = employmentCategory;
	}

	public boolean isUsCitizen() {
		return usCitizen;
	}

	public void setUsCitizen( boolean usCitizen ) {
		this.usCitizen = usCitizen;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId( String taxId ) {
		this.taxId = taxId;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender( Gender gender ) {
		this.gender = gender;
	}

  @Override
  public String toString() {
    return "Person [ageCategory=" + ageCategory + ", employmentCategory=" + employmentCategory + ", gender=" + gender
        + ", id=" + id + ", name=" + name + ", occupation=" + occupation + ", taxId=" + taxId + ", usCitizen="
        + usCitizen + "]";
  }

}
