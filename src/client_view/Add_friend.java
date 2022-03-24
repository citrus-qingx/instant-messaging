package client_view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;

import org.dom4j.DocumentException;

import client.Connect;
import client.FriendList;
import server.ChatRecordsList;
import server.ClientList;

/**
 * ��Ӻ���ҳ��
 * 
 * @author citrus
 *
 */

public class Add_friend extends JFrame {

	private static final long serialVersionUID = -8226424094801114078L;

	Connect c;

	// ����
	private JLabel l;
	// ��Ӻ�����ʾ
	private JLabel l1;
	// �˺�
	private JLabel l2;
	private JTextField lo_number;
	// ��Ӱ�ť
	private JButton b1;

	public Add_friend(Connect c) {
		// ���û���Ϣ
		this.c = c;
		// ���õ�¼���ڱ���
		this.setTitle("IM_��Ӻ���");
		// ���������ʼ��
		init();
		// �����˳�
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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

		b1.addActionListener(new addfri());
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
		// ��Ӻ�����ʾ
		l1 = new JLabel("�����˺���Ӻ���");
		l1.setFont(new Font("����", Font.BOLD, 14));
		l1.setOpaque(true);
		l1.setBackground(Color.WHITE);
		l1.setBounds(110, 80, 115, 20);
		// �˺�
		l2 = new JLabel("�˺�");
		l2.setFont(new Font("����", Font.BOLD, 14));
		l2.setBounds(60, 120, 70, 20);
		// �˺������
		lo_number = new JTextField();
		lo_number.setBounds(100, 120, 150, 20);
		// ��Ӱ�ť
		b1 = new JButton("���");
		b1.setFont(new Font("����", Font.BOLD, 14));
		b1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		b1.setBounds(135, 160, 65, 20);

		l.add(l1);
		l.add(l2);
		l.add(b1);
		panel.add(lo_number);
		panel.add(l);
	}

	public class addfri implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String number = lo_number.getText();
			Boolean s = false;
			// �ж��Ƿ��ѼӺ���
			FriendList f = new FriendList(c.a);
			FriendList fadd = new FriendList(number);
			try {
				s = f.searchFriend(number);
			} catch (DocumentException e1) {
				e1.printStackTrace();
			}
			if (s.equals(true)) {
				JOptionPane.showConfirmDialog(null, "���˺����ں����б���");
				lo_number.setText("");
			} else {
				// û�� �ж��˺��Ƿ����
				try {
					s = ClientList.selclient(number);
				} catch (DocumentException e1) {
					e1.printStackTrace();
				}
				if (s.equals(false)) {
					JOptionPane.showConfirmDialog(null, "���˺Ų�����");
					lo_number.setText("");
				} else {
					// ���� ���º����б� ˫��FL FLJ ���������¼
					try {
						f.addFriend(number);
						fadd.addFriend(c.a);
						new ChatRecordsList(c.a, number).createXml();
						new ChatRecordsList(number, c.a).createXml();
						JOptionPane.showConfirmDialog(null, "��ӳɹ�");
						dispose();
					} catch (DocumentException | IOException e1) {
						e1.printStackTrace();
					}
				}

			}

		}

	}

//	public static void main(String[] args) {
//		new Add_friend(new Message());
//
//	}

}
