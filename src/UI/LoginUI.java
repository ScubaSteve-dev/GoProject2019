
package UI;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

import entity.AI;
import entity.Player;
import utils.DatabaseConnection;

public class LoginUI
{
	private Player targetPlayer;
	
	public Player starting() throws InterruptedException
	{
		
		Stage stage2 = new Stage();
		TextField NameInput = new TextField();
		PasswordField PassWordInput = new PasswordField();
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
		stage2.show();
		
		System.out.println("login");
		LoginButton.setOnAction(e ->
		{
			boolean correct = false;
			{
				String name = NameInput.getText();
				String password = PassWordInput.getText();
				if (name.equals("AI") && password.equals("00000"))
				{
					targetPlayer = new AI();
					correct = true;
					stage2.close();
					System.out.println(correct);
				}
				else
				{
					DatabaseConnection db = new DatabaseConnection();
					// db.stopConnection();
					
					List<Player> players = db.getPlayers();
					for (Player player : players)
					{ // check list for input name
						String listName = player.playerName;
						String listPassword = player.password;
						if (listName.equals(name) && listPassword.equals(password))
						{ // compare username and password
							targetPlayer = player;
							stage2.close();
							System.out.println("Logged In");
							correct = true;
							break;
						}
					}
				}
				if (correct == false)
				{
					System.out.println("here");
					Platform.runLater(() ->
					{
						Alert scoreAlert = new Alert(Alert.AlertType.INFORMATION, "Incorrect Username or Password",
								ButtonType.CANCEL);
						scoreAlert.showAndWait();
					});
				}
			}
		});
		
		return targetPlayer;
		
	}
	
}