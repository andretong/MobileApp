package econtact.org.cgi.ivr;

import java.io.Serializable;

public class PropertiesValue  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9016132402417951809L;
	private String property, value;

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
