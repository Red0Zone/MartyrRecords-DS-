package Driver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import Martyrs.DateStack;
import Martyrs.LocationRecord;
import Martyrs.Martyrs;
import Trees.AVLNode;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import lists.DCircularLinkedList;
import lists.DNode;

public class Main extends Application {
	private App app = new App();
	private DCircularLinkedList<LocationRecord> list = app.getList();
	private TabPane tabPane = app.getTabPane();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		BorderPane main = new BorderPane();
		main.setStyle("-fx-background-image:url('file:77.jpg');-fx-background-size:cover");
		main.setPadding(new Insets(20, 20, 20, 20));
		Label label = new Label("Btselem Records");
		label.setStyle("-fx-text-fill:red;-fx-font-size:35;");

		Image image = new Image("file:archive.jpg");
		ImageView mainImage = new ImageView(image);
		mainImage.setStyle("-fx-boreder-radius: 10px;");
		mainImage.setFitWidth(400);
		mainImage.setFitHeight(300);

		Button chooserBT = app.CustomButton("Choose File");

		main.setTop(label);
		BorderPane.setAlignment(label, Pos.TOP_CENTER);
		main.setCenter(mainImage);
		BorderPane.setAlignment(mainImage, Pos.CENTER);
		main.setBottom(chooserBT);
		BorderPane.setAlignment(chooserBT, Pos.BOTTOM_CENTER);

		stage.setScene(new Scene(main, 600, 600));
		stage.show();

		stage.setOnCloseRequest(e -> {

			Alert alert1 = new Alert(AlertType.CONFIRMATION);
			alert1.setHeaderText("Are you sure you want to close the app?");
			alert1.setTitle("closing program");
			alert1.setContentText("press ok to close or cancel to stay");
			Optional<ButtonType> result = alert1.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK)
				stage.close();
			else
				e.consume();

		});
		chooserBT.setOnAction(e -> {
			FileChooser fileChooser = new FileChooser();
			ExtensionFilter ef = new ExtensionFilter("Text Files(*.txt,*.csv)", "*.txt", "*.csv");
			fileChooser.getExtensionFilters().add(ef);
			File fileChoosed = fileChooser.showOpenDialog(stage);
			if (fileChoosed != null && fileChoosed.canRead()) {
				stage.setScene(new Scene(tabPane, 900, 540));
				app.readFromFile(fileChoosed);
				tabPane.getTabs().add(new FirstTap());

			} else {
				app.alert("Not a vaild file", "choose a file of martyrs to run the program", "", AlertType.INFORMATION);
			}
		});
	}



}
