import java.util.ArrayList;
import java.util.Arrays;

public class Board
{
	public static char[][] currentBoard;
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
	public boolean validMove(int x, int y, boolean blackPlayerTurn)
	{
		// TODO
		return true;
	}
}
