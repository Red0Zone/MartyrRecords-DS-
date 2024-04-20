package Driver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Martyrs.DateStack;
import Martyrs.LocationRecord;
import Martyrs.Martyrs;
import Trees.AVLNode;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TabPane;
import javafx.scene.control.Alert.AlertType;
import lists.DCircularLinkedList;
import lists.DNode;

public class App {
	private static DCircularLinkedList<LocationRecord> list = new DCircularLinkedList<LocationRecord>();
	private static TabPane tabPane = new TabPane();

	public TabPane getTabPane() {
		return tabPane;
	}

	public static DCircularLinkedList<LocationRecord> getList() {
		return list;
	}

	public Button CustomButton(String btName) {
		Button customButton = new Button(btName);
		customButton.setStyle("-fx-background-color: darkgray; " + "-fx-background-radius: 20px; "
				+ "-fx-text-fill: red; " + "-fx-font-size: 20px; " + "-fx-padding: 8px 14px;");
		customButton.setOnMouseEntered(e -> {
			customButton.setStyle("-fx-background-color: #2F4F4F; " + "-fx-background-radius: 20px; "
					+ "-fx-text-fill: red; " + "-fx-font-size: 20px; " + "-fx-padding: 8px 14px;");
		});

		customButton.setOnMouseExited(e -> {
			customButton.setStyle("-fx-background-color: darkgray; " + "-fx-background-radius: 20px; "
					+ "-fx-text-fill: red; " + "-fx-font-size: 20px; " + "-fx-padding: 8px 14px;");
		});
		return customButton;
	}

	public MenuButton CustomMenuButton(String btName) {
		MenuButton customMenuButton = new MenuButton(btName);
		customMenuButton.setStyle("-fx-background-color: darkgray; " + "-fx-background-radius: 20px; "
				+ "-fx-text-fill: red; " + "-fx-font-size: 20px; " + "-fx-padding: 8px 14px;");

		return customMenuButton;
	}

	public void readFromFile(File file) {
		String s;
		try (FileReader fileReader = new FileReader(file);
				BufferedReader bufferReader = new BufferedReader(fileReader)) {
			bufferReader.readLine();
			while ((s = bufferReader.readLine()) != null) {
				String[] s1 = s.split(",");
				if (s1.length != 5)
					continue;
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				Date date = null;
				char c = 'M';
				int age = 0;
				try {
					date = sdf.parse(s1[3]);
					c = s1[4].charAt(0);
					age = Integer.parseInt(s1[1]);
				} catch (ParseException e) {
				} catch (NumberFormatException e) {
				}
				LocationRecord loc = new LocationRecord(s1[2]);
				DNode<LocationRecord> node = list.find(loc);
				Martyrs martyrs = new Martyrs(s1[0], age, s1[2], date, c);
				DateStack dateStack = new DateStack(date);
				DNode<LocationRecord> locNode;
				if (node == null) {
					node = list.insertSorted(loc);
				}
				AVLNode avlNode1 = node.getData().getAvl1().search(martyrs);
				if (avlNode1 == null)
					node.getData().getAvl1().insert(martyrs);

				AVLNode avlNode2 = node.getData().getAvl2().search(dateStack);
				if (avlNode2 == null) {
					node.getData().getAvl2().insert(dateStack);
					AVLNode<DateStack> dateStackNode = node.getData().getAvl2().search(dateStack);
					dateStackNode.getData().getStack().push(martyrs);
				} else {
					AVLNode<DateStack> dateStackNode = node.getData().getAvl2().search(dateStack);
					dateStackNode.getData().getStack().push(martyrs);
				}
			}
		} catch (IOException e) {
		}
	}

	public void alert(String title, String header, String contentText, AlertType alerttype) {
		Alert alert = new Alert(alerttype);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(contentText);
		alert.show();
	}

	public DNode findLocation(String locationName) {
		DNode<LocationRecord> curr = list.getHead().getNext();
		while (curr != list.getHead()
				&& locationName.toLowerCase().compareTo(curr.getData().getLocation().toLowerCase()) > 0)
			curr = curr.getNext();
		if (curr != list.getHead() && curr.getData().getLocation().equalsIgnoreCase(locationName))
			return curr;
		return null;
	}
}
