package UI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import entity.Player;

public class MainUI extends Application
{
	private GameBoardUI gameStarter = new GameBoardUI();
	private LoginUI loginScreen = new LoginUI();
	private RegistrationUI registerScreen = new RegistrationUI();
	private ViewStatsUI viewStatsScreen = new ViewStatsUI();
	private Player p1, p2;
	
	@Override
	public void start(Stage stage) throws Exception
	{
		mainScreen(stage);
		stage.show();
	}
	
	public void mainScreen(Stage stage)
	{
		Button login = new Button("Login");
		Button register = new Button("Register");
		Button viewStats = new Button("View Statistics");
		login.setOnAction(e ->
		{
			try
			{
				if (p1 == null)
				{
					p1 = new Player();
					loginScreen.starting(stage, this, p1);
				}
				else
				{
					p2 = new Player();
					p2.piecesLeft--;
					loginScreen.starting(stage, this, p2);
				}
				
				if (p1 != null && p2 != null)
				{
					gameStarter.startGame(stage, p1, p2);
				}
			}
			catch (Exception e1)
			{
			}
		});
		register.setOnAction(e ->
		{
			registerScreen.starting(stage, this);
		});
		viewStats.setOnAction(e ->
		{
			viewStatsScreen.starting(stage);
		});
		FlowPane fp = new FlowPane(login, register, viewStats);
		stage.setScene(new Scene(fp, 600, 500));
	}
	
	public static void main(String[] args)
	{
		launch();
	}
	
}
