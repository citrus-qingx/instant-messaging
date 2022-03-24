package client_view;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.dom4j.DocumentException;

import client.Connect;
import server.ClientList;
import server.OnlineList;

/**
 * ��½����
 * 
 * @author citrus
 *
 */

public class Login extends JFrame {

	private static final long serialVersionUID = -7433459675363575266L;

	Connect c;
	// ����
	private JLabel l;
	// �˺�
	private JLabel l1;
	private JTextField lo_number;
	// ����
	private JLabel l2;
	private JPasswordField lo_passwd;
	// ��½��ť
	private JButton b1;
	// ע�ᰴť
	private JButton b2;

	public Login() {
		// ���õ�¼���ڱ���
		this.setTitle("IM_��½");
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

		b1.addActionListener(new loginlistener());
		b2.addActionListener(new registerlistener());

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
		l1.setBounds(60, 100, 70, 20);
		// �˺������
		lo_number = new JTextField();
		lo_number.setBounds(100, 100, 150, 20);
		// ����
		l2 = new JLabel("����");
		l2.setFont(new Font("����", Font.BOLD, 14));
		l2.setBounds(60, 130, 70, 20);
		// ���������
		lo_passwd = new JPasswordField();
		lo_passwd.setBounds(100, 130, 150, 20);
		// ��½��ť
		b1 = new JButton("��¼");
		b1.setFont(new Font("����", Font.BOLD, 14));
		b1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		b1.setBounds(135, 160, 65, 20);
		// ע�ᰴť
		b2 = new JButton("ע��");
		b2.setFont(new Font("����", Font.BOLD, 14));
		b2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		b2.setBounds(0, 200, 75, 20);

		l.add(l1);
		l.add(l2);
		l.add(b1);
		l.add(b2);
		panel.add(lo_number);
		panel.add(lo_passwd);
		panel.add(l);

	}

	private class loginlistener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String number = lo_number.getText();
			String password = lo_passwd.getText();
			// ���ݿ��ж�
			String s = null;
			try {
				s = ClientList.selclientById(number);
			} catch (DocumentException e1) {
				e1.printStackTrace();
			}
			// �˺Ų���
			if (!s.equals(password)) {
				JOptionPane.showConfirmDialog(null, "�û���/���벻��ȷ");
				lo_number.setText("");
				lo_passwd.setText("");
			} else
				try {
					if (OnlineList.searchlist(number)) {
						JOptionPane.showConfirmDialog(null, "�û��ѵ�¼�������ظ���¼");
						lo_number.setText("");
						lo_passwd.setText("");
					} else {
						// ���浱ǰ��½�˺�
						// ���ӷ�����
						c = new Connect(number);
						// ������
						JOptionPane.showConfirmDialog(null, "��¼�ɹ�");
						new Main_page(c);
						// �رյ�½����
						dispose();
					}
				} catch (HeadlessException | DocumentException e1) {
					e1.printStackTrace();
				}
		}
	}

	private class registerlistener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			new Register();
			dispose();
		}

	}

	public static void main(String[] args) {
		new Login();
	}

}
