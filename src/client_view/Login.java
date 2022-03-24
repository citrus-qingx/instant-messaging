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
 * 登陆界面
 * 
 * @author citrus
 *
 */

public class Login extends JFrame {

	private static final long serialVersionUID = -7433459675363575266L;

	Connect c;
	// 背景
	private JLabel l;
	// 账号
	private JLabel l1;
	private JTextField lo_number;
	// 密码
	private JLabel l2;
	private JPasswordField lo_passwd;
	// 登陆按钮
	private JButton b1;
	// 注册按钮
	private JButton b2;

	public Login() {
		// 设置登录窗口标题
		this.setTitle("IM_登陆");
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

		b1.addActionListener(new loginlistener());
		b2.addActionListener(new registerlistener());

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
		l1.setBounds(60, 100, 70, 20);
		// 账号输入框
		lo_number = new JTextField();
		lo_number.setBounds(100, 100, 150, 20);
		// 密码
		l2 = new JLabel("密码");
		l2.setFont(new Font("楷体", Font.BOLD, 14));
		l2.setBounds(60, 130, 70, 20);
		// 密码输入框
		lo_passwd = new JPasswordField();
		lo_passwd.setBounds(100, 130, 150, 20);
		// 登陆按钮
		b1 = new JButton("登录");
		b1.setFont(new Font("楷体", Font.BOLD, 14));
		b1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		b1.setBounds(135, 160, 65, 20);
		// 注册按钮
		b2 = new JButton("注册");
		b2.setFont(new Font("楷体", Font.BOLD, 14));
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
			// 数据库判断
			String s = null;
			try {
				s = ClientList.selclientById(number);
			} catch (DocumentException e1) {
				e1.printStackTrace();
			}
			// 账号不对
			if (!s.equals(password)) {
				JOptionPane.showConfirmDialog(null, "用户名/密码不正确");
				lo_number.setText("");
				lo_passwd.setText("");
			} else
				try {
					if (OnlineList.searchlist(number)) {
						JOptionPane.showConfirmDialog(null, "用户已登录，不可重复登录");
						lo_number.setText("");
						lo_passwd.setText("");
					} else {
						// 储存当前登陆账号
						// 连接服务器
						c = new Connect(number);
						// 弹出框
						JOptionPane.showConfirmDialog(null, "登录成功");
						new Main_page(c);
						// 关闭登陆窗口
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
