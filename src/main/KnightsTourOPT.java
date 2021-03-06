package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

public class KnightsTourOPT {

	private int boardSize;
	private KnightPanelOPT p;
	private ArrayList<Point> allowedMoves = new ArrayList<Point>(Arrays.asList(
																new Point(2,1),
																new Point(2,-1),
																new Point(1,2),
																new Point(1,-2),
																new Point(-1,2),
																new Point(-1,-2),
																new Point(-2,1),
																new Point(-2,-1))) ;
	private int count = 0;

	public KnightsTourOPT(int size) {
		this.boardSize = size;
		
		MoveNode[][] board = new MoveNode[size][size];
		

		JFrame frame = new JFrame();
		frame.setSize(new Dimension(670,700));
		this.p = new KnightPanelOPT(size);
		frame.add(p);

		frame.setVisible(true);
		



		for(int i = 0; i < boardSize; i++){
			for(int j = 0; j < boardSize; j++){
				board[i][j] = new MoveNode(new Point(i,j));
			}
		}
		
		for(int i = 0; i < boardSize; i++){
			for(int j = 0; j < boardSize; j++){
				for(Point p : allowedMoves){
					int nextI = i + p.x;
					int nextJ = j + p.y;
					
					if(0 <= (nextI) && (nextI) < boardSize && 0 <= (nextJ) && (nextJ) < boardSize && !board[nextI][nextJ].isVisited()){
						board[i][j].addNeighbour(board[nextI][nextJ]);
					}
				}
			}
		}
		
		long st = System.currentTimeMillis();
		boolean isone = knightBT(board, board[0][0], null, boardSize, 1);
		long dur = System.currentTimeMillis() - st; 
		this.p.setBoard(board, dur);
		this.p.repaint();

		System.out.println("done "+ count + " " +dur);


	}

	public boolean knightBT(MoveNode[][] b, MoveNode here, MoveNode from, int n,int k){
/*
		if(k > ((n*n)/2)){
			if(k < (n*n)+1 && disjointSquare(b))
				return false;
			
			if(disconnected()){
				return false;
			}
		}
 */
		++count;
		
		if(k != 1){
			here.setVisited(true);
			here.setFrom(from.getHere());
		}
		
		int hereI = here.getHere().x;
		int hereJ = here.getHere().y;
		
		if(k < (n*n)+1 && from != null && disconnectionFrom(from)){
			b[hereI][hereJ].setVisited(false);
			b[hereI][hereJ].setFrom(null);
			return false;
			
		}
		
// add a wait so that the algorithm can be watched. Have it be done via a switch in the gui
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		this.p.setBoard(b);
//		this.p.repaint();

		
		
		if(k == (n*n)+1 && hereI == 0 && hereJ == 0)
			return true;
		
		for(MoveNode next : here.getSortedNeighbours()){
			if(next != from && !next.isVisited()){
				if(knightBT(b, next, here, n, k+1)){
					return true;
				}
			}
				
		}
//		System.out.println("backing");
		b[hereI][hereJ].setVisited(false);
		b[hereI][hereJ].setFrom(null);

		return false;
	}
	
	private boolean disconnectionFrom(MoveNode node) {
		for(MoveNode next : node.getNeighbours()){
			if(!next.isVisited() && !next.anyUnvisitedNeghbours())
				return true;
		}
		return false;
	}
/*
//	private boolean disconnected() {
//		for(int i = 1; i < boardSize -3; i++){
//			if(rowVisitCount[i-1] < boardSize && rowVisitCount[i] == boardSize && rowVisitCount[i+1] == boardSize && rowVisitCount[i+2] < boardSize 
//			   || colVisitCount[i-1] < boardSize && colVisitCount[i] == boardSize && colVisitCount[i+1] == boardSize && colVisitCount[i+2] < boardSize){
//				return true;
//			}
//		}
//		return false;
//	}
 */
 

	private boolean disjointSquare(MoveNode[][] b) {
		for(int i = 0; i < boardSize; i++){
			for(int j = 0; j < boardSize; j++){
				if(!b[i][j].isVisited() && !b[i][j].anyUnvisitedNeghbours())
					return true;
			}
		}
		return false;
	}

	

	public static void main(String[] args){
		KnightsTourOPT k = new KnightsTourOPT(Integer.parseInt(args[0]));
	}
}


