package com.wsbc.process;

import com.wsbc.process.constant.ProcessConstant;


public class ForEachListNode extends ListNode {

	@Override
	public void doHanlder() {
		String startName = this.getAttribute(ProcessConstant.START);
		String nextName = startName;
		String[] values = this.getAttribute(ProcessConstant.ForEach.ITEMS).split(",");
		String var = this.getAttribute(ProcessConstant.ForEach.VAR);
		for (int index = 0, count = values.length; index < count; index++) {
			while (nextName != null && !"".equals(nextName)) {
				Node node = this.getNode(nextName);
				node.putRequest(var, values[index]);
				node.putRequest(ProcessConstant.ForEach.INDEX, index + "");
				node.putRequest(ProcessConstant.ForEach.COUNT, count + "");
				node.handler();
				nextName = node.getAttribute(ProcessConstant.NEXT);
			}
			// 重置，进入下一循环
			nextName = startName;
		}
	}

}
