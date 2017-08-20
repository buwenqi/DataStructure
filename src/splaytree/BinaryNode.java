package splaytree;

public class BinaryNode<T> {
	T element;
	BinaryNode<T>left;
    BinaryNode<T>right;
	public BinaryNode(T x) {
		this.element=x;
		left=right=null;
	}

}
