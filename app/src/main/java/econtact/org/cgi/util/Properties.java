package econtact.org.cgi.util;

/**
 * Clase de propiedades , se guarda informacion de los servidores
 * @author      Manuel Reyes <mareyes@e-contact.cl>
 * @version     1.0                               
 * @since       2013-06-21        
 */
public class Properties {

	public static String SERVIDORBASE_NODOSMOVIL;// = "http://54.232.197.28:8080/wsivr";
	public static String SERVIDORBASE_WS_EBANK;// = "http://54.232.197.28:8080/WS_eBank";
	public static String SERVIDORBASE_WS_C2C;// = "http://54.232.197.28:8080/wsdatademo";
	public static String MOVIL="";
	public static String COMENTARIO="";

	public static String getServidorNodosMovil() {
		return SERVIDORBASE_NODOSMOVIL;
	}

	public static String getServidorDatosCliente() {
		return SERVIDORBASE_WS_EBANK;
	}

	public static String getServidorC2C() {
		return SERVIDORBASE_WS_C2C;
	}


	public static String getMovil(){
		return MOVIL;
	}
	
	public static String getComentario(){
		return COMENTARIO;
	}
	
	public static void setMovil(String movil){
		MOVIL=movil;
	}
}
