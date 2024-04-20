package Driver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import Trees.*;
import Martyrs.DateStack;
import Martyrs.LocationRecord;
import Martyrs.Martyrs;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import lists.DCircularLinkedList;
import lists.DNode;

public class Test extends Application {
	private static DCircularLinkedList<LocationRecord> list = new DCircularLinkedList<LocationRecord>();

	public static void main(String[] args) {

		readFromFile(new File("martyrs.txt"));
		LocationRecord lrr = list.getHead().getNext().getNext().getNext().getNext().getData();
		System.out.println(list.delete(lrr));
		lrr.setLocation("AA");
		list.insertSorted(lrr);
		list.traverase();
		DNode<LocationRecord> curr = list.getHead().getNext();
		while (curr != list.getHead()) {
			System.out.println(curr.getData().printAVL2() + "\n");
			curr = curr.getNext();
		}
		DNode<LocationRecord> curr2 = list.getHead().getNext();
		while (curr2 != list.getHead()) {
			System.out.println(curr2.getData().printAVL1() + "\n");
			curr2 = curr2.getNext();
		}

//
//		try {
//		Scanner scan = new Scanner(new File("martyrs.txt"));
//		int c =0;
//		while(scan.hasNext())
//		System.out.println(scan.nextLine()+" "+ ++c);
//	} catch (FileNotFoundException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
	}

	@Override
	public void start(Stage s) throws Exception {

	}

	public static void readFromFile(File file) {
		String s;
		try (FileReader fileReader = new FileReader(file);
				BufferedReader bufferReader = new BufferedReader(fileReader)) {
			bufferReader.readLine();
			while ((s = bufferReader.readLine()) != null) {
				String[] s1 = s.split(",");
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				Date date;
				char c;
				int age;
				try {
					date = sdf.parse(s1[3]);
					c = s1[4].charAt(0);
					age = Integer.parseInt(s1[1]);
				} catch (ParseException | NumberFormatException e) {
					System.out.println(e);
					continue;
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
			System.out.println(e);
		}
	}

}
