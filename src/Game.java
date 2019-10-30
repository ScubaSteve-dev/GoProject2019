
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
		public void play() {
			
			Player toMove  = black;
			int skip= NumberPasses;
		
			while (skip<2) {
				skip= toMove.move() ? 0 : skip + 1;
				toMove = toMove== black ? white : black;
		}
	}

		public void pass() {
			int skip= NumberPasses;
			int toSkip  = skip;
		
			while (skip<2) {
				skip = extracted(skip, toSkip);
		}
	}

		private int extracted(int skip, int toSkip) {
			return skip;
		}
}