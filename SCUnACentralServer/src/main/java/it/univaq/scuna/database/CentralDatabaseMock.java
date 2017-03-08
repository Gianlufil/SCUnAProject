package it.univaq.scuna.database;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.univaq.scuna.common.to.ConfirmationUserDataTO;
import it.univaq.scuna.common.to.CrowdingDataTO;
import it.univaq.scuna.common.to.EntryDataTO;
import it.univaq.scuna.common.to.UserDataTO;

public class CentralDatabaseMock {
	
	private Map<String, List<String>> areaAllowedLists;

	private Map<String, List<String>> examLists;

	private Map<String, UserDataTO> users;

	private List<ConfirmationUserDataTO> examConfirmations;

	private List<ConfirmationUserDataTO> lessonConfirmations;

	private Map<String, List<EntryDataTO>> restrictedAreaAccesses;

	private Map<String, CrowdingDataTO> crowdingData;

	public CentralDatabaseMock() {
		this.areaAllowedLists = new HashMap<String, List<String>>();
		this.examLists = new HashMap<String, List<String>>();
		this.users = new HashMap<String, UserDataTO>();
		this.examConfirmations = new LinkedList<ConfirmationUserDataTO>();
		this.lessonConfirmations = new LinkedList<ConfirmationUserDataTO>();
		this.restrictedAreaAccesses = new HashMap<String, List<EntryDataTO>>();
		this.crowdingData = new HashMap<String, CrowdingDataTO>();
	}

	public Map<String, List<String>> getAreaAllowedLists() {
		return areaAllowedLists;
	}

	public void setAreaAllowedLists(final Map<String, List<String>> accessLists) {
		this.areaAllowedLists = accessLists;
	}

	public Map<String, List<String>> getExamLists() {
		return examLists;
	}

	public void setExamLists(final Map<String, List<String>> examLists) {
		this.examLists = examLists;
	}

	public Map<String, UserDataTO> getUserList() {
		return users;
	}

	public void setUserList(final Map<String, UserDataTO> users) {
		this.users = users;
	}

	public List<ConfirmationUserDataTO> getExamConfirmations() {
		return examConfirmations;
	}

	public void setExamConfirmations(final List<ConfirmationUserDataTO> examConfirmations) {
		this.examConfirmations = examConfirmations;
	}

	public void addExamConfirmation(final ConfirmationUserDataTO examConfirmation) {
		this.examConfirmations.add(examConfirmation);
	}

	public List<ConfirmationUserDataTO> getLessonConfirmations() {
		return lessonConfirmations;
	}

	public void setLessonConfirmations(final List<ConfirmationUserDataTO> lessonConfirmations) {
		this.lessonConfirmations = lessonConfirmations;
	}

	public void addLessonConfirmation(final ConfirmationUserDataTO lessonConfirmation) {
		this.lessonConfirmations.add(lessonConfirmation);
	}

	public Map<String, UserDataTO> getUsers() {
		return users;
	}

	public void setUsers(final Map<String, UserDataTO> users) {
		this.users = users;
	}

	public Map<String, List<EntryDataTO>> getRestrictedAreaAccesses() {
		return restrictedAreaAccesses;
	}

	public void setRestrictedAreaAccesses(final Map<String, List<EntryDataTO>> restrictedAreaAccesses) {
		this.restrictedAreaAccesses = restrictedAreaAccesses;
	}

	public void addRestrictedAreaAccess(final EntryDataTO entryDataTO) {
		if (this.restrictedAreaAccesses.get(entryDataTO.getAreaCode()) == null) {
			this.restrictedAreaAccesses.put(entryDataTO.getAreaCode(), new LinkedList<EntryDataTO>());
		}
		this.restrictedAreaAccesses.get(entryDataTO).add(entryDataTO);
	};

	public void addRestrictedAreaAccess(final String areaCode, final List<EntryDataTO> entryDataList) {
		if (this.restrictedAreaAccesses.get(areaCode) == null) {
			this.restrictedAreaAccesses.put(areaCode, new LinkedList<EntryDataTO>());
		}
		this.restrictedAreaAccesses.get(areaCode).addAll(entryDataList);
	};

	public Map<String, CrowdingDataTO> getCrowdingData() {
		return crowdingData;
	}

	public void setCrowdingData(final Map<String, CrowdingDataTO> crowdingData) {
		this.crowdingData = crowdingData;
	}

	public void updateCrowdingData(final CrowdingDataTO crowdingDataTO) {
		this.crowdingData.put(crowdingDataTO.getAreaCode(), crowdingDataTO);
	}

	public UserDataTO getUser(final String badgeNumber) {
		return this.users.get(badgeNumber);
	}

	// INSERT TEST DATA INTO THE DATABASE
	public void initDatabase() {
		// ACCESS LISTS
		List<String> alist1 = new LinkedList<String>();
		alist1.add("student");
		alist1.add("technician");
		alist1.add("professor");
		alist1.add("phd student");
		areaAllowedLists.put("R0001", alist1);
		List<String> alist2 = new LinkedList<String>();
		alist2.add("technician");
		alist2.add("professor");
		alist2.add("phd student");
		areaAllowedLists.put("R0002", alist2);

		// EXAM LISTS
		List<String> elist1 = new LinkedList<String>();
		elist1.add("0000");
		elist1.add("0001");
		elist1.add("0002");
		elist1.add("0003");
		elist1.add("236538");
		examLists.put("EX0021", elist1);
		
		List<String> elist2 = new LinkedList<String>();
		elist2.add("0004");
		elist2.add("0005");
		elist2.add("0006");
		elist2.add("0007");
		examLists.put("EX0034", elist2);
		
		List<String> elist3 = new LinkedList<String>();
		elist3.add("0008");
		elist3.add("0009");
		elist3.add("0010");
		elist3.add("0011");
		examLists.put("EX0063", elist3);

		// USERS
		UserDataTO userDataTO = new UserDataTO();
		userDataTO.setBadgeID("0000");
		userDataTO.setName("Pietro");
		userDataTO.setSurname("Smusi");
		userDataTO.setBirthdate("25/12/1957");
		users.put(userDataTO.getBadgeID(), userDataTO);
		
		UserDataTO userDataTO1 = new UserDataTO();
		userDataTO1.setBadgeID("0001");
		userDataTO1.setName("Orazio");
		userDataTO1.setSurname("Graziosi");
		userDataTO1.setBirthdate("15/11/1997");
		users.put(userDataTO1.getBadgeID(), userDataTO1);
		
		UserDataTO userDataTO2 = new UserDataTO();
		userDataTO2.setBadgeID("0002");
		userDataTO2.setName("Guliano");
		userDataTO2.setSurname("Ranalli");
		userDataTO2.setBirthdate("25/12/1987");
		users.put(userDataTO2.getBadgeID(), userDataTO2);
		
		UserDataTO userDataTO3 = new UserDataTO();
		userDataTO3.setBadgeID("0003");
		userDataTO3.setName("Kenneth");
		userDataTO3.setSurname("Caselli");
		userDataTO3.setBirthdate("25/12/0000");
		users.put(userDataTO3.getBadgeID(), userDataTO3);	
		
		UserDataTO userDataTO4 = new UserDataTO();
		userDataTO4.setBadgeID("236538");
		userDataTO4.setName("Alessia");
		userDataTO4.setSurname("Bianchi");
		userDataTO4.setBirthdate("14/09/1994");
		users.put(userDataTO4.getBadgeID(), userDataTO4);
	
	}
}
