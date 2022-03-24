package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

import javax.swing.JOptionPane;

import org.dom4j.DocumentException;

import server.OnlineList;

/**
 * �ͻ������ӷ�����
 * 
 * @author citrus
 *
 */

public class Connect {

	public String a; // �˺�
	public Socket s = null; // �ͻ���
	private DataOutputStream dos = null; // ������Ϣ���������ͻ��ˣ�

	public Connect(String a) {
		this.a = a;
		clientconnect();
	}

	// ����
	public void clientconnect() {
		try {
			s = new Socket("127.0.0.1", 8888);
			dos = new DataOutputStream(s.getOutputStream());
			dos.writeUTF(a);
			System.out.println(a + "connected");
			// ���������б�
			try {
				OnlineList.addlist(a);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		} catch (ConnectException e) {
			JOptionPane.showConfirmDialog(null, "������δ����");
			try {
				OnlineList.deletelist(a);
				if (dos != null)
					dos.close();
				if (s != null)
					s.close();
			} catch (DocumentException | IOException e1) {
				e1.printStackTrace();
			}
			// System.exit(0);
		} catch (IOException e) {
			try {
				OnlineList.deletelist(a);
				if (dos != null)
					dos.close();
				if (s != null)
					s.close();
			} catch (DocumentException | IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	// �Ͽ�����
	public void clientdisconnect() {
		try {
			OnlineList.deletelist(a);
			if (dos != null)
				dos.close();
			if (s != null)
				s.close();
			System.out.println("�û�" + a + "����");
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
		}

	}
}
