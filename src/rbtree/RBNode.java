package rbtree;

public class RBNode<T> {
	T element;
	RBNode<T> left;
	RBNode<T> right;
	RBNode<T> parent;
	byte color;//0�����ɫ��1�����ɫ
	
	public RBNode(T x) {
		this.element=x;
	}
	
}

