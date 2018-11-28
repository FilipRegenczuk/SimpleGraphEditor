package project2;


import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*	Program: Simple Graph Editor
 * 	Plik:	 Graph.java
 * 
 * 	Autor:	 Filip Regenczuk
 *  Data:	 listopad 2018 r.
 *  
 *  Klasa Graph reprezentuje graf na plaszczyznie.
 */


public class Graph implements Serializable {
	
	private static final long serialVersionUID = 1L;

	// Lista wierzcholkow grafu
	private List<Vertice> vertices;
	private List<Edge> edges;
	
	
	// Konstruktor zajmuje sie tworzeniem ArrayList
	public Graph() {
		this.vertices = new ArrayList<Vertice>();
		this.edges = new ArrayList<Edge>();
	}
	
	
	public Vertice[] getVertices() {
		Vertice[] array = new Vertice[0];
		return vertices.toArray(array);
	}
	
	
	public void addVertice(Vertice vertice) {
		vertices.add(vertice);
	}
	
	public void removeVertice(Vertice vertice) {
		vertices.remove(vertice);
	}
	
	
	public Edge[] getEdges() {
		Edge[] array = new Edge[0];
		return edges.toArray(array);
	}
	
	
	public void addEdge(Edge edge) {
		edges.add(edge);
	}
	
	
	public void removeEdge(Edge edge) {
		edges.remove(edge);
	}
	
	
	public void saveGraph(String fileName) throws IOException {
		FileOutputStream stream = new FileOutputStream(fileName);
		ObjectOutputStream out = new ObjectOutputStream(stream);
		out.writeObject(this);
		out.close();
	}
	
	
	public static Graph loadGraph(String fileName) throws IOException, ClassNotFoundException {
		FileInputStream stream = new FileInputStream(fileName);
		ObjectInputStream in = new ObjectInputStream(stream);
		Object readed = in.readObject();
		Graph graph = (Graph)readed;				   
		in.close();
		return graph;
	}
	
	
	public void draw(Graphics g) {
		
		for(Edge edge : edges) {
			edge.draw(g);
		}
		
		for(Vertice vertice : vertices) {
			vertice.draw(g);
		}
		
	}
}
