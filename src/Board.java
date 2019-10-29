import java.util.ArrayList;
import java.util.Arrays;

public class Board
{
	public static char[][] currentBoard;
	public static ArrayList<char[][]> pastBoards;
	public static GameBoardUI boardWindow;
	public static Player one, two;
	
	// Method implemented by John Comeaux
	public Board(GameBoardUI win, Player o, Player t)
	{
		one = o;
		two = t;
		boardWindow = win;
		currentBoard = new char[19][19];
		for (char[] c : currentBoard)
		{
			Arrays.fill(c, ' ');
		}
		pastBoards = new ArrayList<>();
		pastBoards.add(currentBoard);
	}
	
	public boolean validMove(int x, int y, boolean blackPlayerTurn)
	{
		// TODO
		return true;
	}
}
