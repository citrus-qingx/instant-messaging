package client_view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import org.dom4j.DocumentException;

import client.Connect;
import client.FriendList;
import server.OnlineList;

/**
 * 好友列表面板 在线/离线
 * 
 * @author citrus
 *
 */

public class FriendListJPanel extends JPanel {

	private static final long serialVersionUID = 6715815938595078180L;

	Connect c;
	String[] st;
	private JScrollPane s;
	private JPanel p, p1, p2, p3, p4;
	private JLabel l2, l3;
	private JButton f1, f4;

	private DataOutputStream out = null;

	public FriendListJPanel(Connect c) {
		super();
		this.c = c;
		try {
			st = new FriendList(c.a).friendlist();
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
		p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.Y_AXIS));
		p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.Y_AXIS));
		p3 = new JPanel();
		p3.setLayout(new BoxLayout(p3, BoxLayout.Y_AXIS));
		p4 = new JPanel();
		p4.setLayout(new BoxLayout(p4, BoxLayout.Y_AXIS));

		for (int i = 0; i < st.length; i++) {
			// 1 序号
			f1 = new JButton(String.valueOf(i + 1));
			p1.add(f1);
			// 2 好友
			l2 = new JLabel(st[i]);
			p2.add(l2);
			if (i < st.length - 1) {
				p2.add(Box.createVerticalStrut(10));
			}
			// 3 在线/离线
			try {
				if (OnlineList.searchlist(st[i])) {
					l3 = new JLabel("在线");
					if (!st[i].equals(c.a))
						f1.addActionListener(new Chatting(st[i]));
				} else {
					l3 = new JLabel("离线");
				}
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			p3.add(l3);
			if (i < st.length - 1) {
				p3.add(Box.createVerticalStrut(10));
			}
			// 4 聊天记录
			f4 = new JButton("聊天记录");
			if (!st[i].equals(c.a)) {
				f4.addActionListener(new ChatRecords(st[i]));
			}
			p4.add(f4);
		}

		p.add(p1);
		p.add(Box.createHorizontalGlue());
		p.add(p2);
		p.add(Box.createHorizontalGlue());
		p.add(p3);
		p.add(Box.createHorizontalGlue());
		p.add(p4);

		// 滚动条
		s = new JScrollPane(p, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(s);

	}

	private class Chatting implements ActionListener {

		String s;

		public Chatting(String s) {
			this.s = s;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				out = new DataOutputStream(c.s.getOutputStream());
				out.writeUTF("start...");
				out.writeUTF(s);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			new Chat(c, s);
		}

	}

	private class ChatRecords implements ActionListener {

		String s;

		public ChatRecords(String s) {
			this.s = s;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			new Chat_records(c.a, s);

		}

	}

}
