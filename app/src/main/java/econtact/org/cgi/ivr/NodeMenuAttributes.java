package econtact.org.cgi.ivr;

import java.io.Serializable;

public class NodeMenuAttributes implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private OpcionMenu[] options;

	public OpcionMenu[] getOptions() {
		return options;
	}

	public void setOptions(OpcionMenu[] options) {
		this.options = options;
	}
}
