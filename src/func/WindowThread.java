package func;

import javafx.stage.Stage;

public class WindowThread extends Thread{
	private Thread thr;
	private String name;
	
	public WindowThread(String name){
		this.name = name;
	}
	
	public void start(Stage primarySD){
		if(thr == null){
			thr = new Thread(this, name);
			thr.start();
		}
	}

	public void run(){
		
	}
}
