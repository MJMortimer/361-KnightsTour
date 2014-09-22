package main;

public class UnionNode {

	private UnionNode parent;
	private Move move;
	
	public UnionNode(Move m){
		this.move = m;
		this.parent = this;
	}
}
