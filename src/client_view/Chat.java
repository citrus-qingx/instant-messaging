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
 * �������
 * 
 * @author citrus
 *
 */

public class Chat extends JFrame {

	private static final long serialVersionUID = 8384678720686109263L;
	TextField tfText = new TextField(); // �����ı�
	TextArea taContent = new TextArea(); // ��ʾ�ı�
	DataOutputStream dos = null; // ������Ϣ������
	DataInputStream dis = null; // ������Ϣ������
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

		this.setTitle("IM_���� chat with " + fri);
		this.setLocation(400, 300);
		this.setSize(300, 300);
		this.add(tfText, BorderLayout.SOUTH);
		this.add(taContent, BorderLayout.CENTER);
		pack(); // ������Ӧ���
		this.setVisible(true);
		// �رմ���
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				try {
					dos.writeUTF("������ĵ����...886");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				dispose();
			}

		});

		tfText.addActionListener(new TFListener());

		// �½��߳̽��շ�����������Ϣ
		new Thread(new RecvThread()).start();
	}

	private class TFListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String str = tfText.getText().trim(); // ����һ���ַ�����ɾ���κ�ǰ����β��ո�
			tfText.setText("");
			// �õ���ǰʱ��
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// �������ڸ�ʽ
			String s = df.format(new Date());
			taContent.setText(taContent.getText() + c.a + " " + s + '\n' + str + '\n');
			// �����¼
			try {
				new ChatRecordsList(c.a,fri).addlist(c.a,s, str);
				new ChatRecordsList(fri,c.a).addlist(c.a,s, str);
			} catch (DocumentException | IOException e2) {
				e2.printStackTrace();
			}
			try {
				dos.writeUTF(c.a + " " + s);
				dos.writeUTF(str);
				dos.flush(); // ˢ��
			} catch (IOException e1) {
				e1.printStackTrace();
			}

		}

	}

	// ���߳̽��ܷ�����������Ϣ
	private class RecvThread implements Runnable {

		private boolean ischatting = false; 
		
		@Override
		public void run() {
			try {
				ischatting = true;
				while (ischatting) {
					String str = dis.readUTF(); // ����
					taContent.setText(taContent.getText() + str + '\n');
					if(str.equals("������ĵ����...886"))
					ischatting = false;
				}
			} catch (SocketException e) {
				ischatting = false;
			} catch (IOException e) {
				JOptionPane.showConfirmDialog(null, "����������,���������������");
			}

		}

	}

}
