package gui;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainWindow {
	private boolean isBar;
	private Bar bar;
	
	private int hours;
	private int minutes;
	
	public MainWindow(){
		this.isBar = false;
		this.bar = null;
	}
	
	public void execStage(Stage primaryStage){
		primaryStage.setTitle("Pro Timer");
		primaryStage.setResizable(false);
		primaryStage.setOnCloseRequest(e -> System.exit(0));
		BorderPane bPane = new BorderPane();
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
		this.hours = hours;
		this.minutes = minutes;
		System.out.println(hours + " " + minutes);
	}
	
	private void setupNewTimer(){
		
	}
}
