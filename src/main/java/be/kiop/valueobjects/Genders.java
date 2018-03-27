package be.kiop.valueobjects;

public enum Genders {
	FEMALE(1),
	MALE(2),
	OTHER(3);
	
	private int genderValue;
	
	Genders (int genderValue){
		this.genderValue = genderValue;
	}
	
	public int getGenderValue() {
		return genderValue;
	}
}
