
public class Game
{
	public Player black, white;
	public long TimeLimit;
	public boolean GameOver, BlackPlayerTurn, playerResigned;
	public int NumberPasses = 0;
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
					String choice = white.move();
					switch (choice)
					{
						case "Pass":
							NumberPasses++;
							break;
						case "Resign":
							GameOver = true;
							break;
						default:
							if (!board.validMove(Integer.parseInt(choice.substring(0, choice.indexOf(' '))),
									Integer.parseInt(choice.substring(choice.indexOf(' ') + 1)), BlackPlayerTurn))
							{
								break;
							}
							board.makeMove(Integer.parseInt(choice.substring(0, choice.indexOf(' '))),
									Integer.parseInt(choice.substring(choice.indexOf(' ') + 1)), BlackPlayerTurn);
							white.piecesLeft--;
							NumberPasses = 0;
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
							break;
						case "Resign":
							GameOver = true;
							break;
						default:
							if (!board.validMove(Integer.parseInt(choice.substring(0, choice.indexOf(' '))),
									Integer.parseInt(choice.substring(choice.indexOf(' ') + 1)), BlackPlayerTurn))
							{
								break;
							}
							board.makeMove(Integer.parseInt(choice.substring(0, choice.indexOf(' '))),
									Integer.parseInt(choice.substring(choice.indexOf(' ') + 1)), BlackPlayerTurn);
							white.piecesLeft--;
							NumberPasses = 0;
					}
				}
			}
			BlackPlayerTurn = !BlackPlayerTurn;
			if (NumberPasses == 2)
			{
				GameOver = true;
			}
		}
		board.score(playerResigned, !BlackPlayerTurn);
	}
	
	public Game(GameBoardUI B)
	{
		BoardWindow = B;
		black = new Player();
		white = new AI();
		board = new Board(B, black, white);
		
	}
}