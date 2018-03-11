package packageproblem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Pack_01 {

	public static void main(String[] args) throws IOException {
		BufferedReader reader=new BufferedReader(new FileReader("resource/packinfo.txt"));
		String[] numInfo=reader.readLine().split(" ");
		int packCapacity=Integer.valueOf(numInfo[0]);
		int stuffNum=Integer.valueOf(numInfo[1]);
		Stuff[] stuffArray=new Stuff[stuffNum+1];
		
		for(int i=1;i<=stuffNum;i++) {//��1��ʼ����Ʒ
			String[] stuffInfoArray=reader.readLine().split(" ");
			int weight=Integer.valueOf(stuffInfoArray[0]);
			int value=Integer.valueOf(stuffInfoArray[1]);
			stuffArray[i]=new Stuff(weight, value);
		}
		
		int dpValue[][]=new int[stuffNum+1][packCapacity+1];
		for(int i=0;i<=packCapacity;i++)
			dpValue[0][i]=0;//�����κ���Ʒ���ܼ�ֵΪ0
		for(int i=1;i<=stuffNum;i++)
			for(int j=0;j<=packCapacity;j++) {
				//�����ǰ��������С�ڵ�j����Ʒ������,���ֵΪ��һ������ż�ֵ
				if(j<stuffArray[i].getWeight()) 
					dpValue[i][j]=dpValue[i-1][j];
				else {
					//�������ֵ�ڲ���i��Ʒ�ͷ�i��Ʒ֮��ļ�ֵ����
					dpValue[i][j]=Math.max(dpValue[i-1][j], (dpValue[i-1][j-stuffArray[i].getWeight()]+stuffArray[i].getValue()));
				}
			}
		for(int i=0;i<=stuffNum;i++) {
			for(int j=1;j<=packCapacity;j++) {
				System.out.print(dpValue[i][j]+"   ");
			}
			System.out.println();
		}
	}

}
