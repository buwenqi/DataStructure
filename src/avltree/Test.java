package avltree;

import java.util.LinkedList;
import java.util.Queue;

public class Test {

	public static void main(String[] args) {
		int MAX=31;
		long current=System.currentTimeMillis();
		AvlTree<Integer>avlTree=new AvlTree<>();
		for(int i=1;i<=MAX;i++) {
			avlTree.insertRecursive(i);
		}
		avlTree.remove(8);
		long end=System.currentTimeMillis();
		System.out.println("递归建立平衡二叉树时间："+(end-current)+"ms");
		//层序遍历按层打印该树
		Queue<AvlNode<Integer>>queue=new LinkedList<>();
		int cur=1;//当前层需要遍历的个数
		int next=0;//下一次需要遍历的个数
		queue.offer(avlTree.getRoot());
		while(!queue.isEmpty()) {
			AvlNode<Integer>curNode=queue.poll();
			System.out.print(curNode.element+"  ");
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
