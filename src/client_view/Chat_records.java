package client_view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import org.dom4j.DocumentException;

import server.ChatRecordsList;

/**
 * �����¼
 * 
 * @author citrus
 *
 */

public class Chat_records extends JFrame {

	private static final long serialVersionUID = -1205710380865198217L;

	private String name;
	private String fri;

	TextArea year, month, day;
	TextArea taContent;
	JPanel p;
	JButton b;

	public Chat_records(String name, String fri) {
		this.name = name;
		this.fri = fri;
		this.setTitle("IM_�����¼ with " + fri);
		this.setLocation(400, 300);
		this.setSize(300, 300);

		year = new TextArea("��", 1, 2, 3); // һ������ ����ʾ������
		month = new TextArea("��", 1, 1, 3);
		day = new TextArea("��", 1, 1, 3);
		taContent = new TextArea();
		JButton b = new JButton("����");
		JPanel p = new JPanel();
		year.setFont(new Font("����", Font.BOLD, 14));
		month.setFont(new Font("����", Font.BOLD, 14));
		day.setFont(new Font("����", Font.BOLD, 14));
		b.setFont(new Font("����", Font.BOLD, 14));
		p.add(year);
		p.add(month);
		p.add(day);
		p.add(b);
		this.add(p, BorderLayout.NORTH);
		this.add(taContent, BorderLayout.CENTER);
		pack(); // ������Ӧ���
		this.setVisible(true);

		b.addActionListener(new searchListener());
	}

	private class searchListener implements ActionListener {

		private String y;
		private String m;
		private String d;
		private boolean flag = true;

		@Override
		public void actionPerformed(ActionEvent e) {
			y = year.getText();
			m = month.getText();
			d = day.getText();

			if (y.length() == 4 && m.length() == 2 && d.length() == 2) {
				for (int i = 0; i < y.length(); i++) {
					if (!Character.isDigit(y.charAt(i))) {
						flag = false;
						break;
					}
				}
				for (int i = 0; i < m.length(); i++) {
					if (!Character.isDigit(m.charAt(i))) {
						flag = false;
						break;
					}
				}
				for (int i = 0; i < m.length(); i++) {
					if (!Character.isDigit(m.charAt(i))) {
						flag = false;
						break;
					}
				}

			} else {
				flag = false;
			}
			if (flag == false) {
				JOptionPane.showConfirmDialog(null, "��ѯ���ڲ��Ϸ�" + "\n" + "�밴��2000 01 01�ĸ�ʽ����������");
			} else {
				try {
					taContent.setText(new ChatRecordsList(name, fri).searchlist(y + "-" + m + "-" + d));
				} catch (NullPointerException e1) {
					taContent.setText("�������¼");
				} catch (DocumentException e1) {
					e1.printStackTrace();
				}
			}

		}

	}

	public static void main(String[] args) {
		new Chat_records("3", "2");

	}

}
