
package UI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

import entity.Player;
import utils.DatabaseConnection;

public class LoginUI
{
	private Player targetPlayer;
	
	public Player starting() throws InterruptedException
	{
		
		Stage stage2 = new Stage();
		TextField NameInput = new TextField();
		TextField PassWordInput = new TextField();
		Label NameLabel = new Label("UserName:");
		Label PassWordLabel = new Label("Password:");
		Button LoginButton = new Button("Login");
		
		GridPane LoginPane = new GridPane();
		LoginPane.setAlignment(Pos.CENTER);
		
		LoginPane.add(NameLabel, 1, 1); // setRowIndex(NameLabel,1);
		LoginPane.add(NameInput, 2, 1);
		LoginPane.add(PassWordLabel, 1, 2);
		LoginPane.add(PassWordInput, 2, 2);
		LoginPane.add(LoginButton, 4, 4);
		Scene scene = new Scene(LoginPane, 400, 300);
		
		stage2.setScene(scene);
		
		LoginButton.setOnAction(e ->
		{
			new Thread()
			{
				@Override
				public void run()
				{
					String name = NameInput.getText();
					String password = PassWordInput.getText();
					DatabaseConnection db = new DatabaseConnection();
					
					List<Player> players = db.getPlayers();
					for (Player player : players)
					{ // check list for input name
						String listName = player.playerName;
						String listPassword = player.password;
						if (listName.equals(name) && listPassword.equals(password))
						{ // compare username and password
							targetPlayer = player;
							stage2.close();
							break;
						}
					}
				}
			}.start();
		});
		while (targetPlayer == null)
		{
			Thread.sleep(100);
		}
		return targetPlayer;
	}
	
}