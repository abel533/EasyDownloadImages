package com.isea.basic;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;

public abstract class BasePanel extends JPanel implements ILoggerProgress {
	private static final long serialVersionUID = -7899620878551765439L;
	protected JTextArea logger;
	protected JProgressBar progress;
	protected JLabel message;
	
	@Override
	public void setLogger(JTextArea logger) {
		this.logger = logger;
	}
	
	@Override
	public void setProgress(JProgressBar progress) {
		this.progress = progress;
	}

	@Override
	public void setMessage(JLabel message) {
		this.message = message;
	}
	
	/**
	 * 放入下載類
	 * @param helper
	 */
	public void setDownloadHelper(ILoggerProgress helper){
		helper.setLogger(logger);
		helper.setMessage(message);
		helper.setProgress(progress);
	}
	
	
	@Override
	public void log(String str) {
		if(logger!=null){
			logger.append(str+"\n");
			logger.setCaretPosition(logger.getDocument().getLength());
		}
		else {
			System.out.println(str);
		}
	}
	
	@Override
	public void clearLog() {
		if(logger!=null){
			logger.setText("");
		}
	}
	
	@Override
	public void progress(int val) {
		if(progress!=null){
			progress.setValue(val);
		}
	}
	
	@Override
	public void progress(int val,String msg) {
		if(progress!=null){
			progress.setValue(val);
			progress.setString(msg);
		}
	}
	
	@Override
	public void tips(String msg) {
		if(message!=null){
			message.setText(msg);
		}
	}
	
	/**
	 * 下载图片
	 */
	public abstract void download(String savePath);
	
	/**
	 * 清空信息
	 */
	public abstract void clear();
}
