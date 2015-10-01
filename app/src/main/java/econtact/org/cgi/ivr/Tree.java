package econtact.org.cgi.ivr;

import java.io.Serializable;

public class  Tree  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String treeID;
	private String treeName;
	private String treeDescription;
	private  String startingNodeID;
	private  StartingNode startingNode;
	
	public String getTreeID() {
		return treeID;
	}
	public void setTreeID(String treeID) {
		this.treeID = treeID;
	}
	
	public StartingNode getStartingNode() {
		return startingNode;
	}
	public void setStartingNode(StartingNode startingNode) {
		this.startingNode = startingNode;
	}
	public String getTreeName() {
		return treeName;
	}
	public String getTreeDescription() {
		return treeDescription;
	}
	public String getStartingNodeID() {
		return startingNodeID;
	}
	public void setTreeName(String treeName) {
		this.treeName = treeName;
	}
	public void setTreeDescription(String treeDescription) {
		this.treeDescription = treeDescription;
	}
	public void setStartingNodeID(String startingNodeID) {
		this.startingNodeID = startingNodeID;
	}
	
}
