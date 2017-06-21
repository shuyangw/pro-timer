package gui;

import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import gui.NewTimerWindow;
import func.SyncObject;

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
			NewTimerWindow createNew = new NewTimerWindow(primaryStage);
			createNew.getStage().setOnCloseRequest(new EventHandler<WindowEvent>(){
				public void handle(WindowEvent evt){
					
				}
			});
		});
		
		file.getItems().addAll(newTimer);
		return new MenuBar(file);
	}
}
