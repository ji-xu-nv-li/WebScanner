package com.wsbc.process;

import java.util.LinkedHashMap;

import com.wsbc.process.constant.ProcessConstant;

public abstract class ListNode extends Node {
	
	private LinkedHashMap<String, Node> nodes = new LinkedHashMap<String, Node>();
	
	public void addNode(Node node) {
		String name = node.getAttribute(ProcessConstant.NAME);
		nodes.put(name, node);
		node.setNodes(this);
	}

	public Node getNode(String name) {
		return nodes.get(name);
	}
	
}
