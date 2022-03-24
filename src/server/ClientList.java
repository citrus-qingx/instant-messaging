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
 * �û��б� xml
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

	// ����û�
	public static void addclient(ClientList client) throws DocumentException, IOException {
		/**
		 * ����������; �õ�document; ��ȡ��root�ڵ�;�ڸ��ڵ����洴��stu��ǩ ;��stu��ǩ�����������id name age;��id name
		 * age�����������ֵ ;��дxml
		 */

		// ����������
		SAXReader reader = new SAXReader();
		// �õ�document����
		Document document = reader.read("src/xml/client.xml");
		// ��ȡ��root�ڵ�;
		Element root = document.getRootElement();
		// �ڸ��ڵ����洴��cli��ǩ
		Element cli = root.addElement("cli");
		// ��cli��ǩ�����������number password
		Element number = cli.addElement("number");
		Element password = cli.addElement("password");
		// ��number password�����������ֵ
		number.setText(client.getnumber());
		password.setText(client.getpassword());
		// ��дxml
		OutputFormat format = new OutputFormat();
		XMLWriter writer = new XMLWriter(new FileOutputStream("src/xml/client.xml"), format);
		writer.write(document);
		writer.close();
	}

	// ��ѯ�˺�
	public static Boolean selclient(String id) throws DocumentException {
		// ����������
		SAXReader reader = new SAXReader();
		// �õ�document����
		Document document = reader.read("src/xml/client.xml");
		// ��ȡ����id xpath��//number
		List<Node> ids = document.selectNodes("//number");
		// �������е�number�ڵ�
		for (Node node : ids) {
			// �õ�number��ǩ������
			String str = node.getText();
			if (str.equals(id)) {
				// �������ƥ��
				return true;
			}
		}
		return false;
	}

	// ��֤����
	public static String selclientById(String id) throws DocumentException {
		// ����������
		SAXReader reader = new SAXReader();
		// �õ�document����
		Document document = reader.read("src/xml/client.xml");
		// ��ȡ����id xpath��//number
		List<Node> ids = document.selectNodes("//number");
		// �������е�number�ڵ�
		for (Node node : ids) {
			// �õ�number��ǩ������
			String str = node.getText();

			if (str.equals(id)) {
				// �������ƥ��
				// �õ���id�ĸ���ǩ��Ҳ�������ѧ����cli��ǩ
				Element cli = node.getParent();
				// �õ����ѧ����������б�ǩ
				List<Element> list = cli.elements();
				for (Node node2 : list) {
					// ��������ֵ
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
