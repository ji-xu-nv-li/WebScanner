package com.wsbc.process;

import java.util.LinkedHashMap;

import com.wsbc.process.constant.ProcessConstant;

public abstract class ListNode extends Node {
	
	private LinkedHashMap<String, Node> nodes = new LinkedHashMap<String, Node>();
	
	public void addNode(Node node) {
		String name = node.getAttribute(ProcessConstant.Attribute.NAME);
		nodes.put(name, node);
		node.setParent(this);
	}

	public Node getNode(String name) {
		return nodes.get(name);
	}

	public LinkedHashMap<String, Node> getNodes() {
		return nodes;
	}

	public void setNodes(LinkedHashMap<String, Node> nodes) {
		this.nodes = nodes;
	}
	
}
