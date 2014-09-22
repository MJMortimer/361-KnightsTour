package main;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

public class MoveNode implements Comparable<MoveNode> {
	
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
		int thisUnv = this.numberUnvisted();
		int otherUnv = other.numberUnvisted();
		
		if(thisUnv < otherUnv)
			return -1;
		else if(thisUnv > otherUnv)
			return 1;
//		else if (this.neighbours.size() > other.getNeighbours().size())
//			return -1;
//		else if (this.neighbours.size() < other.getNeighbours().size())
//			return 1;
		else return 0;
					
	}

	private int numberUnvisted() {
		int count = 0;
		for(MoveNode m : neighbours){
			if(!m.visited)
				count++;
		}		
		return count;
	}
}
