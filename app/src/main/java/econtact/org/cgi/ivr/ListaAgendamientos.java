package econtact.org.cgi.ivr;

import java.io.Serializable;

public class ListaAgendamientos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Agendamientos[] value;

	public Agendamientos[] getValue() {
		return value;
	}

	public void setValue(Agendamientos[] value) {
		this.value = value;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
