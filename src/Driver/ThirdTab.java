package Driver;

import Martyrs.LocationRecord;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import lists.DCircularLinkedList;
import lists.DNode;

public class ThirdTab extends Tab {
	private App app = new App();
	private DCircularLinkedList<LocationRecord> list = app.getList();

	private DNode<LocationRecord> locationNode;
	private Label label;
	private TextArea tx1, tx2;
	private TextField hight1, hight2, numMartyers, maxDate;

	public ThirdTab(LocationRecord location) {
		this.locationNode = list.find(location);
		label = new Label("Location: " + location);
		label.setStyle("-fx-font-size:30;-fx-text-fill:red;");

		Label tx1Label = new Label("AVL1 Traverse");
		tx1Label.setStyle("-fx-font-size:30;-fx-text-fill:red;");

		Label tx2label = new Label("AVL2 Traverse");
		tx2label.setStyle("-fx-font-size:30;-fx-text-fill:red;");

		tx1 = new TextArea();
		tx1.setMinSize(400, 300);
		tx1.setEditable(false);
		tx2 = new TextArea();
		tx2.setEditable(false);
		tx2.setMinSize(400, 300);

		Label hight1label = new Label("AVL1 Hight");
		hight1label.setStyle("-fx-text-fill:red;-fx-font-size:15");
		Label hight2label = new Label("AVL2 Hight");
		hight2label.setStyle("-fx-text-fill:red;-fx-font-size:15");
		Label maxDatelabel = new Label("Max Date:");
		maxDatelabel.setStyle("-fx-text-fill:red;-fx-font-size:15");
		Label numMartyerslabel = new Label("Number of martyers:");
		numMartyerslabel.setStyle("-fx-text-fill:red;-fx-font-size:15");

		VBox txVbox1 = new VBox(10, tx1Label, tx1);
		txVbox1.setAlignment(Pos.CENTER);
		VBox txVbox2 = new VBox(10, tx2label, tx2);
		txVbox2.setAlignment(Pos.CENTER);
		HBox txHbox = new HBox(40, txVbox1, txVbox2);
		txHbox.setAlignment(Pos.CENTER);

		hight1 = new TextField();
		hight1.setEditable(false);
		hight2 = new TextField();
		hight2.setEditable(false);
		maxDate = new TextField();
		maxDate.setEditable(false);
		numMartyers = new TextField();
		numMartyers.setEditable(false);

		GridPane gp = new GridPane();
		gp.setPadding(new Insets(5, 5, 5, 5));
		gp.setAlignment(Pos.CENTER);
		gp.add(hight1label, 0, 0);
		gp.add(hight1, 1, 0);
		gp.add(hight2label, 0, 1);
		gp.add(hight2, 1, 1);
		gp.add(maxDatelabel, 0, 2);
		gp.add(maxDate, 1, 2);
		gp.add(numMartyerslabel, 0, 3);
		gp.add(numMartyers, 1, 3);

		Polygon prev = createRightArrowShape(60);
		prev.setOnMousePressed(e -> {
			if (locationNode.getPrevious() == list.getHead())
				locationNode = locationNode.getPrevious();
			locationNode = locationNode.getPrevious();
			fillText();
		});

		Polygon next = createLeftArrowShape(60);
		next.setOnMousePressed(e -> {
			if (locationNode.getNext() == list.getHead())
				locationNode = locationNode.getNext();
			locationNode = locationNode.getNext();
			fillText();
		});
		HBox navegations = new HBox(300, prev, next);
		navegations.setAlignment(Pos.CENTER);

		VBox vb = new VBox(20, label, txHbox, gp, navegations);
		vb.setAlignment(Pos.CENTER);
		vb.setStyle("-fx-background-image:url('file:77.jpg');-fx-background-size:cover");
		vb.setPadding(new Insets(15, 15, 15, 15));
		fillText();
		this.setContent(vb);
		this.setText("Statistics");
	}

	private Polygon createLeftArrowShape(double size) {
		Polygon arrow = new Polygon();
		arrow.getPoints().addAll(0.0, 0.0, size, size / 2.0, 0.0, size);
		arrow.setFill(Color.DARKGRAY);
		arrow.setOnMouseEntered(e -> {
			arrow.setFill(Color.RED);
		});
		arrow.setOnMouseExited(e -> {
			arrow.setFill(Color.DARKGRAY);

		});
		return arrow;
	}

	private Polygon createRightArrowShape(double size) {
		Polygon arrow = new Polygon();
		arrow.getPoints().addAll(size, 0.0, 0.0, size / 2.0, size, size);
		arrow.setFill(Color.DARKGRAY);
		arrow.setOnMouseEntered(e -> {
			arrow.setFill(Color.RED);
		});
		arrow.setOnMouseExited(e -> {
			arrow.setFill(Color.DARKGRAY);

		});
		return arrow;
	}

	private void fillText() {

		label.setText("Location:" + locationNode.getData());
		tx1.setText(locationNode.getData().printAVL1());
		tx2.setText(locationNode.getData().printAVL2());
		hight1.setText(locationNode.getData().getAvl1().height() + "");
		hight2.setText(locationNode.getData().getAvl2().height() + "");
		numMartyers.setText(locationNode.getData().getAvl1().size() + "");
		maxDate.setText(locationNode.getData().maxDateStack().getDateString());

	}

}
