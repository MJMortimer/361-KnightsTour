package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JPanel;

import com.sun.java.swing.plaf.gtk.GTKConstants.ArrowType;

public class KnightPanelOPT extends JPanel {

	public int boardSize;
	public int squareSize;
	public int offset = 10;
	public MoveNode[][] board;

	public KnightPanelOPT(int size){
		this.boardSize = size;
		squareSize = (670-(2*offset)) / boardSize;
	}

	@Override
	public void paint(Graphics g){

		g.clearRect(0, 0, 670, 670);
		for(int i = 0; i < boardSize; i++){
			for (int j = 0; j < boardSize; j++){
				g.drawRect(offset+(i*squareSize), offset+(j*squareSize), squareSize, squareSize);
			}
		}
		if(board!= null){
			for(int i = 0; i < board.length; i++){
				for (int j = 0; j < board[0].length; j++){
					int extra = offset + squareSize/2;
					g.drawRect(offset+(i*squareSize), offset+(j*squareSize), squareSize, squareSize);
					assert board[i][j] != null : board[i][j];
					if(board[i][j].getParberryPoint() != null){
						Point here = board[i][j].getHere();
						System.out.println("Parb!");
						System.out.println(board[i][j].getParberryPoint());
						Point parb = board[i][j].getParberryPoint();
						g.drawLine((parb.x * squareSize)+extra, (parb.y * squareSize) +extra, (here.x * squareSize)+extra, (here.y * squareSize)+extra);
					}
					if(board !=null && board[i][j]!=null && board[i][j].getFrom()!=null && board[i][j].isVisited()){
						
						Point from = board[i][j].getFrom();
						Point here = board[i][j].getHere();
						g.drawLine((from.x * squareSize)+extra, (from.y * squareSize) +extra, (here.x * squareSize)+extra, (here.y * squareSize)+extra);
						
					}
				}
			}
		}

	}

	public void setBoard(MoveNode[][] b){
		this.board = b;
	}
}

