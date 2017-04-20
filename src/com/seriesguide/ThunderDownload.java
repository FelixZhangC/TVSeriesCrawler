package com.seriesguide;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

public class ThunderDownload {
	public void download(String filepath,String href)
	{
		Runtime runtime=Runtime.getRuntime();
		Process process=null;
		try {
			process=runtime.exec(filepath);
		} catch (Exception e) {
			System.out.println("Fail to Run");
		}
		//��ȡϵͳ���а�
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		//����String��������
		StringSelection selection = new StringSelection(href);
		//����ı���ϵͳ���а�
		clipboard.setContents(selection, null);
	}
}
