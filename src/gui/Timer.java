package gui;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Timer {
	private int minutes;
	private int hours;
	private int seconds;
	
	private Text time;
	private Button start;
	private Button pause;
	private boolean continueTime;
	
	private MainWindow mainWind;
	
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
	
	public Text getText(){
		return this.time;
	}
	
	public Button[] getButtons(){
		Button[] arr = {this.start, this.pause};
		return arr;
	}

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
	
	private void setupButtons(){
		this.start = new Button("Start");
		this.start.setOnAction(evt -> {
			System.out.println("Start pressed");
			this.continueTime = true;
		});
		this.pause = new Button("Pause");
		this.pause.setOnAction(evt -> {
			System.out.println("Pause pressed");
			this.continueTime = false;
		});
	}
	
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
		this.setupText();
		mainWind.updateText();
		if(seconds == 0 && this.minutes == 0 && this.hours == 0){
			Platform.runLater(new Runnable(){
				public void run(){
					end();
					continueTime = false;
				}
			});
		}
	}
	
	public void end(){
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setHeaderText(null);
		alert.setContentText("Timer ended");
		Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
		alertStage.setAlwaysOnTop(true);
		alertStage.toFront();
		alert.show();
	}
	
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
	
	private void shiftHour(){
		--this.hours;
	}
	
	private int len(int n){
		return (int)(Math.log10(n)+1);
	}
	
	private class TimerThread implements Runnable{
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
