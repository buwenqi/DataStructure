package heap;

public class Test {

	public static void main(String[] args) {
		BinaryHeap<Integer> myHeap=new BinaryHeap<>(new Integer[] {13,21,16,6,31,19,68,65,26,32});
		
		myHeap.printHeap();
		
		myHeap.deleteMin();
		
		myHeap.printHeap();
	}

}
