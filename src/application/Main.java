package application;
	
import javafx.application.Application;
import javafx.stage.Stage;

import gui.MainWindow;
import func.WindowThread;
import func.SyncObject;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		MainWindow wind = new MainWindow();
		wind.execStage(primaryStage);
		
		
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
