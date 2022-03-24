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
 * 注册界面
 * 
 * @author citrus
 *
 */

public class Register extends JFrame {

	private static final long serialVersionUID = 3634139206529429672L;

	// 背景
	private JLabel l;
	// 注册提示
//	private JLabel l1;
	// 账号
	private JLabel l1;
	private JTextField lo_number;
	// 密码
	private JLabel l2;
	private JPasswordField lo_passwd;
	// 注册按钮
	private JButton b1;

	public Register() {
		// 设置登录窗口标题
		this.setTitle("IM_注册");
		// 窗体组件初始化
		init();
		// 设置后台退出
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 设置布局为绝对定位
		this.setLayout(null);
		this.setBounds(0, 0, 355, 265);
		// 窗体大小不能改变
		this.setResizable(false);
		// 居中显示
		this.setLocationRelativeTo(null);
		// 窗体显示
		this.setVisible(true);
		// 设置窗体的图标
		Image img = new ImageIcon("src/pic/xd.jpg").getImage();
		this.setIconImage(img);

		b1.addActionListener(new registerListener());
	}

	// 窗体组件初始化
	public void init() {
		// 将panel设置为frame的内容面板
		JPanel panel = new JPanel();
		this.setContentPane(panel);
		// 设置背景色
		l = new JLabel();
		Image img1 = new ImageIcon("src/pic/xd355x265.jpg").getImage();
		l.setIcon(new ImageIcon(img1));
		l.setBounds(0, 0, 355, 265);
		// 账号
		l1 = new JLabel("账号");
		l1.setFont(new Font("楷体", Font.BOLD, 14));
		l1.setBounds(60, 90, 70, 20);
		// 账号输入框
		lo_number = new JTextField();
		lo_number.setBounds(100, 90, 150, 20);
		// 密码
		l2 = new JLabel("密码");
		l2.setFont(new Font("楷体", Font.BOLD, 14));
		l2.setBounds(60, 120, 70, 20);
		// 密码输入框
		lo_passwd = new JPasswordField();
		lo_passwd.setBounds(100, 120, 150, 20);
		// 注册按钮
		b1 = new JButton("注册");
		b1.setFont(new Font("楷体", Font.BOLD, 14));
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
			// 写入数据库
			try {
				s = ClientList.selclientById(number);
			} catch (DocumentException e1) {
				e1.printStackTrace();
			}
			if (s.equals("")) {
				try {
					ClientList.addclient(client);
					// 新建FL
					FriendList f = new FriendList(number);
					f.createXml();
					// 弹出框
					JOptionPane.showConfirmDialog(null, "注册成功");
					new Login();
					// 关闭注册窗口
					dispose();
				} catch (DocumentException | IOException e1) {
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showConfirmDialog(null, "账号已存在");
				lo_number.setText("");
				lo_passwd.setText("");
			}

		}

	}

	public static void main(String[] args) {
		new Register();

	}

}
