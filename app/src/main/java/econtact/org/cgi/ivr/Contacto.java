package econtact.org.cgi.ivr;

public class Contacto {
	
	private PropertiesValue[] propertiesValue;
	public PropertiesValue[] getPropertiesValue() {
		return propertiesValue;
	}

	public void setPropertiesValue(PropertiesValue[] propertiesValue) {
		this.propertiesValue = propertiesValue;
	}

	public Actions[] getActions() {
		return actions;
	}

	public void setActions(Actions[] actions) {
		this.actions = actions;
	}

	private String errCode,errDesc,optionID;
	Actions[] actions;

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrDesc() {
		return errDesc;
	}

	public void setErrDesc(String errDesc) {
		this.errDesc = errDesc;
	}

	public String getOptionID() {
		return optionID;
	}

	public void setOptionID(String optionID) {
		this.optionID = optionID;
	}
	
	
}
