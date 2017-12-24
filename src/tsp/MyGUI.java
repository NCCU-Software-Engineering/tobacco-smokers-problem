package tsp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

@ClassInfo(createdDate = "Dec 23 2017", createdBy = "Lia", lastModified = "Dec 23 2013", lastModifiedBy = "Lia", version = "1.0.0")
public class MyGUI extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	private Thread thread;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menu1 = new JMenu("放入");
	private JMenu menu2 = new JMenu("拿材料");
	private JMenu menu3 = new JMenu("組合香菸");
	private JMenu menu4 = new JMenu("選項");
	private JPanel panel1;
	private JPanel panel2;

	private int itemCount;
	private int[] moveCount = new int[2];
	private String[] itemName = new String[2];
	private int[] itemState = new int[2];
	private float[][] movePosition = new float[2][2];

	// image
	private ImageIcon agentImg;
	private ImageIcon manImg;
	private ImageIcon smokerImg;
	private ImageIcon angerImg;
	private ImageIcon tableImg;
	private ImageIcon papersImg;
	private ImageIcon tobaccoImg;
	private ImageIcon matchesImg;
	private Image[] itemImg = new Image[2];

	// JLabel
	private JLabel agent = new JLabel("");
	private JLabel[] man = { new JLabel(""), new JLabel(""), new JLabel("") };
	private JLabel table = new JLabel("");
	private JLabel papers = new JLabel("");
	private JLabel tobacco = new JLabel("");
	private JLabel matches = new JLabel("");

	private final int imgSizeX = 100;
	private final int imgSizeY = 100;
	// agent
	private Position agentPosition = new Position(550, 0);
	private Position startPosition = new Position(450, 55);
	// table
	private Position tablePosition = new Position(472, 322);
	private Position[] tableItemPosition = { new Position(500, 240), new Position(600, 240) };
	// man
	private Position[] manPosition = { new Position(0, 400), new Position(1100, 400), new Position(550, 725) };
	private float[][] itemPosition = new float[2][2];
	private Position[][] emptyPosition = { { new Position(100, 300), new Position(100, 500) },
			{ new Position(1000, 300), new Position(1000, 500) }, { new Position(450, 625), new Position(650, 625) } };
	// man item
	private Position tobaccoPosition = new Position(100, 400);
	private Position papersPosition = new Position(1000, 400);
	private Position matchesPosition = new Position(550, 625);

	public MyGUI(String title) {
		super();
		initImg();
		initJMenuBar();
		initOne();
		initTwo();

		setTitle(title);
		setSize(1200, 900);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(menuBar);
		getContentPane().add(panel2);
		setVisible(true);

		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}

	@Override
	public void update(Graphics g) {
		super.update(g);
	}

	@Override
	public void run() {
		while (true) {

			for (int i = 0; i < itemCount; i++) {
				if (itemState[i] == 1 || itemState[i] == 3 || itemState[i] == 5) {
					itemPosition[i][0] += movePosition[i][0];
					itemPosition[i][1] += movePosition[i][1];
					moveCount[i]--;
					if (moveCount[i] < 0) {
						itemState[i]++;
					}
				}
			}
			getContentPane().repaint();
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void initImg() {
		agentImg = new ImageIcon("img/agent.png");
		manImg = new ImageIcon("img/man.png");
		angerImg = new ImageIcon("img/anger.png");
		smokerImg = new ImageIcon("img/smoker.png");
		tableImg = new ImageIcon("img/table.png");
		papersImg = new ImageIcon("img/papers.png");
		tobaccoImg = new ImageIcon("img/tobacco.png");
		matchesImg = new ImageIcon("img/matches.png");
	}

	private void initOne() {
		// 設定開始畫面
		panel1 = new JPanel();
		panel1.setLayout(new BorderLayout());
		panel1.setBackground(Color.CYAN);

		JLabel label = new JLabel("歡迎你");
		JButton button = new JButton("開始執行");

		panel1.add(BorderLayout.CENTER, label);
		panel1.add(BorderLayout.SOUTH, button);

		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("開始囉!!!");
				getContentPane().removeAll();
				getContentPane().add(panel2);
				validate();
			}
		});
	}

	private void initTwo() {
		// 設定執行畫面
		panel2 = new JPanel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				for (int i = 0; i < itemCount; i++) {
					g.drawImage(itemImg[i], (int) itemPosition[i][0], (int) itemPosition[i][1], null);
				}
			}
		};

		panel2.setLayout(null);
		panel2.setBackground(Color.WHITE);

		for (int i = 0; i < 3; i++) {
			man[i].setIcon(manImg);
			man[i].setBounds(manPosition[i].x, manPosition[i].y, imgSizeX, imgSizeY);
			panel2.add(man[i]);
		}
		agent.setIcon(agentImg);
		table.setIcon(tableImg);
		papers.setIcon(papersImg);
		tobacco.setIcon(tobaccoImg);
		matches.setIcon(matchesImg);

		agent.setBounds(agentPosition.x, agentPosition.y, imgSizeX, imgSizeY);
		table.setBounds(tablePosition.x, tablePosition.y, 256, 256);
		papers.setBounds(papersPosition.x, papersPosition.y, imgSizeX, imgSizeY);
		tobacco.setBounds(tobaccoPosition.x, tobaccoPosition.y, imgSizeX, imgSizeY);
		matches.setBounds(matchesPosition.x, matchesPosition.y, imgSizeX, imgSizeY);

		panel2.add(agent);
		panel2.add(table);
		panel2.add(papers);
		panel2.add(tobacco);
		panel2.add(matches);
	}

	private void initJMenuBar() {
		// 設定JMenuBar
		JMenuItem putTobacco = new JMenuItem("放入tobacco");
		JMenuItem putMatches = new JMenuItem("放入matches");
		JMenuItem putPapers = new JMenuItem("放入papers");

		menu1.add(putTobacco);
		menu1.add(putMatches);
		menu1.add(putPapers);

		putTobacco.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		putMatches.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		putPapers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		JMenuItem get11 = new JMenuItem("把桌上的1給拿tobacco的人");
		JMenuItem get12 = new JMenuItem("把桌上的1給拿matches的人");
		JMenuItem get13 = new JMenuItem("把桌上的1給拿papers的人");
		JMenuItem get21 = new JMenuItem("把桌上的2給拿tobacco的人");
		JMenuItem get22 = new JMenuItem("把桌上的2給拿matches的人");
		JMenuItem get23 = new JMenuItem("把桌上的2給拿papers的人");

		menu2.add(get11);
		menu2.add(get12);
		menu2.add(get13);
		menu2.add(get21);
		menu2.add(get22);
		menu2.add(get23);

		get11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		get12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		get13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		get21.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		get22.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});
		get23.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		JMenuItem smokeTobacco = new JMenuItem("tobacco抽菸");
		JMenuItem smokeMatches = new JMenuItem("matches抽菸");
		JMenuItem smokePapers = new JMenuItem("papers抽菸");

		menu3.add(smokeTobacco);
		menu3.add(smokeMatches);
		menu3.add(smokePapers);

		smokeTobacco.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				smoke(0);
			}
		});
		smokeMatches.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				smoke(2);
			}
		});
		smokePapers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				smoke(1);
			}
		});

		JMenuItem re = new JMenuItem("重新開始");

		menu4.add(re);

		re.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restart();
			}
		});

		menuBar.add(menu1);
		menuBar.add(menu2);
		menuBar.add(menu3);
		menuBar.add(menu4);
	}

	public void put(String item1, String item2) {
		if (itemCount == 0) {
			switch (item1) {
			case "tobacco":
				itemImg[0] = tobaccoImg.getImage();
				break;
			case "matches":
				itemImg[0] = matchesImg.getImage();
				break;
			case "paper":
				itemImg[0] = papersImg.getImage();
				break;
			}
			switch (item2) {
			case "tobacco":
				itemImg[1] = tobaccoImg.getImage();
				break;
			case "matches":
				itemImg[1] = matchesImg.getImage();
				break;
			case "paper":
				itemImg[1] = papersImg.getImage();
				break;
			}

			itemName[0] = item1;
			itemName[1] = item2;

			itemPosition[0][0] = startPosition.x;
			itemPosition[0][1] = startPosition.y;
			itemPosition[1][0] = startPosition.x;
			itemPosition[1][1] = startPosition.y;

			movePosition[0][0] = (tableItemPosition[0].x - itemPosition[0][0]) / 100;
			movePosition[0][1] = (tableItemPosition[0].y - itemPosition[0][1]) / 100;
			movePosition[1][0] = (tableItemPosition[1].x - itemPosition[1][0]) / 100;
			movePosition[1][1] = (tableItemPosition[1].y - itemPosition[1][1]) / 100;

			moveCount[0] = 100;
			moveCount[1] = 100;

			itemState[0] = 1;
			itemState[1] = 1;

			itemCount = 2;
		} else {
			System.out.println("too soon1");
		}
	}

	public void get(int ID, String item) {

		int i;
		if (item == itemName[0]) {
			i = 0;
		} else if (item == itemName[1]) {
			i = 1;
		} else {
			return;
		}

		movePosition[i][0] = (emptyPosition[ID][i].x - itemPosition[i][0]) / 100;
		movePosition[i][1] = (emptyPosition[ID][i].y - itemPosition[i][1]) / 100;

		moveCount[i] = 100;
		itemState[i] = 3;
	}

	public void smoke(int ID) {
		if (itemState[0] == 4 && itemState[1] == 4) {
			panel2.remove(man[ID]);
			man[ID].setIcon(smokerImg);
			panel2.add(man[ID]);
			repaint();
		} else {
			System.out.println("too soon3");
		}
	}

	public void anger(int ID) {
		panel2.remove(man[ID]);
		man[ID].setIcon(angerImg);
		panel2.add(man[ID]);
		repaint();
	}

	public void restart() {
		for (int i = 0; i < 3; i++) {
			panel2.remove(man[i]);
			man[i].setIcon(manImg);
			panel2.add(man[i]);
		}
		itemCount = 0;
		itemState[0] = 0;
		itemState[1] = 0;
		panel2.repaint();
	}

	public class Position {
		public int x;
		public int y;

		public Position(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

}
