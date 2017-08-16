package avltree;

import java.util.Stack;

public class AvlNode<T extends Comparable<T>> {
	T element;
	AvlNode<T>left;
	AvlNode<T>right;
	int height;
	
	public AvlNode(T element){
		this.element=element;
		left=null;
		right=null;
		height=0;//Ҷ�ӽڵ�߶�Ϊ0
	}
}
