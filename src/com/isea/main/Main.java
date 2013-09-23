package com.isea.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.net.URLClassLoader;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import source.Icon;

import com.isea.basic.BasePanel;
import com.isea.basic.LoadUtils;

public class Main extends JFrame {
	private static final long serialVersionUID = 8817586638061060424L;
	private JPanel contentPane;
	private JTextField savePath;
	JTabbedPane tabbedPane;
	private JTextArea logger;
	private JProgressBar progress;
	private JLabel message;
	private JButton downloadBtn;
	private JFileChooser chooser;
	
	/**启动*/
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e1) {}
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**界面*/
	public Main() {
		setIconImage(Icon.download.getImage());
		setTitle("图片批量下载");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 592, 603);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 330));
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EmptyBorder(0, 0, 0, 7));
		panel_1.setPreferredSize(new Dimension(10, 20));
		panel.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		message = new JLabel("提示信息：");
		message.setPreferredSize(new Dimension(120, 20));
		panel_1.add(message, BorderLayout.WEST);
		
		progress = new JProgressBar();
		panel_1.add(progress, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new EmptyBorder(0, 0, 0, 10));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		logger = new JTextArea();
		logger.setForeground(new Color(34, 139, 34));
		logger.setBackground(Color.BLACK);
		logger.setLineWrap(true);
		scrollPane.setViewportView(logger);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EmptyBorder(0, 0, 4, 8));
		panel_2.setPreferredSize(new Dimension(10, 74));
		panel.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		downloadBtn = new JButton("开始下载");
		downloadBtn.addActionListener(downloadListener);
		panel_2.add(downloadBtn, BorderLayout.CENTER);
		
		JButton clearBtn = new JButton("清空");
		clearBtn.addActionListener(emptyListener);
		clearBtn.setPreferredSize(new Dimension(70, 34));
		panel_2.add(clearBtn, BorderLayout.WEST);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new EmptyBorder(5, 0, 5, 0));
		panel_3.setPreferredSize(new Dimension(10, 40));
		panel_2.add(panel_3, BorderLayout.NORTH);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JLabel label = new JLabel("保存位置：");
		label.setPreferredSize(new Dimension(70, 34));
		panel_3.add(label, BorderLayout.WEST);
		
		JButton choosePath = new JButton("选择位置");
		choosePath.addActionListener(chooseListener);
		panel_3.add(choosePath, BorderLayout.EAST);
		
		savePath = new JTextField();
		panel_3.add(savePath, BorderLayout.CENTER);
		savePath.setColumns(10);
		
		JButton stopBtn = new JButton("停止下载");
		stopBtn.addActionListener(stopListener);
		stopBtn.setPreferredSize(new Dimension(77, 34));
		panel_2.add(stopBtn, BorderLayout.EAST);
		
		/**載入插件*/
		lodePlugin();
	}
	
	/**下载*/
	private ActionListener downloadListener = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			//下载方法
			downloadBtn.setEnabled(false);
			Thread thread = new Thread(new Runnable() {
				public void run() {
					try {
						BasePanel panel = (BasePanel)tabbedPane.getSelectedComponent();
						panel.download(savePath.getText());
						downloadBtn.setEnabled(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			thread.start();
			
		}
	};
	
	/**清空*/
	private ActionListener emptyListener = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			BasePanel panel = (BasePanel)tabbedPane.getSelectedComponent();
			panel.clear();
		}
	};
	
	/**选择文件位置*/
	private ActionListener chooseListener = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			chooser = new JFileChooser();
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);  
            int returnVal = chooser.showOpenDialog(Main.this);  
            if (returnVal == JFileChooser.APPROVE_OPTION) {  
            	 savePath.setText(chooser.getSelectedFile().getAbsolutePath()); 
            }  
		}
	};
	
	/**停止下载*/
	private ActionListener stopListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			Thread thread = new Thread(new Runnable() {
				public void run() {
					try {
						BasePanel panel = (BasePanel)tabbedPane.getSelectedComponent();
						panel.stop();
						downloadBtn.setEnabled(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			thread.start();
		}
	};
	
	/**載入插件*/
	public void lodePlugin(){
		String pluginPath = null;
		try {
			pluginPath = LoadUtils.getProjectPath(this.getClass())+"/plugin";
		} catch (Exception e) {
			JOptionPane.showMessageDialog(Main.this, e.getMessage());
			return;
		}
		try {
			//load jar
			URLClassLoader loader = LoadUtils.loadJars(pluginPath);
			//loadIni
			Set<Entry<Object, Object>> sets = LoadUtils.loadInis(pluginPath);
			
			for(Entry<Object, Object> set:sets){
				BasePanel basePanel = (BasePanel)loader.loadClass(set.getValue().toString()).newInstance();
				basePanel.setLogger(logger);
				basePanel.setMessage(message);
				basePanel.setProgress(progress);
				tabbedPane.add(set.getKey().toString(), basePanel);
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(Main.this, e.getMessage());
			return;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(Main.this, e.getMessage());
			return;
		}
	}
	
	
}
