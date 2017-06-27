package gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import gui.NewTimerWindow;

/**
 * <code>Bar</code> is the class that creates and houses the main Toolbar
 * on top. Specifically, it contains the options to create a new Timer and to
 * exit in one dropdown menu and a help dialog in another.
 * 
 * @author 		Shuyang Wang
 */

public class Bar {
	/**
	 * The Toolbar/Menubar itself and the main window that houses it
	 */
	private MenuBar bar;
	private MainWindow parent;
	
	/**
	 * Constructor takes in the main stage and the hosting parent class and
	 * then initializes the private fields
	 *  
	 * @param primaryStage		main stage of the JavaFX program
	 * @param parent			hosting parent class
	 */
	public Bar(Stage primaryStage, MainWindow parent){
		this.bar = this.setup(primaryStage);
		this.parent = parent;
	}
	
	/**
	 * LOL honestly don't know why it's called <code>create()</code>
	 * 
	 * @return			the Bar
	 */
	public MenuBar create(){
		return bar;
	}
	
	/**
	 * This method sets up the Toolbar by creating its elements and adding them
	 * to a JavaFX <code>MenuBar</code> class and then returning it to be added in the
	 * <code>MainWindow</code> class.
	 * 
	 * @param primaryStage			main stage of the application
	 * @return						the completed Toolbar/Menubar
	 */
	private MenuBar setup(Stage primaryStage){
		Menu file = new Menu("File");
		Menu help = new Menu("Help");
		
		//Defines file options
		//New Timer button
		MenuItem newTimer = new MenuItem("New");
		newTimer.setOnAction(e -> {
			//Initializes the mechanism to create a new timer
			NewTimerWindow createNew = new NewTimerWindow(primaryStage);
			createNew.getStage().setOnHiding(evt -> {
				/*
				 * Detects the finish of creating a new timer and extracts the input hours, minutes and seconds
				 * Also is what prompts the creation of the time display in the main window
				 */
				parent.receiveTime(createNew.getHours(), createNew.getMinutes(), createNew.getSeconds());
				
				//Closes the dialog when finished
				createNew.exit();
			});
		});
		//Exit button
		MenuItem exit = new MenuItem("Exit");
		exit.setOnAction(evt -> {
			System.exit(0);
		});
		
		//Defines help option as a pop-up alert
		MenuItem helper = new MenuItem("Help");
		helper.setOnAction(e -> {
			Alert info = new Alert(AlertType.INFORMATION);
			info.setHeaderText(null);
			info.setContentText("Click on File->New, enter in how many hours, minutes and seconds you want to time"
					+ " click submit, then click start to start the timer. Press pause to pause the timer.");
			info.show();
		});
		
		//Adds New and Exit options to File drop-down
		file.getItems().addAll(newTimer, exit);
		
		//Adds Help option to Help drop-down
		help.getItems().addAll(helper);
		return new MenuBar(file, help);
	}
}
