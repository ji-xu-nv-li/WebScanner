package com.wsbc.process;

import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

import com.wsbc.process.constant.ProcessConstant;
import com.wsbc.util.file.WsbcXmlUtil;

public class Process {
	
//	private Map<String, Node> nodes = new LinkedHashMap<String, Node>();
//	
//	private String name = "";
	
	private ProcessNode processNode = null;
	
	public Process() {}
	
	public Process(String filePath) {
		Document document = WsbcXmlUtil.init(filePath);
		Element element = document.getRootElement();
		processNode = (ProcessNode)parseProcessXML(element);
	}
	
	@SuppressWarnings("unchecked")
	private Node parseProcessXML(Element element) {
		String nodeType = element.getName();
		Node node = null;
		switch (nodeType) {
			case ProcessConstant.PROCESS:
				node = new ProcessNode();
				break;
			case ProcessConstant.OPERATION:
				node = new OperationNode();
				break;
			case ProcessConstant.FOR_EACH:
				node = new ForEachListNode();
				break;
			default:
				throw new RuntimeException("未知标签[" + nodeType + "]类型或标签配置错误");
		}
		
		List<Attribute> attributes = element.attributes();
		for (Attribute attr : attributes) {
			node.putAttribute(attr.getName(), attr.getValue());
		}
		
		List<Element> elements = element.elements();
		for (Element e : elements) {
			String nodeTypeTemp = e.getName();
			switch (nodeTypeTemp) {
				case ProcessConstant.DATA:
					attributes = element.attributes();
					for (Attribute attr : attributes) {
						node.putRequest(attr.getName(), attr.getValue());
					}
					break;
				case ProcessConstant.OUT:
					attributes = element.attributes();
					for (Attribute attr : attributes) {
						node.putOut(attr.getName(), attr.getValue());
					}
					break;
				default:
					if (node instanceof ListNode) {
						Node n = parseProcessXML(e);
						((ListNode)node).addNode(n);
					}
					break;
			}
		}
		return node;
	}
	
	public void handler() {
		processNode.doHanlder();
	}
	
}
