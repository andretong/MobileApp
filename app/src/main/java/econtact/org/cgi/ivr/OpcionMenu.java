package econtact.org.cgi.ivr;

import java.io.Serializable;

public class OpcionMenu implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getOptionID() {
		return optionID;
	}
	public void setOptionID(String optionID) {
		this.optionID = optionID;
	}
	public String getOptionKey() {
		return optionKey;
	}
	public void setOptionKey(String optionKey) {
		this.optionKey = optionKey;
	}

	public String getNextNodeID() {
		return nextNodeID;
	}
	public void setNextNodeID(String nextNodeID) {
		this.nextNodeID = nextNodeID;
	}
	public NextNode getNextNode() {
		return nextNode;
	}
	public void setNextNode(NextNode nextNode) {
		this.nextNode = nextNode;
	}
	public String getOptionName() {
		return optionName;
	}
	public String getOptionDescription() {
		return optionDescription;
	}
	public int getOptionOrder() {
		return optionOrder;
	}
	public String getOptionAudio() {
		return optionAudio;
	}
	public int getOptionSayKey() {
		return optionSayKey;
	}
	public String getOptionImage() {
		return optionImage;
	}
	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}
	public void setOptionDescription(String optionDescription) {
		this.optionDescription = optionDescription;
	}
	public void setOptionOrder(int optionOrder) {
		this.optionOrder = optionOrder;
	}
	public void setOptionAudio(String optionAudio) {
		this.optionAudio = optionAudio;
	}
	public void setOptionSayKey(int optionSayKey) {
		this.optionSayKey = optionSayKey;
	}
	public void setOptionImage(String optionImage) {
		this.optionImage = optionImage;
	}
	
	private String optionID;
	private String optionName;
	private String optionDescription;
	private int optionOrder;
	private String optionAudio;
	private String optionKey;
	private int optionSayKey;
	private String optionImage;
	private String nextNodeID;
	private NextNode nextNode;
	
	
}
