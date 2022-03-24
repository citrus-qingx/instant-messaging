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
 * �����û��б�
 * 
 * @author citrus
 *
 */

public class OnlineList {

	// �����û�
	public static void addlist(String a) throws DocumentException, IOException {
		// ����������
		SAXReader reader = new SAXReader();
		// �õ�document����
		Document document = reader.read("src/xml/onlinelist.xml");
		// ��ȡ��root�ڵ�
		Element root = document.getRootElement();
		// �ڸ��ڵ����洴��cli��ǩ
		Element cli = root.addElement("a" + a);
		// ��дxml
		OutputFormat format = new OutputFormat();
		XMLWriter writer = new XMLWriter(new FileOutputStream("src/xml/onlinelist.xml"), format);
		writer.write(document);
		writer.close();
	}

	// ɾ���û�
	public static void deletelist(String a) throws DocumentException, IOException {
		// ����������
		SAXReader reader = new SAXReader();
		// �õ�document����
		Document document = reader.read("src/xml/onlinelist.xml");
		// ��ȡ��root�ڵ�
		Element root = document.getRootElement();
		// ��ȡ�ӽڵ�
		Element removeElement = root.element("a" + a);
		root.remove(removeElement);
		// ��дxml
		OutputFormat format = new OutputFormat();
		XMLWriter writer = new XMLWriter(new FileOutputStream("src/xml/onlinelist.xml"), format);
		writer.write(document);
		writer.close();
	}

	// ��ѯ�û�
	public static boolean searchlist(String a) throws DocumentException {
		// ����������
		SAXReader reader = new SAXReader();
		// �õ�document����
		Document document = reader.read("src/xml/onlinelist.xml");
		// ��ȡ��root�ڵ�
		Element root = document.getRootElement();
		Element cli = root.element("a" + a);
		if (cli == null)
			return false;
		else
			return true;
	}
}
