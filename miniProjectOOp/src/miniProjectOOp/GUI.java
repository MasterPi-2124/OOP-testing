package miniProjectOOp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class GUI extends JFrame implements ActionListener {

	private final JFileChooser fileDialog = new JFileChooser();
	private final String title = "Duyet Do Thi";
	private final String author = "Nhom 14";
	private final int colTextField = 5;
	private int widthGraphicsPanl = 1000;
	private int heightGraphicsPanl = 0;
	private final int maxPoint = 100;
	public GraphicsPanel graphicsPanel;
	private Graph graph;
	private JPanel mainPanel = new JPanel(new BorderLayout()), panel1, panel2; 
	private JButton btnCreateGraph, btnAddEdge, btnRun, btnFileChoose, btnImage, btnTab1, btnTab2;
	private JRadioButton radBFS, radDFS;
	private JTextField tfNumberPoint, tfBeginPoint, tfEndPoint, tfStartPoint;
	private JButton btnSimulation;
	
	private static final long serialVersionUID = 1L;
	// frame
	private JFrame frameAbout, frameHelp;
	private String[] listGraphDemo = { "0", "1", "2", "3", "4", "5" };
	private String data[][], head[];
	private JComboBox<String> cbbBeginPoint = new JComboBox<String>();
	private JComboBox<String> cbbEndPoint = new JComboBox<String>();
	private JComboBox<String> cbbGraphDemo = new JComboBox<String>();
	private JRadioButton radUndirected, radDirected, radDraw, radDemo;
	private JButton btnRunAll, btnRunStep;
	private JTable tableMatrix;
	private JTable tableLog;
	// draw
	private JPanel drawPanel = new JPanel();
	private JButton btnPoint, btnLine, btnUpdate, btnMove, btnOpen, btnSave,
			btnNew;
	// graph
	private MyDraw myDraw = new MyDraw();
	// log
	private JTextArea textLog;
	private JTextArea textMatrix;
	private JTextField textNumerPoint;
	private MyPopupMenu popupMenu;
	private int indexBeginPoint = 0, indexEndPoint = 0;
	private int step = 0;
	private boolean mapType = false;

	int WIDTH_SELECT, HEIGHT_SELECT;

	MyDijkstra dijkstra = new MyDijkstra();
	
	public GUI() {
		add(createMainPanel());
		setDisplay();
	}

	private void setDisplay() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(title);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout());

//		panel.add(createTitlePanel(), BorderLayout.PAGE_START);
		panel.add(creatMenu(), BorderLayout.PAGE_START);
		panel.add(createControlPanel(), BorderLayout.WEST);
		panel.add(createDrawPanel(), BorderLayout.CENTER);
		panel.add(createAuthorPanel(), BorderLayout.PAGE_END);
		panel.add(createAddPanel(), BorderLayout.EAST);

		graphicsPanel.setPreferredSize(new Dimension(800, 600));
		return panel;
	}

	private JPanel createDrawPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 5, 0, 0));
		panel.add(btnTab1 = new JButton("Do thi vo huong"));
		panel.add(btnTab2 = new JButton("Do thi co huong"));
		panel.add(new JLabel());
		panel.add(new JLabel());
		panel.add(new JLabel());
		btnTab1.addActionListener(this);
		btnTab2.addActionListener(this);
		btnTab1.setBackground(Color.LIGHT_GRAY);
		btnTab2.setBackground(Color.white);
		createShowTab1Panel();
		createShowTab2Panel();
		mainPanel.add(panel, BorderLayout.PAGE_START);
		mainPanel.add(panel1, BorderLayout.CENTER);
		return mainPanel;
	}
	
	private JPanel createTitlePanel() {
		JPanel panel = new JPanel();
		panel.add(new JLabel(title.toUpperCase()));
		return panel;
	}
	
	private JPanel createAddPanel() {
		
		JPanel panelFirst = new JPanel(new FlowLayout());
		panelFirst.add(btnFileChoose = createButton("choose file"));
		panelFirst.add(btnImage = createButton("print screen"));

		JPanel panel = new JPanel(new BorderLayout());
		JPanel panelTop = new JPanel(new GridLayout(5, 1, 5, 5));
		JPanel panelBottom = new JPanel(new BorderLayout());

		JPanel panelMapTypeTemp = new JPanel(new GridLayout(1, 2, 5, 5));
		panelMapTypeTemp.setBorder(new EmptyBorder(0, 10, 0, 5));
		panelMapTypeTemp.add(radUndirected = createRadioButton("Undirected",
				true));
		panelMapTypeTemp
				.add(radDirected = createRadioButton("Directed", false));
		ButtonGroup groupMapType = new ButtonGroup();
		groupMapType.add(radUndirected);
		groupMapType.add(radDirected);
		JPanel panelMapType = new JPanel(new BorderLayout());
		panelMapType.add(panelMapTypeTemp);

		JPanel panelInputMethodTemp = new JPanel(new GridLayout(1, 2, 5, 5));
		panelInputMethodTemp.setBorder(new EmptyBorder(0, 10, 0, 5));
		panelInputMethodTemp.add(radDraw = createRadioButton("Draw", true));
		JPanel panelDemo = new JPanel(new BorderLayout());
		panelDemo.add(radDemo = createRadioButton("Demo", false),
				BorderLayout.WEST);
		panelDemo.add(cbbGraphDemo = createComboxBox("0"), BorderLayout.CENTER);
		cbbGraphDemo.setEnabled(false);
		cbbGraphDemo.setModel(new DefaultComboBoxModel<String>(listGraphDemo));
		cbbGraphDemo.setMaximumRowCount(3);
		panelInputMethodTemp.add(panelDemo);
		ButtonGroup groupInputMethod = new ButtonGroup();
		groupInputMethod.add(radDraw);
		groupInputMethod.add(radDemo);
		JPanel panelInputMethod = new JPanel(new BorderLayout());
		panelInputMethod.add(panelInputMethodTemp);

		JPanel panelSelectPointTemp = new JPanel(new GridLayout(1, 2, 15, 5));
		panelSelectPointTemp.setBorder(new EmptyBorder(0, 15, 0, 5));
		panelSelectPointTemp.add(cbbBeginPoint = createComboxBox("Begin"));
		panelSelectPointTemp.add(cbbEndPoint = createComboxBox("End"));
		JPanel panelSelectPoint = new JPanel(new BorderLayout());
		panelSelectPoint.add(panelSelectPointTemp);

		JPanel panelRunTemp = new JPanel(new GridLayout(1, 2, 15, 5));
		panelRunTemp.setBorder(new EmptyBorder(0, 15, 0, 5));
		panelRunTemp.add(btnRunAll = createButton("Run All"));
		panelRunTemp.add(btnRunStep = createButton("Run Step"));
		JPanel panelRun = new JPanel(new BorderLayout());
		panelRun.add(panelRunTemp);
		panelTop.add(panelFirst);
		panelTop.add(panelMapType);
		panelTop.add(panelInputMethod);
		panelTop.add(panelSelectPoint);
		panelTop.add(panelRun);
		JPanel abcJPanel = new JPanel();
		abcJPanel.add(panelTop);
		widthGraphicsPanl = (int) panelTop.getPreferredSize().getWidth();
		heightGraphicsPanl = (int) panelTop.getPreferredSize().getHeight();
		return abcJPanel;
	}
	
	private JMenuBar creatMenu() {

		JMenu menuFile = new JMenu("File");
		menuFile.setMnemonic(KeyEvent.VK_F);
		// menuFile.add(menuFileNew);
		menuFile.add(createMenuItem("New", KeyEvent.VK_N, Event.CTRL_MASK));
		menuFile.add(createMenuItem("Open", KeyEvent.VK_O, Event.CTRL_MASK));
		menuFile.add(createMenuItem("Save", KeyEvent.VK_S, Event.CTRL_MASK));
		menuFile.addSeparator();
		menuFile.add(createMenuItem("Exit", KeyEvent.VK_X, Event.CTRL_MASK));

		JMenu menuHelp = new JMenu("Help");
		menuHelp.setMnemonic(KeyEvent.VK_H);
		menuHelp.add(createMenuItem("Help", KeyEvent.VK_H, Event.CTRL_MASK));
		menuHelp.add(createMenuItem("About", KeyEvent.VK_A, Event.CTRL_MASK));

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menuFile);
		menuBar.add(menuHelp);
		return menuBar;
	}


	private JPanel createControlPanel() {

		// create select panel - select algorithm
		JPanel selectPanel = new JPanel(new GridLayout(9, 3, 1, 1));
//		selectPanel.setBorder(new TitledBorder("Khá»Ÿi táº¡o"));
		selectPanel.add(new JLabel("Thuat Toan:"));
		selectPanel.add(radBFS = createRadioButton("BFS", true));
		selectPanel.add(new JLabel(""));
		selectPanel.add(radDFS = createRadioButton("DFS", false));
		selectPanel.add(btnSimulation = new JButton("Simulation"));
		btnSimulation.addActionListener(this);
		btnSimulation.setEnabled(false);
		selectPanel.add(new JLabel("So Dinh:"));
		selectPanel.add(tfNumberPoint = createTextField());
		selectPanel.add(new JLabel(""));
		selectPanel.add(btnCreateGraph = createButton("Nhap:"));

		ButtonGroup btnG = new ButtonGroup();
		btnG.add(radBFS);
		btnG.add(radDFS);

		// create edge panel - select algorithm
		JPanel edgePanel = new JPanel(new GridLayout(4, 2, 5, 10));
		edgePanel.add(new JLabel("Nhap Canh:"));
		edgePanel.add(new JLabel(""));
		edgePanel.add(new JLabel("Dinh Dau:"));
		edgePanel.add(tfBeginPoint = createTextField());
		edgePanel.add(new JLabel("Dinh Cuoi:"));
		edgePanel.add(tfEndPoint = createTextField());
		edgePanel.add(new JLabel(""));
		edgePanel.add(btnAddEdge = createButton("Them"));

		

		JPanel contentPanel = new JPanel(new BorderLayout());
		contentPanel.add(selectPanel, BorderLayout.PAGE_START);
		contentPanel.add(edgePanel, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		panel.add(contentPanel);
		heightGraphicsPanl = (int) contentPanel.getPreferredSize().getHeight();
		return panel;
	}

	private JPanel createShowTab1Panel() {
		panel1 = new JPanel(new BorderLayout());
		panel1.add(graphicsPanel = new GraphicsPanel());
		panel1.setSize(new Dimension(widthGraphicsPanl, 500));
		return panel1;
	}
	private JPanel createShowTab2Panel() {
		panel2 = new JPanel(new BorderLayout());
		panel2.setSize(new Dimension(widthGraphicsPanl, 500));
		popupMenu = createPopupMenu();
		myDraw.setComponentPopupMenu(popupMenu);
		panel2.add(myDraw, BorderLayout.CENTER);
		return panel2;
	}

	private JPanel createAuthorPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel(author.toUpperCase()), BorderLayout.WEST);// create edge panel - select algorithm
		JPanel runPanel = new JPanel(new GridLayout(2, 2, 5, 5));
		runPanel.add(new JLabel("Tham Tu Dinh:"));
		runPanel.add(tfStartPoint = createTextField());
		runPanel.add(new JLabel(""));
		runPanel.add(btnRun = createButton("Duyet:"));
		panel.add(runPanel, BorderLayout.CENTER);
		panel.add(creatLogPanel(), BorderLayout.EAST);
		return panel;
	}

	private JRadioButton createRadioButton(String text, boolean select) {
		JRadioButton rad = new JRadioButton(text, select);
		rad.addActionListener(this);
		return rad;
	}

	private JTextField createTextField() {
		JTextField tf = new JTextField();
		return tf;
	}

//		private void showHelp() {
//			if (frameHelp == null) {
//				frameHelp = new HelpAndAbout(0, "Dijkstra - Help");
//			}
//			frameHelp.setVisible(true);
//		}
//
//		private void showAbout() {
//			if (frameAbout == null) {
//				frameAbout = new HelpAndAbout(1, "Dijkstra - About");
//			}
//			frameAbout.setVisible(true);
//		}

	// ----------------------
		
		private ImageIcon getIcon(String link) {
			return new ImageIcon(getClass().getResource(link));
		}

		private JPanel creatLogPanel() {
			textLog = new JTextArea("Path: ");
			textLog.setRows(3);
			textLog.setEditable(false);
			JScrollPane scrollPath = new JScrollPane(textLog);

			JPanel panel = new JPanel(new BorderLayout());
			panel.setBorder(new TitledBorder("Log"));
			panel.add(scrollPath);
			panel.setPreferredSize(new Dimension(widthGraphicsPanl,
					0));
			return panel;
		}

		private JMenuItem createMenuItem(String title, int keyEvent, int event) {
			JMenuItem mi = new JMenuItem(title);
			mi.setMnemonic(keyEvent);
			mi.setAccelerator(KeyStroke.getKeyStroke(keyEvent, event));
			mi.addActionListener(this);
			return mi;
		}

		private MyPopupMenu createPopupMenu() {
			MyPopupMenu popup = new MyPopupMenu();

			popup.add(createMenuItem("Change cost", 0, 0));
			popup.add(createMenuItem("Delete", 0, 0));

			return popup;
		}

		// create radioButton on group btnGroup and add to panel
		private JRadioButton createRadioButton(String lable, Boolean select) {
			JRadioButton rad = new JRadioButton(lable);
			rad.addActionListener(this);
			rad.setSelected(select);
			return rad;
		}

		// create button and add to panel
		private JButton createButton(String lable) {
			JButton btn = new JButton(lable);
			btn.addActionListener(this);
			return btn;
		}

		// create buttonImage and add to panel
		private JButton createButtonImage(Icon icon, String toolTip) {
			JButton btn = new JButton(icon);
			btn.setMargin(new Insets(0, 0, 0, 0));
			btn.addActionListener(this);
			btn.setToolTipText(toolTip);
			return btn;
		}

		// create comboBox and add to panel
		private JComboBox<String> createComboxBox(String title) {
			String list[] = { title };
			JComboBox<String> cbb = new JComboBox<String>(list);
			cbb.addActionListener(this);
			cbb.setEditable(false);
			cbb.setMaximumRowCount(5);
			return cbb;
		}

		// create matrix panel with cardLayout

		private JTable createTable() {
			JTable table = new JTable();
			return table;
		}

		// ------------------ Action ------------------//

		private void actionUpdate() {
			updateListPoint();
			resetDataDijkstra();
			setDrawResultOrStep(false);
			reDraw();
//			loadMatrix();
//			clearLog();
		}

		private void actionDrawPoint() {
			myDraw.setDraw(1);
			setDrawResultOrStep(false);
		}

		private void actionDrawLine() {
			myDraw.setDraw(2);
			setDrawResultOrStep(false);
		}

		private void actionOpen() {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open graph");
			int select = fc.showOpenDialog(this);
			if (select == 0) {
				String path = fc.getSelectedFile().getPath();
				System.out.println(path);
				myDraw.readFile(path);
				actionUpdate();
			}
		}

		private void actionSave() {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Save graph");
			int select = fc.showSaveDialog(this);
			if (select == 0) {
				String path = fc.getSelectedFile().getPath();
				System.out.println(path);
				myDraw.write(path);
			}
		}

		private void actionNew() {
			setDrawResultOrStep(false);
			myDraw.setResetGraph(true);
			myDraw.repaint();
			myDraw.init();
			updateListPoint();
			clearLog();
			clearMatrix();
		}

		private void actionChoosePoint() {
			resetDataDijkstra();
			setDrawResultOrStep(false);
			reDraw();
			clearLog();
		}

		private void showDialogChangeCost() {
			int index = myDraw.indexLineContain(popupMenu.getPoint());
			if (index > 0) {
				myDraw.changeCost(index);
				actionUpdate();
			} else {
				JOptionPane.showMessageDialog(null, "Haven't line seleced!");
			}
		}

		private void showDialogDelete() {
			int index = myDraw.indexPointContain(popupMenu.getPoint());
			if (index <= 0) {
				index = myDraw.indexLineContain(popupMenu.getPoint());
				if (index > 0) {
					// show message dialog
					MyLine ml = myDraw.getData().getArrMyLine().get(index);
					String message = "Do you want delete the line from "
							+ ml.getIndexPointA() + " to " + ml.getIndexPointB();
					int select = JOptionPane.showConfirmDialog(this, message,
							"Delete line", JOptionPane.OK_CANCEL_OPTION);
					if (select == 0) {
						myDraw.deleteLine(index);
						actionUpdate();
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Haven't point or line seleced!");
				}
			} else {
				// show message dialog
				String message = "Do you want delete the point " + index;
				int select = JOptionPane.showConfirmDialog(this, message,
						"Delete point", JOptionPane.OK_CANCEL_OPTION);
				if (select == 0) {
					myDraw.deletePoint(index);
					actionUpdate();
				}
			}
		}

		private void updateListPoint() {
			int size = myDraw.getData().getArrMyPoint().size();
			String listPoint[] = new String[size];
			listPoint[0] = "Begin";
			for (int i = 1; i < listPoint.length; i++) {
				listPoint[i] = String.valueOf(i);
			}

			cbbBeginPoint.setModel(new DefaultComboBoxModel<String>(listPoint));
			cbbBeginPoint.setMaximumRowCount(5);

			if (size > 1) {
				listPoint = new String[size + 1];
				listPoint[0] = "End";
				for (int i = 1; i < listPoint.length; i++) {
					listPoint[i] = String.valueOf(i);
				}
				listPoint[listPoint.length - 1] = "All";
			} else {
				listPoint = new String[1];
				listPoint[0] = "End";
			}

			cbbEndPoint.setModel(new DefaultComboBoxModel<String>(listPoint));
			cbbEndPoint.setMaximumRowCount(5);
		}

		private void setEnableDraw(boolean check, String matrix) {
			// btnLine.setEnabled(check);
			// btnPoint.setEnabled(check);
			// btnUpdate.setEnabled(check);

			// CardLayout cl = (CardLayout) (matrixPandl.getLayout());
			// cl.show(matrixPandl, matrix);
			cbbGraphDemo.setEnabled(!check);
		}

		private void setEnableMapType(boolean mapType) {
			this.mapType = mapType;
			myDraw.setTypeMap(mapType);
			setDrawResultOrStep(false);
			myDraw.repaint();
			resetDataDijkstra();
			loadMatrix();
		}

		private void setDrawResultOrStep(boolean check) {
			myDraw.setDrawResult(check);
			myDraw.setDrawStep(check);
		}

		private void resetDataDijkstra() {
			step = 0;
			dijkstra = new MyDijkstra();
			dijkstra.setMapType(mapType);
			dijkstra.setArrMyPoint(myDraw.getData().getArrMyPoint());
			dijkstra.setArrMyLine(myDraw.getData().getArrMyLine());
			dijkstra.input();
			dijkstra.processInput();
		}

		private void reDraw() {
			myDraw.setReDraw(true);
			myDraw.repaint();
		}

		private void clearMatrix() {
			DefaultTableModel model = new DefaultTableModel();
			tableMatrix.setModel(model);
		}

		private void clearLog() {
			DefaultTableModel model = new DefaultTableModel();
			tableLog.setModel(model);
			clearPath();
		}

		private void clearPath() {
			textLog.setText("Path : ");
		}

		private void loadMatrix() {
			final int width = 35;
			final int col = WIDTH_SELECT / width - 1;
			int infinity = dijkstra.getInfinity();
			int a[][] = dijkstra.getA();
			head = new String[a.length - 1];
			data = new String[a[0].length - 1][a.length - 1];
			for (int i = 1; i < a[0].length; i++) {
				head[i - 1] = String.valueOf(i);
				for (int j = 1; j < a.length; j++) {
					if (a[i][j] == infinity) {
						data[i - 1][j - 1] = "âˆž";
					} else {
						data[i - 1][j - 1] = String.valueOf(a[i][j]);
					}
				}
			}
			DefaultTableModel model = new DefaultTableModel(data, head);
			tableMatrix.setModel(model);
			if (tableMatrix.getColumnCount() > col) {
				for (int i = 0; i < head.length; i++) {
					TableColumn tc = tableMatrix.getColumnModel().getColumn(i);
					tc.setPreferredWidth(width);
				}
				tableMatrix.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			} else {
				tableMatrix.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			}
		}

		private void loadLog(boolean isStep) {
			final int width = 70;
			final int col = tableLog.getWidth() / width - 1;
			int infinity = dijkstra.getInfinity();
			int logLen[][] = dijkstra.getLogLen();
			int logP[][] = dijkstra.getLogP();
			head = new String[logLen.length - 1];
			data = new String[dijkstra.getNumberPointChecked()][logLen.length - 1];
			boolean check[] = new boolean[logLen.length - 1];

			for (int i = 0; i < logLen.length - 1; i++) {
				head[i] = String.valueOf(i + 1);
				check[i] = false;
				data[0][i] = "[âˆž, âˆž]";
			}

			data[0][indexBeginPoint - 1] = "[0, " + indexBeginPoint + "]";

			for (int i = 1; i < data.length; i++) {
				int min = infinity, indexMin = -1;
				// // check "*" for min len
				for (int j = 1; j < logLen.length; j++) {
					if (min > logLen[i][j] && !check[j - 1]) {
						min = logLen[i][j];
						indexMin = j - 1;
					}
				}
				if (indexMin > -1) {
					check[indexMin] = true;
				}

				for (int j = 1; j < logLen.length; j++) {

					if (min > logLen[i][j] && !check[j - 1]) {
						min = logLen[i][j];
						indexMin = j - 1;
					}

					String p = "âˆž";
					if (logP[i][j] > 0) {
						p = logP[i][j] + "";
					}
					if (check[j - 1]) {
						data[i][j - 1] = "-";
					} else if (logLen[i][j] == infinity) {
						data[i][j - 1] = "[âˆž, " + p + "]";
					} else {
						data[i][j - 1] = "[" + logLen[i][j] + ", " + p + "]";
					}
				}

				if (indexMin > -1) {
					data[i - 1][indexMin] = "*" + data[i - 1][indexMin];
				}
			}

			// check "*" for min len of row last
			int min = infinity, indexMin = -1;
			for (int j = 1; j < logLen.length; j++) {
				if (min > logLen[data.length - 1][j] && !check[j - 1]) {
					min = logLen[data.length - 1][j];
					indexMin = j - 1;
				}
			}
			if (indexMin > -1) {
				check[indexMin] = true;
				data[data.length - 1][indexMin] = "*"
						+ data[data.length - 1][indexMin];
			}

			// update data for table log
			DefaultTableModel model = new DefaultTableModel(data, head);
			tableLog.setModel(model);
			if (tableLog.getColumnCount() > col) {
				for (int i = 0; i < head.length; i++) {
					TableColumn tc = tableLog.getColumnModel().getColumn(i);
					tc.setPreferredWidth(width);
				}
				// tableLog.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			} else {
				// tableLog.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
			}
		}

		private void drawDemo() {
			int demo = cbbGraphDemo.getSelectedIndex();
			myDraw.readDemo(demo);
			actionUpdate();
		}

		private void processInputMatrix() {
			int numberPoint = 0;
			boolean isSuccess = true;
			try {
				numberPoint = Integer.parseInt(textNumerPoint.getText());
				int a[][] = new int[numberPoint][numberPoint];
				String temp = textMatrix.getText();
				Scanner scan = new Scanner(temp);
				for (int i = 0; i < numberPoint; i++) {
					for (int j = 0; j < numberPoint; j++) {
						try {
							a[i][j] = scan.nextInt();
						} catch (InputMismatchException e) {
							JOptionPane.showMessageDialog(null,
									"Enter your matrix!");
							isSuccess = false;
							break;
						}
					}
					if (!isSuccess) {
						break;
					}
				}

				for (int i = 0; i < numberPoint; i++) {
					for (int j = 0; j < numberPoint; j++) {
						System.out.printf("%3d", a[i][j]);
					}
					System.out.println();
				}

				scan.close();

				myDraw.setA(a);
				myDraw.convertMatrixToData();
				myDraw.repaint();
				dijkstra.setA(a);

			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(null,
						"Enter one integer number < 30!");
			}

		}

		private boolean checkRun() {
			int size = myDraw.getData().getArrMyPoint().size() - 1;
			indexBeginPoint = cbbBeginPoint.getSelectedIndex();
			indexEndPoint = cbbEndPoint.getSelectedIndex();
			if (indexEndPoint == size + 1) { // all Point
				indexEndPoint = -1;
			}

			if (size < 1 || indexBeginPoint == 0 || indexEndPoint == 0) {
				JOptionPane.showMessageDialog(null,
						"Error chose points or don't Update graph to chose points",
						"Error", JOptionPane.WARNING_MESSAGE);
				return false;
			}
			return true;
		}

		private void setBeginEndPoint() {
			myDraw.setIndexBeginPoint(indexBeginPoint);
			myDraw.setIndexEndPoint(indexEndPoint);
			dijkstra.setBeginPoint(indexBeginPoint);
			dijkstra.setEndPoint(indexEndPoint);
		}

		private void runAll() {
			if (checkRun()) {
				resetDataDijkstra();
				setBeginEndPoint();
				dijkstra.dijkstra();
				textLog.setText(dijkstra.tracePath());
				loadLog(false);

				myDraw.setDrawStep(false);
				myDraw.setDrawResult(true);
				myDraw.setA(dijkstra.getA());
				myDraw.setP(dijkstra.getP());
				myDraw.setInfinity(dijkstra.getInfinity());
				myDraw.setLen(dijkstra.getLen());
				myDraw.setCheckedPointMin(dijkstra.getCheckedPointMin());
				myDraw.repaint();
			}
		}

		private void runStep() {
			if (checkRun()) {
				setBeginEndPoint();
				dijkstra.dijkstraStep(++step);
				loadLog(true);
				textLog.setText(dijkstra.tracePathStep());

				myDraw.setDrawStep(true);
				myDraw.setDrawResult(false);
				myDraw.setA(dijkstra.getA());
				myDraw.setP(dijkstra.getP());
				myDraw.setArrPointResultStep(dijkstra.getArrPointResultStep());
				myDraw.setLen(dijkstra.getLen());
				myDraw.setCheckedPointMin(dijkstra.getCheckedPointMin());
				myDraw.setInfinity(dijkstra.getInfinity());
				myDraw.repaint();
			}
		}

		private void showHelp() {
			if (frameHelp == null) {
				frameHelp = new HelpAndAbout(0, "Dijkstra - Help");
			}
			frameHelp.setVisible(true);
		}

		private void showAbout() {
			if (frameAbout == null) {
				frameAbout = new HelpAndAbout(1, "Dijkstra - About");
			}
			frameAbout.setVisible(true);
		}


	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnSimulation) { // khi bấm vào nút SIMULATION thì bắt đầu thực hiện :
			int startPoint = getValue(tfStartPoint, graphicsPanel.getNumberPoint()); // lấy điểm bắt đầu
			if (startPoint > 0) {
				startPoint--; // chuyển về đúng vị trí trong ArrayList
				graph.initVisit(); 		// khởi tạo lại graph 
				graph.initListVisit();	// khỏi tạo lại graph
				graph.initPath();		// khởi tạo lại graph
				setPath(); 				// vẽ lại graphPanel (chưa có màu đỉnh và đường đi)
				if (radBFS.isSelected()) { // nếu chọn thuật toán bfs
					try {
						graph.stepDFS1(this, startPoint); 	// gọi hàm để vẽ từng bước một
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else { 					// nếu chọn thuật toán dfs
					try {
						graph.stepDFS1(this, startPoint); 	// gọi hàm để vẽ từng bước một
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
		String command = e.getActionCommand();
		if(e.getSource() == btnTab1) {
			btnTab1.setBackground(Color.LIGHT_GRAY);
			btnTab2.setBackground(Color.white);
			mainPanel.remove(panel2);
			mainPanel.add(panel1, BorderLayout.CENTER);
			pack();
			return;
		} 
		if(e.getSource() == btnTab2) {
			btnTab2.setBackground(Color.LIGHT_GRAY);
			btnTab1.setBackground(Color.white);
			mainPanel.remove(panel1);
			mainPanel.add(panel2, BorderLayout.CENTER);
			myDraw.setDraw(3);
			pack();
			return;
		}
		if (e.getSource() == btnCreateGraph) {
			createGraph();
			return;
		}
		if (e.getSource() == btnAddEdge) {
			addEdge();
			return;
		}
		if (e.getSource() == btnRun) {
			run();
		}
		// select input method
		if (command == "Draw") {
			setEnableDraw(true, "outputMatrix");
		} else if (command == "Matrix") {
			setEnableDraw(true, "inputMatrix");
		} else if (command == "Demo") {
			setEnableDraw(false, "outputMatrix");
			drawDemo();
		}
		
		if (e.getSource() == radUndirected) {
			setEnableMapType(false);
		} else if (e.getSource() == radDirected) {
			setEnableMapType(true);
		}

		if (e.getSource() == cbbGraphDemo) {
			drawDemo();
		}

		// select point
		if (e.getSource() == cbbBeginPoint || e.getSource() == cbbEndPoint) {
			actionChoosePoint();
		}
		// select run
		if (e.getSource() == btnRunStep) {
			runStep();
		}

		if (e.getSource() == btnRunAll) {
			runAll();
		}

		// input matrix
		if (command == "Ok") {
			processInputMatrix();
		}

		// select popup menu
		if (command == "Change cost") {
			showDialogChangeCost();
		}
		if (command == "Delete") {
			showDialogDelete();
		}

		if(e.getSource() == btnFileChoose) {
			int returnVal = fileDialog.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                try {
                	java.io.File file = fileDialog.getSelectedFile();
                	File fileChosen = new File(file.getPath());
                    InputStream inputStream = new FileInputStream(fileChosen);
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader reader = new BufferedReader(inputStreamReader);
             
                    String line = "";
                    line = reader.readLine();
                    createGraph(line);
                    while((line = reader.readLine()) != null){
                    	String [] tmp = line.split(" ");
                    	addEdge(tmp[0], tmp[1]);
                    }
				} catch (Exception e2) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(null, "Error", "Can not open file", JOptionPane.INFORMATION_MESSAGE);
					return;
				}
        		pack();
        		setLocationRelativeTo(null);
        		setVisible(true);
            }
		}
		if(e.getSource() == btnImage) {
			makeScreenshot(this);
			btnImage.requestFocus();
			return;
		}
	}
	
	public static final void makeScreenshot(JFrame argFrame) {
	    Rectangle rec = argFrame.getBounds();
	    BufferedImage bufferedImage = new BufferedImage(rec.width, rec.height, BufferedImage.TYPE_INT_ARGB);
	    argFrame.paint(bufferedImage.getGraphics());
	    try {
	        // Create temp file
	        File temp = File.createTempFile("PrtScr_AimsProject", ".png", new File("C:/Users/ADMIN/Desktop"));
	        // Use the ImageIO API to write the bufferedImage to a temporary file
	        ImageIO.write(bufferedImage, "png", temp);
	        // Delete temp file when program exits
	        JOptionPane.showMessageDialog(null, "Successfully", "Done", JOptionPane.INFORMATION_MESSAGE);
	        temp.deleteOnExit();
			return;
	    } catch (IOException ioe) {
	        ioe.printStackTrace();
	    }
	}

	private void createGraph() {
		int numberPoint = getValue(tfNumberPoint, maxPoint);
		if (numberPoint > 0) {
			graphicsPanel.setNumberPoint(numberPoint);
			graphicsPanel.start(myDraw.getPreferredSize().width,
					myDraw.getPreferredSize().height);

			graph = new Graph();
			graph.setNumberPoint(numberPoint);
			graph.initValue();
			// disabale(true);
			tfBeginPoint.requestFocus();
		}

	}
	
	private void createGraph(String string) {
		try {
			int numberPoint = Integer.parseInt(string);
			if (numberPoint > 0 && numberPoint < maxPoint) {
				graphicsPanel.setNumberPoint(numberPoint);
				graphicsPanel.start(myDraw.getPreferredSize().width,
						myDraw.getPreferredSize().height);

				graph = new Graph();
				graph.setNumberPoint(numberPoint);
				graph.initValue();
				// disabale(true);
				tfBeginPoint.requestFocus();
			}
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Error", "Can not open file", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
	}

	private void addEdge() {
		int max = graphicsPanel.getNumberPoint();
		int indexBeginPoint = getValue(tfBeginPoint, max);
		if (indexBeginPoint > 0) {
			int indexEndPoint = getValue(tfEndPoint, max);
			if (indexEndPoint > 0) {
				indexBeginPoint--;
				indexEndPoint--;
				graphicsPanel.addLine(indexBeginPoint, indexEndPoint);

				graph.getMatrix()[indexBeginPoint][indexEndPoint] = 1;
				graph.getMatrix()[indexEndPoint][indexBeginPoint] = 1;
				tfBeginPoint.setText(" ");
				tfEndPoint.setText("");
				tfBeginPoint.requestFocus();
			}
		}
	}
	
	private void addEdge(String b, String e) {
		try {
			int begin = Integer.parseInt(b);
			int end = Integer.parseInt(e);
			int max = graphicsPanel.getNumberPoint();
			if (begin > 0) {
				if (end > 0) {
					begin--;
					end--;
					graphicsPanel.addLine(begin, end);

					graph.getMatrix()[begin][end] = 1;
					graph.getMatrix()[end][begin] = 1;
					tfBeginPoint.setText(" ");
					tfEndPoint.setText("");
					tfBeginPoint.requestFocus();
				}
			}
		} catch (Exception ex) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Dá»¯ liá»‡u sai", "Error", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}
	

	private void run() {
		int startPoint = getValue(tfStartPoint, graphicsPanel.getNumberPoint());
		if (startPoint > 0) {
			startPoint--;
			graph.initVisit();
			graph.initListVisit();
			graph.initPath();
			if (radBFS.isSelected()) {
				graph.BFS(startPoint);
				textLog.setText(graph.getStringPath());
				btnSimulation.setEnabled(true);
			} else {
				graph.DFS1(startPoint);
				textLog.setText(graph.getStringPath());
				btnSimulation.setEnabled(true);
			}
			setPath();
		}
	}

	private int getValue(JTextField tf, int maxValue) {
		int value = 0;
		try {
			value = Integer.parseInt(tf.getText().toString().trim());
		} catch (Exception e) {
		}
		if (value <= 0 || value > maxValue) {
			JOptionPane.showMessageDialog(null, "Error input", "Error",
					JOptionPane.ERROR_MESSAGE);
			tf.requestFocus();
			return 0;
		}
		return value;
	}

	public void setPath() {
		int numberPointVisit = graph.getListVisit().size();
		graph.showMatrix();
		System.out.println(graph.toStringListPoint(1));
		textLog.setText(graph.getStringPath());

		int numberLine = graphicsPanel.getListLine().size();
		for (int i = 0; i < numberLine; i++) {
			graphicsPanel.getListLine().get(i).setType(0);
			graphicsPanel.getListLine().get(i).setOrder(0);
		}

		int numberPoint = graphicsPanel.getListPoint().size();
		for (int i = 0; i < numberPoint; i++) {
			graphicsPanel.getListPoint().get(i).setType(0);
		}

		for (int i = 0; i < numberPointVisit; i++) {
			int index = graph.getListVisit().get(i);
			if (i == 0) {
				graphicsPanel.getListPoint().get(index).setType(1);
			} else if (i == numberPointVisit - 1) {
				graphicsPanel.getListPoint().get(index).setType(3);
			} else {
				graphicsPanel.getListPoint().get(index).setType(2);
			}
		}

		for (int i = 0; i < numberPointVisit; i++) {
			int index = graph.getListVisit().get(i);

			for (int j = 0; j < numberLine; j++) {
				MyLine line = graphicsPanel.getListLine().get(j);
				
				if ((line.getIndexP1() == index && line.getIndexP2() == graph
						.getBack()[index])
						|| (line.getIndexP1() == graph.getBack()[index] && line
								.getIndexP2() == index)) {
					System.out.println(line.getIndexP1() + " + " + line.getIndexP2());
					line.setType(1);
					line.setOrder(i);
				}
			}
		}
		graphicsPanel.repaint();
		pack();
	}

	public void setPath2() {  // hàm để vẽ lại đồ thị 
		int numberPointVisit = graph.getListVisit().size();  //  lấy số đỉnh đã được thăm
		textLog.setText(graph.getStringPath());
		graph.showMatrix();
		System.out.println(graph.toStringListPoint(1));  // output ra ma trận

		int numberLine = graphicsPanel.getListLine().size(); 	// lấy số cạnh đã được tạo ra từ đầu
		for (int i = 0; i < numberLine; i++) { 	// 	khởi tạo lại cho các cạnh chưa có màu và chưa có thứ tự
			graphicsPanel.getListLine().get(i).setType(0);
			graphicsPanel.getListLine().get(i).setOrder(0);
		}
 
		int numberPoint = graphicsPanel.getListPoint().size();  // lấy số đỉnh đã được tạo ra từ đầu
		for (int i = 0; i < numberPoint; i++) {
			graphicsPanel.getListPoint().get(i).setType(0);
		}

		for (int i = 0; i < numberPointVisit; i++) {	// với mỗi đỉnh đã được thăm, vẽ màu cho nó
			int index = graph.getListVisit().get(i);
			graphicsPanel.getListPoint().get(index).setType(2);
		}

		for (int i = 0; i < numberPointVisit; i++) {  // vẽ màu cho các cạnh đi qua các đỉnh đã được thăm
			int index = graph.getListVisit().get(i);	// với từng đỉnh đã được thăm

			for (int j = 0; j < numberLine; j++) { 	// xét các cạnh của đồ thị
				MyLine line = graphicsPanel.getListLine().get(j); // lấy từng cạnh ra
				
				if ((line.getIndexP1() == index && line.getIndexP2() == graph  	// kiểm tra xem nếu cạnh này chứa đỉnh đã lấy ở trên
						.getBack()[index])
						|| (line.getIndexP1() == graph.getBack()[index] && line
								.getIndexP2() == index)) {
					System.out.println(line.getIndexP1() + " + " + line.getIndexP2());
					line.setType(1);	// nếu đúng thì tô màu
					line.setOrder(i);	// nếu đúng thì ghi thứ tự cho nó
				}
			}
		}
		graphicsPanel.repaint();
	}
	
	public static void main(String[] args) {
		GUI giaoDien = new GUI();
	}

}
