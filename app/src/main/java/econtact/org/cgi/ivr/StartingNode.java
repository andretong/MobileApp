package econtact.org.cgi.ivr;

import java.io.Serializable;

public class StartingNode implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String getNodeID() {
		return nodeID;
	}
	public void setNodeID(String nodeID) {
		this.nodeID = nodeID;
	}
	public NodeMenuAttributes getOptions() {
		return nodeMenuAttributes;
	}
	public void setOptions(NodeMenuAttributes options) {
		this.nodeMenuAttributes = options;
	}
	
	public String getNodeName() {
		return nodeName;
	}
	public String getNodeDescription() {
		return nodeDescription;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public void setNodeDescription(String nodeDescription) {
		this.nodeDescription = nodeDescription;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public NodeMenuAttributes getNodeMenuAttributes() {
		return nodeMenuAttributes;
	}
	public String getNodeTextAttributes() {
		return nodeTextAttributes;
	}
	public String getNodeFunctionAttributes() {
		return nodeFunctionAttributes;
	}
	public void setNodeMenuAttributes(NodeMenuAttributes nodeMenuAttributes) {
		this.nodeMenuAttributes = nodeMenuAttributes;
	}
	public void setNodeTextAttributes(String nodeTextAttributes) {
		this.nodeTextAttributes = nodeTextAttributes;
	}
	public void setNodeFunctionAttributes(String nodeFunctionAttributes) {
		this.nodeFunctionAttributes = nodeFunctionAttributes;
	}
	private String nodeID;
	private String nodeName;
	private String nodeDescription;
	private String nodeType;
	private NodeMenuAttributes nodeMenuAttributes;
	private String nodeTextAttributes;
	private String nodeFunctionAttributes;
	
  
}
