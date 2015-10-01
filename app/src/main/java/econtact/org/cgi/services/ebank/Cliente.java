package econtact.org.cgi.services.ebank;

import java.io.Serializable;

public class Cliente implements Serializable {
	
	private int rut = 0;
	private int dv = 0;
	private String nombre = "";
	private String apellido = "";
	private int estatus = 0;
	private int tipo_cliente = 0;
	private String correo = "";
	private String telefono = "";
	
	
	public Cliente() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	public int getRut() {
		return rut;
	}
	public void setRut(int rut) {
		this.rut = rut;
	}
	public int getDv() {
		return dv;
	}
	public void setDv(int dv) {
		this.dv = dv;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}


	public int getEstatus() {
		return estatus;
	}


	public void setEstatus(int estatus) {
		this.estatus = estatus;
	}


	public int getTipo_cliente() {
		return tipo_cliente;
	}


	public void setTipo_cliente(int tipo_cliente) {
		this.tipo_cliente = tipo_cliente;
	}


	public String getCorreo() {
		return correo;
	}


	public void setCorreo(String correo) {
		this.correo = correo;
	}


	public String getTelefono() {
		return telefono;
	}


	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	

}
