package it.univaq.scuna.common.to;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class ConfirmationUserDataTO {

	private String badgeNumber;

	private String code;

	private long timestamp;

	public ConfirmationUserDataTO() {}

	public String getBadgeNumber() {
		return badgeNumber;
	}

	public void setBadgeNumber(final String badgeNumber) {
		this.badgeNumber = badgeNumber;
	}

	public String getCode() {
		return code;
	}

	public void setCode(final String code) {
		this.code = code;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(final long timestamp) {
		this.timestamp = timestamp;
	}
}
