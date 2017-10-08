package rbtree;

public class RBTree<T extends Comparable<T>> {
	RBNode<T> root;
	RBNode<T> nullNode;
	public RBTree() {
		nullNode=new RBNode<T>(null);
		nullNode.color=1;//nullNode��ɫ���óɺ�ɫ
		root=nullNode;
	}
	
	public RBNode<T>getNode(T x){
		RBNode<T> newNode=new RBNode<T>(x);
		newNode.left=nullNode;
		newNode.right=nullNode;
		newNode.color=0;//�½ڵ������ɫΪ��ɫ
		return newNode;
	}
	
	/**
	 * ��ת���ڵ�p���ж��ӵ�p��λ��
	 * @param p
	 * @return 
	 */
	public void rotateWithRightChild(RBNode<T> p){
		RBNode<T> x=p.right;
		RBNode<T> g=p.parent;
		//p���һ�ָ��ͬʱ�ĸ�ָ��
		p.right=x.left;
		x.left.parent=p;
		//x����ָ��ͬʱ�ĸ�ָ��
		x.left=p;
		p.parent=x;
		
		if(p==root) {
			root=x;//��ת���¸�
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
			root=x;//��ת���¸�
			root.parent=nullNode;//ע���¸��ĸ��ڵ�Ҳ����
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
	 * ����һ���ڵ�ֵΪx���������root��
	 * @param x
	 */
	public void insert(T x) {
		if(root==nullNode) {
			RBNode<T>newRoot=getNode(x);
			newRoot.color=1;//�������ú�ɫ
			root=newRoot;
			root.parent=nullNode;
			return;
		}
		
		RBNode<T> p,cur;
		p=cur=root;//pָ��ǰ������ǰһ���ڵ㣬��ǰ�����ڵ�cur
		while(cur!=nullNode) {
			p=cur;
			if(x.compareTo(cur.element)<0)
				cur=cur.left;
			else if(x.compareTo(cur.element)>0)
				cur=cur.right;
			else //�ҵ��ظ��ģ�����Ҫ���н�һ������
				return;
		}
		//��ʱpλ�ü�Ϊ����λ�õĸ��ڵ�
		RBNode<T> newX=getNode(x);
		if(x.compareTo(p.element)<0) {
			p.left=newX;//��ס�½ڵ�ĳ�ʼ��ɫ�ĺ�ɫ��
			newX.parent=p;
		}else {
			p.right=newX;
			newX.parent=p;
		}
		//������ϣ���ʼ������ɫ�²����x�ڵ�
		if (p.color==1) //�丸�ڵ��Ǻ�ɫ���������
			return;
		else //����ͻ�����е���
			insertBalance(newX);
	}
	
	/**
	 * x�ڵ���丸�ڵ����ͻ�����е���
	 * ��֤x�ĸ��ڵ����
	 * @param x
	 */
	private void insertBalance(RBNode<T> x) {
		while(x.parent.color==0) {//�ڸ��ڵ�Ϊ��ɫ������½��е���
			RBNode<T>p=x.parent;
			if(x==root || p==root) {//p��x���ҿ����Ǹ�,ֱ�ӽ���Ⱦ�ɺ�ɫ������
				root.color=1;
				return;
			}
			//gһ����Ϊnull
			RBNode<T>g=p.parent;
			RBNode<T>w;//p���ֵܽڵ�
			if(p==g.left) {//p������
				w=g.right;
				if(w.color==0) {//p���ֵܽڵ�Ϊ��ɫ�����������ɫ����
					w.color=1;
					p.color=1;
					g.color=0;
					x=g;
					continue;//�����ڵ�����
				}
				else {//w����ɫΪ��ɫ
					if(x==p.right) {//֮����˫�죬��������
						rotateWithRightChild(p);
						x=p;
						p=x.parent;//g����
					}
					//֮���ε�������һ���ͣ�һ���ͻ���һ���ͣ�����������һ���ʹ���
					p.color=1;//�ȱ�ɫ
					g.color=0;
					rotateWithLeftChild(g);
					root.color=1;//����ǰ�ø�����ɫΪ��ɫ
					return;
				}
			}else if(p==g.right) {//pλ���ұ�
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
			if(cur.left==nullNode&&cur.right==nullNode) {//����������Ϊnull
				if(cur==cur.parent.left)
					cur.parent.left=nullNode;
				else
					cur.parent.right=nullNode;//ִ����ɾ��
				balanceNode=nullNode;
			}else {
				if(cur.left!=nullNode&&cur.right!=nullNode) {//������������Ϊ�գ�ɾ������
					RBNode<T> rightMin=findMin(cur.right);
					cur.element=rightMin.element;
					cur=rightMin;//���¶����µ�ɾ����
				}
				//��ʱ��ɾ����ֻ�����ӻ�����Һ���
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
