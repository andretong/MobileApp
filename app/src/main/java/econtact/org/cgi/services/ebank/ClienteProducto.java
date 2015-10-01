package econtact.org.cgi.services.ebank;

import java.io.Serializable;

public class ClienteProducto implements Serializable {
	
	private int rut = 0;
	private String numeroProducto = "";	
	private int tipoProducto = 0;
	private int saldo = 0;
	
	
	public ClienteProducto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public int getRut() {
		return rut;
	}


	public void setRut(int rut) {
		this.rut = rut;
	}


	public String getNumeroProducto() {
		return numeroProducto;
	}


	public void setNumeroProducto(String numeroProducto) {
		this.numeroProducto = numeroProducto;
	}


	public int getTipoProducto() {
		return tipoProducto;
	}


	public void setTipoProducto(int tipoProducto) {
		this.tipoProducto = tipoProducto;
	}


	public int getSaldo() {
		return saldo;
	}


	public void setSaldo(int saldo) {
		this.saldo = saldo;
	}
	
	
	

}
