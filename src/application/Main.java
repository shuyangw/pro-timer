/**
 * <h1> ProTimer </h1>
 * The ProTimer application is a simple timer, counting down 
 * from a user inputed amount of time.
 * 
 * @author Shuyang Wang
 * @version 1.0 
 * @since 2017-06-26	
 */

package application;

import javafx.application.Application;
import javafx.stage.Stage;

import gui.MainWindow;

public class Main extends Application{
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws InterruptedException {
		//Launches the main window of the program
		MainWindow wind = new MainWindow();
		wind.execStage(primaryStage);
	}
}
