package com.wsbc.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.wsbc.constant.Name;
import com.wsbc.httpclient.entity.WsbcForm;
import com.wsbc.httpclient.entity.WsbcHtml;
import com.wsbc.httpclient.entity.WsbcParam;

public class WsbcFileUtil {
	
	/**
	 * 方法描述：读取文件的内容，当文件不存在时，返回null
	 * 创建时间：2016年3月23日  上午10:06:10
	 * 作者：yxm
	 * 本地文件都使用GB2312编码
	 */
	public static String readFile(String filePath){
		if(filePath == null){
			return null;
		}
		File file = new File(filePath);
		
		if(!file.exists()){
			return null;
		}
		
		//文件读取的长度
		int length = 0;
		FileInputStream input = null;
		byte[] buffer = new byte[2048];
		StringBuffer content = new StringBuffer();
		try {
			input = new FileInputStream(file);
			while((length = input.read(buffer, 0, buffer.length))!=-1){
				content.append(new String(buffer,0,length,"GB2312"));
//				content.append(new String(buffer));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if( input != null){
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return content.toString();
//		return code(content.toString(), "GB2312", "utf-8");
	}
	
	public static String writeFile(String content,String filePath){
		return writeFile(content, filePath, true);
	}
	/**
	 * 方法描述：将内容写入文件，并返回文件保存的路径
	 * 		本地文件都使用GB2312编码，默认程序内的编码为utf-8
	 * 创建时间：2016年3月23日  上午10:13:36
	 * 作者：yxm
	 * @param content 保存的内容
	 * @param filePath 文件的路径
	 * @param isNewFile 当文件存在时，是否新建文件
	 * @return 返回文件路径
	 */
	public static String writeFile(String content,String filePath, boolean isNewFile){
		filePath = Name.LOCAL_URL+filePath;
//		if(charset == null){
//			charset = "utf-8";
//		}
		String newFilePath = createFile(filePath, isNewFile);
		File file = new File(newFilePath);
		FileOutputStream output = null;
		try {
			output = new FileOutputStream(file);
			output.write(content.getBytes("GB2312"));
//			output.write(code(content, charset, "ISO-8859-1").getBytes());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try {
				if( output != null){
					output.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return newFilePath;
	}
	
	/**
	 * 方法描述：创建文件 并获得 不重复的文件路径
	 * @param fileParentPath null时  表示没有上级文件夹
	 * @param fileName 文件名
	 * 创建时间：2016年3月23日  上午10:37:18
	 * 作者：yxm
	 * 异常：
	 */
	public static String createFile(String filePath){
		return createFile(filePath, true);
	}
	
	/**
	 * 
	 * 方法描述：创建一个文件，当文件存在时，是否新建
	 * 作者： yxm
	 * 创建时间： 2018年12月31日  下午1:46:06
	 * @param filePath
	 * @param isCover 当文件存在时，是否新建
	 * @return
	 * @throws
	 */
	public static String createFile(String filePath, boolean isNewFile){
//		filePath.replace("/",File.separator);
		int lastSeparator = filePath.lastIndexOf("/");
		//父文件路径
		String fileParentPath = filePath.substring(0, lastSeparator);
		//文件名
		String fileName = filePath.substring(lastSeparator+1, filePath.length());
		//1、判断上级文件夹是否存在
		if(fileParentPath != null && !fileParentPath.equals("")){
			File parentFile = new File(fileParentPath);
			if(!parentFile.exists()){
				parentFile.mkdirs();
			}
		}

		File file = new File(filePath);
		if (file.exists() && !isNewFile) {
			return filePath;
		}
//		String tempparent = file.getParent();
//		String tempname = file.getName();
		//2、判断文件是否存在，使用新的命名
		int i=1;
		int lastDot = fileName.lastIndexOf('.');
		//文件名
		String name = fileName.substring(0, lastDot);
		//后缀
		String suffix = fileName.substring(lastDot+1, fileName.length());
		String tempPath = filePath;
		while(file.exists()){
			tempPath = fileParentPath+"/"+name + "("+i+")." + suffix;
			file = new File(tempPath);
			i++;
		}
		
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tempPath;
	}
	
	
	/**
	 * 方法描述：转换编码  contentType 当前文本的编码
	 * 创建时间：2016年3月23日  上午11:21:17
	 * 返回值：
	 * 作者：yxm
	 * 异常：
	 */
//	public static String code(String content,String contentCharset,String targetCharset){
//		String str=null;
//		try {
//			str = new String(content.getBytes(contentCharset),targetCharset);
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return str;
//	}
	
	/**
	 * 方法描述：给下载来的网页文件命名
	 * 创建时间：2016年3月30日  下午5:26:11
	 * 作者：yxm
	 */
	public static String fileName(WsbcHtml html){
		String url = html.getWsbcUrl().getNotProtocolUrl();
		String contentType = html.getContentType();
		if (contentType == null || contentType.equals("")||contentType.indexOf("html") != -1) {
			// ?????
			url = url.replaceAll("[\\?:*|<>\"]", "_") + ".html";
			return url;
		} else {
			// application/pdf类型
			return url.replaceAll("[\\?:*|<>\"]", "_") + "."
					+ contentType.substring(contentType.lastIndexOf("/") + 1);
		}
	}
	
	/**
	 * 方法描述：页面访问路径
	 * @data 2016年4月11日  下午6:53:38
	 * @author yxm
	 */
	public static String fileName(String url){
		url = url.split("://")[1];
		url = url.replaceAll("[\\?:*|<>\"]", "_") + ".html";
		return url;
	}
	
	/**
	 * 方法描述：保存网页
	 * 创建时间：2016年3月30日  下午5:50:12
	 * 作者：yxm
	 */
	public static void saveWsbcHtml(WsbcHtml html){
		String filePath = writeFile(html.getDocument().toString(), fileName(html));
		html.setDocumentPath(filePath);
	}
	
	/**
	 * 方法描述：保存页面参数  但参数长度大于 100 时，保存到本地文件中
	 * @data 2016年4月11日  下午6:22:48
	 * @author yxm
	 */
	public static void saveWsbcParam(WsbcHtml wsbcHtml){
		WsbcParam wsbcParam = wsbcHtml.getWsbcUrl().getParam();
		if(wsbcParam == null || !wsbcParam.isToLength())
			return;
		//给 参数文件命名
		String filePath = fileName(wsbcHtml)+"_param.txt";
		saveWsbcParam(wsbcParam, filePath);
		
	}
	
	/**
	 * 方法描述：保存参数到本地
	 * @param wsbcParam 参数
	 * @param filePath 文件保存路径
	 * @data 2016年4月11日  下午6:48:21
	 * @author yxm
	 */
	public static void saveWsbcParam(WsbcParam wsbcParam,String filePath){
		if(wsbcParam == null || !wsbcParam.isToLength())
			return;
		String parameters = wsbcParam.getParameters();
		//保存的本地文件中
		filePath = writeFile(parameters, filePath);
		//设置参数内容为空
		wsbcParam.setParameters("");
		//设置参数保存到的本地路径
		wsbcParam.setParametersPath(filePath);
	}
	
	/**
	 * 方法描述：保存表单中的参数到本地文件
	 * @param wsbcForm 
	 * @data 2016年4月11日  下午6:49:23
	 * @author yxm
	 */
	public static void saveWsbcParam(WsbcForm wsbcForm){
		if(wsbcForm == null || !wsbcForm.getParam().isToLength())
			return ;
		String action = wsbcForm.getAction();
		String fileName = fileName(action)+"_param.txt";
		saveWsbcParam(wsbcForm.getParam(), fileName);
	}
	
	/**
	 * 方法描述：删除文件
	 * @data 2016年4月25日  上午11:04:39
	 * @author yxm
	 */
	public static void deleteFile(String filePath){
		if(filePath == null){
			return;
		}
		File file = new File(filePath);
		if(!file.exists()){
			return;
		}
		File[] files = null;
		do{
			file.delete();
			file = file.getParentFile();
			files = file.listFiles();
		}while(files != null && files.length == 0);
	}
	
	/**
	 * 方法描述：替换windows文件路径里，不支持的特殊符号为+
	 * 作者： yxm
	 * 创建时间： 2020年10月18日  下午6:06:02
	 * @param filename
	 * @param replaceDot 是否替换点
	 * @return
	 * @throws
	 */
	public static String replaceFileName(String filename, boolean replaceDot) {
		if (filename == null) {
			return null;
		}
		// . 是单独加的，避免和后缀重复
		String replaceChar = "[\\/:*?\"<>|]";
		if (replaceDot) {
			replaceChar = "[\\/:*?\"<>|.]";
		}
		return filename.replaceAll(replaceChar, "+");
	}
}
