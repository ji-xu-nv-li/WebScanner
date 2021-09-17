package com.wsbc.util.file;

import java.io.File;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

public class WsbcXmlUtil {
	
//	private Document d;
	
//	public WsbcXmlUtil() {
//		SAXReader rd = new SAXReader();
//	}

	/**
	 * 方法描述：将所做的修改更新到xml文件中
	 * 创建时间：2016年4月4日  上午9:32:58
	 * 作者：yxm
	 */
//	private void refush(String path){
//		//格式化输出xml文档。并解决中文问题
//		try{
//			FileWriter fileWriter = new FileWriter(path);
//			//设置了创建xml文件的格式为缩进的
//			OutputFormat xmlFormat = OutputFormat.createPrettyPrint();
//			//设置文件编码格式
//			xmlFormat.setEncoding("gbk");
//			//创建写文件，输入参数是文件，格式
//			XMLWriter xmlWriter = new XMLWriter(fileWriter,xmlFormat);
//			//将doc文档写入文件
//			xmlWriter.write(d);
//			//关闭
//			xmlWriter.close();
//		}catch(IOException e){
//			e.printStackTrace();
//		}
//	}
	
	/**
	 * 方法描述：获取XML文件的文档对象
	 * 创建时间：2016年4月4日  上午10:11:29
	 * 作者：yxm
	 */
	public static Document init(String xmlFilePath){
		SAXReader rd = new SAXReader();
		File file = new File(xmlFilePath);
		Document document = null;
		try {
			document = rd.read(file);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return document;
	}
	
//	public static void readSqlTestData(){
//		Document document = init("src/com/wsbc/xml/SqlTestData.xml");
//		Element root = document.getRootElement();
//		List<Element> sqlTestDatas = root.elements("SqlTestData");
//		
//	}
}
