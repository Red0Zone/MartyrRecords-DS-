package Martyrs;

import java.text.SimpleDateFormat;
import java.util.Date;

import Stack.LinkedStack;
import Stack.Node;

public class DateStack implements Comparable<DateStack> {
	Date date;
	LinkedStack<Martyrs> stack = new LinkedStack<Martyrs>();

	public DateStack(Date date) {
		this.date = date;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public LinkedStack<Martyrs> getStack() {
		return stack;
	}

	public void setStack(LinkedStack<Martyrs> stack) {
		this.stack = stack;
	}

	@Override
	public int compareTo(DateStack o) {
		return this.date.compareTo(o.date);
	}

	@Override
	public boolean equals(Object o) {
		return this.date.equals(((DateStack) o).date);
	}

	@Override
	public String toString() {
//		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

		// TODO Auto-generated method stub
		return "\n" + printStack(stack);
	}

	private String printStack(LinkedStack<Martyrs> stack) {
		Node data = null;
		if (stack.peek() != null) {
			data = stack.pop();
			String s = data.getElement().toString() + "\n" + printStack(stack);
			stack.push((Martyrs) data.getElement());
			return s;
		}
		return "";
	}

	public void DeleteFromStack(Martyrs martyr) {
		Node<Martyrs> temp = stack.pop();
		if (temp == null)
			return;
		if (temp.getElement().compareTo(martyr) == 0)
			return;
		DeleteFromStack(martyr);
		stack.push(temp.getElement());
	}

	public String getDateString() {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		return sdf.format(date);
	}

//	public void updateStackLocation(String s1) {
//		Node<Martyrs> data = null;
//		if (stack.peek() != null) {
//			data = stack.pop();
//			data.getElement().setLocation(s1);
//			updateStackLocation(s1);
//			stack.push((Martyrs) data.getElement());
//		}
//	}

}
