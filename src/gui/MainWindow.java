package gui;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainWindow {
	public void execStage(Stage primaryStage){
		primaryStage.setTitle("Pro Timer");
		primaryStage.setResizable(false);
		primaryStage.setOnCloseRequest(e -> System.exit(0));
		
		BorderPane bPane = new BorderPane();
		VBox top = new VBox();
		Bar bar = new Bar(primaryStage);
		top.getChildren().addAll(bar.create());
		bPane.setTop(top);
		Scene sc = new Scene(bPane, 300, 200);
		primaryStage.setScene(sc);
		primaryStage.show();
	}
}
