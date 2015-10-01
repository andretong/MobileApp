package econtact.org.cgi.services;

public class RespuestaGeneral {
	private int codigo = 99;
	private String msj = "";
	private boolean respuesta = false;
	private Object objeto = null;
	
	
	public RespuestaGeneral(int codigo, String msj, boolean respuesta, Object objeto) {
		super();
		this.codigo = codigo;
		this.msj = msj;
		this.respuesta = respuesta;
		this.objeto = objeto;
	}
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getMsj() {
		return msj;
	}
	public void setMsj(String msj) {
		this.msj = msj;
	}
	public boolean isRespuesta() {
		return respuesta;
	}
	public void setRespuesta(boolean respuesta) {
		this.respuesta = respuesta;
	}

	public Object getObjeto() {
		return objeto;
	}

	public void setObjeto(Object objeto) {
		this.objeto = objeto;
	}
	
	
	
}
