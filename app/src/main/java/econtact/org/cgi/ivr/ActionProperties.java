package econtact.org.cgi.ivr;

import java.io.Serializable;

public class ActionProperties implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String actionID,actionName,actionDescription,actionType,numeroCliente,numeroTransferencia,cola,habilidad,audio,url,
	tiempoEstimadoEspera,cantidadAgentesDisponibles,cantidadLlamadasEnCola;
	private RangoHorarios[] rangosHorarios;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getActionID() {
		return actionID;
	}
	public String getActionName() {
		return actionName;
	}
	public String getActionDescription() {
		return actionDescription;
	}
	public String getActionType() {
		return actionType;
	}
	public String getNumeroCliente() {
		return numeroCliente;
	}
	public String getNumeroTransferencia() {
		return numeroTransferencia;
	}
	public String getCola() {
		return cola;
	}
	public String getHabilidad() {
		return habilidad;
	}
	public String getAudio() {
		return audio;
	}
	public String getUrl() {
		return url;
	}
	public RangoHorarios[] getRangosHorarios() {
		return rangosHorarios;
	}
	public void setActionID(String actionID) {
		this.actionID = actionID;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public void setActionDescription(String actionDescription) {
		this.actionDescription = actionDescription;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public void setNumeroCliente(String numeroCliente) {
		this.numeroCliente = numeroCliente;
	}
	public void setNumeroTransferencia(String numeroTransferencia) {
		this.numeroTransferencia = numeroTransferencia;
	}
	public void setCola(String cola) {
		this.cola = cola;
	}
	public void setHabilidad(String habilidad) {
		this.habilidad = habilidad;
	}
	public void setAudio(String audio) {
		this.audio = audio;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setRangosHorarios(RangoHorarios[] rangosHorarios) {
		this.rangosHorarios = rangosHorarios;
	}
	public String getTiempoEstimadoEspera() {
		return tiempoEstimadoEspera;
	}
	public String getCantidadAgentesDisponibles() {
		return cantidadAgentesDisponibles;
	}
	public String getCantidadLlamadasEnCola() {
		return cantidadLlamadasEnCola;
	}
	public void setTiempoEstimadoEspera(String tiempoEstimadoEspera) {
		this.tiempoEstimadoEspera = tiempoEstimadoEspera;
	}
	public void setCantidadAgentesDisponibles(String cantidadAgentesDisponibles) {
		this.cantidadAgentesDisponibles = cantidadAgentesDisponibles;
	}
	public void setCantidadLlamadasEnCola(String cantidadLlamadasEnCola) {
		this.cantidadLlamadasEnCola = cantidadLlamadasEnCola;
	}
	
}
