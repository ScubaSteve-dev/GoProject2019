//AI is always going to be the black pieces
public class AI extends Player
{
	@Override
	public String move()
	{
		// Choose random intersection between (0,0) and (18,18)
		int x = (int) (Math.random() * 19);
		int y = (int) (Math.random() * 19);
		
		// Make move
		return x + " " + y;
	}
}
