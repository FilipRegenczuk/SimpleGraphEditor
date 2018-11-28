package project2;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.Serializable;


/*	Program: Simple Graph Editor
 * 	Plik:	 Vertice.java
 * 
 * 	Autor:	 Filip Regenczuk
 *  Data:	 listopad 2018 r.
 *  
 *  Klasa Vertice definiuje wierzcholek grafu, zawiera gettery i settery
 *  do danych wierzcholka oraz zajmuje sie rysowaniem tego wierzcholka. 
 */


public class Vertice implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int x;
	private int y;
	private int r;
	private Color color;
	private String name;
	
	
	// Konstruktor przyjmujacy zadane wspolrzedne, ustalony promien, domyslny kolor i nazwe
	public Vertice(int x, int y) {
		this.x = x;
		this.y = y;
		this.r = 20;
		this.color = Color.GRAY;
		this.name = "";
	}
	
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getX() {
		return x;
	}
	
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getY() {
		return y;
	}
	
	
	public void setR(int r) {
		this.r = r;
	}
	
	public int getR() {
		return r;
	}

	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	
	public void setColor(Color color) {
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}
	
	
	
	@Override
	public String toString() {
		return ("(" + x + ", " + y + ", " + r + ")");
	}
	
	

	void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawOval(x-r, y-r, 2*r, 2*r);
		g.setColor(color);
		g.fillOval(x-r, y-r, 2*r, 2*r);
		g.setColor(Color.BLACK);
		g.setFont(new Font("SansSerif", 1, 16));
		g.drawString(name, x-5, y+5);
	}
	
	
	public boolean isMouseOver(int mx, int my) {
		return Math.pow((x-mx), 2) + Math.pow((y-my), 2) <= Math.pow(r, 2);
	}
	
	
}
