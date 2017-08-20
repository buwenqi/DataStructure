package splaytree;

import java.util.LinkedList;
import java.util.Queue;

public class RemoveTest {

	public static void main(String[] args) {
		SplayTree<Integer>splayTree=new SplayTree<>();
		for(int i=1;i<8;i++) {
			splayTree.insert(i);
		}
		
		splayTree.insert(1);
		splayTree.remove(2);
		Queue<BinaryNode<Integer>> queue=new LinkedList<>();
		queue.offer(splayTree.getRoot());
		int cur=1;
		int next=0;
		while(!queue.isEmpty()) {
			BinaryNode<Integer> curNode=queue.poll();
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
