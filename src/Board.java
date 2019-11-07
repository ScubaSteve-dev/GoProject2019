import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

public class Board
{
	public static char[][] currentBoard;
	public static ArrayList<char[][]> pastBoards;
	public static GameBoardUI boardWindow;
	public static Player black, white;
	private ArrayList<Point> traversedPoints;
	private ArrayList<Point> libertiesAvailable;
	
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
		
	}
	
	// To be implemented
	public void score(boolean playerResigned, boolean blackPlayerTurn)
	{
		if (playerResigned)
		{
			System.out.println((blackPlayerTurn ? black.playerName : white.playerName) + " resigned");
		}
		// TODO
	}
	
	
	//Zack stuff. Shhhhhhhhhhhhhhhhh it's okay john, shhhhhhhhhhhhhhhh. don't look at it.
		//If row is on edge return -1 if requesting liberty not on board or has been traversed already "traversedPoints"
	public boolean validMove(int x, int y, boolean blackPlayerTurn)
	{
		libertiesAvailable.clear();
		traversedPoints.clear();
		//check holds 'B' if player black or 'W' if player white
		char check;
		if (blackPlayerTurn == true) {
			check = 'B';
		}
		else check = 'W';
		//check if board position is empty
		if(currentBoard[y][x]== ' ') {
			traversedPoints.add(new Point(x,y));
			int right = right(y,x,check);
			int left = left(y,x,check);
			int up = up(y,x,check);
			int down = down(y,x,check);
			if(right!=-1) {
				validMoveHelper(right,y,check);
				}
			if(left!=-1) {
				validMoveHelper(left,y,check);
			}
			if(down!=-1) {
				validMoveHelper(x,down,check);
			}
			if(up!=-1) {
				validMoveHelper(x,up,check);
			}
			}
		if(libertiesAvailable.isEmpty())
			return false;
		else return true;
	}
	private void validMoveHelper(int x, int y, char check) {
		Point currentPoint = new Point(x,y);
		traversedPoints.add(currentPoint);
		if(currentBoard[y][x]==' ') {
			if(!libertiesAvailable.contains(currentPoint)) 
			libertiesAvailable.add(currentPoint);
		}
		else {
			int right = right(y,x,check);
			int left = left(y,x,check);
			int up = up(y,x,check);
			int down = down(y,x,check);
			if(right!=-1) {
				validMoveHelper(right,y,check);
				}
			if(left!=-1) {
				validMoveHelper(left,y,check);
			}
			if(down!=-1) {
				validMoveHelper(x,down,check);
			}
			if(up!=-1) {
				validMoveHelper(x,up,check);
			}
		}
	}
		private int up(int row,int column,char check) {
			//checks if requesting point off board, or point already traversed
			if (row == 0 || traversedPoints.contains(new Point(column, row-1)))
				return -1;
			// checks if space is empty or contains a like piece
			else if (currentBoard[column] [row-1]!= ' '&& currentBoard[column][row-1]!=check) {
				return -1;
			}
			else return row-1;
		}
		private int down(int row,int column,char check) {
			//checks if requesting point off board, or point already traversed
			if (row == 18 || traversedPoints.contains(new Point(column, row+1 ))) 
				return -1;
			// checks if space is empty or contains a like piece
			else if (currentBoard[column] [row+1]!= ' '&& currentBoard[column][row+1]!=check) {
				return -1;
			}
			else return row+1;
		}
		private int right(int row,int column, char check) {
			//checks if requesting point off board, or point already traversed
			if ((column == 18) || traversedPoints.contains(new Point(column+1, row )))
				return -1;
			// checks if space is empty or contains a like piece
			else if (currentBoard[column+1] [row]!= ' '&& currentBoard[column+1][row]!=check) {
				return -1;
			}
			return column+1;
		}
		private int left(int row,int column,char check) {
			//checks if requesting point off board, or point already traversed
			if (column == 0 || traversedPoints.contains(new Point(column-1, row)))
				return -1;
			else if (currentBoard[column-1] [row]!= ' '&& currentBoard[column-1][row]!=check) {
				return -1;
			}
			return column-1;
		}
}

}
