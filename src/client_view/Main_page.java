package client_view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import client.Connect;

/**
 * ��ҳ
 * 
 * @author citrus
 *
 */

public class Main_page extends JFrame {

	private static final long serialVersionUID = 1953237410095564750L;

	Connect c;

	// ����
	private JPanel p1;
	private JPanel p11;
	private JLabel l11, l12, l13;
	// �в�
	private FriendListJPanel p2;
	// �ϲ�
	private JPanel p3;
	private JButton b31, b32;

	public Main_page(Connect c) {
		// ���û���Ϣ
		this.c = c;
		// ���õ�¼���ڱ���
		this.setTitle("IM_��ҳ");
		// ���������ʼ��
		init();
		// ���ú�̨�˳� �ͻ��˶Ͽ� �����б��Ƴ�
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				c.clientdisconnect();
				System.exit(0);
			}

		});
		// ������ʾ
		this.setVisible(true);
		// ���ô����ͼ��
		Image img = new ImageIcon("src/pic/xd.jpg").getImage();
		this.setIconImage(img);

		b31.addActionListener(new addfriendListener());
		b32.addActionListener(new refreshlist());
	}

	// ���������ʼ��
	public void init() {

		/* ���� */
		p1 = new JPanel();
		p11 = new JPanel();
		// BoxLayout
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		// ���ñ���ɫ
		p11.setBackground(new Color(176, 196, 222));
		// ͷ��
		l11 = new JLabel(new ImageIcon("src/pic/xd50x50.jpg"));
		// �˺�
		l12 = new JLabel("�˺ţ�" + c.a);
		l12.setFont(new Font("����", Font.BOLD, 14));
		// �����б�
		l13 = new JLabel("�����б�");
		l13.setFont(new Font("����", Font.BOLD, 15));
		// ���ô�С
		l13.setPreferredSize(new Dimension(0, 20));
		p11.setPreferredSize(new Dimension(0, 60));
		// �����
		p11.setAlignmentX(Component.LEFT_ALIGNMENT);

		p11.add(l11);
		p11.add(l12);
		p1.add(p11);
		p1.add(l13);

		/* �в� */
		p2 = new FriendListJPanel(c);
		p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));

		/* �ϲ� */
		p3 = new JPanel();
		// ���ñ���ɫ
		p3.setBackground(new Color(176, 196, 222));
		b31 = new JButton("��Ӻ���");
		b31.setFont(new Font("����", Font.BOLD, 18));
		b32 = new JButton("ˢ��ҳ��");
		b32.setFont(new Font("����", Font.BOLD, 18));
		p3.add(b31);
		p3.add(b32);

		this.add(p1, BorderLayout.NORTH);
		this.add(p2, BorderLayout.CENTER);
		this.add(p3, BorderLayout.SOUTH);
		this.pack();

	}

	private class addfriendListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Add_friend(c);
		}

	}

	private class refreshlist implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Main_page(c);
			dispose();
		}

	}

//	public static void main(String[] args) {
//		new Main_page(new Connect(new Message()));
//	}

}
