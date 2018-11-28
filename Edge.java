package project2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.Serializable;


/*	Program: Simple Graph Editor
 * 	Plik:	 Edge.java
 * 
 * 	Autor:	 Filip Regenczuk
 *  Data:	 listopad 2018 r.
 *  
 *  Klasa Edge zajmuje sie tworzeniem krawedzi grafu laczacej
 *  dwa dowolne wierzcholki grafu
 */

public class Edge implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Vertice startVertice;
	private Vertice endVertice;
	private Color color;
	
	
	public Edge(Vertice startVertice, Vertice endVertice) {
		this.startVertice = startVertice;
		this.endVertice = endVertice;
		this.color = Color.BLACK;
	}
	
	
	public Edge(Vertice startVertice, Vertice endVertice, Color color) {
		this.startVertice = startVertice;
		this.endVertice = endVertice;
		this.color = color;
	}
	
	
	public Vertice getStartVertice() {
		return startVertice;
	}
	
	
	public Vertice getEndVertice() {
		return endVertice;
	}
	
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	
	public Color getColor() {
		return color;
	}
	
	
	void draw(Graphics g) {
	    g.setColor(color);
	    
	    ((Graphics2D)g).setStroke(new BasicStroke(3.0F));
	    g.drawLine(startVertice.getX(), startVertice.getY(), endVertice.getX(), endVertice.getY());
	    
	    ((Graphics2D)g).setStroke(new BasicStroke(1.0F));
	    g.setColor(Color.BLACK);
	  }
	  
		
	public boolean isMouseOver(int mx, int my) {
		  
	  if (((mx > startVertice.getX()) && (mx > endVertice.getX())) || ((mx < startVertice.getX()) && (mx < endVertice.getX()))) {
		  return false;
	  }
	  if (((my > startVertice.getY()) && (my > endVertice.getY())) || ((my < startVertice.getY()) && (my < endVertice.getY()))) {
		  return false;
	  }
	    
	  int A = endVertice.getY() - startVertice.getY();
	  int B = startVertice.getX() - endVertice.getX();
	  int C = endVertice.getX() * startVertice.getY() - startVertice.getX() * endVertice.getY();
	  double d = Math.abs(A * mx + B * my + C) / Math.sqrt(A * A + B * B);
	  return d <= 2.0D;
	  }
	  
  
	  public String toString()
	  {
	    return "[ " + startVertice + " ==> " + endVertice + " ]";
	  }
}
