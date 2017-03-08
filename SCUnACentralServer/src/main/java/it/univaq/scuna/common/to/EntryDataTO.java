package it.univaq.scuna.common.to;

import it.univaq.scuna.common.types.EntryDataType;

public class EntryDataTO {

	private Long timestamp;

	private String badgeNumber;

	private String areaCode;

	private EntryDataType type;

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getBadgeNumber() {
		return badgeNumber;
	}

	public void setBadgeNumber(final String badgeNumber) {
		this.badgeNumber = badgeNumber;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public EntryDataType getType() {
		return type;
	}

	public void setType(EntryDataType type) {
		this.type = type;
	}
}
