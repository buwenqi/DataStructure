package avltree;

import java.util.Stack;

public class AvlTree<T extends Comparable<T>>{
	private AvlNode<T>root;
	
	public AvlTree() {
		root=null;
	}
	
	private int height(AvlNode<T>node) {
		return node==null?-1:node.height;
	}
	private AvlNode<T> rotateWithLeftChild(AvlNode<T> k1){
		AvlNode<T> k2=k1.left;
		k1.left=k2.right;
		k2.right=k1;
		k1.height=Math.max(height(k1.left), height(k1.right))+1;
		k2.height=Math.max(height(k2.left), k1.height)+1;//k1现在是k2的右子树根
		return k2;
	}
	
	private AvlNode<T> rotateWithRightChild(AvlNode<T> k1){
		AvlNode<T> k2=k1.right;
		k1.right=k2.left;
		k2.left=k1;
		k1.height=Math.max(height(k1.left), height(k1.right))+1;
		k2.height=Math.max(k1.height, height(k2.right))+1;//k1现在是k2的左子树根
		return k2;
		
	}
	
	private AvlNode<T> doubleRotateWithLeftChild(AvlNode<T> k1){
		AvlNode<T> k2=k1.left;
		k1.left=rotateWithRightChild(k2);
		return rotateWithLeftChild(k1);
	}
	
	private AvlNode<T> doubleRotateWithRightChild(AvlNode<T> k1){
		AvlNode<T>k2=k1.right;
		k1.right=rotateWithLeftChild(k2);
		return rotateWithRightChild(k1);
	}
	
	public AvlNode<T> balance(AvlNode<T> k1){
		if(k1==null)
			return k1;
		if(height(k1.left)-height(k1.right)>1) {//左子树更高的情况
			AvlNode<T> k2=k1.left;
			if(height(k2.left)>=height(k2.right)) {//左左不平衡情况
				k1=rotateWithLeftChild(k1);
			}else {//左右不平衡情况
				k1=doubleRotateWithLeftChild(k1);
			}
		}else if(height(k1.right)-height(k1.left)>1) {//右子树更高的情况
			AvlNode<T>k2=k1.right;
			if(height(k2.right)>=height(k2.left)) {//右右不平衡的情况
				k1=rotateWithRightChild(k1);
			}else {//右左不平衡的情况
				k1=doubleRotateWithRightChild(k1);
			}
		}
		//再算一遍新根的高度，处理非不平衡的情况
		k1.height=Math.max(height(k1.left), height(k1.right))+1;
		return k1;
	}
	
	public AvlNode<T> insert(T x, AvlNode<T>root){
		Stack<AvlNode<T>> pathStack=new Stack<>();
		if(root==null)
			return new AvlNode<T>(x);
		AvlNode<T> currentNode=root;
		while(currentNode!=null) {
			if(x.compareTo(currentNode.element)<0) {
				pathStack.push(currentNode);
				currentNode=currentNode.left;
			}else if(x.compareTo(currentNode.element)>0) {
				pathStack.push(currentNode);
				currentNode=currentNode.right;
			}else {
				return root;
			}
		}
		
		currentNode=pathStack.pop();
		if(x.compareTo(currentNode.element)<0)
			currentNode.left=new AvlNode<T>(x);
		else
			currentNode.right=new AvlNode<T>(x);
		while(!pathStack.isEmpty()) {
			AvlNode<T>nextNode=pathStack.pop();
			if(nextNode.right==currentNode)
				nextNode.right=balance(currentNode);
			else if(nextNode.right==currentNode)
				nextNode.left=balance(currentNode);
			currentNode=nextNode;
		}
		//剩余最后一个 节点没有balance
		return balance(currentNode);
	}
	
	public AvlNode<T> insertRecursive(T x, AvlNode<T>root){
		if(root==null) {
			return new AvlNode<T>(x);
		}
		
		if(x.compareTo(root.element)>0)
			root.right=insertRecursive(x, root.right);
		else if(x.compareTo(root.element)<0)
			root.left=insertRecursive(x, root.left);
		return balance(root);
	}
	
	public AvlNode<T> findMin(AvlNode<T>root){
		if(root!=null) {
			while(root.left!=null)
				root=root.left;
		}
		return root;
	}
	public AvlNode<T> remove(T x, AvlNode<T>root){
		if(root==null)
			return null;
		if(x.compareTo(root.element)>0) {
			root.right=remove(x,root.right);
		}else if(x.compareTo(root.element)<0) {
			root.left=remove(x,root.left);
		}else if(root.left!=null&&root.right!=null) {
			root.element=findMin(root.right).element;
			root.right=remove(root.element,root.right);
		}else {
			root=(root.left==null?root.right:root.left);
			//这种情况涵盖了左右子树都为空和只有左子树或右子树的情况
			//如果都为空，那么会root会返回null，如果 左儿子为空，则会返回右孩子的引用
		}
		return balance(root);
		
	}
	
	public void insert(T x) {
		root=insert(x,root);
	}
	
	public void insertRecursive(T x) {
		root=insertRecursive(x, root);
	}
	
	public void remove(T x) {
		root=remove(x,root);
	}
	public AvlNode<T>getRoot(){
		return root;
	}
}
