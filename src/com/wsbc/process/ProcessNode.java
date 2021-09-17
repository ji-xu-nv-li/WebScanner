package com.wsbc.process;

import com.wsbc.process.constant.ProcessConstant;


public class ProcessNode extends ListNode {

	@Override
	public void doHanlder() {
		String startName = this.getAttribute(ProcessConstant.START);
		String nextName = startName;
		while (nextName != null && !"".equals(nextName)) {
			Node node = this.getNode(nextName);
			if (node == null) {
				throw new RuntimeException("无法找到节点名称：" + nextName);
			}
			node.handler();
			nextName = node.getAttribute(ProcessConstant.NEXT);
		}
	}
	
}
