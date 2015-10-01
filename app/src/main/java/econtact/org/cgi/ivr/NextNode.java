package econtact.org.cgi.ivr;

import java.io.Serializable;

public class NextNode implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String nodeId;
	private String nodeName;
	private String nodeDescription;
	private String nodeType;
	private String nodeMenuAttributes;
	private String nodeTextAttributes;
	private NodeFunctionAttributes nodeFunctionAttributes;
	public String getNodeId() {
		return nodeId;
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
	public String getNodeMenuAttributes() {
		return nodeMenuAttributes;
	}
	public String getNodeTextAttributes() {
		return nodeTextAttributes;
	}
	public NodeFunctionAttributes getNodeFunctionAttributes() {
		return nodeFunctionAttributes;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
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
	public void setNodeMenuAttributes(String nodeMenuAttributes) {
		this.nodeMenuAttributes = nodeMenuAttributes;
	}
	public void setNodeTextAttributes(String nodeTextAttributes) {
		this.nodeTextAttributes = nodeTextAttributes;
	}
	public void setNodeFunctionAttributes(NodeFunctionAttributes nodeFunctionAttributes) {
		this.nodeFunctionAttributes = nodeFunctionAttributes;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	

}
