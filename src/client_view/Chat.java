package client_view;

import java.awt.BorderLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

import org.dom4j.DocumentException;

import client.Connect;
import server.ChatRecordsList;

/**
 * 聊天界面
 * 
 * @author citrus
 *
 */

public class Chat extends JFrame {

	private static final long serialVersionUID = 8384678720686109263L;
	TextField tfText = new TextField(); // 输入文本
	TextArea taContent = new TextArea(); // 显示文本
	DataOutputStream dos = null; // 聊天信息传出流
	DataInputStream dis = null; // 聊天信息传入流
	Connect c;
	String fri;

	public Chat(Connect c, String fri) {
		this.c = c;
		this.fri = fri;
		
		try {
			dos = new DataOutputStream(c.s.getOutputStream());
			dis = new DataInputStream(c.s.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.setTitle("IM_聊天 chat with " + fri);
		this.setLocation(400, 300);
		this.setSize(300, 300);
		this.add(tfText, BorderLayout.SOUTH);
		this.add(taContent, BorderLayout.CENTER);
		pack(); // 窗口适应组件
		this.setVisible(true);
		// 关闭窗口
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				try {
					dos.writeUTF("今天就聊到这吧...886");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				dispose();
			}

		});

		tfText.addActionListener(new TFListener());

		// 新建线程接收服务器聊天信息
		new Thread(new RecvThread()).start();
	}

	private class TFListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String str = tfText.getText().trim(); // 返回一个字符串，删除任何前导和尾随空格
			tfText.setText("");
			// 得到当前时间
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
			String s = df.format(new Date());
			taContent.setText(taContent.getText() + c.a + " " + s + '\n' + str + '\n');
			// 聊天记录
			try {
				new ChatRecordsList(c.a,fri).addlist(c.a,s, str);
				new ChatRecordsList(fri,c.a).addlist(c.a,s, str);
			} catch (DocumentException | IOException e2) {
				e2.printStackTrace();
			}
			try {
				dos.writeUTF(c.a + " " + s);
				dos.writeUTF(str);
				dos.flush(); // 刷新
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

	}

	// 多线程接受服务器聊天信息
	private class RecvThread implements Runnable {

		private boolean ischatting = false; 
		
		@Override
		public void run() {
			try {
				ischatting = true;
				while (ischatting) {
					String str = dis.readUTF(); // 阻塞
					taContent.setText(taContent.getText() + str + '\n');
					if(str.equals("今天就聊到这吧...886"))
					ischatting = false;
				}
			} catch (SocketException e) {
				ischatting = false;
			} catch (IOException e) {
				JOptionPane.showConfirmDialog(null, "好友下线了,等他上线再聊天吧");
			}

		}

	}

}
