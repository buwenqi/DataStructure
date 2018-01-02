package rbtree;

public class RBTree<T extends Comparable<T>> {
	RBNode<T> root;
	RBNode<T> nullNode;//所有的叶子节点都指向它,其父节点为root,作用限制于此
	public RBTree() {
		nullNode=new RBNode<T>(null);
		nullNode.color=1;//nullNode颜色设置成黑色
		root=nullNode;
	}
	
	public RBNode<T>getNewNode(T x){
		RBNode<T> newNode=new RBNode<T>(x);
		newNode.left=nullNode;
		newNode.right=nullNode;
		newNode.color=0;//新节点出生颜色为红色
		return newNode;
	}
	
	/**
	 * 旋转父节点p的右儿子到p的位置
	 * @param p
	 * @return 
	 */
	public void rotateWithRightChild(RBNode<T> p){
		RBNode<T> x=p.right;
		RBNode<T> g=p.parent;
		//break1: p的右换指，同时改父指针
		//tip1: x.left可能为nullNode,因为在处理的过程中对nullNode的parent
		//可以是任意值，所以这里不做处理
		p.right=x.left;
		x.left.parent=p;
		//break2: x的左换指，同时改父指针
		x.left=p;
		p.parent=x;
		
		//break3: g的孩子更换为x
		//tip2: g为nullNode,说明p为root,旋转需要换新根
		if(g==nullNode) {
			root=x;//旋转后换新根
			root.parent=nullNode;
		}
		else if(g.left==p) {
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
		//break1: p的左指针更换
		// x.right可能为nullNode，不做处理可以
		p.left=x.right;
		x.right.parent=p;
		//break2: x和p的位置更换
		x.right=p;
		p.parent=x;
		
		//break3: g的孩子更换
		if(g==nullNode) {
			root=x;//旋转后换新根
			root.parent=nullNode;//注意新根的父节点也换掉
		}
		else if(g.left==p) {
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
		RBNode<T> p,cur;
		p=nullNode;//p指向cur的父节点
		cur=root;
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
		RBNode<T> newX=getNewNode(x);//新生成的节点为红色
		newX.parent=p;//即便p为nullNode也是符合的
		if(p==nullNode) {
			root=newX;
			root.color=1;
			return;//如果是根的话可以直接返回
		}else if(x.compareTo(p.element)<0) {
			p.left=newX;
		}else {
			p.right=newX;
		}
		//插入完毕，开始调整红色新插入的x节点
		if (p.color==1) //其父节点是黑色，插入完毕
			return;
		else //红红冲突，进行调整
			insertBalance(newX);
	}
	
	/**
	 * x节点和其父节点红红冲突，进行调整
	 * @param x
	 */
	private void insertBalance(RBNode<T> x) {
		while(x.parent.color==0) {//在父节点为红色的情况下进行调整
			RBNode<T>p=x.parent;
			//g一定不为null，因为root为黑色，nullNode也是黑色的，所以x必是三层及一下的
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
						//更改相应的引用
						x=p;
						p=x.parent;//g不变
					}
					//之字形调整后是一字型，一字型还是一字型，接下来都按一字型处理
					p.color=1;//先变色
					g.color=0;
					rotateWithLeftChild(g);
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
				}
			}
		}
		
		//最后置root为黑色，w为红或黑都有可能结束循环
		root.color=1;
		
	}
	/**
	 * 寻找一个节点左子树的最小值
	 * @param node
	 * @return
	 */
	public RBNode<T> findRightMin(RBNode<T> node){
		RBNode<T>p=node;
		RBNode<T>k=node.right;
		while(k!=nullNode) {
			p=k;
			k=k.left;
		}
		return p;
	}
	
	/**
	 * 将y移植到x的位置,只添加y和x父节点之间的关系
	 * @param x
	 * @param y
	 */
	public void transplant(RBNode<T> x, RBNode<T>y) {
		//两个方面的链表需要变动,y的父节点变成x的父节点，x父节点的孩子变为y
		if(x.parent==nullNode) {
			root=y;
		}else if(x==x.parent.left) {
			x.parent.left=y;
		}else {
			x.parent.right=y;
		}
		y.parent=x.parent;
	}
	
	public void remove(T x) {
		RBNode<T>cur=root;
		//查找待删除节点
		while(cur!=nullNode &&cur.element.compareTo(x)!=0) {
			if(x.compareTo(cur.element)<0)
				cur=cur.left;
			else
				cur=cur.right;
		}
		
		RBNode<T>balanceNode;//记录删除节点的孩子节点，也就是待调整节点
		int deleteColor;
		if(cur!=nullNode) {
			if(cur.left==nullNode) {//左子树为空和左右子树都为空一并处理
				balanceNode=cur.right;
				transplant(cur,cur.right);
				deleteColor=cur.color;
			}else if(cur.right==nullNode){
				balanceNode=cur.left;
				transplant(cur,cur.left);
				deleteColor=cur.color;
			}else {
				RBNode<T> minNode=findRightMin(cur);
				cur.element=minNode.element;
				deleteColor=minNode.color;
				//这时候可以执行删除minNode
				if(minNode.left==nullNode) {
					balanceNode=minNode.right;
					transplant(minNode,minNode.right);
				}else {//minNode右子树为空
					balanceNode=minNode.left;
					transplant(minNode,minNode.left);
				}
			}
			
			//删除完毕，开始执行调整被删除节点的
			//如果删除的是红色节点，则不影响，只考虑删除的是黑色节点
			if(deleteColor==1)
				if(balanceNode.color==0) {
					//balanceNode为红色，则删除的黑色补充到这个节点即可
					balanceNode.color=1;
				}else {
					balanceDoubleBlack(balanceNode);
				}
				
		}
	}

	/**
	 * balanceNode为双重黑色，进行调整
	 * @param balanceNode
	 */
	private void balanceDoubleBlack(RBNode<T> balanceNode) {
		//在balanceNode不为根节点，并且颜色为黑的情况下进行调整
		while(balanceNode!=root&& balanceNode.color==1) {
			RBNode<T> p=balanceNode.parent;
			RBNode<T> w;
			if(p.left==balanceNode) {
				//balanceNode为其父节点的左孩子
				//按balanceNode的兄弟节点的颜色分两大类
				w=p.right;
				if(w.color==0) {
					//兄弟节点为红色
					w.color=1;
					p.color=0;
					rotateWithRightChild(p);
					w=p.right;//w变了
				}else {
					//兄弟节点为黑色
					//当兄弟节点的颜色为黑色的时候分为3种情况
					//1. 兄弟节点左右子还是都是黑色节点，则从两边各提一层黑
					if(w.left.color==1&&w.right.color==1) {
						w.color=0;
						balanceNode=p;
					}else {
						//2. 兄弟节点的左孩子为红色，右还是为黑色，w与w-leftchild,p构成之字形
						//在不改变黑高的情况下调整成一字型
						if(w.left.color==0&&w.right.color==1) {
							w.color=0;
							w.left.color=1;
							rotateWithLeftChild(w);
							w=p.right;
						}
						//3. 兄弟节点的右孩子为红色，p,w与w.right一字型，
						//w变成p的颜色，p变成黑色，w.right变成黑色，左旋
						w.color=p.color;
						p.color=1;
						w.right.color=1;
						rotateWithRightChild(p);
						//调整结束,最后将根节点调为黑色
						balanceNode=root;
						break;
					}
				}
				
			}else {
				//balanceNode为其父节点的右孩子
				w=p.left;
				if(w.color==0) {
					//兄弟节点为红色
					p.color=0;
					w.color=1;
					rotateWithLeftChild(p);
					w=p.left;
				}else {
					//兄弟节点为黑色
					if(w.left.color==1&& w.right.color==1) {
						w.color=0;
						balanceNode=p;
					}else {
						if(w.left.color==1&& w.right.color==0) {
							w.right.color=1;
							w.color=0;
							rotateWithRightChild(w);
							w=p.left;
						}
						w.color=p.color;
						p.color=1;
						w.left.color=1;
						rotateWithLeftChild(p);
						balanceNode=root;
						break;
					}
				}
			}
		}
		balanceNode.color=1;//防止最后的调整好的根节点为红色
	}

}
