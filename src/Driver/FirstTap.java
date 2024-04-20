package Driver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

import Martyrs.LocationRecord;
import Martyrs.Martyrs;
import Trees.AVLNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import lists.DCircularLinkedList;
import lists.DNode;

public class FirstTap extends Tab {
	private App app = new App();
	private DCircularLinkedList<LocationRecord> list = app.getList();
	private ListView<LocationRecord> LocationList;
	private TabPane tabPane = app.getTabPane();

	public FirstTap() {

		LocationList = new ListView<LocationRecord>();
		LocationList.setStyle("-fx-control-inner-background: #e0e0e0;");

		locationListFill();
		Button insert = app.CustomButton("Insert new Location");
		Button delete = app.CustomButton("Delete location");
		Button update = app.CustomButton("Update location");
		Button load = app.CustomButton("Load location Recored");
		MenuButton fileOptions = app.CustomMenuButton("File Options");

		MenuItem readFromFile = new MenuItem("import");
		MenuItem printOnFile = new MenuItem("Export");
		fileOptions.getItems().addAll(readFromFile, printOnFile);

		fileOptions.setAlignment(Pos.CENTER);

		Label headerLabel = new Label("Locations");
		headerLabel.setStyle(
				"-fx-font-size: 30px;-fx-font-weight: bold;-fx-text-fill: red;-fx-padding: 5px 10px;-fx-border-radius: 5px;");
		headerLabel.setAlignment(Pos.CENTER);
		HBox hb = new HBox(10, insert, delete, update, load);
		hb.setPadding(new Insets(10, 10, 10, 10));
		hb.setAlignment(Pos.BOTTOM_CENTER);
		VBox vb = new VBox(10, headerLabel, LocationList, hb, fileOptions);
		vb.setPadding(new Insets(10, 10, 10, 10));
		vb.setAlignment(Pos.CENTER);
		vb.setStyle("-fx-background-image:url('file:77.jpg');-fx-background-size:cover");
		this.setClosable(false);
		this.setContent(vb);
		this.setText("Locations");

		insert.setOnAction(e -> {

			TextInputDialog dialog = new TextInputDialog("Location");
			dialog.setTitle("Add new Location");
			dialog.setHeaderText("Type a new Location Name");
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent() && !result.get().isBlank()) {
				DNode location = app.findLocation(result.get());
				if (location == null) {
					String s = result.get();
					s = s.substring(0, 1).toUpperCase() + s.substring(1, s.length());
					app.getList().insertSorted(new LocationRecord(s.trim()));
					locationListFill();
				} else {
					Alert a = new Alert(AlertType.WARNING);
					a.setHeaderText("Loaction is already exists");
					a.show();
				}
			} else {

				Alert alert = new Alert(AlertType.WARNING);
				alert.setHeaderText("Dont leave it empty!!");
				alert.show();
			}
		});
		delete.setOnAction(e -> {
			LocationRecord lr = LocationList.getSelectionModel().getSelectedItem();
			if (lr != null) {
				Alert dialog = new Alert(AlertType.CONFIRMATION);
				dialog.setHeaderText("Warining!");
				dialog.setContentText("This location will be deleted for ever\nare you sure ? press ok");
				Optional<ButtonType> result = dialog.showAndWait();
				if (result.isPresent() && result.get().equals(ButtonType.OK)) {
					if (list.delete(lr) == true) {
						app.alert("Deleted successfuly", "The location [" + lr.getLocation() + "] was deleted", "",
								AlertType.WARNING);
						locationListFill();
						if (tabPane.getTabs().size() == 2)
							tabPane.getTabs().remove(1);
						else if (tabPane.getTabs().size() == 3) {
							tabPane.getTabs().remove(2);
							tabPane.getTabs().remove(1);
						}
					}
				}
			} else
				app.alert("Choose item", "select an item from the list to delete it", "", AlertType.WARNING);
		});
		update.setOnAction(e -> {
			LocationRecord lr = LocationList.getSelectionModel().getSelectedItem();
			if (lr != null) {
				TextInputDialog dialog = new TextInputDialog("new location");
				dialog.setTitle("Update Location");
				dialog.setHeaderText("Type a new name for the Location");
				Optional<String> result = dialog.showAndWait();
				if (result.isPresent() && !result.get().isBlank()) {
					list.delete(lr);
					lr.updupdateLocation(result.get());
					list.insertSorted(lr);
					locationListFill();

				} else {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setHeaderText("Dont leave it empty!!");
					alert.show();
				}
			} else
				app.alert("Choose item", "select an item from the list to Update it", "", AlertType.WARNING);

		});
		load.setOnAction(e -> {
			LocationRecord lr = LocationList.getSelectionModel().getSelectedItem();
			if (lr != null) {
				SecondTab secondTab = new SecondTab(lr);
				if (tabPane.getTabs().size() == 2)
					tabPane.getTabs().remove(1);
				else if (tabPane.getTabs().size() == 3) {
					tabPane.getTabs().remove(2);
					tabPane.getTabs().remove(1);
				}

				tabPane.getTabs().add(secondTab);

			} else
				app.alert("Choose item", "select a Record from the list to Load it", "", AlertType.WARNING);
		});
		printOnFile.setOnAction(e -> {
			Stage stage = new Stage();
			FileChooser fileChooser = new FileChooser();
			ExtensionFilter ef = new ExtensionFilter("Text Files(*.txt,*.csv)", "*.txt", "*.csv");
			fileChooser.getExtensionFilters().add(ef);
			File fileChoosed = fileChooser.showSaveDialog(stage);
			if (fileChoosed!=null)
				printOnFile(fileChoosed);

		});
		readFromFile.setOnAction(e -> {
			Stage stage = new Stage();
			FileChooser fileChooser = new FileChooser();
			ExtensionFilter ef = new ExtensionFilter("Text Files(*.txt,*.csv)", "*.txt", "*.csv");
			fileChooser.getExtensionFilters().add(ef);
			File fileChoosed = fileChooser.showOpenDialog(stage);
			if (fileChoosed != null && fileChoosed.canRead()) {

				app.readFromFile(fileChoosed);
				locationListFill();

			} else {
				app.alert("Not a vaild file", "choose a file of martyrs to run the program", "", AlertType.INFORMATION);
			}
		});

	}

	private void printOnFile(File fileChoosed) {
		try (PrintWriter pw = new PrintWriter(fileChoosed)) {
			pw.println("Name,Age,Event location - District,Date of death,Gender");
			DNode<LocationRecord> curr = list.getHead().getNext();
			while (curr != list.getHead()) {
				pw.println(curr.getData().printAVL1());
				curr = curr.getNext();
			}
			app.alert("Data saved successfuly", "The data is was printed to the file", "", AlertType.INFORMATION);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void locationListFill() {
		LocationList.getItems().clear();
		DNode<LocationRecord> curr = list.getHead().getNext();
		while (curr != list.getHead()) {
			LocationList.getItems().add(curr.getData());
			curr = curr.getNext();
		}
	}

}
