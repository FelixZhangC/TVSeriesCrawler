package com.seriesguide;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class SeriesChoicesFrame extends JFrame{
	JButton confirmButton=new JButton("х╥хо");
	ButtonGroup seasons=new ButtonGroup();
	ImageIcon icon=new ImageIcon("src/images/Background1.jpg");
	public void init(WebChooseAPI choiceList)
	{
		
		setLayout(new BorderLayout(30,30));
		setTitle("Choose Season");
		this.getContentPane().setBackground(Color.WHITE);
		int num=choiceList.getSize();
		LinkedHashMap<String, String> seasonsList=choiceList.getSeasonList();
		JRadioButton[] seasonArray=new JRadioButton[num];
		JPanel panel=new JPanel();
		JPanel panelButton=new JPanel();
		panel.setLayout(new GridLayout(3, 2,30,30));
		int i=0;
		for(Object key:seasonsList.keySet())
		{
			seasonArray[i]=new JRadioButton(key.toString());
			seasonArray[i].setBackground(Color.WHITE);
			panel.add(seasonArray[i]);
			seasons.add(seasonArray[i]);
			i++;
		}
		confirmButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String subURL=""; 
				boolean hasSelected=false;
				for (int j=0;j<num;j++)
				 {
					 if(seasonArray[j].isSelected()) {
						 subURL=seasonsList.get(seasonArray[j].getText());
						 hasSelected=true;
					 }	
				 }
				if (hasSelected) {
					EpisodeChooseAPI episodeChoice=new EpisodeChooseAPI(subURL);
					if (episodeChoice.getSize()==0) {
						JOptionPane.showMessageDialog(null, "No Related Resources", "Information", JOptionPane.INFORMATION_MESSAGE); 
					}
					else {
						new EpisodeChoicesFrame().init(episodeChoice);
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Please Choose the Season", "Information", JOptionPane.INFORMATION_MESSAGE); 
				}
				
			}
		});
		panelButton.add(confirmButton);
		panel.setBackground(Color.WHITE);
		panelButton.setBackground(Color.WHITE);
		add(panel,BorderLayout.NORTH);
		add(panelButton,BorderLayout.CENTER);
		//setBounds(300, 200, 400, 200);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
}
