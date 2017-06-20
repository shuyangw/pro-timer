package gui;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Bar {
	private MenuBar bar;
	
	public Bar(Stage primaryStage){
		this.bar = this.setup(primaryStage);
	}
	
	public MenuBar create(){
		return bar;
	}
	
	private MenuBar setup(Stage primaryStage){
		Menu file = new Menu("File");
		
		MenuItem newTimer = new MenuItem("New");
		newTimer.setOnAction(e -> {
			Stage dialog = new Stage();
			dialog.initStyle(StageStyle.UTILITY);
			dialog.setResizable(false);
			dialog.setTitle("New Timer");
			dialog.initModality(Modality.APPLICATION_MODAL);
			dialog.initOwner(primaryStage);
			
			VBox box = new VBox();
			Label instructions = new Label();
			instructions.setText("Please enter ");
			box.getChildren().add(instructions);
			
			dialog.setScene(new Scene(box,200,150));
			dialog.show();
		});
		
		file.getItems().addAll(newTimer);
		return new MenuBar(file);
	}
}
