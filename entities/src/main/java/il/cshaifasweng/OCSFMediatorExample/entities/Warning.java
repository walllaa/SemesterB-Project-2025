package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.time.LocalTime;

public class Warning implements Serializable {

	private static final long serialVersionUID = -8224097662914849956L;

	private String warningText;
	private LocalTime issuedAt;

	public Warning(String warningText) {
		this.warningText = warningText;
		this.issuedAt = LocalTime.now();
	}

	public String getWarningText() {
		return warningText;
	}

	public void setWarningText(String warningText) {
		this.warningText = warningText;
	}

	public LocalTime getIssuedAt() {
		return issuedAt;
	}
}
