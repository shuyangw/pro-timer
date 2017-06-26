package gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import gui.NewTimerWindow;

public class Bar {
	private MenuBar bar;
	private MainWindow parent;
	
	public Bar(Stage primaryStage, MainWindow parent){
		this.bar = this.setup(primaryStage);
		this.parent = parent;
	}
	
	public MenuBar create(){
		return bar;
	}
	
	private MenuBar setup(Stage primaryStage){
		Menu file = new Menu("File");
		Menu help = new Menu("Help");
		
		//Defines file options
		MenuItem newTimer = new MenuItem("New");
		newTimer.setOnAction(e -> {
			NewTimerWindow createNew = new NewTimerWindow(primaryStage);
			createNew.getStage().setOnHiding(evt -> {
				parent.receiveTime(createNew.getHours(), createNew.getMinutes(), createNew.getSeconds());
				createNew.exit();
			});
		});
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(evt -> {
			System.exit(0);
		});
		
		//Defines help options
		MenuItem helper = new MenuItem("Help");
		helper.setOnAction(e -> {
			Alert info = new Alert(AlertType.INFORMATION);
			info.setHeaderText(null);
			info.setContentText("Click on File->New, enter in how many hours, minutes and seconds you want to time"
					+ " click submit, then click start to start the timer. Press pause to pause the timer.");
			info.show();
		});
		
		file.getItems().addAll(newTimer, exit);
		help.getItems().addAll(helper);
		return new MenuBar(file, help);
	}
}
