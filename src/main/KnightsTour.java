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
	private int count = 0;

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
				board[i][j] = new Move(new Point(i,j));
			}
		}

		long st = System.currentTimeMillis();
		knightBT(board, 0, 0, 0, 0, boardSize, 1);
		long dur = System.currentTimeMillis() - st; 
		this.p.setBoard(board, dur);
		this.p.repaint();

		System.out.println("done "+ count);


	}

	public boolean knightBT(Move[][] b, int hereI,int hereJ, int fromI, int fromJ, int n,int k){
		++count;
		if(k != 1){
			b[hereI][hereJ].setVisited(true);
			b[hereI][hereJ].setFrom(new Point(fromI, fromJ));
		}
// add a wait so that the algorithm can be watched. Have it be done via a switch in the gui
//		try {
//			Thread.sleep(25);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		this.p.setBoard(b);
//		this.p.repaint();

		if(k == (n*n)+1 && hereI == 0 && hereJ == 0)
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
		KnightsTour k = new KnightsTour(Integer.parseInt(args[0]));
	}
}


