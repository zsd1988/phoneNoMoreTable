package com.qingpu.phone.common.utils;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.json.AbstractJsonWriter;
import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JsonWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

/**
 * @Desc   对象之间相互转换的工具函数
 * @author Gangyahaidao
 */
public class ConversionObjectUtils {
	/**
	 * @Desc 扩展stream，使其支持CDATA块
	 * */
	public static XStream xstream = new XStream(new XppDriver(){
		@Override
		public HierarchicalStreamWriter createWriter(Writer out){
			return new PrettyPrintWriter(out){
				//对所有的xml节点的转换都增加CDATA标记
				boolean cdata = true;
				@Override
				public void startNode(String name, Class clazz){
					super.startNode(name, clazz);
				}
				@Override
				protected void writeText(QuickWriter writer, String text){
					if(cdata){
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					}else{
						writer.write(text);
					}
				}
			};
		}
	});
	/**
	 * @Desc 将javabean转换成xml字符串
	 * */
	public static String convertBeanToJsonStr(Object object){
		String jsonStr = "";
		XStream xstream = new XStream(new JsonHierarchicalStreamDriver(){
			@Override
			public HierarchicalStreamWriter createWriter(Writer out) {
				return new JsonWriter(out, AbstractJsonWriter.DROP_ROOT_MODE);
			}
		});
		xstream.alias("xml", Object.class);
		return xstream.toXML(object);		
	}
	/**
	 * @Desc json字符串转换为Map对象
	 * @param jsonStr字符串
	 * @return Map
	 * */
	public static Map<String, Object> convertJsonstr2Map(String jsonStr){
		ObjectMapper objectMapper = new ObjectMapper();
		Map<String, Object> maps = null;
		try {
			 
            //将json字符串转成map结合解析出来，并打印(这里以解析成map为例)
            maps = objectMapper.readValue(jsonStr, Map.class);
            Set<String> key = maps.keySet();
            Iterator<String> iter = key.iterator();
            while (iter.hasNext()) {
                String field = iter.next();
            }
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }	
		
		return maps;
	}
	/**
	 * @Desc 将xml字符串转换成Map对象
	 * */
	public static Map<String, String> convertXmlStrToMap(String xml) {
		Map<String, String> map = new HashMap<String, String>();		
		Document doc = null;
		int num = 0;
		try {

			doc = DocumentHelper.parseText(xml);
			Element employees = doc.getRootElement();
			for (Iterator j = employees.elementIterator(); j.hasNext();) {
				Element node = (Element) j.next();
				if (map.size() > 0 && null != map.get(node.getName())) {
					map.put(node.getName() + String.valueOf(num),
							node.getText());
				} else {
					map.put(node.getName(), node.getText());
				}
				num++;
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}
