

package UI;

import core.Game;
import UI.GameBoardUI;
import utils.DatabaseConnection;
import entity.Player;

import javafx.application.Platform;
import javafx.event.ActionEvent;
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

import javax.swing.JDialog;

public class LoginUI {
	
	public int UsersLoggedIn = 0;
	/*
	 public static void main(String[] args) {
	    	LoginUI l = new LoginUI();
	        l.starting();
	 }
	*/
	
	public void starting()  {
		
		Stage stage2 = new Stage();
    	TextField NameInput = new TextField();
    	TextField PassWordInput = new TextField();
    	TextArea outputArea = new TextArea();
    	Label NameLabel = new Label("UserName:");
    	Label PassWordLabel = new Label("Password:");
    	Button LoginButton = new Button("Login");
    	
    	GridPane LoginPane = new GridPane();  
    	LoginPane.setAlignment(Pos.CENTER); 
    	
    	LoginPane.add(NameLabel, 1,1);  //setRowIndex(NameLabel,1);
    	LoginPane.add(NameInput, 2, 1);
    	LoginPane.add(PassWordLabel, 1, 2);
    	LoginPane.add(PassWordInput, 2, 2);
    	LoginPane.add(LoginButton, 4, 4);    	
    	Scene scene = new Scene(LoginPane, 400, 300);
    	
    	stage2.setScene(scene);
    	stage2.show();
      
    	
    	LoginButton.setOnAction(new EventHandler()
        {
            public void handle(Event event)
            {
            	Player targetPlayer;
            	String name = NameInput.getText();
            	String password = PassWordInput.getText();
            	DatabaseConnection db = new DatabaseConnection();
            	
            	List<Player> players = db.getPlayers();
            	for(int i = 0; i < players.size(); i++) {  //check list for input name
            		String listName = players.get(i).playerName;
            		String listPassword = players.get(i).password;
            		if(listName.equals(name) && listPassword.equals(password)) { //compare username and password 
            			targetPlayer=players.get(i);
            			UsersLoggedIn++;
            			stage2.close();
            			break;
            		}            		
            	}
            	
            	if(UsersLoggedIn == 2) {
            		//start game loop 
            	}
            	
            }

			
        });
        

    }

	
}