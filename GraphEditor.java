package project2;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/*	Program: Simple Graph Editor
 * 	Plik:	 GraphEditor.java
 * 
 * 	Autor:	 Filip Regenczuk
 *  Data:	 listopad 2018 r.
 *  
 *  Klasa GraphEditor tworzy okno glowne
 *  dla programu.
 */


public class GraphEditor extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private static final String AUTHOR = "Author:  Filip Regenczuk\nDate:      November 2018";
	private static final String PROGRAM_NAME = "Simple Graph Editor";
	private static final String APP_INSTRUCTION =
			"                                I N S T R U C T I O N  \n\n" +
			"Mouse:\n" +
			"LMB  -  to move vertice/edge/graph\n" +
			"RMB  -  to create new vertice\n\n" +
			"When the cursor is above vertice:\n" +
			"RMB  -  to remove the vertice/to create new edge/\nto change name or color of the vertice\n\n" +
			"When the cursor is above edge:\n" +
			"RMB  -  to remove the edge/to change color of the edge\n\n" +
			"To create new edge:\n" +
			"1) RMB/Create new edge\n" +
			"2) Choose vertice\n" +
			"3) RMB/Create new edge\n\n\n" +
			"Keybord:\n" +
			"Arrows  -  to move whole graph\n" +
			"SHIFT + arrows  -  to move whole graph quickly\n\n\n";
	

	private GraphPanel panel = new GraphPanel();
	
	
	// Elementy menu
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menuFile = new JMenu("File");
	private JMenu menuGraph = new JMenu("Graph");
	private JMenu menuHelp = new JMenu("Help");
	private JMenuItem menuNew = new JMenuItem("New");
	private JMenuItem menuSave = new JMenuItem("Save as...");
	private JMenuItem menuLoad = new JMenuItem("Open...");
	private JMenuItem menuExample = new JMenuItem("Example graph");
	private JMenuItem menuExit = new JMenuItem("Exit");
	private JMenuItem menuListOfVertices = new JMenuItem("Vertices");
	private JMenuItem menuListOfEdges = new JMenuItem("Edges");
	private JMenuItem menuInstruction = new JMenuItem("Instruction");
	private JMenuItem menuAuthor = new JMenuItem("Author");
	
	
	
	public static void main(String[] args) {
		new GraphEditor();
	}

	
	public GraphEditor() {
		
		super(PROGRAM_NAME);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600,600);
		setLocationRelativeTo(null);
		
		Image img = new ImageIcon("graphIcon.png").getImage();
		setIconImage(img);
		
		setContentPane(panel);
		createMenu();
		showBuildinExample();
		
		setVisible(true);
	}

	
	private void createMenu() {
		
		menuFile.add(menuNew);
		menuFile.add(menuSave);
		menuFile.add(menuLoad);
		menuFile.addSeparator();
		menuFile.add(menuExample);
		menuFile.addSeparator();
		menuFile.add(menuExit);
		
		menuGraph.add(menuListOfVertices);
		menuGraph.add(menuListOfEdges);
		
		menuHelp.add(menuInstruction);
		menuHelp.add(menuAuthor);
		
		menuNew.addActionListener(this);
		menuSave.addActionListener(this);
		menuLoad.addActionListener(this);
		menuExample.addActionListener(this);
		menuExit.addActionListener(this);
		menuListOfVertices.addActionListener(this);
		menuListOfEdges.addActionListener(this);
		menuInstruction.addActionListener(this);
		menuAuthor.addActionListener(this);
		
		
		menuBar.add(menuFile);
		menuBar.add(menuGraph);
		menuBar.add(menuHelp);
		setJMenuBar(menuBar);
		
	}

	
	// Metoda do pokazywania listy wierzcholkow
	private void showListOfVertices(Graph graph) {
		Vertice array[] = graph.getVertices();
		int i = 0;
		
		StringBuilder message = new StringBuilder("Number of vertices:               " + array.length + "\nTheir coordinates (x,y,r):     ");
		for (Vertice Vertice : array) {
			message.append(Vertice + "    ");
			if (++i % 5 == 0)
				message.append("\n");
		}
		JOptionPane.showMessageDialog(this, message, PROGRAM_NAME + " - Number of vertices", JOptionPane.PLAIN_MESSAGE);
	}

	
	private void showListOfEdges(Graph graph) {
		Edge array[] = graph.getEdges();
		int i = 0;
		
		StringBuilder message = new StringBuilder("Number of edges:               " + array.length + "\nTheir coordinates (x,y,s):     ");
		for (Edge edge : array) {
			message.append(edge + "    ");
			if (++i % 5 == 0)
				message.append("\n");
		}
		JOptionPane.showMessageDialog(this, message, PROGRAM_NAME + " - Number of edges", JOptionPane.PLAIN_MESSAGE);
	}

	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		Object source = event.getSource();
		if (source == menuNew) {
			panel.setGraph(new Graph());
		}
		if (source == menuSave) {
			String fileName = JOptionPane.showInputDialog("Enter file name"); 
			
			if (fileName == null || fileName.equals("")) {
				return;
			}
			
			try {
				panel.getGraph().saveGraph(fileName);
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (source == menuLoad) {
			String fileName = JOptionPane.showInputDialog("Enter file name");
			
			if (fileName == null || fileName.equals("")) {
				return;
			}
			
			try {
				panel.setGraph(Graph.loadGraph(fileName));
			}
			catch (ClassNotFoundException | IOException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error", 0);
			}
			panel.repaint();
		}
		if (source == menuExample) {
			showBuildinExample();
		}
		if (source == menuExit) {
			System.exit(0);
		}
		if (source == menuListOfVertices) {
			showListOfVertices(panel.getGraph());
		}
		if (source == menuListOfEdges) {
			showListOfEdges(panel.getGraph());
		}
		if (source == menuInstruction) {
			JOptionPane.showMessageDialog(this, APP_INSTRUCTION, PROGRAM_NAME + " - Instruction", JOptionPane.PLAIN_MESSAGE);
		}
		if (source == menuAuthor) {
			JOptionPane.showMessageDialog(this, AUTHOR, PROGRAM_NAME + " - Author", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void showBuildinExample() {
		Graph graph = new Graph();

		Vertice v1 = new Vertice(280, 50);
		v1.setColor(Color.RED);
		v1.setName("A");
		Vertice v2 = new Vertice(200, 100);
		v2.setName("I");
		Vertice v3 = new Vertice(360, 100);
		v3.setName("II");
		Vertice v4 = new Vertice(100, 160);
		v4.setName("1");
		v4.setColor(Color.PINK);
		Vertice v5 = new Vertice(470, 160);
		v5.setName("2");
		v5.setColor(Color.PINK);
		Vertice v6 = new Vertice(280, 300);
		v6.setName("N");
		v6.setColor(Color.CYAN);
		
		Edge e1 = new Edge(v1, v2);
		e1.setColor(Color.RED);
		Edge e2 = new Edge(v1, v3);
		e2.setColor(Color.RED);
		Edge e3 = new Edge(v2, v4);
		e3.setColor(Color.GRAY);
		Edge e4 = new Edge(v3, v5);
		e4.setColor(Color.GRAY);
		Edge e5 = new Edge(v4, v6);
		e5.setColor(Color.PINK);
		Edge e6 = new Edge(v5, v6);
		e6.setColor(Color.PINK);
		Edge e7 = new Edge(v1, v6);
		e7.setColor(Color.RED);
		
		
		graph.addVertice(v1);
		graph.addVertice(v2);
		graph.addVertice(v3);
		graph.addVertice(v4);
		graph.addVertice(v5);
		graph.addVertice(v6);
		
		graph.addEdge(e1);
		graph.addEdge(e2);
		graph.addEdge(e3);
		graph.addEdge(e4);
		graph.addEdge(e5);
		graph.addEdge(e6);
		graph.addEdge(e7);
		
		panel.setGraph(graph);
	}
}

