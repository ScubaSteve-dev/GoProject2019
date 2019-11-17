package UI;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.List;

import entity.Player;
import utils.DatabaseConnection;

public class RegistrationUI {

	public void starting()  {

    	TextField NameInput = new TextField();
    	TextField PassWordInput = new TextField();
    	TextArea outputArea = new TextArea();
    	Label NameLabel = new Label("UserName:");
    	Label PassWordLabel = new Label("Password:");
    	Label ConfirmPassWord = new Label("Confirm Password: ");
    	TextField ConfirmPassWordInput = new TextField();
    	Button RegisterButton = new Button("Register");
    	
    	GridPane LoginPane = new GridPane();    	
    	LoginPane.setAlignment(Pos.CENTER); 
    	
    	LoginPane.add(NameLabel, 1,1);  //setRowIndex(NameLabel,1);
    	LoginPane.add(NameInput, 2, 1);
    	LoginPane.add(PassWordLabel, 1, 2);
    	LoginPane.add(PassWordInput, 2, 2);
    	LoginPane.add(ConfirmPassWord, 1, 3);
    	LoginPane.add(ConfirmPassWordInput, 2, 3);
    	LoginPane.add(RegisterButton, 4, 4);    	
    	Scene scene = new Scene(LoginPane, 400,300);
    	Stage stage2 = new Stage();
    	stage2.setScene(scene);
    	stage2.show();
      
    	RegisterButton.setOnAction(new EventHandler()
        {
            public void handle(Event event)
            {
            	Player targetPlayer;
            	Player newUser = new Player();
            	String name = NameInput.getText();
            	String password = PassWordInput.getText();
            	DatabaseConnection db = new DatabaseConnection();
            	boolean checkpoint = true;
            	
            	List<Player> players = db.getPlayers();
            	for(int i = 0; i < players.size(); i++) {  //check list for input name
            		String listName = players.get(i).playerName;
            		String listPassword = players.get(i).password;
            		if(listName.equals(name)) { //check to see if username is already been created
            			checkpoint = false;
            			Platform.runLater(() ->
            			{
            				Alert scoreAlert = new Alert(Alert.AlertType.INFORMATION,
            						"This Username is already being used", ButtonType.CANCEL);
            				scoreAlert.showAndWait();
            			});            			
            			break;
            		}            		
            	}
            	if(checkpoint = true) {
            		newUser.setUserName(name);
            		newUser.setPassword(password);
            		DatabaseConnection connection=new DatabaseConnection();
            		connection.insertPlayer(newUser);
            		stage2.close(); 
            	}
            	
            }

			
        });
        

    }

	
}


