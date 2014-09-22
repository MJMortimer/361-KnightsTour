package main;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class MoveNode {
	
	private Point here;
	private MoveNode from;
	private boolean visited;
	List<MoveNode> neighbours;
	
	
	public MoveNode(Point here) {
		this.neighbours = new ArrayList<MoveNode>();
		this.here = here;
		this.visited = false;
	}
	
	public void addNeighbour(MoveNode n){
		this.neighbours.add(n);
	}

	public Point getHere() {
		return here;
	}

	public MoveNode getFrom() {
		return from;
	}
	
	public void setFrom(MoveNode m){
		this.from = m;
	}
	
	public void setVisited(boolean v){
		this.visited = v;
	}
	
	public boolean isVisited(){
		return visited;
	}

	public List<MoveNode> getNeighbours() {
		return this.neighbours;
	}
}
