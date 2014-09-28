package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFrame;

import sun.security.util.Length;

public class KnightsTourParb {

	private int boardSize;
	private int[] rowVisitCount;
	private int[] colVisitCount;
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
	
	private MoveNode[][] sixXsix;
	private MoveNode[][] sixXeight;
	private MoveNode[][] eightXsix;
	private MoveNode[][] eightXeight;
	private MoveNode[][] eightXten;
	private MoveNode[][] tenXeight;
	private MoveNode[][] tenXten;

	public KnightsTourParb(int size) {
//		if(size < 12)
//			throw new IllegalArgumentException("Size must be greater than or equal to 12");
		
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(670,700));
		this.p = new KnightPanelOPT(size);
		frame.add(p);
		frame.setVisible(true);
		
		MoveNode[][] b = runAlg(size,size);
		this.p.setBoard(b);
		this.p.repaint();
		System.out.println("done oi");
//		
//		
//		
//		
//		this.boardSize = size;
//		this.rowVisitCount = new int[boardSize];
//		this.colVisitCount = new int[boardSize];
//		
//		MoveNode[][] board = new MoveNode[size][size];
//		
//		for(int i = 0; i < boardSize; i++){
//			for(int j = 0; j < boardSize; j++){
//				board[i][j] = new MoveNode(new Point(i,j));
//			}
//		}
//		
//		for(int i = 0; i < boardSize; i++){
//			for(int j = 0; j < boardSize; j++){
//				for(Point p : allowedMoves){
//					int nextI = i + p.x;
//					int nextJ = j + p.y;
//					
//					if(0 <= (nextI) && (nextI) < boardSize && 0 <= (nextJ) && (nextJ) < boardSize && !board[nextI][nextJ].isVisited()){
//						board[i][j].addNeighbour(board[nextI][nextJ]);
//					}
//				}
//			}
//		}
//		
//long st = System.currentTimeMillis();
//		boolean isone = knightBT(board, board[0][0], null, boardSize, 1);
//long dur = System.currentTimeMillis() - st; 
//		this.p.setBoard(board);
//		this.p.repaint();
//
//		System.out.println("done "+ count + " " +dur);


	}

	private MoveNode[][] runAlg(int h, int w) {
		if(h % 2 != 0 || w % 2 !=0)
			throw new IllegalArgumentException("Size must be even");
		

		
		//base case
		if(h <= 12 && w <= 12){
			//this.rowVisitCount = new int[boardSize];
			//this.colVisitCount = new int[boardSize];
			if(h == 6 && w == 6 && sixXsix != null){
				return copyOf(sixXsix);
			}else if(h == 6 && w == 8 && sixXeight != null){
				return copyOf(sixXeight);
			}else if(h == 8 && w == 6 && eightXsix != null){
				return copyOf(eightXsix);
			}else if(h == 8 && w == 8 && eightXeight != null){
				return copyOf(eightXeight);
			}else if(h == 8 && w == 10 && eightXten != null){
				return copyOf(eightXten);
			}else if(h == 10 && w == 8 && tenXeight != null){
				return copyOf(tenXeight);
			}else if(h == 10 && w == 10 && tenXten != null){
				return copyOf(tenXten);
			}	
			
			MoveNode[][] board = new MoveNode[h][w];
			
			for(int r = 0; r < h; r++){
				for(int c = 0; c < w; c++){
					board[r][c] = new MoveNode(new Point(r,c));
				}
			}
			
			for(int r = 0; r < h; r++){
				for(int c = 0; c < w; c++){
					for(Point p : allowedMoves){
						int nextR = r + p.x;
						int nextC = c + p.y;
						
						if(0 <= (nextR) && (nextR) < h && 0 <= (nextC) && (nextC) < w && !board[nextR][nextC].isVisited()){
							board[r][c].addNeighbour(board[nextR][nextC]);
						}
					}
				}
			}
			
			long st = System.currentTimeMillis();
			boolean isone = knightBT(board, board[0][0], null, h, w, 1);
			long dur = System.currentTimeMillis() - st; 
			this.p.setBoard(board);
			this.p.repaint();

			System.out.println("done "+ count + " " +dur);
			
//			if(h == 6 && w == 6){
//				sixXsix = board;
//			}else if(h == 6 && w == 8){
//				sixXeight = board;
//			}else if(h == 8 && w == 6){
//				eightXsix = board;
//			}else if(h == 8 && w == 8){
//				eightXeight = board;
//			}else if(h == 8 && w == 10){
//				eightXten = board;
//			}else if(h == 10 && w == 8){
//				tenXeight = board;
//			}else if(h == 10 && w == 10){
//				tenXten = board;
//			}			
			
			return board;
		}
		
		//divide
		int size1 = 0;
		int size2 = 0;

		

		int newSize = h/2;
		if(newSize % 2 != 0){
			size1 = newSize - 1;
			size2 = newSize + 1;
		}else{
			size1 = newSize;
			size2 = newSize;
		}

		//conquer
		MoveNode[][] board1 = runAlg(size1, size1);
		MoveNode[][] board2 = runAlg(size1, size2);
		MoveNode[][] board3 = runAlg(size2, size1);
		MoveNode[][] board4 = runAlg(size2, size2);
		
		if(board1[0][0] == board2[0][0])
			System.out.println("fak");
				
		//combine		
		MoveNode[][] combined = new MoveNode[size1+size2][size1+size2];
		
		//top left
		for(int r = 0; r < size1; r++){
			for(int c = 0; c < size1; c++){
				combined[r][c] = board1[r][c];
			}
		}
		
		//top right
		for(int r = 0; r < size1; r++){
			for(int c = size1; c < size1+size2; c++){
				board2[r][c - size1].incCol(size1);
				combined[r][c] = board2[r][c-size1];
			}
		}
		
		//bottom left
		for(int r = size1; r < size1+size2; r++){
			for(int c = 0; c < size1; c++){
				board3[r-size1][c].incRow(size1);
				combined[r][c] = board3[r-size1][c];
			}
		}
		
		//bottom right
		for(int r = size1; r < size1+size2; r++){
			for(int c = size1; c < size1+size2; c++){
				board4[r-size1][c-size1].incBoth(size1);
				combined[r][c] = board4[r-size1][c-size1];
			}
		}
		
		
		MoveNode a1 = combined[size1-1][size1-2];
		MoveNode a2 = combined[size1-3][size1-1];
		
		MoveNode b1 = combined[size1+1][size1-3];
		MoveNode b2 = combined[size1][size1-1];
		
		MoveNode c1 = combined[size1+2][size1];
		MoveNode c2 = combined[size1][size1+1];
		
		MoveNode d1 = combined[size1-1][size1];
		MoveNode d2 = combined[size1-2][size1+2];
		System.out.println(a1.getHere());
		System.out.println(a2.getHere());
		System.out.println(b1.getHere());
		System.out.println(b2.getHere());
		System.out.println(c1.getHere());
		System.out.println(c2.getHere());
		System.out.println(d1.getHere());
		System.out.println(d2.getHere());
		

		
		if(a1.getFrom().equals(a2.getHere())){
			a1.setFrom(null);
		}else{
			a2.setFrom(null);
		}
		
		if(b1.getFrom().equals(b2.getHere())){
			b1.setFrom(null);
		}else{
			b2.setFrom(null);
		}
		
		if(c1.getFrom().equals(c2.getHere())){
			c1.setFrom(null);
		}else{
			c2.setFrom(null);
		}
		
		if(d1.getFrom().equals(d2.getHere())){
			d1.setFrom(null);
		}else{
			d2.setFrom(null);
		}
		
		a1.setParberryPoint(b1.getHere());
		d1.setParberryPoint(a2.getHere());
		c2.setParberryPoint(d2.getHere());
		b2.setParberryPoint(c1.getHere());		
		
		return combined;
	}


	private MoveNode[][] copyOf(MoveNode[][] array) {
		MoveNode[][] ret = new MoveNode[array.length][array[0].length];
		for(int i = 0; i < array.length; i++){
			for (int j = 0; j < array[0].length; j++){
					ret[i][j] = array[i][j].clone();
			}
		}
		return ret;
	}

	public boolean knightBT(MoveNode[][] b, MoveNode here, MoveNode from, int h, int w, int k){
//		if(k > ((h*w)/2)){
//			if(k < (h*w)+1 && disjointSquare(b))
//				return false;
//			
////			if(disconnected()){
////				return false;
////			}
//		}
		
		++count;
		
		if(k != 1){
			here.setVisited(true);
			here.setFrom(from.getHere());
			//this.rowVisitCount[here.getHere().x]++;
			//this.colVisitCount[here.getHere().y]++;
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

		int hereI = here.getHere().x;
		int hereJ = here.getHere().y;
		
		if(k < (h*w)+1 && from != null && disconnectionFrom(from)){
			b[hereI][hereJ].setVisited(false);
			b[hereI][hereJ].setFrom(null);
			return false;
			
		}
		
		if(k == (h*w)+1 && hereI == 0 && hereJ == 0){
			return true;
		}
		
		for(MoveNode next : here.getSortedNeighbours()){
			if(next != from && !next.isVisited()){
				if(!disconnectionFrom(next) && knightBT(b, next, here, h,w, k+1)){
					return true;
				}
			}
				
		}
		//this.rowVisitCount[here.getHere().x]--;
		//this.colVisitCount[here.getHere().y]--;
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

	private boolean disconnected() {
		for(int i = 1; i < boardSize -3; i++){
			if(rowVisitCount[i-1] < boardSize && rowVisitCount[i] == boardSize && rowVisitCount[i+1] == boardSize && rowVisitCount[i+2] < boardSize 
			   || colVisitCount[i-1] < boardSize && colVisitCount[i] == boardSize && colVisitCount[i+1] == boardSize && colVisitCount[i+2] < boardSize){
				return true;
			}
		}
		return false;
	}

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
		KnightsTourParb k = new KnightsTourParb(6);
		//455810968 24648 with disct for 6
		//540925981 22080 otherwise for 6
		//98803214 36762
	}
}


