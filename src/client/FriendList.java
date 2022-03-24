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
 * �����б� xml
 * 
 * @author citrus
 *
 */

public class FriendList {

	private String name;

	public FriendList(String name) {
		this.name = name;
	}

	// ע��ʱ��ʼ����xml�ļ�
	public void createXml() {
		try {
			// ����document����
			Document document = DocumentHelper.createDocument();
			// �������ڵ�rss Ϊ�˺���
			Element rss = document.addElement("name" + name);
			// ��rss�ڵ����version����
			rss.addAttribute("version", "1.0");
			// �����ӽڵ㼰�ӽڵ����� ��ʼ�Լ����Լ�����
			Element fri = rss.addElement("fri");
			Element number = fri.addElement("number");
			number.setText(name);
			// ��������xml�ĸ�ʽ
			OutputFormat format = OutputFormat.createPrettyPrint();
			// ���ñ����ʽ
			format.setEncoding("UTF-8");
			// ����xml�ļ�
			File file = new File("src/xml/" + name + ".xml");
			XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
			// �����Ƿ�ת�壬Ĭ��ʹ��ת���ַ�
			writer.setEscapeText(false);
			writer.write(document);
			writer.close();
			System.out.println("����" + name + ".xml�ɹ�");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("����" + name + ".xmlʧ��");
		}
	}

	// ��Ӻ���
	public void addFriend(String addname) throws DocumentException, IOException {
		// ����������
		SAXReader reader = new SAXReader();
		// �õ�document����
		Document document = reader.read("src/xml/" + name + ".xml");
		// ��ȡ��root�ڵ�;
		Element root = document.getRootElement();
		// �ڸ��ڵ����洴��fri��ǩ
		Element fri = root.addElement("fri");
		// ��fri��ǩ�������number
		Element number = fri.addElement("number");
		// ��number�������ֵ
		number.setText(addname);
		// ��дxml
		OutputFormat format = new OutputFormat();
		XMLWriter writer = new XMLWriter(new FileOutputStream("src/xml/" + name + ".xml"), format);
		writer.write(document);
		writer.close();
	}

	// ��ѯ����
	public boolean searchFriend(String searchname) throws DocumentException {
		// ����������
		SAXReader reader = new SAXReader();
		// �õ�document����
		Document document = reader.read("src/xml/" + name + ".xml");
		// ��ȡ����number xpath��//number
		List<Node> ids = document.selectNodes("//number");
		// �������е�number�ڵ�
		for (Node node : ids) {
			// �õ�number��ǩ������
			String str = node.getText();
			if (str.equals(searchname)) {
				// �������ƥ��
				return true;
			}
		}
		return false;
	}

	// ���������б�
	public String[] friendlist() throws DocumentException {
		Set<String> set = new HashSet<String>();
		// ����������
		SAXReader reader = new SAXReader();
		// �õ�document����
		Document document = reader.read("src/xml/" + name + ".xml");
		// ��ȡ����number xpath��//number
		List<Node> ids = document.selectNodes("//number");
		// �������е�number�ڵ�
		for (Node node : ids) {
			// �õ�number��ǩ������
			String str = node.getText();
			set.add(str);
		}
		return set.toArray(new String[set.size()]);
	}

}
