package sort;

public class SortStrategy<T extends Comparable<? super T>> {
	
	/**
	 * �������������1��ʼ���
	 * @param array
	 * @param lastIndex Ҫ��������һ��Ԫ�ص�λ��
	 */
	public void insertSort(T[] array, int lastIndex) {
		for(int i=2;i<=lastIndex;i++) {
			array[0]=array[i];//���λ
			int j;
			for(j=i;array[0].compareTo(array[j-1])<0;j--) {
				array[j]=array[j-1];
			}
			array[j]=array[0];
		}
	}
	
	public void shellSort(T[] array, int lastIndex) {
		for(int gap= lastIndex/2;gap>0; gap/=2) {//ȷ����ͬ����������
			for(int i=gap+1;i<=lastIndex;i++){//�ӵ�һ���������ֵ��ʼ��������е�ֵ
				T tmp=array[i];
				int j;
				for(j=i; j>gap&&tmp.compareTo(array[j-gap])<0; j-=gap) 
					array[j]=array[j-gap];
				array[j]=tmp;
			}
		}
	}
	
	public void heapSort(T[] array, int lastIndex) {
		buildHeap(array,lastIndex);//��С���������������
		T tmp;
		for(int i=lastIndex;i>1;i--) {
			tmp=array[i];
			array[i]=array[1]; //���׽ڵ��Ƶ�1���
			array[1]=tmp;//������һ���ڵ�
			percDown(1, array, i-1);
		}
	}
	
	private void buildHeap(T[] array, int lastIndex) {
		for(int i=lastIndex/2;i>0;i--)
			percDown(i,array,lastIndex);
	}

	private void percDown(int i, T[] array, int lastIndex) {
		T tmp=array[i];
		int child;
		while(i*2<=lastIndex) {
			child =i*2;
			if(child!=lastIndex&&array[child].compareTo(array[child+1])<0)
				child++;
			if(tmp.compareTo(array[child])<0)
				array[i]=array[child];
			else
				break;
			i=child;
		}
		array[i]=tmp;
		
	}
	
	public void quickSort(T[] array, int lastIndex) {
		quickSort(array, 1, lastIndex);
	}
	
	public void quickSort(T[] array, int left, int right) {//Ԫ�ش�1��ʼ���
		if(left<right) {
			int low=left;
			int high=right;
			T pivot=array[low];
			while(low<high) {
				while(low<high&&array[high].compareTo(pivot)>0) 
					high--;//��ָ������
				array[low]=array[high];
				while(low<high&&array[low].compareTo(pivot)<0)
					low++;//��ߵĽ�С������
				array[high]=array[low];
			}
			array[low]=pivot;
			quickSort(array, left, low-1);
			quickSort(array, low+1, right);
		}
	}
	
	public void mergeSort(T[] array, int lastIndex) {
		T[] tmpArray=(T[]) new Comparable[lastIndex+1];
		mergeSort(array, tmpArray, 1, lastIndex);
	}
	
	void mergeSort(T[] array, T[] tmpArray, int left, int right) {
		if(left<right) {
			int center = (left+right)/2;
			mergeSort(array, tmpArray, left, center);
			mergeSort(array, tmpArray, center+1, right);
			merge(array,tmpArray,left,center+1,right);
		}
	}
	
	/**
	 * �ϲ����ڵ�����������õ�����
	 * @param array ԭ����
	 * @param tmpArray ��ʱ���飬����ÿ�εݹ�����½�����ʱ����
	 * @param leftPos ��߿�ʼλ��
	 * @param rightPos �ұ߿�ʼλ�ã���߽���λ��ΪleftPos-1
	 * @param rightEnd �ұ߽���λ��
	 */
	public void merge(T[] array, T[] tmpArray, int leftPos, int rightPos, int rightEnd ) {
		int leftEnd=rightPos-1;
		int curPosition=leftPos;
		int numElements=rightEnd-leftPos+1;
		
		while(leftPos<=leftEnd && rightPos<=rightEnd) {
			if(array[leftPos].compareTo(array[rightPos])<=0)
				tmpArray[curPosition++]=array[leftPos++];
			else
				tmpArray[curPosition++]=array[rightPos++];
		}
		
		while(leftPos<=leftEnd)
			tmpArray[curPosition++]=array[leftPos++];
		while(rightPos<=rightEnd)
			tmpArray[curPosition++]=array[rightPos++];
		
		for(int i=0;i<numElements;i++,rightEnd--)
			array[rightEnd]=tmpArray[rightEnd];
	}

	public void printArray(T[] array, int lastIndex) {
		System.out.println("�����Ľ���ǣ�");
		for(int i=1;i<=lastIndex;i++) {
			System.out.print(array[i]+"	");
		}
		System.out.println();
	}
}
