//AI is always going to be the black pieces
public class AI extends Player
{
	
	public String Move()
	{
		// Choose random intersection between (0,0) and (18,18)
		int x = (int) Math.random() * 18;
		int y = (int) Math.random() * 18;
		
		// Make move
		return x + " " + y;
	}
}
