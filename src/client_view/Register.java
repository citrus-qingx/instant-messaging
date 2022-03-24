package client_view;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import org.dom4j.DocumentException;

import client.FriendList;
import server.ClientList;

/**
 * ע�����
 * 
 * @author citrus
 *
 */

public class Register extends JFrame {

	private static final long serialVersionUID = 3634139206529429672L;

	// ����
	private JLabel l;
	// ע����ʾ
//	private JLabel l1;
	// �˺�
	private JLabel l1;
	private JTextField lo_number;
	// ����
	private JLabel l2;
	private JPasswordField lo_passwd;
	// ע�ᰴť
	private JButton b1;

	public Register() {
		// ���õ�¼���ڱ���
		this.setTitle("IM_ע��");
		// ���������ʼ��
		init();
		// ���ú�̨�˳�
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// ���ò���Ϊ���Զ�λ
		this.setLayout(null);
		this.setBounds(0, 0, 355, 265);
		// �����С���ܸı�
		this.setResizable(false);
		// ������ʾ
		this.setLocationRelativeTo(null);
		// ������ʾ
		this.setVisible(true);
		// ���ô����ͼ��
		Image img = new ImageIcon("src/pic/xd.jpg").getImage();
		this.setIconImage(img);

		b1.addActionListener(new registerListener());
	}

	// ���������ʼ��
	public void init() {
		// ��panel����Ϊframe���������
		JPanel panel = new JPanel();
		this.setContentPane(panel);
		// ���ñ���ɫ
		l = new JLabel();
		Image img1 = new ImageIcon("src/pic/xd355x265.jpg").getImage();
		l.setIcon(new ImageIcon(img1));
		l.setBounds(0, 0, 355, 265);
		// �˺�
		l1 = new JLabel("�˺�");
		l1.setFont(new Font("����", Font.BOLD, 14));
		l1.setBounds(60, 90, 70, 20);
		// �˺������
		lo_number = new JTextField();
		lo_number.setBounds(100, 90, 150, 20);
		// ����
		l2 = new JLabel("����");
		l2.setFont(new Font("����", Font.BOLD, 14));
		l2.setBounds(60, 120, 70, 20);
		// ���������
		lo_passwd = new JPasswordField();
		lo_passwd.setBounds(100, 120, 150, 20);
		// ע�ᰴť
		b1 = new JButton("ע��");
		b1.setFont(new Font("����", Font.BOLD, 14));
		b1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		b1.setBounds(135, 160, 65, 20);

		l.add(l1);
		l.add(l2);
		l.add(b1);
		panel.add(lo_number);
		panel.add(lo_passwd);
		panel.add(l);
	}

	private class registerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String number = lo_number.getText();
			String passwd = lo_passwd.getText();
			String s = null;
			ClientList client = new ClientList(number, passwd);
			// д�����ݿ�
			try {
				s = ClientList.selclientById(number);
			} catch (DocumentException e1) {
				e1.printStackTrace();
			}
			if (s.equals("")) {
				try {
					ClientList.addclient(client);
					// �½�FL
					FriendList f = new FriendList(number);
					f.createXml();
					// ������
					JOptionPane.showConfirmDialog(null, "ע��ɹ�");
					new Login();
					// �ر�ע�ᴰ��
					dispose();
				} catch (DocumentException | IOException e1) {
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showConfirmDialog(null, "�˺��Ѵ���");
				lo_number.setText("");
				lo_passwd.setText("");
			}

		}

	}

	public static void main(String[] args) {
		new Register();

	}

}
