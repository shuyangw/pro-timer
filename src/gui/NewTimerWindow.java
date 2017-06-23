package gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class NewTimerWindow {
	private final int LEFT_OFFSET = 3;
	private Stage dialog;
	
	private int hours;
	private int minutes;
	
	public NewTimerWindow(Stage primaryStage){
		dialog = new Stage();
		this.prompt(primaryStage);
	}
	
	public Stage getStage(){
		return this.dialog;
	}
	
	public int getHours(){
		return this.hours;
	}
	
	public int getMinutes(){
		return this.minutes;
	}
	
	private void prompt(Stage primaryStage){
		//Removes minimize and restore buttons
		dialog.initStyle(StageStyle.UTILITY);
		dialog.setResizable(false);
		dialog.setTitle("New Timer");
		dialog.initOwner(primaryStage);
		
		//Sets up grid with vertical and horiz gaps of 5px
		GridPane grid = new GridPane();
		grid.setVgap(5);
		grid.setHgap(5);
		
		//Creates instruction label at top
		Label instructions = new Label();
		instructions.setTranslateX(LEFT_OFFSET);
		instructions.setText("Enter time...");
		GridPane.setConstraints(instructions, 0, 0);
		grid.getChildren().add(instructions);
		
		//Hours textfield
		final TextField hoursField = new TextField();
		Text hoursFieldText = new Text("Hours: ");
		hoursFieldText.setTranslateX(LEFT_OFFSET);
		hoursField.setTranslateX(-15);
		GridPane.setConstraints(hoursFieldText, 0, 1);
		GridPane.setConstraints(hoursField, 1, 1);
		grid.getChildren().addAll(hoursField, hoursFieldText);
		
		//Minutes textfield
		final TextField minutesField = new TextField();
		Text minutesFieldText = new Text("Minutes: ");
		minutesFieldText.setTranslateX(LEFT_OFFSET);
		minutesField.setTranslateX(-15);
		GridPane.setConstraints(minutesFieldText, 0, 2);
		GridPane.setConstraints(minutesField, 1, 2);
		grid.getChildren().addAll(minutesField, minutesFieldText);
		
		//Define submit button
		Button submit = new Button("Submit");
		submit.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent evt){
				if(hoursField.getText().isEmpty() && minutesField.getText().isEmpty()){
					raiseNoTimeAlert();
				}
				else if(!checkStringValidity(hoursField.getText()) || !checkStringValidity(minutesField.getText())){
					raiseIllegalStringAlert();
				}
				else if(!minutesField.getText().isEmpty() && Integer.parseInt(minutesField.getText()) > 60){
					raiseBadMinuteAlert();
				}
				else{
					if(!hoursField.getText().isEmpty()){
						hours = Integer.parseInt(hoursField.getText());
					}
					if(!minutesField.getText().isEmpty()){
						minutes = Integer.parseInt(minutesField.getText());
					}
					dialog.close();
				}
			}
		});
		GridPane.setConstraints(submit, 2, 1);
		grid.getChildren().add(submit);
		
		//Define clear button
		Button clear = new Button(" Clear  ");
		clear.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent evt){
				hoursField.clear();
				minutesField.clear();
			}
		});
		GridPane.setConstraints(clear, 2, 2);
		grid.getChildren().add(clear);
		
		dialog.setScene(new Scene(grid,300,150));
		dialog.show();
	}
	
	private boolean checkStringValidity(String str){
		for(int i = 0; i < str.length(); i++){
			if(!Character.isDigit(str.charAt(i))){
				return false;
			}
		}
		return true;
	}
	
	private void raiseNoTimeAlert(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText(null);
		alert.setContentText("Please enter a time");
		alert.show();
	}
	
	private void raiseIllegalStringAlert(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText(null);
		alert.setContentText("Please enter a valid time");
		alert.show();
	}
	
	private void raiseBadMinuteAlert(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText(null);
		alert.setContentText("Please enter minutes less than 60");
		alert.show();
	}
	
	public void exit(){
		dialog.close();
	}
}
