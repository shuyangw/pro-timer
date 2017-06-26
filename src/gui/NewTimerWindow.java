package gui;

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
	private final int RIGHT_OFFSET = 3;
	private final int LEFT_OFFSET = -15;
	
	private Stage dialog;
	
	private int hours;
	private int minutes;
	private int seconds;
	
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
	
	public int getSeconds(){
		return this.seconds;
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
		instructions.setTranslateX(RIGHT_OFFSET);
		instructions.setText("Enter time...");
		GridPane.setConstraints(instructions, 0, 0);
		grid.getChildren().add(instructions);
		
		//Hours textfield
		final TextField hoursField = new TextField();
		Text hoursFieldText = new Text("Hours: ");
		hoursFieldText.setTranslateX(RIGHT_OFFSET);
		hoursField.setTranslateX(LEFT_OFFSET);
		GridPane.setConstraints(hoursFieldText, 0, 1);
		GridPane.setConstraints(hoursField, 1, 1);
		grid.getChildren().addAll(hoursField, hoursFieldText);
		
		//Minutes textfield
		final TextField minutesField = new TextField();
		Text minutesFieldText = new Text("Minutes: ");
		minutesFieldText.setTranslateX(RIGHT_OFFSET);
		minutesField.setTranslateX(LEFT_OFFSET);
		GridPane.setConstraints(minutesFieldText, 0, 2);
		GridPane.setConstraints(minutesField, 1, 2);
		grid.getChildren().addAll(minutesField, minutesFieldText);
		
		//Seconds textfield
		final TextField secondsField = new TextField();
		Text secondsFieldText = new Text("Seconds: ");
		secondsFieldText.setTranslateX(RIGHT_OFFSET);
		secondsField.setTranslateX(LEFT_OFFSET);
		GridPane.setConstraints(secondsFieldText, 0, 3);
		GridPane.setConstraints(secondsField, 1, 3);
		grid.getChildren().addAll(secondsField, secondsFieldText);
		
		//Define submit button
		Button submit = new Button("Submit");
		submit.setOnAction(evt -> { 
			if(hoursField.getText().isEmpty() && minutesField.getText().isEmpty() && secondsField.getText().isEmpty()){
				raiseNoTimeAlert();
			}
			else if(
					!checkStringValidity(hoursField.getText()) || 
					!checkStringValidity(minutesField.getText()) || 
					!checkStringValidity(secondsField.getText())){
				raiseIllegalStringAlert();
			}
			else if(!hoursField.getText().isEmpty() && (int)(Math.log10(Integer.parseInt(hoursField.getText()))+1) > 2){
				raiseBadHourAlert();
			}
			else{
				if(!hoursField.getText().isEmpty()){
					hours = Integer.parseInt(hoursField.getText());
				}
				if(!minutesField.getText().isEmpty()){
					minutes = Integer.parseInt(minutesField.getText());
				}
				if(!secondsField.getText().isEmpty()){
					seconds = Integer.parseInt(secondsField.getText());
				}
				dialog.close();
			}
		});
		GridPane.setConstraints(submit, 2, 1);
		grid.getChildren().add(submit);
		
		//Define clear button
		Button clear = new Button(" Clear  ");
		clear.setOnAction(evt -> {
			hoursField.clear();
			minutesField.clear();
			secondsField.clear();
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
	
	private void raiseBadHourAlert(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText(null);
		alert.setContentText("Please enter a number of hours less than 100");
		alert.show();
	}
	
	private void raiseIllegalStringAlert(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText(null);
		alert.setContentText("Please enter a valid time");
		alert.show();
	}
	
	public void exit(){
		dialog.close();
	}
}
