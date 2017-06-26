package application;
	
import javafx.application.Application;
import javafx.stage.Stage;

import gui.MainWindow;

public class Main extends Application implements Runnable{
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws InterruptedException {
		
		MainWindow wind = new MainWindow();
		wind.execStage(primaryStage);
		
		@SuppressWarnings("unused")
		Thread mainThread = Thread.currentThread();
		Thread thr = new Thread(this, "main");
		thr.start();
	}
	
	public void run(){
		
	}
}
