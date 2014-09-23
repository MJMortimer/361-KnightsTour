package main;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

public class MoveNode implements Comparable<MoveNode>, Cloneable {
	
	private Point here;
	private Point from;
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

	public Point getFrom() {
		return from;
	}
	
	public void setFrom(Point m){
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
	
	public List<MoveNode> getSortedNeighbours() {
		java.util.Collections.sort(this.neighbours);
		return this.neighbours;
	}

	public boolean anyUnvisitedNeghbours() {
		for(MoveNode m : neighbours){
			if(!m.visited)
				return true;
		}
		return false;
	}

	@Override
	public int compareTo(MoveNode other) {
		return this.numberUnvisted() - other.numberUnvisted();					
	}

	private int numberUnvisted() {
		int count = 0;
		for(MoveNode m : neighbours){
			if(!m.visited)
				count++;
		}		
		return count;
	}

	public void incBoth(int size1) {
		incRow(size1);
		incCol(size1);
	}

	public void incRow(int size1) {
		this.here.x+=size1;
	}

	public void incCol(int size1) {
		this.here.y+=size1;
	}
	
	//A very barebones clone method
	protected MoveNode clone(){

	    MoveNode clone = new MoveNode(new Point(this.getHere().x, this.getHere().y));
	    clone.setFrom(new Point(this.getFrom().x, this.getFrom().y));
	    clone.visited = this.visited;
	    return clone;

	  }
}
