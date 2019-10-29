
public class Game
{
	public Player black, white;
	public long TimeLimit;
	public boolean GameOver, BlackPlayerTurn;
	public int NumberPasses= 0;
	public Board board;
	public GameBoardUI BoardWindow;
	public boolean move() {
		while (NumberPasses !=2)
		{
	}
		return GameOver;
}
	
	public void Start() {
		while (!GameOver) 
		{
			
		}
		
	}
	public Game (GameBoardUI B) {
		BoardWindow= B;
		black=new Player();
		white=new AI();
		board=new Board(B,black,white);
		
		
	}
	public void run() {
	
		Player toMove = black;
		int skip= NumberPasses;
	
		while (skip<2) {
			skip= toMove.move() ? 0 : skip + 1;
	}
	
	}
	
	
}
