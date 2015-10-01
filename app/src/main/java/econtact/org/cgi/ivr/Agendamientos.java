package econtact.org.cgi.ivr;

import java.io.Serializable;

public class Agendamientos implements Serializable {
	
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String attemps;
	private String attempsDelay;
	private String id;
	private String lastUpdateTime;
	private String mobileNumber;
	private String queue;
	private RequestType requestType;
	private Status status;
	private String endDate;
	private String startDate;
	
	public String getAttemps() {
		return attemps;
	}
	public String getAttempsDelay() {
		return attempsDelay;
	}
	public String getId() {
		return id;
	}
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public String getQueue() {
		return queue;
	}
	public RequestType getRequestType() {
		return requestType;
	}
	public Status getStatus() {
		return status;
	}
	public String getEndDate() {
		return endDate;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setAttemps(String attemps) {
		this.attemps = attemps;
	}
	public void setAttempsDelay(String attempsDelay) {
		this.attempsDelay = attempsDelay;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public void setQueue(String queue) {
		this.queue = queue;
	}
	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
