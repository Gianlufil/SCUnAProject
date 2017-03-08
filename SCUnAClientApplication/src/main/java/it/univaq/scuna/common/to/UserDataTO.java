package it.univaq.scuna.common.to;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserDataTO {

	private String name;

	private String surname;

	private String badgeID;

	private String birthdate;

	public UserDataTO() {
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public String getBadgeID() {
		return badgeID;
	}

	public void setBadgeID(final String badgeID) {
		this.badgeID = badgeID;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(final String birthdate) {
		this.birthdate = birthdate;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof UserDataTO) {
			UserDataTO user = (UserDataTO) o;
			return this.badgeID.equals(user.getBadgeID());
		} else {
			return false;
		}
	}
	
}
