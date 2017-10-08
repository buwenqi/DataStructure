package rbtree;

public class RBTree<T extends Comparable<T>> {
	RBNode<T> root;
	RBNode<T> nullNode;
	public RBTree() {
		nullNode=new RBNode<T>(null);
		nullNode.color=1;//nullNode颜色设置成黑色
		root=nullNode;
	}
	
	public RBNode<T>getNode(T x){
		RBNode<T> newNode=new RBNode<T>(x);
		newNode.left=nullNode;
		newNode.right=nullNode;
		newNode.color=0;//新节点出生颜色为红色
		return newNode;
	}
	
	/**
	 * 旋转父节点p的有儿子到p的位置
	 * @param p
	 * @return 
	 */
	public void rotateWithRightChild(RBNode<T> p){
		RBNode<T> x=p.right;
		RBNode<T> g=p.parent;
		//p的右换指，同时改父指针
		p.right=x.left;
		x.left.parent=p;
		//x的左换指，同时改父指针
		x.left=p;
		p.parent=x;
		
		if(p==root) {
			root=x;//旋转后换新根
			root.parent=nullNode;
		}
		else if(x.element.compareTo(g.element)<0) {
			g.left=x;
			x.parent=g;
		}else {
			g.right=x;
			x.parent=g;
		}
	}
	
	public void rotateWithLeftChild(RBNode<T> p){
		RBNode<T> x=p.left;
		RBNode<T> g=p.parent;
		p.left=x.right;
		x.right.parent=p;
		
		x.right=p;
		p.parent=x;
		
		if(p==root) {
			root=x;//旋转后换新根
			root.parent=nullNode;//注意新根的父节点也换掉
		}
		else if(x.element.compareTo(g.element)<0) {
			g.left=x;
			x.parent=g;
		}else {
			g.right=x;
			x.parent=g;
		}
	}
	
	/**
	 * 输入一个节点值为x到红黑树根root中
	 * @param x
	 */
	public void insert(T x) {
		if(root==nullNode) {
			RBNode<T>newRoot=getNode(x);
			newRoot.color=1;//树根设置黑色
			root=newRoot;
			root.parent=nullNode;
			return;
		}
		
		RBNode<T> p,cur;
		p=cur=root;//p指向当前遍历的前一个节点，当前遍历节点cur
		while(cur!=nullNode) {
			p=cur;
			if(x.compareTo(cur.element)<0)
				cur=cur.left;
			else if(x.compareTo(cur.element)>0)
				cur=cur.right;
			else //找到重复的，不需要进行进一步操作
				return;
		}
		//此时p位置即为插入位置的父节点
		RBNode<T> newX=getNode(x);
		if(x.compareTo(p.element)<0) {
			p.left=newX;//记住新节点的初始颜色的红色的
			newX.parent=p;
		}else {
			p.right=newX;
			newX.parent=p;
		}
		//插入完毕，开始调整红色新插入的x节点
		if (p.color==1) //其父节点是黑色，插入完毕
			return;
		else //红红冲突，进行调整
			insertBalance(newX);
	}
	
	/**
	 * x节点和其父节点红红冲突，进行调整
	 * 保证x的父节点存在
	 * @param x
	 */
	private void insertBalance(RBNode<T> x) {
		while(x.parent.color==0) {//在父节点为红色的情况下进行调整
			RBNode<T>p=x.parent;
			if(x==root || p==root) {//p和x都右可能是根,直接将根染成黑色，返回
				root.color=1;
				return;
			}
			//g一定不为null
			RBNode<T>g=p.parent;
			RBNode<T>w;//p的兄弟节点
			if(p==g.left) {//p是左孩子
				w=g.right;
				if(w.color==0) {//p的兄弟节点为红色，仅需调整颜色即可
					w.color=1;
					p.color=1;
					g.color=0;
					x=g;
					continue;//调整节点上推
				}
				else {//w的颜色为黑色
					if(x==p.right) {//之字形双红，进行左旋
						rotateWithRightChild(p);
						x=p;
						p=x.parent;//g不变
					}
					//之字形调整后是一字型，一字型还是一字型，接下来都按一字型处理
					p.color=1;//先变色
					g.color=0;
					rotateWithLeftChild(g);
					root.color=1;//返回前置根的颜色为黑色
					return;
				}
			}else if(p==g.right) {//p位于右边
				w=g.left;
				if(w.color==0) {
					g.color=0;
					w.color=1;
					p.color=1;
					x=g;
					continue;
				}else {
					if(x==p.left) {
						rotateWithLeftChild(p);
						x=p;
						p=x.parent;
					}
					g.color=0;
					p.color=1;
					rotateWithRightChild(g);
					root.color=1;
					return;
				}
			}
			
		}
		
	}
	
	public RBNode<T> findMin(RBNode<T> node){
		RBNode<T>p=node;
		RBNode<T>k=node.left;
		while(k!=nullNode) {
			p=k;
			k=k.left;
		}
		return p;
	}
	
	public void remove(T x) {
		RBNode<T>cur=root;
		while(cur!=nullNode|| cur.element.compareTo(x)==0) {
			if(cur.element.compareTo(x)<0)
				cur=cur.left;
			else
				cur=cur.right;
		}
		RBNode<T>balanceNode;
		if(cur!=nullNode) {
			if(cur.left==nullNode&&cur.right==nullNode) {//左右子树都为null
				if(cur==cur.parent.left)
					cur.parent.left=nullNode;
				else
					cur.parent.right=nullNode;//执行了删除
				balanceNode=nullNode;
			}else {
				if(cur.left!=nullNode&&cur.right!=nullNode) {//左右子树均不为空，删除其后继
					RBNode<T> rightMin=findMin(cur.right);
					cur.element=rightMin.element;
					cur=rightMin;//重新定义新的删除点
				}
				//此时被删除点只有左孩子或仅有右孩子
				if(cur==cur.parent.left) {
					cur.parent.left=(cur.left==nullNode?cur.right:cur.left);
					//balanceNode=cur
				}
				else
					cur.parent.right=(cur.left==nullNode?cur.right:cur.left);
			}
		}
	}

}
