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
	 * ֱ����������
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
		for(int i=0;i<array.length;i++) {//�Ѵ�1��ʼ��
			array[i]=oddArray[i];
		}
	}
	
	/**
	 * ����һ��Ԫ�أ���������
	 * @param x
	 */
	public void insert(T x) {
		if(currentSize==array.length-1) {//0λ�ÿճ�����1��ʼ��
			enlargeArray(array.length*2+1);
		}
		int hole= ++currentSize;
		array[0]= x;//��Ϊ�ڱ�ֵ����hole���1ʱ������
		while(x.compareTo(array[hole/2])<0) {//����ǰ�ڵ��С�ڸ��ڵ�
			array[hole]=array[hole/2];
			hole/=2;
		}//whileѭ��ʵ������
		array[hole]=x;//�ŵ�����λ����
	}
	
	/**
	 * ���˹���
	 * @param hole ���˵�Ԫ��λ��
	 */
	public void percolateDown(int hole) {
		int child;//���ڴ洢��Ҫ�滻��λ��
		T tmp=array[hole];//ȡ��holeλ�õ�ֵ
		while(hole*2<=currentSize) {
			child=hole*2;//����λ��
			if(child!=currentSize&&array[child+1].compareTo(array[child])<0)//�Һ��Ӵ��ڣ�������
				child++;//ѡ���С������
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
	 * ��ʼ���Ѳ��ôӵ����϶����е����ṹ��������
	 */
	public void buildHeap() {
		for(int i=currentSize/2;i>0;i--) {//�ӵ�һ����Ҷ�ӽڵ�Ľڵ��������
			percolateDown(i);
		}
	}
	
	/**
	 * ɾ���Ѷ�Ԫ��
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
