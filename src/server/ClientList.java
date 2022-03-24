package server;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * 用户列表 xml
 * 
 * @author citrus
 *
 */

public class ClientList {

	private String number;
	private String password;

	public ClientList(String number, String password) {
		this.number = number;
		this.password = password;
	}

	public String getnumber() {
		return number;
	}

	public String getpassword() {
		return password;
	}

	// 添加用户
	public static void addclient(ClientList client) throws DocumentException, IOException {
		/**
		 * 创建解析器; 得到document; 获取到root节点;在根节点上面创建stu标签 ;在stu标签上面依次添加id name age;在id name
		 * age上面依次添加值 ;回写xml
		 */

		// 创建解析器
		SAXReader reader = new SAXReader();
		// 得到document对象
		Document document = reader.read("src/xml/client.xml");
		// 获取到root节点;
		Element root = document.getRootElement();
		// 在根节点上面创建cli标签
		Element cli = root.addElement("cli");
		// 在cli标签上面依次添加number password
		Element number = cli.addElement("number");
		Element password = cli.addElement("password");
		// 在number password上面依次添加值
		number.setText(client.getnumber());
		password.setText(client.getpassword());
		// 回写xml
		OutputFormat format = new OutputFormat();
		XMLWriter writer = new XMLWriter(new FileOutputStream("src/xml/client.xml"), format);
		writer.write(document);
		writer.close();
	}

	// 查询账号
	public static Boolean selclient(String id) throws DocumentException {
		// 创建解析器
		SAXReader reader = new SAXReader();
		// 得到document对象
		Document document = reader.read("src/xml/client.xml");
		// 获取所有id xpath：//number
		List<Node> ids = document.selectNodes("//number");
		// 遍历所有的number节点
		for (Node node : ids) {
			// 得到number标签的内容
			String str = node.getText();
			if (str.equals(id)) {
				// 如果内容匹配
				return true;
			}
		}
		return false;
	}

	// 验证密码
	public static String selclientById(String id) throws DocumentException {
		// 创建解析器
		SAXReader reader = new SAXReader();
		// 得到document对象
		Document document = reader.read("src/xml/client.xml");
		// 获取所有id xpath：//number
		List<Node> ids = document.selectNodes("//number");
		// 遍历所有的number节点
		for (Node node : ids) {
			// 得到number标签的内容
			String str = node.getText();

			if (str.equals(id)) {
				// 如果内容匹配
				// 得到该id的父标签，也就是这个学生的cli标签
				Element cli = node.getParent();
				// 得到这个学生下面的所有标签
				List<Element> list = cli.elements();
				for (Node node2 : list) {
					// 返回密码值
					String name = node2.getName();
					String text = node2.getText();
					if (name.equals("password")) {
						return text;
					}
				}
			}
		}
		return "";
	}

}
