package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

import javax.swing.JOptionPane;

import org.dom4j.DocumentException;

import server.OnlineList;

/**
 * 客户端连接服务器
 * 
 * @author citrus
 *
 */

public class Connect {

	public String a; // 账号
	public Socket s = null; // 客户端
	private DataOutputStream dos = null; // 聊天信息传出流（客户端）

	public Connect(String a) {
		this.a = a;
		clientconnect();
	}

	// 连接
	public void clientconnect() {
		try {
			s = new Socket("127.0.0.1", 8888);
			dos = new DataOutputStream(s.getOutputStream());
			dos.writeUTF(a);
			System.out.println(a + "connected");
			// 加入在线列表
			try {
				OnlineList.addlist(a);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		} catch (ConnectException e) {
			JOptionPane.showConfirmDialog(null, "服务器未连接");
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

	// 断开连接
	public void clientdisconnect() {
		try {
			OnlineList.deletelist(a);
			if (dos != null)
				dos.close();
			if (s != null)
				s.close();
			System.out.println("用户" + a + "离线");
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
		}

	}
}
