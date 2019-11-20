//Javafx portion written by Carolyn Langhoff
//I used documentation from Oracle's website to make the table

//Data retrieval done by Manjul and Mohammad

package UI;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ViewStatsUI extends Application
{
	
  private TableView table = new TableView();
 // public static void main(String[] args) {
   //   launch(args);
 // }

  @Override
  public void start(Stage stage) {
      Scene scene = new Scene(new Group());
      stage.setWidth(357);
      stage.setHeight(500);

            

      table.setEditable(true);

      TableColumn blank = new TableColumn("  ");
      TableColumn userName = new TableColumn("Name");
      TableColumn userWin = new TableColumn("Winner");
      TableColumn userLoss = new TableColumn("Loser");
      
      table.getColumns().addAll(blank, userName, userWin, userLoss);
      
      
      //Add data from database here
      //table.setItems("place holder for data variable");
     
      

      final VBox vbox = new VBox();
      vbox.setSpacing(5);
      vbox.setPadding(new Insets(10, 0, 0, 10));
      vbox.getChildren().addAll(table);

      ((Group) scene.getRoot()).getChildren().addAll(vbox);

      stage.setScene(scene);
      stage.show();
  }
  
  //Class to retrieve data from the database
  public static void getData() {
  	
 
  	
  }
  
  
 
}