package com.seriesguide;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedHashMap;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EpisodeChoicesFrame extends JFrame{
	JButton downloadButton=new JButton("DOWNLOAD");
	JButton chooseThunder=new JButton("ChooseThunder");
	JButton chooseAll=new JButton("Select All");
	JTextField path=new JTextField(30);
	public void init (EpisodeChooseAPI episodechoice)
	{
		setLayout(new BorderLayout());
		setTitle("Choose Episode");
		this.getContentPane().setBackground(Color.WHITE);
		LinkedHashMap<String, String> episodeList=episodechoice.getEpisodeList();
		int num=episodechoice.getSize();
		JCheckBox[] episodeArray=new JCheckBox[num];
		JPanel panel=new JPanel();
		JPanel paneldownload=new JPanel();
		JPanel panelchoose=new JPanel();
		panel.setLayout(new GridLayout(4,3,30,10));
		int i=0;
		for(Object key:episodeList.keySet())
		{
			episodeArray[i]=new JCheckBox(key.toString());
			episodeArray[i].setBackground(Color.WHITE);
			panel.add(episodeArray[i]);
			i++;
		}
		downloadButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String href="";
				for(int j=0;j<num;j++)
				{
					if (episodeArray[j].isSelected()) {
						href+=episodeList.get(episodeArray[j].getText());
						href+="\n";
					}
				}
				new ThunderDownload().download(path.getText(),href);
				//System.out.println(href);
				
			}
		});
		chooseThunder.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser jfc=new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				jfc.showDialog(new JLabel(), "选择");
				File file=jfc.getSelectedFile();
				if (file.isDirectory()) {
					path.setText("请选择Thunder文件");
				}else if (file.isFile()) {
					path.setText(file.getAbsolutePath());
				}
			}
		});
		
		chooseAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(chooseAll.getText()=="Select All")
				{
					for(int k=0;k<num;k++)
					{
						if (!episodeArray[k].isSelected()) {
							episodeArray[k].setSelected(true);
						}
					}
					chooseAll.setText("Select None");
				}
				else if (chooseAll.getText()=="Select None") {
					for(int k=0;k<num;k++)
					{
						episodeArray[k].setSelected(false);
					}
					chooseAll.setText("Select All");
				}
				
			}
		});
		paneldownload.add(chooseAll);
		paneldownload.add(downloadButton);
		panelchoose.add(path);
		panelchoose.add(chooseThunder);
		panel.setBackground(Color.WHITE);
		panelchoose.setBackground(Color.WHITE);
		paneldownload.setBackground(Color.WHITE);
		add(panel,BorderLayout.NORTH);
		add(panelchoose,BorderLayout.CENTER);
		add(paneldownload,BorderLayout.SOUTH);
		//setBounds(100, 100, 400, 300);
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
