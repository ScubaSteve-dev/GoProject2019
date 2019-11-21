package core;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

import UI.GameBoardUI;
import entity.Player;

public class Board
{
	public static char[][] currentBoard;
	public static char[][] scoringBoard;
	public static ArrayList<char[][]> pastBoards;
	public static GameBoardUI boardWindow;
	public static Player black, white;
	
	// Method implemented by John Comeaux
	public Board(GameBoardUI win, Player o, Player t)
	{
		black = o;
		white = t;
		boardWindow = win;
		currentBoard = new char[19][19];
		for (char[] c : currentBoard)
		{
			Arrays.fill(c, ' ');
		}
		pastBoards = new ArrayList<>();
		pastBoards.add(currentBoard);
	}
	
	// To be implemented
	public void makeMove(int x, int y, boolean isAi, boolean blackPlayerTurn)
	{
		pastBoards.add(currentBoard.clone());
		currentBoard[x][y] = blackPlayerTurn ? 'B' : 'W';
		boardWindow.updateBoard(currentBoard);
	}
	
	// To be implemented
	public double[] score(boolean playerResigned, boolean blackPlayerTurn)
	{
		if (playerResigned)
		{
			System.out.println((blackPlayerTurn ? black.playerName : white.playerName) + " resigned");
			return new double[] { blackPlayerTurn ? -1.0 : 0.0, !blackPlayerTurn ? -1.0 : 0.0 };
		}
		scoringBoard();
		double[] scores = new double[2];
		for (char t[] : scoringBoard)
		{
			for (char h : t)
			{
				if (h == 'B')
				{
					scores[0] += 1;
				} else if (h == 'W')
				{
					scores[1] += 1;
				}
			}
		}
		return scores;
	}
	public void scoringBoard() {
		scoringBoard = cloneArray(currentBoard);
		ArrayList<Point> blackList = new ArrayList<Point>();
		ArrayList<Point> whiteList = new ArrayList<Point>();
		char B = 'B';
		char W = 'W';
		for(int x =0;x<19;x++) {
			for(int y=0;y<19;y++) {
				if(blackList.contains(new Point(x,y))) {
					continue;
				}
				//check originates from pieces
				if(scoringBoard[x][y] == B) {
					blackList= scoringHelper(x,y, B, scoringBoard, blackList);
				}
			}
			
		}
		for(int x =0;x<19;x++) {
			for(int y=0;y<19;y++) {
				if(whiteList.contains(new Point(x,y))) {
					continue;
				}
				//check originates from pieces
				if(scoringBoard[x][y] == B) {
					whiteList= scoringHelper(x,y, B, scoringBoard, whiteList);
				}
			}
			
		}
		ArrayList<Point> whiteListTemp = (ArrayList<Point>) whiteList.clone();
		whiteList.removeAll(blackList);
		blackList.removeAll(whiteListTemp);
		for(char[] q: scoringBoard) {
			for(char r: q) {
				r = ' ';
			}
		}
		for(Point e: blackList) {
			scoringBoard[e.x][e.y]= 'B';
		}
		for(Point e: whiteListTemp) {
			scoringBoard[e.x][e.y]= 'W';
		}
	}
	public ArrayList<Point> scoringHelper(int x, int y,char chIn, char[][] board, ArrayList<Point> traversedPoints) {
		Point currentPoint = new Point(x,y);
		if(board[x][y] == chIn||board[x][y]==' ') {
			if(!traversedPoints.contains(currentPoint)) {
				traversedPoints.add(currentPoint);
				int check = up(y,x,chIn, board, traversedPoints);
				if(check!=-1) {
					traversedPoints = scoringHelper(x,check,chIn,board,traversedPoints);
				}
				check = down(y,x,chIn, board, traversedPoints);
				if(check!=-1) {
					traversedPoints = scoringHelper(x,check,chIn,board,traversedPoints);
				}
				check = right(y,x,chIn, board, traversedPoints);
				if(check!=-1) {
					traversedPoints = scoringHelper(check,y,chIn,board,traversedPoints);
				}
				check = left(y,x,chIn, board, traversedPoints);
				if(check!=-1) {
					traversedPoints = scoringHelper(check,y,chIn,board,traversedPoints);
				}
			}
		}
		return traversedPoints;
	}
	// Zack stuff. Shhhhhhhhhhhhhhhhh it's okay john, shhhhhhhhhhhhhhhh. don't look
	// at it.
	// If row is on edge return -1 if requesting liberty not on board or has been
	// traversed already "traversedPoints"
	// returns null if liberties are found, returns list of deletable items if no
	// liberties found
	public boolean validMove(int x, int y, boolean blackPlayerTurn)
	{
		// check holds 'B' if player black or 'W' if player white
		char check;
		char checkNot;
		check = blackPlayerTurn ? 'B' : 'W';
		checkNot = !blackPlayerTurn ? 'B' : 'W';
		char[][] arrayClone = cloneArray(currentBoard);
		// check if board position is empty
		if (currentBoard[x][y] == ' ')
		{
			// prime arrayClone by placing piece to be place in spot
			arrayClone[x][y] = check;
			// check if capture of other players pieces takes place (up, right, left, down)
			// checkNot
			captureCheck(x, y, checkNot, arrayClone);
			// if our candidate placement has liberties after capture check then place
			// piece!
			if (hasLibTest(x, y, check, arrayClone))
			{
				if (pastBoards.contains(arrayClone))
				{
					// do not allow repeat boards
					return false;
				}
				currentBoard = cloneArray(arrayClone);
				// make move handles actually piece placement so remove check from board
				arrayClone[x][y] = ' ';
				// arrayClone contains updated positions with captured pieces removed
				return true;
			}
			
		}
		return false;
		
	}
	
	// make [][] array to replace currentBoard.
	private void captureCheck(int x, int y, char checkNot, char[][] captureBoard)
	{
		// it is possible to capture 4 different groups of pieces with one piece
		// placement
		ArrayList<Point> up = new ArrayList<>();
		ArrayList<Point> down = new ArrayList<>();
		ArrayList<Point> left = new ArrayList<>();
		ArrayList<Point> right = new ArrayList<>();
		if (right(y, x, checkNot, captureBoard, right) != -1)
		{
			if (captureBoard[x + 1][y] == checkNot)
			{
				right = hasLiberties(x + 1, y, checkNot, captureBoard);
				if (right != null)
				{
					while (!right.isEmpty())
					{
						Point remove = right.remove(0);
						captureBoard[remove.x][remove.y] = ' ';
					}
				}
			}
		}
		if (left(y, x, checkNot, captureBoard, left) != -1)
		{
			if (captureBoard[x - 1][y] == checkNot)
			{
				left = hasLiberties(x - 1, y, checkNot, captureBoard);
				if (left != null)
				{
					while (!left.isEmpty())
					{
						Point remove = left.remove(0);
						captureBoard[remove.x][remove.y] = ' ';
					}
				}
			}
		}
		if (up(y, x, checkNot, captureBoard, up) != -1)
		{
			if (captureBoard[x][y - 1] == checkNot)
			{
				up = hasLiberties(x, y - 1, checkNot, captureBoard);
				if (up != null)
				{
					while (!up.isEmpty())
					{
						Point remove = up.remove(0);
						captureBoard[remove.x][remove.y] = ' ';
					}
				}
			}
		}
		if (down(y, x, checkNot, captureBoard, down) != -1)
		{
			if (captureBoard[x][y + 1] == checkNot)
			{
				down = hasLiberties(x, y + 1, checkNot, captureBoard);
				if (down != null)
				{
					while (!down.isEmpty())
					{
						Point remove = down.remove(0);
						captureBoard[remove.x][remove.y] = ' ';
					}
				}
			}
		}
		
	}
	
	public boolean hasLibTest(int x, int y, char check, char[][] workingBoard)
	{
		ArrayList<Point> test = hasLiberties(x, y, check, workingBoard);
		if (test == null)
		{
			return true;
		} else
		{
			System.out.println(test.toString());
			return false;
		}
	}
	
	public ArrayList<Point> hasLiberties(int x, int y, char check, char[][] hasLibertiesBoard)
	{
		// create two ArrayList to hold traversed points and liberties found
		ArrayList<Point> feedHasLiberties = new ArrayList<Point>();
		ArrayList<Point> feedHasLibertiesTraversed = new ArrayList<Point>();
		// put those array list into an array so that we can return two array list from
		// our method call
		ArrayList<ArrayList<Point>> stacked = new ArrayList<>();
		stacked.add(new ArrayList<Point>());
		stacked.add(new ArrayList<Point>());
		stacked.set(0, feedHasLiberties);
		stacked.set(1, feedHasLibertiesTraversed);
		stacked = hasLibertiesHelper(x, y, check, feedHasLiberties, feedHasLibertiesTraversed, hasLibertiesBoard);
		if (stacked.get(0).isEmpty())
		{
			return stacked.get(1);
		}
		return null;
	}
	
	public ArrayList<ArrayList<Point>> hasLibertiesHelper(int x, int y, char check, ArrayList<Point> traversedPoints,
			ArrayList<Point> hasLibertiesHelperlibertiesAvailable, char[][] hasLibertiesHelperBoard)
	{
		Point currentPoint = new Point(x, y);
		ArrayList<ArrayList<Point>> stackedHelper = new ArrayList<>();
		stackedHelper.add(new ArrayList<>());
		stackedHelper.add(new ArrayList<>());
		traversedPoints.add(currentPoint);
		if (hasLibertiesHelperBoard[x][y] == ' ')
		{
			if (!hasLibertiesHelperlibertiesAvailable.contains(currentPoint))
			{
				hasLibertiesHelperlibertiesAvailable.add(currentPoint);
			}
		} else
		{
			int right = right(y, x, check, hasLibertiesHelperBoard, traversedPoints);
			
			if (right != -1)
			{
				stackedHelper = hasLibertiesHelper(right, y, check, traversedPoints,
						hasLibertiesHelperlibertiesAvailable, hasLibertiesHelperBoard);
				hasLibertiesHelperlibertiesAvailable = stackedHelper.get(0);
				traversedPoints = stackedHelper.get(1);
				
			}
			int left = left(y, x, check, hasLibertiesHelperBoard, traversedPoints);
			if (left != -1)
			{
				stackedHelper = hasLibertiesHelper(left, y, check, traversedPoints,
						hasLibertiesHelperlibertiesAvailable, hasLibertiesHelperBoard);
				hasLibertiesHelperlibertiesAvailable = stackedHelper.get(0);
				traversedPoints = stackedHelper.get(1);
			}
			int down = down(y, x, check, hasLibertiesHelperBoard, traversedPoints);
			if (down != -1)
			{
				stackedHelper = hasLibertiesHelper(x, down, check, traversedPoints,
						hasLibertiesHelperlibertiesAvailable, hasLibertiesHelperBoard);
				hasLibertiesHelperlibertiesAvailable = stackedHelper.get(0);
				traversedPoints = stackedHelper.get(1);
			}
			int up = up(y, x, check, hasLibertiesHelperBoard, traversedPoints);
			if (up != -1)
			{
				stackedHelper = hasLibertiesHelper(x, up, check, traversedPoints, hasLibertiesHelperlibertiesAvailable,
						hasLibertiesHelperBoard);
				hasLibertiesHelperlibertiesAvailable = stackedHelper.get(0);
				traversedPoints = stackedHelper.get(1);
			}
		}
		stackedHelper.set(0, hasLibertiesHelperlibertiesAvailable);
		stackedHelper.set(1, traversedPoints);
		return stackedHelper;
	}
	// Directional handle:
	// Prevents out of bounds exceptions
	// increments in direction
	// checks point against ArrayList traversedPoints, returns -1 if
	// traversedPoints.contains(new Point(x, y))
	
	private int up(int row, int column, char check, char[][] upBoard, ArrayList<Point> traversedPoints)
	{
		// checks if requesting point off board, or point already traversed
		if (row == 0 || traversedPoints.contains(new Point(column, row - 1)))
		{
			return -1;
		} else if (upBoard[column][row - 1] != ' ' && upBoard[column][row - 1] != check)
		{
			return -1;
		} else
		{
			return row - 1;
		}
	}
	
	private int down(int row, int column, char check, char[][] downBoard, ArrayList<Point> traversedPoints)
	{
		// checks if requesting point off board, or point already traversed
		if (row == 18 || traversedPoints.contains(new Point(column, row + 1)))
		{
			return -1;
		} else if (downBoard[column][row + 1] != ' ' && downBoard[column][row + 1] != check)
		{
			return -1;
		} else
		{
			return row + 1;
		}
	}
	
	private int right(int row, int column, char check, char[][] rightBoard, ArrayList<Point> traversedPoints)
	{
		// checks if requesting point off board, or point already traversed
		if (column == 18 || traversedPoints.contains(new Point(column + 1, row)))
		{
			return -1;
		} else if (rightBoard[column + 1][row] != ' ' && rightBoard[column + 1][row] != check)
		{
			return -1;
		}
		return column + 1;
	}
	
	private int left(int row, int column, char check, char[][] leftBoard, ArrayList<Point> traversedPoints)
	{
		// checks if requesting point off board, or point already traversed
		if (column == 0 || traversedPoints.contains(new Point(column - 1, row)))
		{
			return -1;
		} else if (leftBoard[column - 1][row] != ' ' && leftBoard[column - 1][row] != check)
		{
			return -1;
		}
		return column - 1;
	}
	
	/*
	 * Taken from Internet, I could have wrote this myself but by the time I knew
	 * what the problem was the solution was right in front of me
	 * https://stackoverflow.com/questions/5617016/how-do-i-copy-a-2-dimensional-
	 * array-in-java
	 */
	public static char[][] cloneArray(char[][] src)
	{
		int length = src.length;
		char[][] target = new char[length][src[0].length];
		for (int i = 0; i < length; i++)
		{
			System.arraycopy(src[i], 0, target[i], 0, src[i].length);
		}
		return target;
	}
}
