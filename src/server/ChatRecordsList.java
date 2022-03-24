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
 * �����¼�ļ�
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

	// �Ӻ���ʱ��ʼ����xml�ļ�
	public void createXml() {
		try {
			// ����document����
			Document document = DocumentHelper.createDocument();
			// �������ڵ�rss Ϊ�˺���
			Element rss = document.addElement("name" + name1 + name2);
			// ��rss�ڵ����version����
			rss.addAttribute("version", "1.0");
			// ��������xml�ĸ�ʽ
			OutputFormat format = OutputFormat.createPrettyPrint();
			// ���ñ����ʽ
			format.setEncoding("UTF-8");
			// ����xml�ļ�
			File file = new File("src/xml/" + name1 + "+" + name2 + ".xml");
			XMLWriter writer = new XMLWriter(new FileOutputStream(file), format);
			// �����Ƿ�ת�壬Ĭ��ʹ��ת���ַ�
			writer.setEscapeText(false);
			writer.write(document);
			writer.close();
			System.out.println("����" + name1 + "+" + name2 + ".xml�ɹ�");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("����" + name1 + "+" + name2 + ".xmlʧ��");
		}
	}

	// ���������¼
	public void addlist(String name, String time, String chat) throws DocumentException, IOException {
		// ����������
		SAXReader reader = new SAXReader();
		// �õ�document����
		Document document = reader.read("src/xml/" + name1 + "+" + name2 + ".xml");
		// ��ȡ��root�ڵ�
		Element root = document.getRootElement();
		// �ڸ��ڵ����洴��date��ǩ
		Element date;
		if (root.selectSingleNode("T" + time.substring(0, 10)) != null) {
			date = (Element) root.selectSingleNode("T" + time.substring(0, 10));
		} else {
			date = root.addElement("T" + time.substring(0, 10)); // ������ yyyy-MM-dd
		}
		Element date_exact = date
				.addElement("time" + time.substring(11, 13) + time.substring(14, 16) + time.substring(17) + name); // yyyy-MM-dd
																													// HH:mm:ss
																													// ȥð��
		date_exact.setText(chat);
		// ��дxml
		OutputFormat format = new OutputFormat();
		XMLWriter writer = new XMLWriter(new FileOutputStream("src/xml/" + name1 + "+" + name2 + ".xml"), format);
		writer.write(document);
		writer.close();
	}

	// ��ѯ�����¼
	public String searchlist(String time) throws DocumentException {
		String s = "";
		// ����������
		SAXReader reader = new SAXReader();
		// �õ�document����
		Document document = reader.read("src/xml/" + name1 + "+" + name2 + ".xml");
		// ��ȡ��root�ڵ�
		Element root = document.getRootElement();
		Element cli = root.element("T" + time.substring(0, 10));
		List<Element> chats = cli.elements(); // �ӽڵ�
		for (Element chat : chats) {
			s = s + chat.getName().substring(4, 6) + ":" + chat.getName().substring(6, 8) + ":"
					+ chat.getName().substring(8, 10) + " " + chat.getName().substring(10) + "\n" + chat.getText()
					+ "\n";
		}
		return s;
	}

}
