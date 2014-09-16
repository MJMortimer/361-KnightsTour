package main;

import java.awt.Graphics;

import javax.swing.JPanel;

public class KnightPanel extends JPanel {

	public int boardSize;
	public int squareSize;
	public int offset = 10;
	public Move[][] board;
	
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
				if(board[i][j].isVisited()){
					g.drawLine((board[i][j].getHere().x * squareSize)+offset + squareSize/2, (board[i][j].getHere().y * squareSize) +offset + squareSize/2, (board[i][j].getFrom().x * squareSize)+offset + squareSize/2, (board[i][j].getFrom().y * squareSize)+offset + squareSize/2);
				}
			}
		}
	}
	
	public void setBoard(Move[][] b){
		this.board = b;
	}
}
