package econtact.org.cgi.ivr;

import java.io.Serializable;

public class Actions implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errCode;
	private String errDesc;
	private ActionProperties[] actions;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getErrCode() {
		return errCode;
	}
	public String getErrDesc() {
		return errDesc;
	}
	public ActionProperties[] getActions() {
		return actions;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public void setErrDesc(String errDesc) {
		this.errDesc = errDesc;
	}
	public void setActions(ActionProperties[] actions) {
		this.actions = actions;
	}
	
	
}
