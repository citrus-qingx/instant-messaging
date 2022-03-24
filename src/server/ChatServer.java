package server;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * ������
 * 
 * @author citrus
 *
 */

public class ChatServer {
	ServerSocket ss = null; // ������
	String name;
	boolean isstarted = false; // �������Ƿ���
	private DataInputStream in = null; // ������Ϣ������ ����������
	public static HashMap<String, Client> map = new HashMap<String, Client>(); // �˺�����Ӧ�ͻ���

	public static void main(String[] args) {
		new ChatServer().start();
	}

	public void start() {
		try {
			ss = new ServerSocket(8888);
			isstarted = true;
		} catch (BindException e) {
			System.out.println("�˿�ʹ���С���");
			System.out.println("��ص���س����������з�����");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			while (isstarted) {
				Socket s = ss.accept(); // ���ӿͻ��� ����
				in = new DataInputStream(s.getInputStream());
				name = in.readUTF();
				Client c = new Client(s, name);
				new Thread(c).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// ���շ������ر�
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// ���߳̿ͻ��� (�ڲ��ࣩ
	class Client implements Runnable {
		String name;
		String friname;
		private Socket s;
		private DataInputStream dis = null; // ������Ϣ������ ����������
		private DataOutputStream dos = null; // ������Ϣ����������������
		private boolean isconnected = false; // �Ƿ������Ͽͻ���
		private boolean ischatting = false; // �Ƿ�������

		// ���췽��
		public Client(Socket s, String name) {
			this.s = s;
			this.name = name;
			try {
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
				isconnected = true;
				map.put(name, this); // ����map
				System.out.println("client " + name + " connected");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		// ������Ϣ����
		public void send(String str) {
			try {
				dos.writeUTF(str);
			} catch (IOException e) {
				// �Ƴ��ͻ���
				map.remove(name);
			}
		}

		@Override
		public void run() {
			try {
				while (true) {
					String st;
					st = dis.readUTF();
					if (st.equals("start...")) {
						friname = dis.readUTF();
						System.out.println("����" + friname);
						ischatting = true;
						break;
					}
				}
			} catch (IOException e1) {
				isconnected = false;
			}

			Client c = map.get(friname);

			try {

				while (isconnected && ischatting) {
					String str = dis.readUTF(); // ����
					System.out.println(str);
					// �����ѿͻ��˷���
					c.send(str);
					if (str.equals("������ĵ����...886")) {
						ischatting = false;
						System.out.println("Chatting closed");
						Client c1 = new Client(s, name);
						new Thread(c1).start();
					}
				}

			} catch (IOException e) {
				try {
					if (s != null)
						s.close();
					if (dis != null)
						dis.close();
					if (dos != null)
						dos.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		}
	}
}