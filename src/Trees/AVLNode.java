	package Trees;
	public class AVLNode<T extends Comparable<T>> {
		T data;
		AVLNode left;
		AVLNode right;
	
	
		public AVLNode(T data) {
			this.data = data;
		}
	
		public void setData(T data) {
			this.data = data;
		}
	
		public T getData() {
			return data;
		}
	
		public AVLNode getLeft() {
			return left;
		}
	
		public void setLeft(AVLNode left) {
			this.left = left;
		}
	
		public AVLNode getRight() {
			return right;
		}
	
		public void setRight(AVLNode right) {
			this.right = right;
		}
	
		public boolean isLeaf() {
			return (left == null && right == null);
		}
	
		public boolean hasLeft() {
			return left != null;
		}
	
		public boolean hasRight() {
			return right != null;
		}
	
		public String toString() {
			return "[" + data + "]";
		}
	
	}