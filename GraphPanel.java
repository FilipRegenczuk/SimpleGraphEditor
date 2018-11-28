package project2;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JColorChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/*	Program: Simple Graph Editor
 * 	Plik:	 GraphPanel.java
 * 
 * 	Autor:	 Filip Regenczuk
 *  Data:	 listopad 2018 r.
 *  
 *  Klasa GraphPanel zajmuje sie funkcjonalnoscia programu.
 *  Dostepne sa tu nastepujace funkcje:
 *  	- rysowanie grafu
 *  	- obsluga zdarzen generowanych przez myszke
 *  	- tworzenie i obsluga menu umozliwiajacych
 *  	  edycje grafu
 */


public class GraphPanel extends JPanel implements MouseListener, MouseMotionListener, KeyListener {

	private static final long serialVersionUID = 1L;
	
	private Graph graph;
	private int mouseX = 0;
	private int mouseY = 0;
	private boolean mouseLeft = false;
	@SuppressWarnings("unused")
	private boolean mouseRight = false;
	private int mouseCursor = Cursor.DEFAULT_CURSOR;
	private Vertice verticeUnderCursor = null;
	private Edge edgeUnderCursor = null;
	private Vertice startVertice;
	private Vertice endVertice;
	private boolean check = true;

	
	
	// Konstruktor GraphPanel implementuje sluchaczy zdarzen
	public GraphPanel() {
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		
	    setFocusable(true);
	    requestFocus();
	}

	
	public void setGraph(Graph graph) {
		this.graph = graph;
		repaint();
	}
	
	public Graph getGraph() {
		return graph;
	}
	
	
	// Metoda zwraca dane wierzcholka, nad ktorym jest kursor
	private Vertice findVertice(int mx, int my) {
		
		for(Vertice vertice : graph.getVertices()) {
			if (vertice.isMouseOver(mx, my)) {
				return vertice;
			}
		}
		return null;
	}
	
	
	private Vertice findVertice(MouseEvent event) {
		return findVertice(event.getX(), event.getY());
	}
	
	
	private Edge findEdge(int mx, int my) {
		
		for(Edge edge : graph.getEdges()) {
			if (edge.isMouseOver(mx, my)) {
				return edge;
			}
		}
		return null;
	}
	
	
	private Edge findEdge(MouseEvent event) {
		return findEdge(event.getX(), event.getY());
	}
	
	
	// Metoda ustawia rodzaj kursora
	protected void setMouseCursor(MouseEvent event) {
		verticeUnderCursor = findVertice(event);
		edgeUnderCursor = findEdge(event);
		
		if (verticeUnderCursor != null) {
			mouseCursor = Cursor.HAND_CURSOR;
		} 
		else if (edgeUnderCursor != null) {
			mouseCursor = Cursor.CROSSHAIR_CURSOR;
		}
		else if (mouseLeft) {
			mouseCursor = Cursor.MOVE_CURSOR;
		} 
		else {
			mouseCursor = Cursor.DEFAULT_CURSOR;
		}
		
		setCursor(Cursor.getPredefinedCursor(mouseCursor));
		mouseX = event.getX();
		mouseY = event.getY();
	}
	
	private void setMouseCursor() {
		verticeUnderCursor = findVertice(mouseX, mouseY);
		edgeUnderCursor = findEdge(mouseX, mouseY);
		
		
		if (verticeUnderCursor != null) {
			mouseCursor = Cursor.HAND_CURSOR;
		}
		else if (edgeUnderCursor != null) {
			mouseCursor = Cursor.CROSSHAIR_CURSOR;
		}
		else if (mouseLeft) {
			mouseCursor = Cursor.MOVE_CURSOR;
		} 
		else {
			mouseCursor = Cursor.DEFAULT_CURSOR;
		}
		
		setCursor(Cursor.getPredefinedCursor(mouseCursor));
	}
	
	
	// Metoda przesuwajaca wierzcholek
	private void moveVertice(int dx, int dy, Vertice vertice){
		vertice.setX(vertice.getX() + dx);
		vertice.setY(vertice.getY() + dy);
	}
	
	
	// Metoda przesuwajaca wszystkie wierzcholki
	private void moveAllVertices(int dx, int dy) {
		for (Vertice vertice : graph.getVertices()) {
			moveVertice(dx, dy, vertice);
		}
	}
	

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (graph == null) {
			return;
		}
		graph.draw(g);
	}
	
		
	
	/*	Implementacja interfejsu MouseListener - 
	 *  obsluga zdarzen generowanych przez myszke.
	 */
	
	@Override
	public void mouseClicked(MouseEvent event) {
	}

	
	@Override
	public void mouseEntered(MouseEvent event) {
	}

	
	@Override
	public void mouseExited(MouseEvent event) {
	}

	
	@Override
	public void mousePressed(MouseEvent event) {
		
		if (event.getButton() == 1) {		// oznaczenia przyciskow myszy: 1 - lewy, 3 - prawy
			mouseLeft = true;
		}
		if (event.getButton() == 3) {
			mouseRight = true;
		}
		setMouseCursor(event);
	}

	
	@Override
	public void mouseReleased(MouseEvent event) {
		
		if (event.getButton() == 1) {
			mouseLeft = false;
		}
		if (event.getButton() == 3) {
			mouseRight = false;
		}
		setMouseCursor(event);
		
		if (event.getButton() == 3) {
			if (verticeUnderCursor != null) {
				createPopupMenu(event, verticeUnderCursor);
				
			} 
			else if (edgeUnderCursor != null) {
				createPopupMenu(event, edgeUnderCursor);
			}
			else {
				createPopupMenu(event);
			}
		}
	
	}


	/*	Implementacja interfejsu MouseMotionListener - 
	 *  obsluga zdarzen generowanych przez ruch myszki.
	 */
	@Override
	public void mouseDragged(MouseEvent event) {
		
		if (mouseLeft == true) {
			if (verticeUnderCursor != null) {
				moveVertice(event.getX() - mouseX, event.getY() - mouseY, verticeUnderCursor);
			}
			else {
				moveAllVertices(event.getX() - mouseX, event.getY() - mouseY);
			}
		}
		
		mouseX = event.getX();
		mouseY = event.getY();
		repaint();
	}

	
	@Override
	public void mouseMoved(MouseEvent event) {
		setMouseCursor(event);
	}

	
	/* 
     *  Impelentacja interfejsu KeyListener - obs³uga zdarzeñ generowanych
     *  przez klawiaturê gdy focus jest ustawiony na ten obiekt.
     */ 
	@Override
	public void keyPressed(KeyEvent event) {
		
	  int shift;
	  if (event.isShiftDown()) {
		  shift = 10;
	  }
	  else {
		  shift = 1;
	  }
	  switch (event.getKeyCode()) {
	  	case KeyEvent.VK_LEFT:
	  		moveAllVertices(-shift, 0);
	  		break;
	  	case KeyEvent.VK_RIGHT:
	  		moveAllVertices(shift, 0);
			break;
		case KeyEvent.VK_UP:
			moveAllVertices(0, -shift);
			break;
		case KeyEvent.VK_DOWN:
			moveAllVertices(0, shift);
			break;
		}
		
		repaint();
		setMouseCursor();
	}

	@Override
	public void keyReleased(KeyEvent event) {
	}

	@Override
	public void keyTyped(KeyEvent event) {
	}	

	
	
	/*	Tworzenie i obsluga menu uruchamianego przez prawy 
	 *  przycisk myszki.
	 */
	private void createPopupMenu(MouseEvent event) {
		JPopupMenu popup = new JPopupMenu();
	       
		// Tworzenie nowego wierzcholka
        JMenuItem menuItem = new JMenuItem("Create new vertice");
		menuItem.addActionListener((action) -> {
			graph.addVertice(new Vertice(event.getX(), event.getY()));
			repaint();
		});
		popup.add(menuItem);
		
        popup.show(event.getComponent(), event.getX(), event.getY());
            
    }
	
	

	
	private void createPopupMenu(MouseEvent event, Vertice vertice) {
		JMenuItem menuItem;
		JPopupMenu popup = new JPopupMenu();

		
		// Dodawanie nowej krawedzi
		menuItem = new JMenuItem("Create new edge");
		menuItem.addActionListener((action) -> {
			
			
			if (check) {
				startVertice = verticeUnderCursor;
				check = false;
			}
			else if (!check) {				
				endVertice = verticeUnderCursor;
				
				if (startVertice != endVertice) {
					graph.addEdge(new Edge(startVertice, endVertice));
					repaint();
				}
				
				startVertice = null;
				endVertice = null;
				check = true;
			}
		});
		popup.add(menuItem);
		
		
		// Dodanie nazwy wierzcholkowi
		menuItem = new JMenuItem("Change vertice name");
		menuItem.addActionListener((action) -> {
			String newName = JOptionPane.showInputDialog("Enter new name"); 
			
			if (newName != null) {
				vertice.setName(newName);
			}
			repaint();
			
		});
		popup.add(menuItem);
		
		
		// Zmiana koloru wierzcholka
		menuItem = new JMenuItem("Change vertice color");
		menuItem.addActionListener((action) -> {
			Color newColor = JColorChooser.showDialog(this, "Choose color", vertice.getColor());
			
			if (newColor != null) {
				vertice.setColor(newColor);
			}
			repaint();
			
		});
		popup.add(menuItem);
		
		
		// Usuwanie wierzcholka
		menuItem = new JMenuItem("Remove vertice");
		menuItem.addActionListener((action) -> {
			graph.removeVertice(vertice);
			
			// Usuwanie krawedzi stykajacych sie z danym wierzcholkiem
			for (Edge edge : graph.getEdges()) {
				if (edge.isMouseOver(verticeUnderCursor.getX(), verticeUnderCursor.getY())) {
					graph.removeEdge(edge);
				}
			}
			repaint();
			
		});
		popup.add(menuItem);
		
		
		popup.show(event.getComponent(), event.getX(), event.getY());
	}

	
	private void createPopupMenu(MouseEvent event, Edge edge){
		JMenuItem menuItem;
		JPopupMenu popup = new JPopupMenu();
		
		menuItem = new JMenuItem("Change edge color");
		menuItem.addActionListener((action) -> {
			Color newColor = JColorChooser.showDialog(this, "Choose edge color", edge.getColor());
			if(newColor != null){
				edge.setColor(newColor);
			}
			repaint();
		});
		popup.add(menuItem);
		
		menuItem = new JMenuItem("Remove edge");
		menuItem.addActionListener((action) -> {
			graph.removeEdge(edge);
			repaint();
		});
		popup.add(menuItem);
		
		
		popup.show(event.getComponent(), event.getX(), event.getY());
	}
		
}
