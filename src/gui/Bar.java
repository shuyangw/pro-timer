package gui;

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
		
		MenuItem newTimer = new MenuItem("New");
		newTimer.setOnAction(e -> {
			NewTimerWindow createNew = new NewTimerWindow(primaryStage);
			createNew.getStage().setOnHiding(evt -> {
				parent.receiveTime(createNew.getHours(), createNew.getMinutes());
				createNew.exit();
			});
		});
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(evt -> {
			System.exit(0);
		});
		
		file.getItems().addAll(newTimer, exit);
		return new MenuBar(file);
	}
}
