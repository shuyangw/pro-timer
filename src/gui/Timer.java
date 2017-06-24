package gui;

import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class Timer {
	private int minutes;
	private int hours;
	
	private Text time;
	private Button start;
	private Button pause;
	private boolean continueTime;
	
	public Timer(int mins, int hrs){
		this.minutes = mins;
		this.hours = hrs;
		this.continueTime = false;
		
		this.setupText();
		this.setupButtons();
		
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
		if(len(hours) == 1){
			textString += "0"+hours+" : ";
		}
		else{
			textString += hours + " : ";
		}
		if(len(minutes) == 1){
			textString += "0"+minutes;
		}
		else if(minutes == 0){
			textString += "00";
		}
		else{
			textString += minutes;
		}
		this.time = new Text(textString);
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
					}
				}
			}
		}
	}
	
}
