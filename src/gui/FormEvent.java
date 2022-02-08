package gui;
import java.util.EventObject;

public class FormEvent extends EventObject {

	private static final long serialVersionUID = 1L;

	private String name;
	private String occupation;
	private int ageCategory;
	private String employmentCategory;
	private boolean usCitizen;
	private String taxId;
	private String gender;

	public FormEvent( Object source ) {
		super( source );
	}

	public FormEvent( Object source, String name, String occupation, int ageCategory, String employmentCategory,
			boolean useCitizen, String taxId, String gender ) {
		super( source );

		this.name = name;
		this.occupation = occupation;
		this.ageCategory = ageCategory;
		this.employmentCategory = employmentCategory;
		this.usCitizen = useCitizen;
		this.taxId = taxId;
		this.gender = gender;
	}

	public String getGender() {
		return gender;
	}

	public void setGender( String gender ) {
		this.gender = gender;
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

	public String getEmploymentCategory() {
		return employmentCategory;
	}

	public void setEmploymentCategory( String employmentCategory ) {
		this.employmentCategory = employmentCategory;
	}

	public int getAgeCategory() {
		return ageCategory;
	}

	public void setAgeCategory( int ageCategory ) {
		this.ageCategory = ageCategory;
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


}
