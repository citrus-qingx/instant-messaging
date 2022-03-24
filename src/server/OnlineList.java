package server;

import java.io.FileOutputStream;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 在线用户列表
 * 
 * @author citrus
 *
 */

public class OnlineList {

	// 增加用户
	public static void addlist(String a) throws DocumentException, IOException {
		// 创建解析器
		SAXReader reader = new SAXReader();
		// 得到document对象
		Document document = reader.read("src/xml/onlinelist.xml");
		// 获取到root节点
		Element root = document.getRootElement();
		// 在根节点上面创建cli标签
		Element cli = root.addElement("a" + a);
		// 回写xml
		OutputFormat format = new OutputFormat();
		XMLWriter writer = new XMLWriter(new FileOutputStream("src/xml/onlinelist.xml"), format);
		writer.write(document);
		writer.close();
	}

	// 删除用户
	public static void deletelist(String a) throws DocumentException, IOException {
		// 创建解析器
		SAXReader reader = new SAXReader();
		// 得到document对象
		Document document = reader.read("src/xml/onlinelist.xml");
		// 获取到root节点
		Element root = document.getRootElement();
		// 获取子节点
		Element removeElement = root.element("a" + a);
		root.remove(removeElement);
		// 回写xml
		OutputFormat format = new OutputFormat();
		XMLWriter writer = new XMLWriter(new FileOutputStream("src/xml/onlinelist.xml"), format);
		writer.write(document);
		writer.close();
	}

	// 查询用户
	public static boolean searchlist(String a) throws DocumentException {
		// 创建解析器
		SAXReader reader = new SAXReader();
		// 得到document对象
		Document document = reader.read("src/xml/onlinelist.xml");
		// 获取到root节点
		Element root = document.getRootElement();
		Element cli = root.element("a" + a);
		if (cli == null)
			return false;
		else
			return true;
	}
}
