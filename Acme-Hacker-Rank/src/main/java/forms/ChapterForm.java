package forms;

import domain.Area;
import security.UserAccount;

public class ChapterForm {
	
	private UserAccount	userAccount;
	private String		name;
	private String		middlename;
	private String		surname;
	private String		phoneNumber;
	private String		email;
	private String		address;
	private String		photo;
	private String		title;
	private Area		area;
	private boolean     accepted;

	// Getters and Setters
	public UserAccount getUserAccount() {
		return this.userAccount;
	}
	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	public String getName() {
		return this.name;
	}
	public void setName(final String name) {
		this.name = name;
	}

	public String getMiddlename() {
		return this.middlename;
	}
	public void setMiddlename(final String middlename) {
		this.middlename = middlename;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return this.email;
	}
	public void setEmail(final String email) {
		this.email = email;
	}

	public String getSurname() {
		return this.surname;
	}
	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public String getAddress() {
		return this.address;
	}
	public void setAddress(final String address) {
		this.address = address;
	}

	public String getPhoto() {
		return this.photo;
	}
	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	public String getTitle() {
		return this.title;
	}
	public void setTitle(final String title) {
		this.title = title;
	}

	public Area getArea() {
		return this.area;
	}
	public void setArea(final Area area) {
		this.area = area;
	}
	
	public boolean isAccepted() {
		return this.accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	@Override
	public String toString() {
		return "RegistrationFormCustomer [Name=" + this.name + "]";
	}

}
