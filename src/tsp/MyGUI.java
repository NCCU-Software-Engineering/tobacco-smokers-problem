package tsp;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MyGUI {

	private static JFrame frame = new JFrame();
	private static JMenuBar menuBar = new JMenuBar();
	private static JMenu menu1 = new JMenu("demo1");
	private static JMenu menu2 = new JMenu("demo2");
	private static JPanel panel1 = new JPanel();
	private static JPanel panel2 = new JPanel();

	public static void start() {
		initJFrame();
		initOne();
		initTwo();
		iniJMenuBar();

		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(panel2);
		frame.setVisible(true);
	}

	private static void initJFrame() {
		// 設定frame
		frame.setTitle("Tobacco Smokers Problem");
		frame.setSize(1200, 900);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private static void iniJMenuBar() {
		// 設定JMenuBar
		menu1.add(new JMenuItem("JButton1"));
		menu1.add(new JMenuItem("JButton1"));
		menu1.add(new JMenuItem("JButton1"));

		menu2.add(new JMenuItem("JButton2"));
		menu2.add(new JMenuItem("JButton2"));
		menu2.add(new JMenuItem("JButton2"));

		menuBar.add(menu1);
		menuBar.add(menu2);
	}

	private static void initOne() {
		// 設定開始畫面
		panel1.setLayout(new BorderLayout());
		panel1.setBackground(Color.CYAN);

		JLabel label = new JLabel("歡迎你");
		JButton button = new JButton("開始執行");

		panel1.add(BorderLayout.CENTER, label);
		panel1.add(BorderLayout.SOUTH, button);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("開始囉!!!");
				frame.getContentPane().removeAll();
				frame.getContentPane().add(panel2);
				frame.repaint();
			}
		});
	}

	private static void initTwo() {
		// 設定執行畫面
		panel2.setLayout(new BorderLayout());
		panel2.setBackground(Color.WHITE);

		BufferedImage agent = null;
		BufferedImage table = null;
		BufferedImage papers = null;
		BufferedImage tobacco = null;
		BufferedImage matches = null;
		try {
			agent = ImageIO.read(new File("D:/Desktop/img/agent.jpg"));
			table = ImageIO.read(new File("D:/Desktop/img/table.png"));
			papers = ImageIO.read(new File("D:/Desktop/img/papers.jpg"));
			tobacco = ImageIO.read(new File("D:/Desktop/img/tobacco.png"));
			matches = ImageIO.read(new File("D:/Desktop/img/matches.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		panel2.add(BorderLayout.EAST, new JLabel(new ImageIcon(papers)));
		panel2.add(BorderLayout.WEST, new JLabel(new ImageIcon(tobacco)));
		panel2.add(BorderLayout.SOUTH, new JLabel(new ImageIcon(matches)));
		panel2.add(BorderLayout.NORTH, new JLabel(new ImageIcon(agent)));
		panel2.add(BorderLayout.CENTER, new JLabel(new ImageIcon(table)));
	}
}
