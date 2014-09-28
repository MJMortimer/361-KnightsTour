package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;

import com.sun.java.swing.plaf.gtk.GTKConstants.ArrowType;

public class KnightPanel extends JPanel {

	public int boardSize;
	public int squareSize;
	public int offset = 10;
	public Move[][] board;
	private long timeTaken;

	public KnightPanel(int size){
		this.boardSize = size;
		squareSize = (670-(2*offset)) / boardSize;
	}

	@Override
	public void paint(Graphics g){

		g.clearRect(0, 0, 670, 670);
		for(int i = 0; i < boardSize; i++){
			for (int j = 0; j < boardSize; j++){
				g.drawRect(offset+(i*squareSize), offset+(j*squareSize), squareSize, squareSize);
				assert board[i][j] != null : board[i][j];
				if(board !=null && board[i][j]!=null && board[i][j].isVisited()){
					int extra = offset + squareSize/2;
					Point from = board[i][j].getFrom();
					Point here = board[i][j].getHere();
					g.drawLine((from.x * squareSize)+extra, (from.y * squareSize) +extra, (here.x * squareSize)+extra, (here.y * squareSize)+extra);
				}
			}
		}
		if(timeTaken != 0){
			g.drawString(String.format("Time taken: %d ms",timeTaken), 10, boardSize*squareSize+3+offset*2);
		}else{
			g.drawString("Computing...", 10, boardSize*squareSize+3+offset*2);
		}
		

	}

	public void setBoard(Move[][] b, long dur){
		this.board = b;
		this.timeTaken = dur;
	}
}

