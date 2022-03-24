package server;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * 服务器
 * 
 * @author citrus
 *
 */

public class ChatServer {
	ServerSocket ss = null; // 服务器
	String name;
	boolean isstarted = false; // 服务器是否开启
	private DataInputStream in = null; // 聊天信息传入流 （服务器）
	public static HashMap<String, Client> map = new HashMap<String, Client>(); // 账号所对应客户端

	public static void main(String[] args) {
		new ChatServer().start();
	}

	public void start() {
		try {
			ss = new ServerSocket(8888);
			isstarted = true;
		} catch (BindException e) {
			System.out.println("端口使用中……");
			System.out.println("请关掉相关程序并重新运行服务器");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			while (isstarted) {
				Socket s = ss.accept(); // 连接客户端 阻塞
				in = new DataInputStream(s.getInputStream());
				name = in.readUTF();
				Client c = new Client(s, name);
				new Thread(c).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 最终服务器关闭
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 多线程客户端 (内部类）
	class Client implements Runnable {
		String name;
		String friname;
		private Socket s;
		private DataInputStream dis = null; // 聊天信息传入流 （服务器）
		private DataOutputStream dos = null; // 聊天信息传出流（服务器）
		private boolean isconnected = false; // 是否连接上客户端
		private boolean ischatting = false; // 是否在聊天

		// 构造方法
		public Client(Socket s, String name) {
			this.s = s;
			this.name = name;
			try {
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
				isconnected = true;
				map.put(name, this); // 加入map
				System.out.println("client " + name + " connected");
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		// 聊天信息传出
		public void send(String str) {
			try {
				dos.writeUTF(str);
			} catch (IOException e) {
				// 移除客户端
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
						System.out.println("好友" + friname);
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
					String str = dis.readUTF(); // 阻塞
					System.out.println(str);
					// 给好友客户端发送
					c.send(str);
					if (str.equals("今天就聊到这吧...886")) {
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