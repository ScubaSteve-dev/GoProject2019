//AI is always going to be the black pieces
public class AI extends Player
{
	
	public void Move() {
	int x, y;
	
	do {
	//Choose random intersection between (0,0) and (18,18)
	x = (int) Math.random() * 18;
	y = (int) Math.random() * 18;
	
		
	//Check if the move is valid
	if (Board.validMove(x,y,Game.BlackPlayerTurn) == true) {
	
		//Make move w/ grey pieces
		Board.makeMove(x, y, true, Game.BlackPlayerTurn);
	}
	
	} while (Board.validMove(x, y, Game.BlackPlayerTurn) != true);
	
	//Make move
	Board.makeMove(x, y, false, Game.BlackPlayerTurn);
	
	}
}
