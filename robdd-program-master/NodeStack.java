import java.util.NoSuchElementException;
public class NodeStack<T> implements Stack<T> {
	private Node<T> top;
	private int size;

	public NodeStack() {
		this.top = null;
		this.size = 0;
	}
	public boolean isEmpty() {
		return (this.size == 0);
	}
	public T peek() {
		if(size > 0) {
			return this.top.getValue();
		} else {
			throw new NoSuchElementException("Stack is Empty.");
		}
	}	
	public T pop() {
		if(size > 0) {
			// Temp variables to hold top values and list
			Node<T> temp = top.getNext();
			T value = top.getValue();
			
			// Delete the top, set new top and return
			this.top.setNext(null);
			this.top = temp;
			this.size--;
			return value;
		} else {
			throw new NoSuchElementException("Stack is Empty.");
		}
	}
	public void push(T value) {
		if(this.size == 0) {
			this.top = new Node<>(value, null);
		} else {
			this.top = new Node<>(value, this.top);
		}
		this.size++;
	}

	public int size() {
		return this.size;
	}
}