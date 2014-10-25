package com.wondersgroup;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * Title: �ļ����� 
 * Project: DecFile 
 * @author ym.zhang
 * Description: ����ԭ�еļ����㷨����Ҫ���ܵ��ļ����н���
 * Copyright: Copyright (c) 2014
 * Company: �����Ϣ�ɷ����޹�˾
 */
public class DecFile extends JFrame {
	private JPanel root;
	private JTextField input;
	private JButton start;
	private JLabel label;

	public DecFile() {
		init();
	}

	private void init() {
		root = new JPanel(new GridLayout(4, 1, 0, 20));
		input = new JTextField("�������ļ�·��");
		start = new JButton("����");
//		start.setPreferredSize(new Dimension(10,20));
		start.setBounds(3, 2, 30, 20);
		
		label = new JLabel();
		label.setSize(50, 50);
		
		root.add(input);
		root.add(start);
		root.add(label);
		
		setContentPane(root);
		
		setSize(600, 200);
		setResizable(true);
		setVisible(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					label.setText("");
					String key = FileEncryptAndDecryptUtils.myKey;
					File fileIn = new File(input.getText());
					if(fileIn.exists()){
						//���н���
						File fileOut = new File(input.getText().substring(0,input.getText().lastIndexOf(".")));
						OutputStream output = new FileOutputStream(fileOut);
						FileEncryptAndDecryptUtils.decrypt(fileIn, output, key);
						//ɾ��Դ�ļ�
						fileIn.delete();
					}else {
						label.setText("���ܴ���������ļ�·������ȷ�����ʵ������룡");
					}
					
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
					label.setText("���ܴ���");
				}
				
				label.setText("���ܳɹ�,���ܺ���ļ���"+input.getText().substring(0,input.getText().lastIndexOf(".")));
			}
		});
	}

	public static void main(String[] args) {
		DecFile decFile = new DecFile();
	}
}
