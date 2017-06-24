package gui;

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
	
	public void receiveTime(int hours, int minutes){
		this.setupNewTimer(minutes%60, hours+minutes/60);
	}
	
	private void setupNewTimer(int minutes, int hours){
		//Setup timer text
		Timer time = new Timer(minutes, hours);
		time.getText().setFont(new Font("Digital", 72));
		time.getText().setTranslateY(-20);
		bPane.setCenter(time.getText());
		
		//Setup timer buttons 
		Button start = time.getButtons()[0];
		start.setTranslateY(160);
		Button pause = time.getButtons()[1];
		pause.setTranslateY(160);
		bPane.setLeft(start);
		bPane.setRight(pause);
	}
}
