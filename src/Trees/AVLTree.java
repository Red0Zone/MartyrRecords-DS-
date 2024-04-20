package Trees;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class AVLTree<T extends Comparable<T>> {
	private AVLNode<T> root;

	public AVLNode<T> getRoot() {
		return root;
	}

	public void insert(T data) {
		if (root == null)
			root = new AVLNode<>(data);
		else {
			AVLNode rootNode = root;
			addEntry(data, rootNode);
			root = rebalance(rootNode);
		}
	}

	private void addEntry(T data, AVLNode rootNode) {
		assert rootNode != null;
		if (data.compareTo((T) rootNode.data) < 0) { // right into left subtree
			if (rootNode.hasLeft()) {
				addEntry(data, rootNode.left);
				rootNode.left = rebalance(rootNode.left);
			} else
				rootNode.left = new AVLNode(data);
		} else { // right into right subtree
			if (rootNode.hasRight()) {
				addEntry(data, rootNode.right);
				rootNode.right = rebalance(rootNode.right);
			} else
				rootNode.right = new AVLNode(data);
		}
	}

	private AVLNode rebalance(AVLNode nodeN) {
		int diff = getHeightDifference(nodeN);
		if (diff > 1) { // addition was in node's left subtree
			if (getHeightDifference(nodeN.left) > 0)
				nodeN = rotateRight(nodeN);
			else
				nodeN = rotateLeftRight(nodeN);
		} else if (diff < -1) { // addition was in node's right subtree
			if (getHeightDifference(nodeN.right) < 0)
				nodeN = rotateLeft(nodeN);
			else
				nodeN = rotateRightLeft(nodeN);
		}
		return nodeN;
	}

	private AVLNode<T> rotateRight(AVLNode<T> N) {
		AVLNode<T> C = N.left;
		N.setLeft(C.getRight());
		C.setRight(N);
		return C;
	}

	private AVLNode<T> rotateLeft(AVLNode<T> N) {
		AVLNode<T> C = N.right;
		N.setRight(C.getLeft());
		C.setLeft(N);
		return C;
	}

	private AVLNode rotateRightLeft(AVLNode N) {
		AVLNode C = N.right;
		N.right = rotateRight(C);
		return rotateLeft(N);
	}

	private AVLNode<T> rotateLeftRight(AVLNode<T> N) {
		AVLNode<T> C = N.left;
		N.left = rotateLeft(C);
		return rotateRight(N);
	}

	public int height() {
		if (root == null)
			return 0;
		return height(root);
	}

	private int height(AVLNode<T> n) {
		if (n == null)
			return 0;
		int lh = height(n.left);
		int rh = height(n.right);
		return 1 + Math.max(lh, rh);
	}

	public int getHeightDifference(AVLNode<T> n) {
		return (n == null) ? 0 : height(n.left) - height(n.right);
	}

	public AVLNode<T> search(T data) {
		return search(data, root);
	}

	private AVLNode<T> search(T data, AVLNode node) {
		if (node == null) {
			return null;
		}

		if (data.compareTo((T) node.data) == 0) {
			return node;
		} else if (data.compareTo((T) node.data) < 0) {
			return search(data, node.left);
		} else {
			return search(data, node.right);
		}

	}

	public void deleteNode(T data) {
		root = deleteNode(data, root);
		if (root != null)
			root = rebalance(root);
	}

	private AVLNode<T> deleteNode(T data, AVLNode<T> node) {
		// No node at current position --> go up the recursion
		if (node == null) {
			return null;
		}

		// Traverse the tree to the left or right depending on the key
		if (data.compareTo((T) node.getData()) < 0) {
			node.left = deleteNode(data, node.left);
		} else if (data.compareTo((T) node.data) > 0) {
			node.right = deleteNode(data, node.right);
		}

		// At this point, "node" is the node to be deleted

		// Node has no children --> just delete it
		else if (node.left == null && node.right == null) {
			node = null;
		}

		// Node has only one child --> replace node by its single child
		else if (node.left == null) {
			node = node.right;
		} else if (node.right == null) {
			node = node.left;
		}

		// Node has two children
		else {
			deleteNodeWithTwoChildren(node);

		}

		return node;
	}

	private void deleteNodeWithTwoChildren(AVLNode<T> node) {
		// Find minimum node of right subtree ("inorder successor" of current node)
		AVLNode<T> inOrderSuccessor = findMinimum(node.right);

		// Copy inorder successor's data to current node
		node.data = inOrderSuccessor.data;

		// Delete inorder successor recursively
		node.right = deleteNode((T) inOrderSuccessor.data, node.right);
	}

	private AVLNode<T> findMinimum(AVLNode<T> node) {
		while (node.left != null) {
			node = node.left;
		}
		return node;
	}

	public void inOrderTraverse() {
		inOrderTraverse(root);
	}

	private void inOrderTraverse(AVLNode<T> root) {
		if (root != null) {
			if (root.hasLeft())
				inOrderTraverse(root.getLeft());
			System.out.print(root.getData() + " ");
			if (root.hasRight())
				inOrderTraverse(root.getRight());
		} else
			System.out.println("Tree is Empty!!");
	}

	public void preOrderTraverse() {
		preOrderTraverse(root);
	}

	private void preOrderTraverse(AVLNode<T> root) {
		if (root != null) {
			System.out.print(root.getData() + " ");
			if (root.hasLeft())
				preOrderTraverse(root.getLeft());
			if (root.hasRight())
				preOrderTraverse(root.getRight());
		}
	}

//	public String postOrderTraverse() {
//		String s = "\n";
//		s += postOrderTraverse(root, s);
//		return s;
//	}
//
//	private String postOrderTraverse(AVLNode<T> root, String s) {
//		if (root != null) {
//			if (root.hasLeft())
//				postOrderTraverse(root.getLeft(), s);
//			if (root.hasRight())
//				postOrderTraverse(root.getRight(), s);
//			return s += root.getData() + " ";
//		}
//		return s;
//
//	}
	public String postOrderTraverse() {
		String s = postOrderTraverse(root);
		return s;
	}

	private String postOrderTraverse(AVLNode<T> root) {
		String result = "";

		if (root != null) {

			if (root.hasRight())
				result += postOrderTraverse(root.getRight());

			result += root.getData() + " ";
			if (root.hasLeft())
				result += postOrderTraverse(root.getLeft());
		}

		return result;
	}

	public String levelOrderTraverse() {
		return levelOrderTraverse(root);
	}

	private String levelOrderTraverse(AVLNode<T> root) {
		Queue<AVLNode<T>> queue = new ConcurrentLinkedQueue<>();
		String s = "\n";
		if (root != null) {
			queue.add(root);
			while (!queue.isEmpty()) {
				if (queue.peek().hasLeft())
					queue.add(queue.peek().getLeft());
				if (queue.peek().hasRight())
					queue.add(queue.peek().getRight());
				s += (queue.poll().data + "\n");
			}
			return s;
		} else
			return "Tree is Empty!!!!\n";

	}

	public int size() {
		return size(root);
	}

	private int size(AVLNode<T> root) {
		if (root == null) {
			return 0;
		} else {
			int leftSize = size(root.getLeft());
			int rightSize = size(root.getRight());
			return 1 + leftSize + rightSize;
		}
	}

	public static void main(String[] s) {
		AVLTree<Integer> avl = new AVLTree();
		for (int i = 1; i < 293; i++)
			avl.insert(i);
		System.out.println(avl.levelOrderTraverse());
		System.out.println(avl.height());
	}
}