package gui;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * MainWindow is the class that houses the main GUI of the
 * program. Does nothing when initialized but afterwards, the
 * <code>execStage()</code> method is meant to be called to initialize the bar
 * on top. Also contains the initialization mechanisms for the actual
 * timer.
 * 
 * @author Shuyang Wang
 */

public class MainWindow {
	//Boolean indicating the existence of the menu bar and the menu bar itself
	private boolean isBar;
	private Bar bar;
	
	/**
	 * JavaFX stage and BorderPane pertaining to the containment of the
	 * GUI elements
	 */
	private Stage primaryStage;
	private BorderPane bPane;
	
	/**
	 * 	Class that houses the timing mechanisms of the program
	 */
	private Timer time;
	
	/**
	 * Simple constructor with no inputs and does not initialize the bar
	 */
	public MainWindow(){
		this.isBar = false;
		this.bar = null;
	}
	
	/**
	 * This method initializes the stage elements. First sets title, resizing
	 * restrictions, and close functions and then creates the Vertical Box to 
	 * house the Toolbar up top. The stage then adds the scene that includes the
	 * BorderPane and Toolbar and then shows itself. 
	 * 
	 * @param primary		Stage initialized by the main application
	 */
	public void execStage(Stage primary){
		this.primaryStage = primary;
		primaryStage.setTitle("Pro Timer");
		primaryStage.setResizable(false);
		primaryStage.setOnCloseRequest(e -> System.exit(0));
		bPane = new BorderPane();
		VBox top = new VBox();
		bar = new Bar(primaryStage, this);
		top.getChildren().addAll(bar.create());
		isBar = true;
		bPane.setTop(top);
		Scene sc = new Scene(bPane, 300, 200);
		primaryStage.setScene(sc);
		primaryStage.show();
	}
	
	/**
	 * Simple method that returns if the Toolbar is initialized
	 * 
	 * @return			if the bar is initialized
	 */
	public boolean barSetup(){
		return isBar;
	}
	
	/**
	 * Returns the toolbar
	 * 
	 * @return		the toolbar as a </code>Bar</code> object
	 */
	public Bar getBar(){
		return bar;
	}
	
	/**
	 * This method receives the input time from the <code>NewTimerWindow</code> class and
	 * initializes the actual timer in the <code>Timer</code> class. It then performs
	 * mathematical operations to trim the input so that it resembles an actual clock
	 * and not have any overflow values, i.e. something like 80 seconds or 90 minutes, which
	 * will turn into 1 minute, 20 seconds and 1 hour, 30 minutes respectively
	 * 
	 * @param seconds			number of seconds from the input prompt
	 * @param minutes			number of minutes from the input prompt
	 * @param hours				number of hours from the input prompt
	 */
	public void receiveTime(int seconds, int minutes, int hours){
		this.setupNewTimer(seconds%60, minutes%60+seconds/60, hours+minutes/60);
	}
	
	/**
	 * 	This method updates the time displayed on the window as per the values of the
	 * 	<code>Time</code> object. As we cannot change UI elements from a non-JavaFX
	 * 	application thread, we must invoke this mechanism to change the displayed text
	 * 
	 */
	public void updateText(){
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				time.getText().setTranslateY(-20);
				bPane.setCenter(time.getText());
			}
		});
	}
	
	/**
	 * This method initializes the timing mechanism of the program. Takes in the 
	 * user inputed seconds, minutes and hours and initializes the text displayed
	 * in the window. Also initializes the start and pause buttons. Slight positional 
	 * modifications must be made for a uniform appearance.
	 * 
	 * @param seconds			number of seconds from the input prompt
	 * @param minutes			number of minutes from the input prompt
	 * @param hours				number of hours from the input prompt
	 */
	private void setupNewTimer(int seconds, int minutes, int hours){
		//Setup timer text
		time = new Timer(hours, minutes, seconds, this, bPane);
		time.getText().setFont(new Font("Digital", 48));
		time.getText().setTranslateY(-20);
		bPane.setCenter(time.getText());
		
		//Setup timer buttons 
		Button start = time.getButtons()[0];
		start.setTranslateY(160);
		Button pause = time.getButtons()[1];
		pause.setTranslateY(160);
		pause.setTranslateX(-7);
		bPane.setLeft(start);
		bPane.setRight(pause);
	}
}
