package com.isea.basic;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public interface ILoggerProgress {

	void setLogger(JTextArea logger);

	void setProgress(JProgressBar progress);

	void setMessage(JLabel message);

	/**
	 * 记录日志
	 * @param str
	 */
	void log(String str);

	/**
	 * 清空日志
	 */
	void clearLog();

	/**
	 * 显示进度
	 * @param val
	 */
	void progress(int val);

	/**
	 * 进度和文字
	 * @param val
	 * @param msg
	 */
	void progress(int val, String msg);

	/**
	 * 进度条前的提示信息
	 * @param msg
	 */
	void tips(String msg);

}