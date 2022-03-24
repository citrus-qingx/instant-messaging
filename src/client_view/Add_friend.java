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
 * 添加好友页面
 * 
 * @author citrus
 *
 */

public class Add_friend extends JFrame {

	private static final long serialVersionUID = -8226424094801114078L;

	Connect c;

	// 背景
	private JLabel l;
	// 添加好友提示
	private JLabel l1;
	// 账号
	private JLabel l2;
	private JTextField lo_number;
	// 添加按钮
	private JButton b1;

	public Add_friend(Connect c) {
		// 传用户信息
		this.c = c;
		// 设置登录窗口标题
		this.setTitle("IM_添加好友");
		// 窗体组件初始化
		init();
		// 设置退出
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
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

		b1.addActionListener(new addfri());
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
		// 添加好友提示
		l1 = new JLabel("输入账号添加好友");
		l1.setFont(new Font("楷体", Font.BOLD, 14));
		l1.setOpaque(true);
		l1.setBackground(Color.WHITE);
		l1.setBounds(110, 80, 115, 20);
		// 账号
		l2 = new JLabel("账号");
		l2.setFont(new Font("楷体", Font.BOLD, 14));
		l2.setBounds(60, 120, 70, 20);
		// 账号输入框
		lo_number = new JTextField();
		lo_number.setBounds(100, 120, 150, 20);
		// 添加按钮
		b1 = new JButton("添加");
		b1.setFont(new Font("楷体", Font.BOLD, 14));
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
			// 判断是否已加好友
			FriendList f = new FriendList(c.a);
			FriendList fadd = new FriendList(number);
			try {
				s = f.searchFriend(number);
			} catch (DocumentException e1) {
				e1.printStackTrace();
			}
			if (s.equals(true)) {
				JOptionPane.showConfirmDialog(null, "该账号已在好友列表中");
				lo_number.setText("");
			} else {
				// 没加 判断账号是否存在
				try {
					s = ClientList.selclient(number);
				} catch (DocumentException e1) {
					e1.printStackTrace();
				}
				if (s.equals(false)) {
					JOptionPane.showConfirmDialog(null, "该账号不存在");
					lo_number.setText("");
				} else {
					// 存在 更新好友列表 双方FL FLJ 创建聊天记录
					try {
						f.addFriend(number);
						fadd.addFriend(c.a);
						new ChatRecordsList(c.a, number).createXml();
						new ChatRecordsList(number, c.a).createXml();
						JOptionPane.showConfirmDialog(null, "添加成功");
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
