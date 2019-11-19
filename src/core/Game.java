package core;

import UI.GameBoardUI;
import entity.AI;
import entity.Player;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class Game
{
	public Player black, white;
	public long TimeLimit;
	public static boolean GameOver, BlackPlayerTurn = true, playerResigned;
	public static int NumberPasses = 0;
	public Board board;
	public GameBoardUI BoardWindow;
	
	public void Start()
	{
		while (!GameOver)
		{
			if (BlackPlayerTurn)
			{
				boolean validFound = false;
				while (!validFound)
				{
					String choice = black.move();
					switch (choice)
					{
						case "Pass":
							NumberPasses++;
							validFound = true;
							break;
						case "Resign":
							// System.out.println("Someone resigned");
							GameOver = true;
							validFound = true;
							break;
						default:
							if (!board.validMove(Integer.parseInt(choice.substring(0, choice.indexOf(' '))),
									Integer.parseInt(choice.substring(choice.indexOf(' ') + 1)), true))
							{
								break;
							}
							board.makeMove(Integer.parseInt(choice.substring(0, choice.indexOf(' '))),
									Integer.parseInt(choice.substring(choice.indexOf(' ') + 1)), false, true);
							black.piecesLeft--;
							NumberPasses = 0;
							validFound = true;
					}
					if (GameOver)
					{
						validFound = true;
						// System.out.println("core.Game ended somehow.");
					}
				}
			}
			else
			{
				boolean validFound = false;
				while (!validFound)
				{
					String choice = white.move();
					switch (choice)
					{
						case "Pass":
							NumberPasses++;
							validFound = true;
							break;
						case "Resign":
							// System.out.println("Someone resigned");
							GameOver = true;
							validFound = true;
							break;
						default:
							if (!board.validMove(Integer.parseInt(choice.substring(0, choice.indexOf(' '))),
									Integer.parseInt(choice.substring(choice.indexOf(' ') + 1)), false))
							{
								break;
							}
							board.makeMove(Integer.parseInt(choice.substring(0, choice.indexOf(' '))),
									Integer.parseInt(choice.substring(choice.indexOf(' ') + 1)), false, false);
							white.piecesLeft--;
							NumberPasses = 0;
							validFound = true;
					}
					if (GameOver)
					{
						validFound = true;
						// System.out.println("core.Game ended somehow.");
					}
				}
			}
			BlackPlayerTurn = !BlackPlayerTurn;
			GameBoardUI.passClicked = false;
			GameBoardUI.newMove = null;
			GameBoardUI.resignClicked = false;
			if (NumberPasses == 2)
			{
				GameOver = true;
			}
		}
		double[] scores = board.score(playerResigned, !BlackPlayerTurn);
		
		Platform.runLater(() ->
		{
			Alert scoreAlert = new Alert(Alert.AlertType.INFORMATION,
					"Black Score: " + scores[0] + " White Score: " + scores[1], ButtonType.CANCEL);
			scoreAlert.showAndWait();
		});
	}
	
	public Game(GameBoardUI B)
	{
		BoardWindow = B;
		black = new Player();
		white = new AI();
		board = new Board(B, black, white);
		
	}
}
