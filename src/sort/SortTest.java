package sort;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class SortTest {
	final static int size=1001;
	static Integer testArray[];
	static {
		testArray=new Integer[size];
		Random random=new Random();
		for(int i=1;i<size;i++) {
			testArray[i]=random.nextInt(1000000);
		}
	}

	@Test
	public void test() {
		Integer[] test= {0,34,8,64,51,32,21};
		long curTime=System.currentTimeMillis();
		//insertSort(testArray, size-1);
		//shellSort(testArray, size-1);
		//heapSort(testArray, size-1);
		//quickSort(test, 6);
		mergeSort(testArray, size-1);
		long endTime=System.currentTimeMillis();
		System.out.println("花费的时间为："+((endTime-curTime)/1000)+" s");
	}
	
	public void mergeSort(Integer[] array, int lastIndex) {
		SortStrategy<Integer> sortStrategy=new SortStrategy<>();
		sortStrategy.mergeSort(array, lastIndex);
		sortStrategy.printArray(array, lastIndex);
	}
	
	public void quickSort(Integer[] array,int lastIndex) {
		SortStrategy<Integer> sortStrategy=new SortStrategy<>();
		sortStrategy.quickSort(array, lastIndex);
		sortStrategy.printArray(array, lastIndex);
	}
	
	public void heapSort(Integer[] array, int lastIndex) {
		SortStrategy<Integer> sortStrategy=new SortStrategy<>();
		sortStrategy.heapSort(array, lastIndex);
		sortStrategy.printArray(array, lastIndex);
	}
	
	public void shellSort(Integer[] array, int lastIndex) {
		SortStrategy<Integer> sortStrategy=new SortStrategy<>();
		sortStrategy.shellSort(array, lastIndex);
		sortStrategy.printArray(array, lastIndex);
	}
	
	public void insertSort(Integer[] array, int lastIndex) {
		SortStrategy<Integer>sortStrategy=new SortStrategy<>();
		sortStrategy.insertSort(array, lastIndex);
		//sortStrategy.printArray(array, lastIndex);
	}

}
