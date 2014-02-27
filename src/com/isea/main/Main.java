package com.isea.main;

import com.isea.basic.BasePanel;
import com.isea.basic.LoadUtils;
import source.Icon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.net.URLClassLoader;
import java.util.Map.Entry;
import java.util.Set;

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
		setTitle("图片批量下载 - abel533");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 592, 603);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuProcess = new JMenu("操作");
		menuBar.add(menuProcess);
		
		JMenuItem menuItemSaveParameters = new JMenuItem("保存当前参数配置");
		menuProcess.add(menuItemSaveParameters);
		
		JMenuItem menuItemReadParameters = new JMenuItem("读取参数配置文件");
		menuProcess.add(menuItemReadParameters);
		
		JMenuItem menuItemSaveLog = new JMenuItem("保存日志");
		menuProcess.add(menuItemSaveLog);
		
		JMenuItem menuItemClearLog = new JMenuItem("清空日志");
		menuProcess.add(menuItemClearLog);
		
		JMenu menuPlugin = new JMenu("插件管理");
		menuBar.add(menuPlugin);
		
		JMenuItem menuItemPlugin = new JMenuItem("插件管理");
		menuPlugin.add(menuItemPlugin);
		
		JMenu menuHelp = new JMenu("帮助");
		menuBar.add(menuHelp);
		
		JMenuItem menuItemHelp = new JMenuItem("使用帮助");
		menuHelp.add(menuItemHelp);
		
		JMenuItem menuItemLearn = new JMenuItem("学习选择器");
		menuHelp.add(menuItemLearn);
		
		JMenuItem menuItemSorftUrl = new JMenuItem("访问软件地址");
		menuHelp.add(menuItemSorftUrl);
		
		JMenuItem menuItemCheckUpdata = new JMenuItem("检查更新");
		menuHelp.add(menuItemCheckUpdata);
		
		JMenuItem menuItemAbout = new JMenuItem("关于");
		menuHelp.add(menuItemAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 300));
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
		progress.setStringPainted(true);
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
		panel_2.setPreferredSize(new Dimension(10, 118));
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
		panel_3.setPreferredSize(new Dimension(10, 80));
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
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new EmptyBorder(0, 0, 0, 0));
		panel_4.setPreferredSize(new Dimension(10, 34));
		panel_3.add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(null);
		
		JLabel label_1 = new JLabel("超时时间：");
		label_1.setBounds(0, 10, 69, 15);
		panel_4.add(label_1);
		
		timeout = new JTextField();
		timeout.setHorizontalAlignment(SwingConstants.RIGHT);
		timeout.setText("3000");
		timeout.setBounds(69, 0, 114, 30);
		panel_4.add(timeout);
		timeout.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("ms          0ms为不限时");
		lblNewLabel.setBounds(185, 10, 319, 15);
		panel_4.add(lblNewLabel);
		
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
			final int _timeout;
			try {
				if(timeout.getText().equals("")){
					throw new Exception("超时时间不能为空!");
				}
				_timeout = Integer.parseInt(timeout.getText());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(Main.this, e.getMessage(), "提示", JOptionPane.WARNING_MESSAGE);
				return;
			}
			Thread thread = new Thread(new Runnable() {
				public void run() {
					try {
						BasePanel panel = (BasePanel)tabbedPane.getSelectedComponent();
						panel.download(savePath.getText(),_timeout);
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
	private JTextField timeout;
	
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
