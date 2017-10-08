package rbtree;

import java.util.LinkedList;
import java.util.Queue;

import avltree.AvlNode;

public class Test {
	public static void main(String[] args) {
		Integer[] insertElements=new Integer[] {10,85,15,70,20,60,30,50,65,80,90,40,5,55};
		RBTree<Integer> myRbTree=new RBTree<>();
		for(int i=0;i<14;i++) {
			myRbTree.insert(insertElements[i]);
		}
		//������������ӡ����
		Queue<RBNode<Integer>>queue=new LinkedList<>();
		int cur=1;//��ǰ����Ҫ�����ĸ���
		int next=0;//��һ����Ҫ�����ĸ���
		queue.offer(myRbTree.root);
		while(!queue.isEmpty()) {
			RBNode<Integer>curNode=queue.poll();
			System.out.print(curNode.element+"-"+curNode.color+" ");
			cur--;
			if(curNode.left!=null) {
				queue.offer(curNode.left);
				next++;
			}
			if(curNode.right!=null) {
				queue.offer(curNode.right);
				next++;
			}
			
			if(cur==0) {
				System.out.println();
				cur=next;
				next=0;
			}
		}
	}

}
