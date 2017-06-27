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

/**
 * This class contains the mechanism to prompt the user to create a new Timer.
 * A pop-up window is created in the form of a JavaFX stage with text-boxes in the form of
 * JavaFX TextFields are created to prompt for hours/minutes/seconds inputs. Also sets up
 * a submit button to submit the inputs and a clear button to clear the current inputs
 * 
 * @author 		Shuyang Wang
 */

public class NewTimerWindow {
	/**
	 * Private constant integers
	 * <code>RIGHT_OFFSET</code> Defines the right shift in pixels for the text-box description text so they won't
	 * be right on the edge of the screen.
	 * <code>LEFT_OFFSET</code> Defines the left shift in pixels for text-boxes so they will be farther
	 * away from the description text
	 */
	private final int RIGHT_OFFSET = 3;
	private final int LEFT_OFFSET = -15;
	
	//The stage
	private Stage dialog;
	
	private int hours;
	private int minutes;
	private int seconds;
	
	/**
	 * Simple constructor, initializes the dialog then starts its setup
	 * 
	 * @param primaryStage		Stage of the main project
	 */
	public NewTimerWindow(Stage primaryStage){
		dialog = new Stage();
		this.prompt(primaryStage);
	}
	
	/**
	 * @return					the stage for the dialog
	 */
	public Stage getStage(){
		return this.dialog;
	}
	
	/**
	 * The next three methods returns the hours, minutes and seconds of the input
	 * 
	 * @return					hours, minutes, seconds
	 */
	public int getHours(){
		return this.hours;
	}
	public int getMinutes(){
		return this.minutes;
	}
	public int getSeconds(){
		return this.seconds;
	}
	
	/**
	 * This massive methods initializes the pop-up window for the user prompt, creates
	 * the elements of the pop-up. Then shifts the elements accordingly so everything
	 * looks nice. Specifically, the methods first creates a short instructions text,
	 * then defines the three text boxes used for user input and then the submit and
	 * clear buttons
	 * 
	 * When submit is pressed, it is necessary to examine each input to make sure each
	 * input is valid, i.e., the hours must be a feasible number and each input must not
	 * contain any non-integer values. Otherwise, warning messages which are defined
	 * further down are presented to the user.
	 * 
	 * @param primaryStage		the main stage for the application
	 */
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
		//Moves this right by 3 pixels so it won't be at the edge of the screen
		instructions.setTranslateX(RIGHT_OFFSET);
		instructions.setText("Enter time...");
		GridPane.setConstraints(instructions, 0, 0);
		grid.getChildren().add(instructions);
		
		//Hours textfield
		final TextField hoursField = new TextField();
		Text hoursFieldText = new Text("Hours: ");
		//Moves this right by 3 pixels so it won't be at the edge of the screen
		hoursFieldText.setTranslateX(RIGHT_OFFSET);
		/*
		 * Moves the actual text field left by 15 pixels so it will be closer to the text
		 * These two movements are continued for the rest of the text prompts
		 */
		hoursField.setTranslateX(LEFT_OFFSET);
		/*
		 * Positions the description for the hours field and the actual field in accordance
		 * with the grid pane
		 * This is continued similarly for the other elements of the window
		 */
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
		
		//Activates when submit is pressed
		submit.setOnAction(evt -> { 
			//If all fields are empty, prompts a warning page telling the user that there is no input
			if(hoursField.getText().isEmpty() && minutesField.getText().isEmpty() && secondsField.getText().isEmpty()){
				raiseNoTimeAlert();
			}
			//If one field has an illegal input, specifically, runs if there is a non-integer element in the input, will prompt a warning			
			else if(
					!checkStringValidity(hoursField.getText()) || 
					!checkStringValidity(minutesField.getText()) || 
					!checkStringValidity(secondsField.getText())){
				raiseIllegalStringAlert();
			}
			//If the hours field has an input that is more than 2 digits large, will let the user know
			else if(!hoursField.getText().isEmpty() && len(Integer.parseInt(hoursField.getText())) > 2){
				raiseBadHourAlert();
			}
			else{
				//Sets the hours/minutes/seconds fields to the inputs
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
			//Clears every text field in the window
			hoursField.clear();
			minutesField.clear();
			secondsField.clear();
		});
		GridPane.setConstraints(clear, 2, 2);
		grid.getChildren().add(clear);
		
		dialog.setScene(new Scene(grid,300,150));
		dialog.show();
	}
	
	/**
	 * Method that checks the validity of a string with respect to this program
	 * 
	 * @param str			An input string, specifically an input in the 
	 *						text-box in the new timer prompt
	 *						
	 * @return				A boolean, indicating whether the string was valid or not
	 */
	private boolean checkStringValidity(String str){
		for(int i = 0; i < str.length(); i++){
			if(!Character.isDigit(str.charAt(i))){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Warns the user that there is no input when submit is pressed
	 */
	private void raiseNoTimeAlert(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText(null);
		alert.setContentText("Please enter a time");
		alert.show();
	}
	
	/**
	 * Warns the user that the input hours is more than 2 digits
	 */
	private void raiseBadHourAlert(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText(null);
		alert.setContentText("Please enter a number of hours less than 100");
		alert.show();
	}
	
	/**
	 * Warns the user that there is an alphabetical element in the input string
	 */
	private void raiseIllegalStringAlert(){
		Alert alert = new Alert(AlertType.WARNING);
		alert.setHeaderText(null);
		alert.setContentText("Please enter a valid time");
		alert.show();
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
	 * Closes the new timer window
	 */
	public void exit(){
		dialog.close();
	}
}
