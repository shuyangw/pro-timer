package func;

import gui.MainWindow;
import javafx.application.Platform;
import javafx.stage.Stage;

public class WindowThread extends Thread{
	private Thread thr;
	private String name;
	private Stage stage;
	
	private MainWindow wind;
	
	public WindowThread(String name){
		this.name = name;
		this.wind = new MainWindow();
	}
	
	public MainWindow getWindow(){
		return wind;
	}
	
	public void start(Stage primaryStage){
		this.stage = primaryStage;
		if(thr == null){
			thr = new Thread(this, name);
			thr.start();
		}
	}

	public void run(){
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				wind.execStage(stage);
			}
		});
	}
}
