package main;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFrame;

public class KnightsTourParb {

	private KnightPanelOPT p;
	private ArrayList<Point> allowedMoves = new ArrayList<Point>(Arrays.asList(
			new Point(2, 1), new Point(2, -1), new Point(1, 2),
			new Point(1, -2), new Point(-1, 2), new Point(-1, -2), new Point(
					-2, 1), new Point(-2, -1)));
	private int count = 0;

	public KnightsTourParb(int size) {
		// if(size < 12)
		// throw new
		// IllegalArgumentException("Size must be greater than or equal to 12");

		JFrame frame = new JFrame();
		frame.setSize(new Dimension(670, 700));
		this.p = new KnightPanelOPT(size);
		frame.add(p);
		frame.setVisible(true);

		long st = System.currentTimeMillis();
		MoveNode[][] b = runAlg(size, size);
		long dur = System.currentTimeMillis() - st;
		this.p.setBoard(b, dur);
		this.p.repaint();
		System.out.println("done " + count + " " + dur);
	}

	private MoveNode[][] runAlg(int h, int w) {
		if (h % 2 != 0 || w % 2 != 0)
			throw new IllegalArgumentException("Size must be even");

		// base case
		if (h <= 12 && w <= 12) {

			MoveNode[][] board = new MoveNode[h][w];

			for (int r = 0; r < h; r++) {
				for (int c = 0; c < w; c++) {
					board[r][c] = new MoveNode(new Point(r, c));
				}
			}

			for (int r = 0; r < h; r++) {
				for (int c = 0; c < w; c++) {
					for (Point p : allowedMoves) {
						int nextR = r + p.x;
						int nextC = c + p.y;

						if (0 <= (nextR) && (nextR) < h && 0 <= (nextC)
								&& (nextC) < w
								&& !board[nextR][nextC].isVisited()) {
							board[r][c].addNeighbour(board[nextR][nextC]);
						}
					}
				}
			}

			knightBT(board, board[0][0], null, h, w, 1);

			return board;
		}

		// divide
		int size1 = 0;
		int size2 = 0;
		MoveNode[][] board1 = null;
		MoveNode[][] board2 = null;
		MoveNode[][] board3 = null;
		MoveNode[][] board4 = null;
		if (h == w) {
			int newSize = h / 2;
			if (newSize % 2 != 0) {
				size1 = newSize - 1;
				size2 = newSize + 1;
			} else {
				size1 = newSize;
				size2 = newSize;
			}

			// conquer
			board1 = runAlg(size1, size1);
			board2 = runAlg(size1, size2);
			board3 = runAlg(size2, size1);
			board4 = runAlg(size2, size2);
		} else if (h == w - 2) {
			board1 = runAlg((h / 2) % 2 == 0 ? h / 2 : (h / 2) - 1,
					(w / 2) % 2 == 0 ? w / 2 : (w / 2) - 1);
			board2 = runAlg((h - (w / 2)) % 2 == 0 ? h - (w / 2) : h - (w / 2)
					+ 1, (w / 2) % 2 == 0 ? w / 2 : (w / 2) + 1);
			board3 = runAlg((h / 2) % 2 == 0 ? h / 2 : (h / 2) + 1,
					(w / 2) % 2 == 0 ? w / 2 : (w / 2) - 1);
			board4 = runAlg((h - (w / 2)) % 2 == 0 ? h - (w / 2) : h - (w / 2)
					+ 1, (w / 2) % 2 == 0 ? w / 2 : (w / 2) + 1);

		} else if (h == w + 2) {
			board1 = runAlg(h / 2 % 2 == 0 ? h / 2 : (h / 2) - 1,
					h / 2 % 2 == 0 ? h / 2 : (h / 2) - 1);
			board2 = runAlg(h / 2 % 2 == 0 ? h / 2 : (h / 2) - 1,
					h / 2 % 2 == 0 ? h / 2 : (h / 2) - 1);
			board3 = runAlg(h / 2 % 2 == 0 ? h / 2 : (h / 2) + 1,
					(w - (h / 2)) % 2 == 0 ? w - (h / 2) : w - (h / 2) + 1);
			board4 = runAlg(h / 2 % 2 == 0 ? h / 2 : (h / 2) + 1,
					(w - (h / 2)) % 2 == 0 ? w - (h / 2) : w - (h / 2) + 1);
		}

		// combine
		MoveNode[][] combined = new MoveNode[h][w];

		// top left
		for (int r = 0; r < board1.length; r++) {
			for (int c = 0; c < board1[0].length; c++) {
				combined[r][c] = board1[r][c];
			}
		}

		// top right
		for (int r = 0; r < board2.length; r++) {
			for (int c = board1[0].length; c < board1[0].length
					+ board2[0].length; c++) {
				board2[r][c - board1[0].length].incCol(board1[0].length);
				combined[r][c] = board2[r][c - board1[0].length];
			}
		}

		// bottom left
		for (int r = board1.length; r < board1.length + board3.length; r++) {
			for (int c = 0; c < board3[0].length; c++) {
				board3[r - board1.length][c].incRow(board1.length);
				combined[r][c] = board3[r - board1.length][c];
			}
		}

		// bottom right
		for (int r = board1.length; r < board1.length + board4.length; r++) {
			for (int c = board1[0].length; c < board1[0].length
					+ board4[0].length; c++) {
				board4[r - board1.length][c - board1[0].length]
						.incRow(board1.length);
				board4[r - board1.length][c - board1[0].length]
						.incCol(board1[0].length);
				combined[r][c] = board4[r - board1.length][c - board1[0].length];
			}
		}

		MoveNode a1 = combined[board1.length - 1][board1[0].length - 2];
		MoveNode a2 = combined[board1.length - 3][board1[0].length - 1];

		MoveNode b1 = combined[board1.length + 1][board1[0].length - 3];
		MoveNode b2 = combined[board1.length][board1[0].length - 1];

		MoveNode c1 = combined[board1.length + 2][board1[0].length];
		MoveNode c2 = combined[board1.length][board1[0].length + 1];

		MoveNode d1 = combined[board1.length - 1][board1[0].length];
		MoveNode d2 = combined[board1.length - 2][board1[0].length + 2];

		if (a1.getFrom().equals(a2.getHere())) {
			a1.setFrom(null);
		} else {
			a2.setFrom(null);
		}

		if (b1.getFrom().equals(b2.getHere())) {
			b1.setFrom(null);
		} else {
			b2.setFrom(null);
		}

		if (c1.getFrom().equals(c2.getHere())) {
			c1.setFrom(null);
		} else {
			c2.setFrom(null);
		}

		if (d1.getFrom().equals(d2.getHere())) {
			d1.setFrom(null);
		} else {
			d2.setFrom(null);
		}

		a1.setParberryPoint(b1.getHere());
		d1.setParberryPoint(a2.getHere());
		c2.setParberryPoint(d2.getHere());
		b2.setParberryPoint(c1.getHere());
		//
		return combined;
	}

	public boolean knightBT(MoveNode[][] b, MoveNode here, MoveNode from,
			int h, int w, int k) {
		++count;

		if (k != 1) {
			here.setVisited(true);
			here.setFrom(from.getHere());

		}
		// add a wait so that the algorithm can be watched. Have it be done via
		// a switch in the gui
		// try {
		// Thread.sleep(500);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// this.p.setBoard(b);
		// this.p.repaint();

		int hereI = here.getHere().x;
		int hereJ = here.getHere().y;

		if (k < (h * w) + 1 && from != null && disconnectionFrom(from)) {
			b[hereI][hereJ].setVisited(false);
			b[hereI][hereJ].setFrom(null);
			return false;

		}

		if (k == (h * w) + 1 && hereI == 0 && hereJ == 0) {
			return true;
		}

		for (MoveNode next : here.getSortedNeighbours()) {
			if (next != from && !next.isVisited()) {
				if (!disconnectionFrom(next)
						&& knightBT(b, next, here, h, w, k + 1)) {
					return true;
				}
			}

		}
		b[hereI][hereJ].setVisited(false);
		b[hereI][hereJ].setFrom(null);

		return false;
	}

	private boolean disconnectionFrom(MoveNode node) {
		for (MoveNode next : node.getNeighbours()) {
			if (!next.isVisited() && !next.anyUnvisitedNeghbours())
				return true;
		}
		return false;
	}

	public static void main(String[] args) {
		KnightsTourParb k = new KnightsTourParb(Integer.parseInt(args[0]));
	}
}
