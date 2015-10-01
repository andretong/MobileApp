package econtact.org.android;

import android.content.Context;
import android.widget.TextView;
/**
 * Clase que se utiliza en el menu para listar las distintas opciones(facturacion, deuda, etc).
 * @author      Hector Quezada <hector.quezada@mobiletouch.cl>
 * @version     1.0                               
 * @since       2012-11-10        
 */
public class TextviewMenu  extends TextView{
	private String url, icon,descripcion;
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public TextviewMenu(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	
}
