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
 * 主页
 * 
 * @author citrus
 *
 */

public class Main_page extends JFrame {

	private static final long serialVersionUID = 1953237410095564750L;

	Connect c;

	// 北部
	private JPanel p1;
	private JPanel p11;
	private JLabel l11, l12, l13;
	// 中部
	private FriendListJPanel p2;
	// 南部
	private JPanel p3;
	private JButton b31, b32;

	public Main_page(Connect c) {
		// 传用户信息
		this.c = c;
		// 设置登录窗口标题
		this.setTitle("IM_主页");
		// 窗体组件初始化
		init();
		// 设置后台退出 客户端断开 在线列表移除
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				c.clientdisconnect();
				System.exit(0);
			}

		});
		// 窗体显示
		this.setVisible(true);
		// 设置窗体的图标
		Image img = new ImageIcon("src/pic/xd.jpg").getImage();
		this.setIconImage(img);

		b31.addActionListener(new addfriendListener());
		b32.addActionListener(new refreshlist());
	}

	// 窗体组件初始化
	public void init() {

		/* 北部 */
		p1 = new JPanel();
		p11 = new JPanel();
		// BoxLayout
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		// 设置背景色
		p11.setBackground(new Color(176, 196, 222));
		// 头像
		l11 = new JLabel(new ImageIcon("src/pic/xd50x50.jpg"));
		// 账号
		l12 = new JLabel("账号：" + c.a);
		l12.setFont(new Font("楷体", Font.BOLD, 14));
		// 好友列表
		l13 = new JLabel("好友列表");
		l13.setFont(new Font("楷体", Font.BOLD, 15));
		// 设置大小
		l13.setPreferredSize(new Dimension(0, 20));
		p11.setPreferredSize(new Dimension(0, 60));
		// 左对齐
		p11.setAlignmentX(Component.LEFT_ALIGNMENT);

		p11.add(l11);
		p11.add(l12);
		p1.add(p11);
		p1.add(l13);

		/* 中部 */
		p2 = new FriendListJPanel(c);
		p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));

		/* 南部 */
		p3 = new JPanel();
		// 设置背景色
		p3.setBackground(new Color(176, 196, 222));
		b31 = new JButton("添加好友");
		b31.setFont(new Font("楷体", Font.BOLD, 18));
		b32 = new JButton("刷新页面");
		b32.setFont(new Font("楷体", Font.BOLD, 18));
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
