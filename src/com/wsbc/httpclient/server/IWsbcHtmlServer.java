package com.wsbc.httpclient.server;

import java.io.Serializable;
import java.util.List;

import com.wsbc.httpclient.entity.WsbcGroup;
import com.wsbc.httpclient.entity.WsbcHtml;
import com.wsbc.httpclient.entity.WsbcUrl;
import com.wsbc.httpclient.entity.WsbcUser;
import com.wsbc.util.Page;

public interface IWsbcHtmlServer {

	/**
	 * 方法描述：保存文档
	 * 创建时间：2016年3月27日  上午8:46:31
	 * 作者：yxm
	 */
	public void save(WsbcHtml html);
	
	/**
	 * 方法描述：保存一组的网页
	 * @data 2016年4月28日  上午9:19:50
	 * @author yxm
	 */
	public void save(List<WsbcHtml> htmls);
	
	/**
	 * 方法描述：保存文档
	 * @param 访问路径
	 * @data 2016年4月12日  下午2:07:34
	 * @author yxm
	 */
	public void save(String url);
	
	/**
	 * 方法描述：保存文档
	 * @param wsbcUrl 访问路径
	 * @data 2016年4月12日  下午2:08:05
	 * @author yxm
	 */
	public void save(WsbcUrl wsbcUrl);

	/**
	 * 方法描述：根据ID获取网页信息
	 * @data 2016年5月18日  下午3:10:58
	 * @author yxm
	 */
	public WsbcHtml getHtmlById(Integer id);
	
	/**
	 * 方法描述：获得用户下的所有扫描页面，按响应时间倒叙显示，分页获取
	 * @data 2016年4月12日  下午2:13:04
	 * @author yxm
	 */
	public List<WsbcHtml> getAllUserHtml(WsbcUser wsbcUser,Page page);
	
	/**
	 * 方法描述：获得系统下的所有页面，按组分开 
	 * @data 2016年4月12日  下午2:15:55
	 * @return Map<Timestamp,List<WsbcHtml>> key 表示一组     value 表示一组内的页面
	 * @author yxm
	 */
	public List<List<WsbcHtml>> getAllHtml(Page page);
	
	/**
	 * 方法描述：获得用户下，同一组内扫描的页面，分页获取，默认获取最后一次扫描的结果
	 * @param groupTag 同一组标志
	 * @data 2016年4月13日  下午2:15:42
	 * @author yxm
	 */
	public List<WsbcHtml> getGroupHtml(WsbcUser wsbcUser,WsbcGroup wsbcGroup,Page page);
	
	/**
	 * 方法描述：删除文档
	 * @data 2016年4月25日  上午9:40:46
	 * @author yxm
	 */
	public void delete(WsbcHtml wsbcHtml);
	
	/**
	 * 方法描述：根据ID删除
	 * @data 2016年4月25日  上午10:01:02
	 * @author yxm
	 */
	public void deleteById(Serializable id);
	
	/**
	 * 方法描述：删除一个组的页面
	 * @data 2016年4月25日  下午5:53:59
	 * @author yxm
	 */
	public void deleteHtmls(WsbcGroup wsbcGroup);
	
	/**
	 * 方法描述：删除本页面相关的所有本地文件
	 * @data 2016年4月26日  上午9:09:56
	 * @author yxm
	 */
	public void deleteLocalFile(WsbcHtml wsbcHtml);
}
