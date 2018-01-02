package rbtree;

public class RBTree<T extends Comparable<T>> {
	RBNode<T> root;
	RBNode<T> nullNode;//���е�Ҷ�ӽڵ㶼ָ����,�丸�ڵ�Ϊroot,���������ڴ�
	public RBTree() {
		nullNode=new RBNode<T>(null);
		nullNode.color=1;//nullNode��ɫ���óɺ�ɫ
		root=nullNode;
	}
	
	public RBNode<T>getNewNode(T x){
		RBNode<T> newNode=new RBNode<T>(x);
		newNode.left=nullNode;
		newNode.right=nullNode;
		newNode.color=0;//�½ڵ������ɫΪ��ɫ
		return newNode;
	}
	
	/**
	 * ��ת���ڵ�p���Ҷ��ӵ�p��λ��
	 * @param p
	 * @return 
	 */
	public void rotateWithRightChild(RBNode<T> p){
		RBNode<T> x=p.right;
		RBNode<T> g=p.parent;
		//break1: p���һ�ָ��ͬʱ�ĸ�ָ��
		//tip1: x.left����ΪnullNode,��Ϊ�ڴ���Ĺ����ж�nullNode��parent
		//����������ֵ���������ﲻ������
		p.right=x.left;
		x.left.parent=p;
		//break2: x����ָ��ͬʱ�ĸ�ָ��
		x.left=p;
		p.parent=x;
		
		//break3: g�ĺ��Ӹ���Ϊx
		//tip2: gΪnullNode,˵��pΪroot,��ת��Ҫ���¸�
		if(g==nullNode) {
			root=x;//��ת���¸�
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
		//break1: p����ָ�����
		// x.right����ΪnullNode�������������
		p.left=x.right;
		x.right.parent=p;
		//break2: x��p��λ�ø���
		x.right=p;
		p.parent=x;
		
		//break3: g�ĺ��Ӹ���
		if(g==nullNode) {
			root=x;//��ת���¸�
			root.parent=nullNode;//ע���¸��ĸ��ڵ�Ҳ����
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
	 * ����һ���ڵ�ֵΪx���������root��
	 * @param x
	 */
	public void insert(T x) {
		RBNode<T> p,cur;
		p=nullNode;//pָ��cur�ĸ��ڵ�
		cur=root;
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
		RBNode<T> newX=getNewNode(x);//�����ɵĽڵ�Ϊ��ɫ
		newX.parent=p;//����pΪnullNodeҲ�Ƿ��ϵ�
		if(p==nullNode) {
			root=newX;
			root.color=1;
			return;//����Ǹ��Ļ�����ֱ�ӷ���
		}else if(x.compareTo(p.element)<0) {
			p.left=newX;
		}else {
			p.right=newX;
		}
		//������ϣ���ʼ������ɫ�²����x�ڵ�
		if (p.color==1) //�丸�ڵ��Ǻ�ɫ���������
			return;
		else //����ͻ�����е���
			insertBalance(newX);
	}
	
	/**
	 * x�ڵ���丸�ڵ����ͻ�����е���
	 * @param x
	 */
	private void insertBalance(RBNode<T> x) {
		while(x.parent.color==0) {//�ڸ��ڵ�Ϊ��ɫ������½��е���
			RBNode<T>p=x.parent;
			//gһ����Ϊnull����ΪrootΪ��ɫ��nullNodeҲ�Ǻ�ɫ�ģ�����x�������㼰һ�µ�
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
						//������Ӧ������
						x=p;
						p=x.parent;//g����
					}
					//֮���ε�������һ���ͣ�һ���ͻ���һ���ͣ�����������һ���ʹ���
					p.color=1;//�ȱ�ɫ
					g.color=0;
					rotateWithLeftChild(g);
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
				}
			}
		}
		
		//�����rootΪ��ɫ��wΪ���ڶ��п��ܽ���ѭ��
		root.color=1;
		
	}
	/**
	 * Ѱ��һ���ڵ�����������Сֵ
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
	 * ��y��ֲ��x��λ��,ֻ���y��x���ڵ�֮��Ĺ�ϵ
	 * @param x
	 * @param y
	 */
	public void transplant(RBNode<T> x, RBNode<T>y) {
		//���������������Ҫ�䶯,y�ĸ��ڵ���x�ĸ��ڵ㣬x���ڵ�ĺ��ӱ�Ϊy
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
		//���Ҵ�ɾ���ڵ�
		while(cur!=nullNode &&cur.element.compareTo(x)!=0) {
			if(x.compareTo(cur.element)<0)
				cur=cur.left;
			else
				cur=cur.right;
		}
		
		RBNode<T>balanceNode;//��¼ɾ���ڵ�ĺ��ӽڵ㣬Ҳ���Ǵ������ڵ�
		int deleteColor;
		if(cur!=nullNode) {
			if(cur.left==nullNode) {//������Ϊ�պ�����������Ϊ��һ������
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
				//��ʱ�����ִ��ɾ��minNode
				if(minNode.left==nullNode) {
					balanceNode=minNode.right;
					transplant(minNode,minNode.right);
				}else {//minNode������Ϊ��
					balanceNode=minNode.left;
					transplant(minNode,minNode.left);
				}
			}
			
			//ɾ����ϣ���ʼִ�е�����ɾ���ڵ��
			//���ɾ�����Ǻ�ɫ�ڵ㣬��Ӱ�죬ֻ����ɾ�����Ǻ�ɫ�ڵ�
			if(deleteColor==1)
				if(balanceNode.color==0) {
					//balanceNodeΪ��ɫ����ɾ���ĺ�ɫ���䵽����ڵ㼴��
					balanceNode.color=1;
				}else {
					balanceDoubleBlack(balanceNode);
				}
				
		}
	}

	/**
	 * balanceNodeΪ˫�غ�ɫ�����е���
	 * @param balanceNode
	 */
	private void balanceDoubleBlack(RBNode<T> balanceNode) {
		//��balanceNode��Ϊ���ڵ㣬������ɫΪ�ڵ�����½��е���
		while(balanceNode!=root&& balanceNode.color==1) {
			RBNode<T> p=balanceNode.parent;
			RBNode<T> w;
			if(p.left==balanceNode) {
				//balanceNodeΪ�丸�ڵ������
				//��balanceNode���ֵܽڵ����ɫ��������
				w=p.right;
				if(w.color==0) {
					//�ֵܽڵ�Ϊ��ɫ
					w.color=1;
					p.color=0;
					rotateWithRightChild(p);
					w=p.right;//w����
				}else {
					//�ֵܽڵ�Ϊ��ɫ
					//���ֵܽڵ����ɫΪ��ɫ��ʱ���Ϊ3�����
					//1. �ֵܽڵ������ӻ��Ƕ��Ǻ�ɫ�ڵ㣬������߸���һ���
					if(w.left.color==1&&w.right.color==1) {
						w.color=0;
						balanceNode=p;
					}else {
						//2. �ֵܽڵ������Ϊ��ɫ���һ���Ϊ��ɫ��w��w-leftchild,p����֮����
						//�ڲ��ı�ڸߵ�����µ�����һ����
						if(w.left.color==0&&w.right.color==1) {
							w.color=0;
							w.left.color=1;
							rotateWithLeftChild(w);
							w=p.right;
						}
						//3. �ֵܽڵ���Һ���Ϊ��ɫ��p,w��w.rightһ���ͣ�
						//w���p����ɫ��p��ɺ�ɫ��w.right��ɺ�ɫ������
						w.color=p.color;
						p.color=1;
						w.right.color=1;
						rotateWithRightChild(p);
						//��������,��󽫸��ڵ��Ϊ��ɫ
						balanceNode=root;
						break;
					}
				}
				
			}else {
				//balanceNodeΪ�丸�ڵ���Һ���
				w=p.left;
				if(w.color==0) {
					//�ֵܽڵ�Ϊ��ɫ
					p.color=0;
					w.color=1;
					rotateWithLeftChild(p);
					w=p.left;
				}else {
					//�ֵܽڵ�Ϊ��ɫ
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
		balanceNode.color=1;//��ֹ���ĵ����õĸ��ڵ�Ϊ��ɫ
	}

}
