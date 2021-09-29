package com.wsbc.process.util;

import com.wsbc.process.ListNode;
import com.wsbc.process.Node;

public class ProcessUtil {

	/**
	 * 
	 * 方法描述：获取流程内节点输出的数据。
	 * 作者： yxm
	 * 创建时间： 2020年2月7日  下午6:03:57
	 * @param node
	 * @param location . 隔开表示多层级
	 * 待验证
	 */
	public static Object getResponseValue(Node node, String location) {
		// 无法获取比自己多层次的数据
		String[] strs = location.split("\\.");
		ListNode listNode = null;
		try {
			if (strs.length > 1) {
				// 获取节点列表
				for (int index = 0 ; index < strs.length - 1 ; index++) {
					node = node.getParent();
				}
				listNode = (ListNode) node;
				// 获取对应节点
				for (int index = 0 ; index < strs.length - 2 ; index++) {
					listNode = (ListNode) listNode.getNode(strs[index]);
				}
				node = listNode.getNode(strs[strs.length - 2]);
				// 获取节点数据
				return node.getResponse(strs[strs.length - 1]);
			} else {
				// 否则是当前节点request里的数据
				return node.getRequest().get(strs[0]);
			}
		} catch (Throwable e) {
			throw new RuntimeException("查找变量[" + location + "]发生错误", e);
		}
	}
}
