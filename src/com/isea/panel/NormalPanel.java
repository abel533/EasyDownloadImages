package com.isea.panel;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class NormalPanel extends BasePanel {
	private static final long serialVersionUID = 9080426160199827735L;
	private JTextField url;
	private JTextField page;
	private JTextField selector;
	private JTextField pageSize;
	private JRadioButton firstRadio;
	private JTextField start;
	
	public NormalPanel() {
		setLayout(null);
		
		JLabel lblhttpwwwblogcompage = new JLabel("该模式针对地址为：http://www.blog.com/page/1 类似地址的网站");
		lblhttpwwwblogcompage.setBounds(69, 10, 431, 26);
		add(lblhttpwwwblogcompage);
		lblhttpwwwblogcompage.setHorizontalAlignment(SwingConstants.CENTER);
		lblhttpwwwblogcompage.setPreferredSize(new Dimension(200, 26));
		
		firstRadio = new JRadioButton("首地址不带页码");
		firstRadio.setSelected(true);
		firstRadio.setBounds(116, 130, 116, 23);
		add(firstRadio);
		
		JLabel label_1 = new JLabel("首地址：");
		label_1.setBounds(24, 135, 52, 15);
		add(label_1);
		
		JLabel lblNewLabel = new JLabel("基础地址：");
		lblNewLabel.setBounds(24, 54, 65, 15);
		add(lblNewLabel);
		
		url = new JTextField();
		url.setBounds(119, 46, 450, 30);
		add(url);
		url.setText("http://joyreactor.cc/tag/%25D0%25BB%25D0%25B0%25D1%2582%25D0%25B5%25D0%25BA%25D1%2581");
		url.setColumns(10);
		
		JLabel label = new JLabel("连续地址：");
		label.setBounds(24, 95, 65, 15);
		add(label);
		
		page = new JTextField();
		page.setBounds(119, 87, 450, 30);
		add(page);
		page.setText("/");
		page.setColumns(10);
		
		JLabel label_2 = new JLabel("图片选择器：");
		label_2.setBounds(24, 170, 86, 15);
		add(label_2);
		
		selector = new JTextField();
		selector.setText("div.image img");
		selector.setBounds(119, 163, 450, 30);
		add(selector);
		selector.setColumns(10);
		
		JLabel label_3 = new JLabel("页码限制：");
		label_3.setBounds(420, 135, 69, 15);
		add(label_3);
		
		pageSize = new JTextField();
		pageSize.setHorizontalAlignment(SwingConstants.RIGHT);
		pageSize.setText("10");
		pageSize.setBounds(514, 127, 55, 30);
		add(pageSize);
		pageSize.setColumns(10);
		
		JLabel label_4 = new JLabel("起始页码：");
		label_4.setBounds(248, 135, 69, 15);
		add(label_4);
		
		start = new JTextField();
		start.setHorizontalAlignment(SwingConstants.RIGHT);
		start.setText("1");
		start.setBounds(350, 126, 55, 30);
		add(start);
		start.setColumns(10);

	}

	/**
	 * 下载按钮执行的方法
	 */
	@Override
	public void download(String savePath) {
		if(!checkField()){
			return;
		}
		log("开始下载");
		log("保存路径:"+savePath);
		downLoadUtils.downloadMore(savePath, 
				url.getText(), 
				selector.getText(), 
				page.getText(),
				Integer.parseInt(start.getText()),
				Integer.parseInt(pageSize.getText()), 
				firstRadio.isSelected());
	}

	/**
	 * 校验用户填入信息
	 */
	private boolean checkField(){
		if(url.getText().equals("")){
			JOptionPane.showMessageDialog(NormalPanel.this, "基础地址不能为空", "提示", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(selector.getText().equals("")){
			JOptionPane.showMessageDialog(NormalPanel.this, "图片选择器不能为空", "提示", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(page.getText().equals("")){
			JOptionPane.showMessageDialog(NormalPanel.this, "连续地址不能为空", "提示", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(start.getText().equals("")){
			JOptionPane.showMessageDialog(NormalPanel.this, "起始页码不能为空", "提示", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(pageSize.getText().equals("")){
			JOptionPane.showMessageDialog(NormalPanel.this, "页码限制不能为空", "提示", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		return true;
	}
	
	@Override
	public void clear() {
		//清空界面信息
		url.setText("");
		selector.setText("");
		page.setText("");
		start.setText("");
		pageSize.setText("");
		firstRadio.setSelected(true);
	}
}
