package rbtree;

public class RBNode<T> {
	T element;
	RBNode<T> left;
	RBNode<T> right;
	RBNode<T> parent;
	byte color;//0代表红色，1代表黑色
	
	public RBNode(T x) {
		this.element=x;
	}
	
}

