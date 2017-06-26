package gui;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainWindow {
	private boolean isBar;
	private Bar bar;
	
	private Stage primaryStage;
	private BorderPane bPane;
	
	private Timer time;
	
	public MainWindow(){
		this.isBar = false;
		this.bar = null;
	}
	
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
	
	public boolean barSetup(){
		return isBar;
	}
	
	public Bar getBar(){
		return bar;
	}
	
	public void receiveTime(int seconds, int minutes, int hours){
		this.setupNewTimer(seconds%60, minutes%60+seconds/60, hours+minutes/60);
	}
	
	public void updateText(){
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				time.getText().setTranslateY(-20);
				bPane.setCenter(time.getText());
			}
		});
	}
	
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
