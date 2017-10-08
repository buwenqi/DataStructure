package heap;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.table.TableColumn;

public class BinaryHeap<T extends Comparable<? super T>> {
	private static final int Defalut_Capacity=10;
	private int currentSize;
	private T[] array;
	public BinaryHeap() {
		this(Defalut_Capacity);
	}
	public BinaryHeap(int capacity) {
		currentSize=0;
		array=(T[])new Comparable[capacity];
	}
	
	/**
	 * 直接输入数组
	 * @param tarray
	 */
	public BinaryHeap(T[] tarray) {
		currentSize=tarray.length;
		array=(T[])new Comparable[currentSize+10];
		int i=1;
		for(T item:tarray) {
			array[i++]=item;
		}
		buildHeap();
	}
	
	private void enlargeArray(int size) {
		T[] oddArray=array;
		array=(T[]) new Comparable[size];
		for(int i=0;i<array.length;i++) {//堆从1开始放
			array[i]=oddArray[i];
		}
	}
	
	/**
	 * 插入一个元素，采用上滤
	 * @param x
	 */
	public void insert(T x) {
		if(currentSize==array.length-1) {//0位置空出，从1开始放
			enlargeArray(array.length*2+1);
		}
		int hole= ++currentSize;
		array[0]= x;//作为哨兵值，在hole变成1时起作用
		while(x.compareTo(array[hole/2])<0) {//若当前节点的小于父节点
			array[hole]=array[hole/2];
			hole/=2;
		}//while循环实现上滤
		array[hole]=x;//放到最终位置上
	}
	
	/**
	 * 下滤过程
	 * @param hole 下滤的元素位置
	 */
	public void percolateDown(int hole) {
		int child;//用于存储将要替换的位置
		T tmp=array[hole];//取出hole位置的值
		while(hole*2<=currentSize) {
			child=hole*2;//左孩子位置
			if(child!=currentSize&&array[child+1].compareTo(array[child])<0)//右孩子存在，且左孩子
				child++;//选择较小的左孩子
			if(tmp.compareTo(array[child])>0) {
				array[hole]=array[child];
			}else {
				break;
			}
			hole=child;
		}
		array[hole]=tmp;
	}
	
	/**
	 * 初始建堆采用从底往上对已有的树结构进行下滤
	 */
	public void buildHeap() {
		for(int i=currentSize/2;i>0;i--) {//从第一个非叶子节点的节点进行下滤
			percolateDown(i);
		}
	}
	
	/**
	 * 删除堆顶元素
	 */
	public T deleteMin() {
		if(currentSize==0)
			throw new EmptyStackException();
		T minItem=array[1];
		array[1]=array[currentSize--];
		percolateDown(1);
		return minItem;
	}
	
	public void printHeap() {
		int cur=1;
		int next=0;
		Queue<Integer> queue=new LinkedList<>();
		queue.offer(1);
		int curIdx;
		int lchild;
		while(!queue.isEmpty()) {
			curIdx=queue.poll();
			cur--;
			System.out.print(array[curIdx]+"  ");
			lchild=curIdx*2;
			if(lchild<=currentSize) {
				queue.offer(lchild);
				next++;
			}
			if(lchild+1<=currentSize) {
				queue.offer(lchild+1);
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
