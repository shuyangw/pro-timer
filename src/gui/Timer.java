package gui;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *	This class houses the main timer mechanisms. It initializes the object, start
 *	pause buttons and the text for the timer. In addition, there is a private class
 *	that defines the infinite loop for the timer which gets and compares milliseconds
 *	to determine the passage of seconds. A thread is necessary here as without the usage
 *	of one, the main JavaFX thread will be blocked from implementing UI elements and the
 *	application will freeze.
 * 
 * @author 		Shuyang Wang
 */

public class Timer {
	//Minutes, hours and seconds of the current moment of time for the Timer
	private int minutes;
	private int hours;
	private int seconds;
	
	/*
	 * The text that displays the current remaining time, the start and pause buttons and
	 * a boolean that determines whether or not to continue the timer at any given moment
	 */
	private Text time;
	private Button start;
	private Button pause;
	private boolean continueTime;
	
	//The main window object
	private MainWindow mainWind;
	
	/**
	 * Constructor. Receives and initializes the hours/minutes/seconds,
	 * takes in the main window and the BorderPane of the application so
	 * that the text can be correctly implemented within the main JavaFX
	 * thread. Also starts the thread that loops for the duration of the
	 * input time.
	 * 
	 * @param secs			an integer indicating the number of seconds put in by the user
	 * @param mins			an integer indicating the number of minutes put in by the user
	 * @param hrs			an integer indicating the number of hours put in by the user
	 * @param mainWind		the main window
	 * @param bPane			the border pane of the application
	 */
	public Timer(int secs, int mins, int hrs, MainWindow mainWind, BorderPane bPane){
		this.minutes = mins;
		this.hours = hrs;
		this.seconds = secs;
		this.continueTime = false;
		
		this.setupText();
		this.setupButtons();
		
		this.mainWind = mainWind;
		
		TimerThread timerThread = new TimerThread();
		new Thread(timerThread).start();
	}
	
	/**
	 * 
	 * @return			the text indicating the current time remaining in the Timer
	 */
	public Text getText(){
		return this.time;
	}
	
	/**
	 * I forgot why I have this method but it returns the start and pause buttons
	 * 	
	 * @return			the start and pause buttons
	 */
	public Button[] getButtons(){
		Button[] arr = {this.start, this.pause};
		return arr;
	}
	
	/**
	 * Raises an alert indicating that the timer has ended. Also grabs the JavaFX
	 * stage of the alert and sets it to the front ensuring that no matter what else
	 * the user is doing, the user will receive the alert
	 */
	public void end(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setContentText("Timer ended");
		Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
		alertStage.setAlwaysOnTop(true);
		alertStage.toFront();
		alert.show();
	}
	
	/**
	 * Sets up the text in the display time. Precautions are taken to ensure that
	 * each hours, minutes or second field will have 2 digits in it, no matter what
	 * each value is to create consistency
	 */
	private void setupText(){
		String textString = "";
		//Adding dummy zeroes for hours
		if(len(hours) == 1){
			textString += "0"+hours+" : ";
		}
		else if(hours == 0){
			textString += "00 : ";
		}
		else{
			textString += hours + " : ";
		}
		
		//Adding dummy zeroes for minutes
		if(len(minutes) == 1){
			textString += "0"+minutes+ " : ";
		}
		else if(minutes == 0){
			textString += "00 : ";
		}
		else{
			textString += minutes + " : ";
		}
		
		if(len(seconds) == 1){
			textString += "0"+seconds;
		}
		else if(seconds == 0){
			textString += "00";
		}
		else{
			textString += seconds;
		}
		
		this.time = new Text(textString);
		this.time.setFont(new Font("Digital", 48));
	}
	
	/**
	 * Sets up the start and pause buttons. Each does what its suggests
	 */
	private void setupButtons(){
		this.start = new Button("Start");
		this.start.setOnAction(evt -> {
			//Will only start the timer if seconds, minutes and hours are all non-zero
			if(!(this.seconds == 0 && this.minutes == 0 && this.hours == 0)){
				System.out.println("Start pressed");
				this.continueTime = true;
			}
			
		});
		this.pause = new Button("Pause");
		this.pause.setOnAction(evt -> {
			System.out.println("Pause pressed");
			this.continueTime = false;
		});
	}
	
	/**
	 * Shifts the number of seconds down by 1. Specifically, it grabs the portion
	 * of the text displayed that represents the seconds, parses it into an integer,
	 * and shifts it down by 1. If it is already at 0, then it is reset back to 59,
	 * and the method will initialize a shift in minutes too. This method also detects
	 * if the timer is at an end after the shift in seconds, and if so, it raises the
	 * end message and pauses the timer.
	 * 
	 */
	private void shiftSecond(){
		System.out.println(hours + " " + minutes + " " + seconds);
		int realSeconds = Integer.parseInt(time.getText().substring(10,12));
		
		if(realSeconds == 0){
			this.seconds = 59;
			this.shiftMinute();
		}
		else{
			--this.seconds;
		}
		//Re-parses and updates the current times
		this.setupText();
		mainWind.updateText();
		if(seconds == 0 && this.minutes == 0 && this.hours == 0){
			//Mechanism to alter UI elements outside the current non-main thread
			Platform.runLater(new Runnable(){
				public void run(){
					end();
					continueTime = false;
				}
			});
		}
	}
	
	/**
	 * Grabs the minutes from the display text, shifts the minutes down by 1, returning
	 * to 59 if it is already at 0 and calls a shift in hours
	 */
	private void shiftMinute(){
		int realMinutes = Integer.parseInt(time.getText().substring(5,7));
		if(realMinutes == 0){
			this.minutes = 59;
			this.shiftHour();
		}
		else{
			--this.minutes;
		}
	}
	
	/*
	 * Simply shifts the number of hours down
	 */
	private void shiftHour(){
		--this.hours;
	}
	
	/**
	 *	Returns the length of an integer in digits
	 * 
	 * @param n			integer
	 * @return			the length in digits
	 */
	private int len(int n){
		return (int)(Math.log10(n)+1);
	}
	
	/**
	 * The thread of the timing mechanism. For some reason we need the print statement in the
	 * code block under the header of the while loop or else the program will fail. Spooky code,
	 * not sure what it means. 
	 * 
	 * @author Shuyang Wang
	 *
	 */
	private class TimerThread implements Runnable{
		/**
		 * The run method of the Runnable interface. Before the while loop, it grabs the current
		 * time in milliseconds. So long as the <code>continueTime</code> boolean is true, it checks
		 * to see if the difference of a new millisecond time and the original one is greater than 1000,
		 * or at least 1 second has passed since the initialization. When that happens, the original
		 * millisecond time is replaced with the current one, and an indication that 1 second has passed
		 * is thrown.
		 */
		public void run(){
			long prevTimeMilli = System.currentTimeMillis();
			while(true){
				{ //CURSED CODE DO NOT TOUCH
					System.out.print("");
				} //WONT WORK WITHOUT IT WTF MAN
				
				if(continueTime){
					long currTimeMilli = System.currentTimeMillis();
					if(currTimeMilli - prevTimeMilli >= 1000){
						prevTimeMilli = currTimeMilli;
						shiftSecond();
					}
				}
			}
		}
	}
}
