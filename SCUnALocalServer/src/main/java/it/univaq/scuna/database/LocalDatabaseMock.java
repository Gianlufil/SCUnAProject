package it.univaq.scuna.database;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.univaq.scuna.common.to.CrowdingDataTO;
import it.univaq.scuna.common.to.EntryDataTO;

public class LocalDatabaseMock {

	private Map<String, List<String>> areaAllowedGroups;

	private Map<String, CrowdingDataTO> crowdingData;

	private Map<String, List<EntryDataTO>> restrictedAreaAccesses;

	public LocalDatabaseMock() {
		this.areaAllowedGroups = new HashMap<String, List<String>>();
		this.crowdingData = new HashMap<String, CrowdingDataTO>();
		this.restrictedAreaAccesses = new HashMap<String, List<EntryDataTO>>();
	}

	public Map<String, List<String>> getAreaAllowedGroups() {
		return areaAllowedGroups;
	}

	public void setAreaAllowedGroups(Map<String, List<String>> areaAllowedGroups) {
		this.areaAllowedGroups = areaAllowedGroups;
	}

	public List<String> getAllowedGroups(final String areaCode) {
		return this.areaAllowedGroups.get(areaCode);
	}

	public Map<String, CrowdingDataTO> getCrowdingData() {
		return crowdingData;
	}

	public Map<String, List<EntryDataTO>> getRestrictedAreaAccesses() {
		return restrictedAreaAccesses;
	}

	public void setRestrictedAreaAccesses(final Map<String, List<EntryDataTO>> restrictedAreaAccesses) {
		this.restrictedAreaAccesses = restrictedAreaAccesses;
	}

	public void setCrowdingData(final Map<String, CrowdingDataTO> crowdingData) {
		this.crowdingData = crowdingData;
	}

	public void addCrowdingData(final CrowdingDataTO crowdingDataTO) {
		this.crowdingData.put(crowdingDataTO.getAreaCode(), crowdingDataTO);
	}

	public void addAccess(final EntryDataTO entryDataTO) {
		if (restrictedAreaAccesses.get(entryDataTO.getAreaCode()) == null) {
			restrictedAreaAccesses.put(entryDataTO.getAreaCode(), new LinkedList<EntryDataTO>());
		}
		restrictedAreaAccesses.get(entryDataTO.getAreaCode()).add(entryDataTO);	}

}
