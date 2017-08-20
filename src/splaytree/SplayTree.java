package splaytree;

import avltree.AvlNode;

public class SplayTree<T extends Comparable<T>> {
	BinaryNode<T> root;
	public SplayTree() {
		root=null;
	}
	
	public BinaryNode<T> getRoot(){
		return root;
	}
	
	private BinaryNode<T> rotateWithLeftChild(BinaryNode<T> k1){
		BinaryNode<T> k2=k1.left;
		k1.left=k2.right;
		k2.right=k1;
		return k2;
	}
	
	private BinaryNode<T> rotateWithRightChild(BinaryNode<T> k1){
		BinaryNode<T> k2=k1.right;
		k1.right=k2.left;
		k2.left=k1;
		return k2;
		
	}
	
	public BinaryNode<T> splay(T x, BinaryNode<T> root){
		BinaryNode< T> headerNode=new BinaryNode<>(null);//ͷ�ڵ㣬��㶨����ֵ
		BinaryNode<T>rightHeader=headerNode;
		BinaryNode<T>leftHeader=headerNode;
		BinaryNode<T>leftMax=leftHeader;
		BinaryNode<T>rightMin=rightHeader;
		if(root==null)
			return root;
		
		while(true) {
			if(x.compareTo(root.element)<0) {
				if(root.left==null)
					break;
				if(x.compareTo(root.left.element)<0)
					root=rotateWithLeftChild(root);
				rightMin.left=root;
				rightMin=root;
				root=root.left;
			}else if(x.compareTo(root.element)>0) {
				if(root.right==null)
					break;
				if(x.compareTo(root.right.element)>0)
					root=rotateWithRightChild(root);
				leftMax.right=root;
				leftMax=root;
				root=root.right;
			}else //���ڵ����������ѭ��
				break;
		}
		//��ʼ�ϲ�
		leftMax.right=root.left;
		rightMin.left=root.right;
		root.left=leftHeader.right;
		root.right=rightHeader.left;
		return root;
	}
	
	public BinaryNode<T> insert(T x, BinaryNode<T> root){
		if(root==null)
			return new BinaryNode<T>(x);
		root=splay(x, root);
		BinaryNode<T>newRoot;
		if(x.compareTo(root.element)>0) {
			newRoot=new BinaryNode<T>(x);
			newRoot.left=root;
			newRoot.right=root.right;
			root.right=null;
		}else if(x.compareTo(root.element)<0){
			newRoot=new BinaryNode<T>(x);
			newRoot.right=root;
			newRoot.left=root.left;
			root.left=null;
		}else {
			newRoot=root;
		}
		return newRoot;
	}
	
	public void insert(T x) {
		root=insert(x,root);
	}
	
	public void remove(T x) {
		BinaryNode<T> newRoot=splay(x,root);
		if(x.compareTo(newRoot.element)!=0) {
			root= newRoot;
		}else {//�����ڵ����
			if(newRoot.left==null) {//����������null
				newRoot=newRoot.right;
			}else {
				BinaryNode<T> leftMax=newRoot.left;
				while(leftMax.right!=null) {
					leftMax=leftMax.right;
				}
				leftMax.right=newRoot.right;
				newRoot=newRoot.left;
			}
			root=newRoot;
		}
	}
}
