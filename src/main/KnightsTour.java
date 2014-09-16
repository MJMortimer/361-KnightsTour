package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;

public class KnightsTour {

	private int boardSize;
	private KnightPanel p;
	private ArrayList<Point> allowedMoves = new ArrayList<Point>(Arrays.asList(
																new Point(2,1),
																new Point(2,-1),
																new Point(1,2),
																new Point(1,-2),
																new Point(-1,2),
																new Point(-1,-2),
																new Point(-2,1),
																new Point(-2,-1))) ;
	
	public KnightsTour(int size) {
		this.boardSize = size;
		Move[][] board = new Move[size][size];
		
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(670,700));		
		this.p = new KnightPanel(size);
		frame.add(p);
		
		frame.setVisible(true);
		
		
		
		for(int i = 0; i < boardSize; i++){
			for(int j = 0; j < boardSize; j++){
				for(int k = 0; k < boardSize; k++){
					for(int l = 0; l < boardSize; l++){
						board[k][l] = new Move(new Point(k,l));
					}
				}
				knightBT(board, i, j, i, j, boardSize, 1);
			}
		}
		
		System.out.println("done");
		
		
	}
	
	public boolean knightBT(Move[][] b, int hereI,int hereJ, int fromI, int fromJ, int n,int k){
		b[hereI][hereJ].setVisited(true);
		b[hereI][hereJ].setFrom(new Point(fromI, fromJ));		
// add a wait so that the algorithm can be watched. Have it be done via a switch in the gui
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		this.p.setBoard(b);
		this.p.repaint();
		
		if(k == n*n)
			return true;
		for(Point p : allowedMoves){
			int nextI = hereI + p.x;
			int nextJ = hereJ + p.y;
			if(0 <= (nextI) && (nextI) < n && 0 <= (nextJ) && (nextJ) < n && !b[nextI][nextJ].isVisited()){
				if(knightBT(b, nextI, nextJ, hereI, hereJ, n, k+1)){
					return true;
				}
			}
		}
		
		b[hereI][hereJ].setVisited(false);
		b[hereI][hereJ].setFrom(null);
		return false;
		
		
	}



	public static void main(String[] args){
		KnightsTour k = new KnightsTour(5);
	}
}
