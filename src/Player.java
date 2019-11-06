
public class Player
{
	public String playerName;
	public int piecesLeft;
	
	public Player()
	{
		playerName = "Player";
		piecesLeft = 181;
	}
	
	public String move()
	{
		while (GameBoardUI.newMove == null && !GameBoardUI.passClicked && !GameBoardUI.resignClicked)
		{
		}
		if (GameBoardUI.passClicked)
		{
			GameBoardUI.passClicked = false;
			return "Pass";
		}
		else if (GameBoardUI.resignClicked)
		{
			GameBoardUI.resignClicked = false;
			return "Resign";
		}
		else
		{
			String temp = GameBoardUI.newMove.x + " " + GameBoardUI.newMove.y;
			GameBoardUI.newMove = null;
			return temp;
		}
	}
}
