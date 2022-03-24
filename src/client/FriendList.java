package client;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 好友列表 xml
 * 
 * @author citrus
 *
 */

public class FriendList {

	private String name;

	public FriendList(String name) {
		this.name = name;
	}

	// 注册时初始创建xml文件
	public void createXml() {
		try {
			// 创建document对象
			Document document = DocumentHelper.createDocument();
			// 创建根节点rss 为账号名
			Element rss = document.addElement("name" + name);
			// 向rss节点添加version属性
			rss.addAttribute("version", "1.0");
			// 生成子节点及子节点内容 初始自己是自己好友
			Element fri = rss.addElement("fri");
			Element number = fri.addElement("number");
			number.setText(name);
			// 设置生成xml的格式
			OutputFormat format = OutputFormat.createPrettyPrint();
			// 设置编码格式
			format.setEncoding("UTF-8");
			// 生成xml文件
			File file = new File("src/xml/" + name + ".xml");
			XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
			// 设置是否转义，默认使用转义字符
			writer.setEscapeText(false);
			writer.write(document);
			writer.close();
			System.out.println("生成" + name + ".xml成功");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("生成" + name + ".xml失败");
		}
	}

	// 添加好友
	public void addFriend(String addname) throws DocumentException, IOException {
		// 创建解析器
		SAXReader reader = new SAXReader();
		// 得到document对象
		Document document = reader.read("src/xml/" + name + ".xml");
		// 获取到root节点;
		Element root = document.getRootElement();
		// 在根节点上面创建fri标签
		Element fri = root.addElement("fri");
		// 在fri标签上面添加number
		Element number = fri.addElement("number");
		// 在number上面添加值
		number.setText(addname);
		// 回写xml
		OutputFormat format = new OutputFormat();
		XMLWriter writer = new XMLWriter(new FileOutputStream("src/xml/" + name + ".xml"), format);
		writer.write(document);
		writer.close();
	}

	// 查询好友
	public boolean searchFriend(String searchname) throws DocumentException {
		// 创建解析器
		SAXReader reader = new SAXReader();
		// 得到document对象
		Document document = reader.read("src/xml/" + name + ".xml");
		// 获取所有number xpath：//number
		List<Node> ids = document.selectNodes("//number");
		// 遍历所有的number节点
		for (Node node : ids) {
			// 得到number标签的内容
			String str = node.getText();
			if (str.equals(searchname)) {
				// 如果内容匹配
				return true;
			}
		}
		return false;
	}

	// 传出好友列表
	public String[] friendlist() throws DocumentException {
		Set<String> set = new HashSet<String>();
		// 创建解析器
		SAXReader reader = new SAXReader();
		// 得到document对象
		Document document = reader.read("src/xml/" + name + ".xml");
		// 获取所有number xpath：//number
		List<Node> ids = document.selectNodes("//number");
		// 遍历所有的number节点
		for (Node node : ids) {
			// 得到number标签的内容
			String str = node.getText();
			set.add(str);
		}
		return set.toArray(new String[set.size()]);
	}

}
