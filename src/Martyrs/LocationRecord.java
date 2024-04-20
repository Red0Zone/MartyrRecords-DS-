package Martyrs;

import java.util.Date;

import Trees.AVLNode;
import Trees.AVLTree;

public class LocationRecord implements Comparable<LocationRecord> {
	String location;
	AVLTree<Martyrs> avl1 = new AVLTree<Martyrs>();
	AVLTree<DateStack> avl2 = new AVLTree<DateStack>();

	public LocationRecord(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public AVLTree<Martyrs> getAvl1() {
		return avl1;
	}

	public AVLTree<DateStack> getAvl2() {
		return avl2;
	}

	@Override
	public int compareTo(LocationRecord o) {
		return this.location.compareTo(o.location);
	}

	@Override
	public String toString() {
		return location;
	}

	public String printAVL1() {
		return avl1.levelOrderTraverse();
	}

	public String printAVL2() {
		return avl2.postOrderTraverse();

	}

	public void updupdateLocation(String location) {
		this.location = location;
		updateLocation(avl1.getRoot(), location);
		// updateLocationAvl2(avl2.getRoot(), location);
	}

	private void updateLocation(AVLNode<Martyrs> root, String location) {
		if (root != null) {
			root.getData().setLocation(location);
			if (root.hasLeft())
				updateLocation(root.getLeft(), location);
			if (root.hasRight())
				updateLocation(root.getRight(), location);
		}

	}

	public void insertMartyer(Martyrs martyr) {
		AVLNode<DateStack> node = avl2.search(new DateStack(martyr.getDateOfDeath()));
		if (node != null) {
			node.getData().getStack().push(martyr);

		} else {
			DateStack daStack = new DateStack(martyr.getDateOfDeath());
			daStack.stack.push(martyr);
			avl2.insert(daStack);
		}

	}

//	public DateStack maxDateStack() {
//		DateStack max = new DateStack(new Date());
//		return maxDateStack(avl2.getRoot(), max);
//		// updateLocationAvl2(avl2.getRoot(), location);
//	}
//
//	private DateStack maxDateStack(AVLNode<DateStack> root, DateStack max) {
//		if (root != null) {
//
//			if (max.getStack().length() < root.getData().getStack().length())
//				max = root.getData();
//
//			if (root.hasLeft())
//				maxDateStack(root.getLeft(), max);
//			if (root.hasRight())
//				maxDateStack(root.getRight(), max);
//
//		}
//		return max;
//
//	}
	public DateStack maxDateStack() {
		DateStack max = new DateStack(new Date()); // Initialize max with an empty DateStack
		return findMaxDateStack(avl2.getRoot(), max);
	}

	private DateStack findMaxDateStack(AVLNode<DateStack> root, DateStack max) {
		if (root != null) {
			if (root.getData().getStack().length() > max.getStack().length()) {
				max = root.getData();
			}

			if (root.hasLeft()) {
				max = findMaxDateStack(root.getLeft(), max);
			}

			if (root.hasRight()) {
				max = findMaxDateStack(root.getRight(), max);
			}
		}
		return max;
	}

//	private void updateLocationAvl2(AVLNode<DateStack> root, String location) {
//		if (root != null) {
//			root.getData().updateStackLocation(location);
//			if (root.hasLeft())
//				updateLocationAvl2(root.getLeft(), location);
//			if (root.hasRight())
//				updateLocationAvl2(root.getRight(), location);
//		}
//
//	}

}
