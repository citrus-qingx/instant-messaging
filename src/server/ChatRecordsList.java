package server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 聊天记录文件
 * 
 * @author citrus
 *
 */

public class ChatRecordsList {

	private String name1;
	private String name2;

	public ChatRecordsList(String name1, String name2) {
		this.name1 = name1;
		this.name2 = name2;
	}

	// 加好友时初始创建xml文件
	public void createXml() {
		try {
			// 创建document对象
			Document document = DocumentHelper.createDocument();
			// 创建根节点rss 为账号名
			Element rss = document.addElement("name" + name1 + name2);
			// 向rss节点添加version属性
			rss.addAttribute("version", "1.0");
			// 设置生成xml的格式
			OutputFormat format = OutputFormat.createPrettyPrint();
			// 设置编码格式
			format.setEncoding("UTF-8");
			// 生成xml文件
			File file = new File("src/xml/" + name1 + "+" + name2 + ".xml");
			XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
			// 设置是否转义，默认使用转义字符
			writer.setEscapeText(false);
			writer.write(document);
			writer.close();
			System.out.println("生成" + name1 + "+" + name2 + ".xml成功");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("生成" + name1 + "+" + name2 + ".xml失败");
		}
	}

	// 增加聊天记录
	public void addlist(String name, String time, String chat) throws DocumentException, IOException {
		// 创建解析器
		SAXReader reader = new SAXReader();
		// 得到document对象
		Document document = reader.read("src/xml/" + name1 + "+" + name2 + ".xml");
		// 获取到root节点
		Element root = document.getRootElement();
		// 在根节点上面创建date标签
		Element date;
		if (root.selectSingleNode("T" + time.substring(0, 10)) != null) {
			date = (Element) root.selectSingleNode("T" + time.substring(0, 10));
		} else {
			date = root.addElement("T" + time.substring(0, 10)); // 年月日 yyyy-MM-dd
		}
		Element date_exact = date
				.addElement("time" + time.substring(11, 13) + time.substring(14, 16) + time.substring(17) + name); // yyyy-MM-dd
																													// HH:mm:ss
																													// 去冒号
		date_exact.setText(chat);
		// 回写xml
		OutputFormat format = new OutputFormat();
		XMLWriter writer = new XMLWriter(new FileOutputStream("src/xml/" + name1 + "+" + name2 + ".xml"), format);
		writer.write(document);
		writer.close();
	}

	// 查询聊天记录
	public String searchlist(String time) throws DocumentException {
		String s = "";
		// 创建解析器
		SAXReader reader = new SAXReader();
		// 得到document对象
		Document document = reader.read("src/xml/" + name1 + "+" + name2 + ".xml");
		// 获取到root节点
		Element root = document.getRootElement();
		Element cli = root.element("T" + time.substring(0, 10));
		List<Element> chats = cli.elements(); // 子节点
		for (Element chat : chats) {
			s = s + chat.getName().substring(4, 6) + ":" + chat.getName().substring(6, 8) + ":"
					+ chat.getName().substring(8, 10) + " " + chat.getName().substring(10) + "\n" + chat.getText()
					+ "\n";
		}
		return s;
	}

}
