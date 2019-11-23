package UI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
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
					System.out.println("p1");
					p1 = loginScreen.starting();
				}
				else
				{
					System.out.println("p2");
					p2 = loginScreen.starting();
				}
				System.out.println(p1 + " " + p2);
				
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
			registerScreen.starting();
		});
		viewStats.setOnAction(e ->
		{
			viewStatsScreen.start(stage);
		});
		FlowPane fp = new FlowPane(Orientation.VERTICAL, login, register, viewStats);
		fp.setPadding(new Insets(50));
		stage.setScene(new Scene(fp, 600, 400));
	}
	
	public static void main(String[] args)
	{
		launch();
	}
	
}
