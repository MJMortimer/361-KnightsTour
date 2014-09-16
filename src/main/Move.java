package main;

import java.awt.Point;

public class Move {
	
	private Point here;
	private Point from;
	private boolean visited;
	
	public Move(Point here) {
		this.here = here;
		this.visited = false;
	}

	public Point getHere() {
		return here;
	}

	public Point getFrom() {
		return from;
	}
	
	public void setFrom(Point p){
		this.from = p;
	}
	
	public void setVisited(boolean v){
		this.visited = v;
	}
	
	public boolean isVisited(){
		return visited;
	}
}
