package econtact.org.cgi.ivr;

import java.io.Serializable;

public class RangoHorarios implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String diaSemana,horaInicio,horaTermino;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getDiaSemana() {
		return diaSemana;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public String getHoraTermino() {
		return horaTermino;
	}

	public void setDiaSemana(String diaSemana) {
		this.diaSemana = diaSemana;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public void setHoraTermino(String horaTermino) {
		this.horaTermino = horaTermino;
	}
	

}
