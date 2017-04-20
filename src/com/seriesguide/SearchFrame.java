package com.seriesguide;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchFrame{
	JFrame searchFrame=new JFrame("Æ¤Æ¤Ïº×·¾ç");
	JButton searchButton=new JButton("Æ¤Æ¤Ò»Ïº");
	JTextField keywordTextField=new JTextField(30);
	ImageIcon imageiconInit=new ImageIcon("src/images/SearchFramePic.jpg");
	Image image=imageiconInit.getImage();
	Image resizePic=image.getScaledInstance(255, 200, Image.SCALE_SMOOTH);
	ImageIcon imageicon=new ImageIcon(resizePic);
	JLabel picLabel=new JLabel(imageicon);
	public void init()
	{
		searchFrame.setLayout(new BorderLayout());
		JPanel panel=new JPanel();
		JPanel panelpic=new JPanel();
		panel.setBackground(Color.WHITE);
		panelpic.setBackground(Color.WHITE);
		panelpic.add(picLabel);
		panel.add(keywordTextField);
		panel.add(searchButton);
		searchButton.setPreferredSize(new Dimension(100,30));
		keywordTextField.setPreferredSize(new Dimension(100,30));
		searchButton.setFont(new Font("ËÎÌå",Font.BOLD,12));
		keywordTextField.setFont(new Font("ËÎÌå",Font.BOLD,12));
		searchFrame.add(panelpic,BorderLayout.NORTH);
		searchFrame.add(panel,BorderLayout.CENTER);
		searchFrame.setBounds(300, 200, 500, 300);
		//searchFrame.pack();
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				WebChooseAPI seasonChoice=new WebChooseAPI("http://cn163.net/",keywordTextField.getText());
				if (keywordTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Please Input the Keyword", "Information", JOptionPane.INFORMATION_MESSAGE); 
				}
				else if (seasonChoice.getSize()==0) {
					JOptionPane.showMessageDialog(null, "No Related Resources", "Information", JOptionPane.INFORMATION_MESSAGE);
				}
				else {
					new SeriesChoicesFrame().init(seasonChoice);
				}
			}
		});
		searchFrame.setVisible(true);
		searchFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
}
