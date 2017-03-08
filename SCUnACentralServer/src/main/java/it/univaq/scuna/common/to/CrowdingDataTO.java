package it.univaq.scuna.common.to;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CrowdingDataTO {

	private String areaCode;

	private int presences;

	private long timestamp;

	public CrowdingDataTO() {}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(final String areaCode) {
		this.areaCode = areaCode;
	}

	public int getPresences() {
		return presences;
	}

	public void setPresences(final int presences) {
		this.presences = presences;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
	}

}
