package UI;

import javafx.application.Application;
import javafx.stage.Stage;

import entity.Player;

public class MainUI extends Application
{
	private GameBoardUI gameStarter = new GameBoardUI();
	private LoginUI loginScreen = new LoginUI();
	private RegistrationUI registerScreen = new RegistrationUI();
	private ViewStatsUI viewStats = new ViewStatsUI();
	private Player p1, p2;
	
	@Override
	public void start(Stage stage) throws Exception
	{
		gameStarter.startGame(stage);
		stage.show();
	}
	
	public static void main(String[] args)
	{
		launch();
	}
	
}
