package entity;

import UI.GameBoardUI;
import core.Game;

/*
Author:
Modified By: Manjul Shrestha
Change Log:
1. Added username password and id to store in database(Manjul Shrestha)


 */
public class Player
{
	public String playerName;
	public int piecesLeft;
	public String password;
	public String userName;
	public int id;
	
	public Player()
	{
		playerName = "entity.Player";
		piecesLeft = 181;
	}

	public Player(int id){
		this.id=id;
	}
	
	public String move()
	{
		while (GameBoardUI.newMove == null && !GameBoardUI.passClicked && !GameBoardUI.resignClicked)
		{
			try
			{
				Thread.sleep(100); // Necessary so the thread will terminate without input
			}
			catch (Exception e)
			{
			}
			if (Game.GameOver)
			{
				// System.out.println("Window Closed");
				return "Resign";
			}
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
