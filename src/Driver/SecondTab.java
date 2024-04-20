package Driver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Martyrs.DateStack;
import Martyrs.LocationRecord;
import Martyrs.Martyrs;
import Trees.AVLNode;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lists.DCircularLinkedList;
import lists.DNode;

public class SecondTab extends Tab {
	private App app = new App();
	private DCircularLinkedList<LocationRecord> list = App.getList();
	private TextField searchFilter = new TextField();
	private TableView<Martyrs> tv = new TableView<Martyrs>();
	private TableColumn<Martyrs, String> nameCoulmn;
	private TableColumn<Martyrs, Integer> ageCoulmn;
	private TableColumn<Martyrs, String> dateCoulmn;
	private TableColumn<Martyrs, Character> genderCoulmn;
	private Label label;
	private LocationRecord location;

	public SecondTab(LocationRecord location) {
		this.location = location;
		label = new Label("Location :" + location);
		tableView();

		TextField name = new TextField();
		name.setPrefColumnCount(30);
		name.setPromptText("Insert Name:");
		name.setStyle("-fx-prompt-text-fill:green;-fx-background-color: #F2F2F2; -fx-border-color: #CCCCCC;");

		TextField age = new TextField();
		age.setPrefColumnCount(5);
		age.setPromptText("Age:");
		age.setStyle("-fx-prompt-text-fill:green;-fx-background-color: #F2F2F2; -fx-border-color: #CCCCCC;");

		Button add = app.CustomButton("Add");

		Button delete = app.CustomButton("Delete");

		Button search = app.CustomButton("Search");
		Button update = app.CustomButton("Update");
		Button statistics = app.CustomButton("Statistics");
		Button all = app.CustomButton("All");

		searchFilter.setPrefColumnCount(30);
		searchFilter.setPromptText("search:");
		searchFilter.setStyle("-fx-prompt-text-fill:green;-fx-background-color: #F2F2F2; -fx-border-color: #CCCCCC;");

		Label searchBox = new Label("Search Box");
		searchBox.setStyle("-fx-font-size:20;-fx-text-fill:red");

		HBox hb3 = new HBox(10, add, delete, update, statistics);
		hb3.setPadding(new Insets(15, 15, 15, 15));
		hb3.setAlignment(Pos.CENTER);

		HBox hb2 = new HBox(15, searchBox, searchFilter, search, all);
		hb2.setPadding(new Insets(15, 15, 15, 15));
		label.setStyle("-fx-font-size:30;-fx-text-fill:red;");
		hb2.setAlignment(Pos.CENTER);

		VBox vb = new VBox(15);
		vb.setAlignment(Pos.CENTER);
		vb.setPadding(new Insets(15, 15, 15, 15));
		vb.setPrefSize(1000, 800);
		vb.setStyle("-fx-background-image:url('file:77.jpg');-fx-background-size:cover");
		vb.getChildren().addAll(label, hb2, tv, hb3);
		this.setContent(vb);
		this.setText(location + " Record");

		search.setOnAction(e -> {
			if (!searchFilter.getText().isBlank()) {
				tv.getItems().clear();
				searchFill(location.getAvl1().getRoot(), searchFilter.getText().trim());
			}
			if (tv.getItems().size() == 0) {
				app.alert("Not Found", "No martyr with this name", "", AlertType.INFORMATION);
			}
		});
		all.setOnAction(e -> {

			tv.getItems().clear();
			tableViewFill(location.getAvl1().getRoot());

		});
		add.setOnAction(e -> {
			AddScreen();
		});
		delete.setOnAction(e -> {
			Martyrs martyr = tv.getSelectionModel().getSelectedItem();
			if (martyr != null) {
				location.getAvl1().deleteNode(martyr);
				AVLNode<DateStack> ds = location.getAvl2().search(new DateStack(martyr.getDateOfDeath()));
				ds.getData().DeleteFromStack(martyr);
				if (ds.getData().getStack().peek() == null) {
					location.getAvl2().deleteNode(ds.getData());
				}

				tv.getItems().clear();
				tableViewFill(location.getAvl1().getRoot());

			} else {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setHeaderText("Select a Martyr from list first");
				alert.show();
			}
		});
		update.setOnAction(e -> {
			if (tv.getSelectionModel().getSelectedItem() != null) {
				updateScreen();
			} else
				app.alert("Choose from list", "select a martyr from the list to update", "", AlertType.INFORMATION);
		});
		statistics.setOnAction(e -> {
			TabPane tabPane = app.getTabPane();
			if (tabPane.getTabs().size() == 3)
				tabPane.getTabs().remove(2);

			app.getTabPane().getTabs().add(new ThirdTab(location));
		});

	}

	private void tableView() {

		nameCoulmn = new TableColumn("Name");
		nameCoulmn.setCellValueFactory(new PropertyValueFactory<Martyrs, String>("name"));
		nameCoulmn.setPrefWidth(340);

		ageCoulmn = new TableColumn("Age");
		ageCoulmn.setCellValueFactory(new PropertyValueFactory<Martyrs, Integer>("age"));

		dateCoulmn = new TableColumn("DateOfDeath");
		dateCoulmn.setCellValueFactory(new PropertyValueFactory<Martyrs, String>("dateString"));

		genderCoulmn = new TableColumn("Gender");
		genderCoulmn.setCellValueFactory(new PropertyValueFactory<Martyrs, Character>("gender"));

		tv.getColumns().addAll(nameCoulmn, ageCoulmn, dateCoulmn, genderCoulmn);

		tableViewFill(location.getAvl1().getRoot());

	}

	private void tableViewFill(AVLNode<Martyrs> root) {
		if (root != null) {
			if (root.hasLeft())
				tableViewFill(root.getLeft());

			tv.getItems().add(root.getData());

			if (root.hasRight())
				tableViewFill(root.getRight());
		}
	}

	private void searchFill(AVLNode<Martyrs> root, String name) {
		if (root != null) {
			if (root.hasLeft())
				searchFill(root.getLeft(), name);

			if (root.getData().getName().toLowerCase().contains(name.toLowerCase()))
				tv.getItems().add(root.getData());

			if (root.hasRight())
				searchFill(root.getRight(), name);
		}

	}

	private void AddScreen() {
		Label label = new Label();
		label.setStyle("-fx-font-size:20;-fx-text-fill:red");
		TextField name = new TextField();
		name.setPromptText("Martyr Name:");
		name.setStyle("-fx-prompt-text-fill: green;");

		DatePicker datePicker = new DatePicker();
		datePicker.setEditable(false);

		TextField age = new TextField();
		age.setStyle("-fx-prompt-text-fill: green;");
		age.setPromptText("Age:");

		RadioButton male = new RadioButton("M");
		RadioButton female = new RadioButton("F");
		ToggleGroup tg = new ToggleGroup();
		tg.getToggles().addAll(male, female);
		HBox hb = new HBox(5, male, female);
		hb.setAlignment(Pos.CENTER);
		Button bt = new Button("OK");

		VBox vb = new VBox(10, label, name, age, datePicker, hb, bt);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Add new martyer");
		DialogPane dp = new DialogPane();
		dp.setPrefWidth(300);
		dp.setContent(vb);
		alert.setDialogPane(dp);
		alert.show();
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.setOnCloseRequest(e -> {
			stage.close();
		});
		vb.setAlignment(Pos.CENTER);
		bt.setOnAction(e -> {
			if (!name.getText().isBlank() && !age.getText().isBlank() && datePicker.getValue() != null
					&& tg.getSelectedToggle() != null) {

				try {
					int n = Integer.parseInt(age.getText());
					if (n > 0 && n < 120) {
						SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
						Date date = sdf.parse(datePicker.getValue().getMonth().getValue() + "/"
								+ datePicker.getValue().getDayOfMonth() + "/" + datePicker.getValue().getYear());
						Martyrs martyr = new Martyrs(name.getText().trim(), n, location.getLocation(), date,
								tg.getSelectedToggle().equals(male) ? 'M' : 'F');
						if (location.getAvl1().search(martyr) == null) {
							location.getAvl1().insert(martyr);
							location.insertMartyer(martyr);
							tv.getItems().clear();
							tableViewFill(location.getAvl1().getRoot());
						} else
							app.alert("Already Exists", "The Martyer Data you Entered is Already exists", "",
									AlertType.WARNING);

						stage.close();
					} else {
						label.setText("Age from 0 to 120 only");
						e.consume();
					}

				} catch (NumberFormatException ex) {
					label.setText("Enter a valid number");
				} catch (ParseException e1) {
					System.out.println(e1);
				}

			} else {
				label.setText("Dont leave it empty");
				label.setStyle("-fx-font-size:20;-fx-text-fill:red");
			}
		});

	}

	private void updateScreen() {
		Label label = new Label();
		label.setStyle("-fx-font-size:20;-fx-text-fill:red");

		TextField name = new TextField();
		name.setPromptText("Martyr Name:");
		name.setStyle("-fx-prompt-text-fill: green;");

		DatePicker datePicker = new DatePicker();
		datePicker.setEditable(false);

		TextField age = new TextField();
		age.setStyle("-fx-prompt-text-fill: green;");
		age.setPromptText("Age:");

		RadioButton male = new RadioButton("M");
		RadioButton female = new RadioButton("F");
		ToggleGroup tg = new ToggleGroup();
		tg.getToggles().addAll(male, female);
		HBox hb = new HBox(5, male, female);
		hb.setAlignment(Pos.CENTER);
		Button bt = new Button("OK");

		VBox vb = new VBox(10, label, name, age, datePicker, hb, bt);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Add new martyer");
		DialogPane dp = new DialogPane();
		dp.setPrefWidth(300);
		dp.setContent(vb);
		alert.setDialogPane(dp);
		alert.show();
		Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
		stage.setOnCloseRequest(e -> {
			stage.close();
		});
		vb.setAlignment(Pos.CENTER);
		bt.setOnAction(e -> {
			if (!name.getText().isBlank() || !age.getText().isBlank() || datePicker.getValue() != null
					|| tg.getSelectedToggle() != null) {
				try {
					Martyrs martyrs = tv.getSelectionModel().getSelectedItem();
					if (!name.getText().isBlank()) {
						if (location.getAvl1().search(
								new Martyrs(name.getText(), 0, location.getLocation(), new Date(), 'M')) == null) {

							location.getAvl1().deleteNode(martyrs);
							martyrs.setName(name.getText().trim());
							location.getAvl1().insert(martyrs);

							stage.close();
						} else {
							app.alert("Already Exists", "The name you Entered is Already exists", "",
									AlertType.WARNING);
							stage.close();
						}
					} else {
						label.setText("Name cant be empty!!");
						e.consume();
					}
					if (datePicker.getValue() != null) {
						SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
						Date date = sdf.parse(datePicker.getValue().getMonth().getValue() + "/"
								+ datePicker.getValue().getDayOfMonth() + "/" + datePicker.getValue().getYear());
						AVLNode<DateStack> s = location.getAvl2().search(new DateStack(martyrs.getDateOfDeath()));
						s.getData().DeleteFromStack(martyrs);
						martyrs.setDateOfDeath(date);
						if (s.getData().getStack().peek() == null)
							location.getAvl2().deleteNode(s.getData());
						location.insertMartyer(martyrs);
						stage.close();

					}
					if (!age.getText().isBlank()) {
						int n = Integer.parseInt(age.getText());
						if (n > 0 && n < 120) {
							martyrs.setAge(n);
							stage.close();
						} else {
							label.setText("Age from 0 to 120 only");
							e.consume();
						}

					}
					if (tg.getSelectedToggle() != null) {
						martyrs.setGender(tg.getSelectedToggle().equals(male) ? 'M' : 'F');
						stage.close();
					}
					tv.getItems().clear();
					tableViewFill(location.getAvl1().getRoot());

				} catch (NumberFormatException ex) {
					label.setText("Enter a valid number");
				} catch (ParseException e1) {
					System.out.println(e1);
				}

			} else {
				label.setText("Dont leave it empty");
				label.setStyle("-fx-font-size:20;-fx-text-fill:red");
			}
		});
	}

}
