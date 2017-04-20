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
		//获取系统剪切板
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		//构建String数据类型
		StringSelection selection = new StringSelection(href);
		//添加文本到系统剪切板
		clipboard.setContents(selection, null);
	}
}
