package econtact.org.cgi.ivr;

import java.io.Serializable;

public class NodeFunctionAttributes implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nodeSubroutine;
	private String nodeURL;
	private int nodeBack;
	private String nextNodeID;
	private String nextNode;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getNodeSubroutine() {
		return nodeSubroutine;
	}
	public String getNodeURL() {
		return nodeURL;
	}
	public int getNodeBack() {
		return nodeBack;
	}
	public String getNextNodeID() {
		return nextNodeID;
	}
	public String getNextNode() {
		return nextNode;
	}
	public void setNodeSubroutine(String nodeSubroutine) {
		this.nodeSubroutine = nodeSubroutine;
	}
	public void setNodeURL(String nodeURL) {
		this.nodeURL = nodeURL;
	}
	public void setNodeBack(int nodeBack) {
		this.nodeBack = nodeBack;
	}
	public void setNextNodeID(String nextNodeID) {
		this.nextNodeID = nextNodeID;
	}
	public void setNextNode(String nextNode) {
		this.nextNode = nextNode;
	}
	
	
}
