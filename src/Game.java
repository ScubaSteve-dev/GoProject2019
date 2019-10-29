
public class Game
{
	public static Player black, white;
	public static long TimeLimit;
	public static boolean GameOver, BlackPlayerTurn;
	public static int NumberPasses;
	public static Board board;
	public static GameBoardUI BoardWindow;
	public Game (GameBoardUI B) {
		BoardWindow= B;
		black=new Player();
		white=new AI();
		board=new Board(B,black,white);
		
	}
}
