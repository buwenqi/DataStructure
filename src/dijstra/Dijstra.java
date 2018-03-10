package dijstra;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Dijstra {

	public static void main(String[] args) {
		// ʹ�õ�ͼ
		Graph graph = null;
		// �����㵽��ǰ�ڵ�����·��
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("����ڵ�����");
			int vexNum = sc.nextInt();
			sc.nextLine();
			System.out.println("����ڵ������Կո������");
			String vexNames = sc.nextLine();
			String[] vexNameArray = vexNames.split(" ");
			graph = new Graph(vexNum, vexNameArray);
			int edgeNum = 0;
			System.out.println("����Ҫ����ı���");
			edgeNum = sc.nextInt();
			sc.nextLine();
			System.out.println("����ڵ�Ժ�Ȩ�أ��Կո����");

			List<String> vexNameList = Arrays.asList(vexNameArray);
			while (edgeNum != 0) {
				int node1 = vexNameList.indexOf(sc.next());
				int node2 = vexNameList.indexOf(sc.next());
				int weight = sc.nextInt();
				sc.nextLine();
				System.out.println(node1 + "->" + node2 + "=" + weight);
				graph.getArc()[node1][node2] = weight;
				graph.getArc()[node2][node1] = weight;
				edgeNum--;
				System.out.println("����"+edgeNum+"������Ҫ����");
			}

			// ����ͼ��Ϣ��ɣ���ʼ����
			Dist[] dist = new Dist[vexNum];
			for (int i = 0; i < vexNum; i++) {
				dist[i] = new Dist();
				dist[i].setPath(vexNameArray[i]);
			}
			// ��ʼ�����0��distΪ0,���ұ�־��Ϊ����̱��
			dist[0].setDist(0);
			dist[0].setFlag(true);
			int curNode = 0;// ���õ�ǰ�����Ľڵ���0
			//����ʣ��n-1���ڵ�����·��
			for (int i = 1; i < vexNum; i++) {
				int min=Integer.MAX_VALUE;//��Сֵ�ļĴ���
				
				for (int j = 0; j < vexNum; j++) {
					// �뵱ǰ�ڵ㲻���ڵĽڵ㲻�ڿ��Ƿ�Χ��,�Ѿ�������С�ڵ���еĲ�����
					if (graph.getArc()[curNode][j] == -1|| dist[j].isFlag())
						continue;
					// �����ǰ�����������
					// ���߱ȵ�ǰ��С·���ڵ�������͵�ǰ�ڵ�ıߣ���ô˵�����ߵľ����С������
					if (dist[j].getDist() == -1
							|| dist[j].getDist() > (dist[curNode].getDist() + graph.getArc()[curNode][j])) {
						dist[j].setDist(dist[curNode].getDist() + graph.getArc()[curNode][j]);
						dist[j].setPath(dist[curNode].getPath()+"->"+vexNameArray[j]);
					}
				}
				
				//��ʼ����δ������С·���ڵ㼯����С��
				for(int j=0;j<vexNum;j++) {
					if(dist[j].isFlag()||dist[j].getDist()==-1)//�ѽ���Ĳ�ͳ��,dist��Ϊ����Ĳ���
						continue;
					//������Сֵ��Ϊ��һ��curNode
					if(dist[j].getDist()<min) {
						min=dist[j].getDist();
						curNode=j;
					}
				}
				//����С��dist��ֵ������С����ڵ㼯��
				dist[curNode].setFlag(true);
			}
			for(int i=0;i<vexNum;i++) {
				System.out.println(dist[i].getPath()+":"+dist[i].getDist());
			}
		}

	}

}
