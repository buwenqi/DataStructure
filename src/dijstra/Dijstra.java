package dijstra;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Dijstra {

	public static void main(String[] args) {
		// 使用的图
		Graph graph = null;
		// 出生点到当前节点的最短路径
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("输入节点数：");
			int vexNum = sc.nextInt();
			sc.nextLine();
			System.out.println("输入节点名，以空格相隔：");
			String vexNames = sc.nextLine();
			String[] vexNameArray = vexNames.split(" ");
			graph = new Graph(vexNum, vexNameArray);
			int edgeNum = 0;
			System.out.println("输入要输入的边数");
			edgeNum = sc.nextInt();
			sc.nextLine();
			System.out.println("输入节点对和权重，以空格相隔");

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
				System.out.println("还有"+edgeNum+"条边需要输入");
			}

			// 输入图信息完成，开始处理
			Dist[] dist = new Dist[vexNum];
			for (int i = 0; i < vexNum; i++) {
				dist[i] = new Dist();
				dist[i].setPath(vexNameArray[i]);
			}
			// 初始化起点0的dist为0,并且标志其为已最短标记
			dist[0].setDist(0);
			dist[0].setFlag(true);
			int curNode = 0;// 设置当前入加入的节点是0
			//计算剩余n-1个节点的最短路径
			for (int i = 1; i < vexNum; i++) {
				int min=Integer.MAX_VALUE;//最小值的寄存区
				
				for (int j = 0; j < vexNum; j++) {
					// 与当前节点不相邻的节点不在考虑范围内,已经加入最小节点队列的不考虑
					if (graph.getArc()[curNode][j] == -1|| dist[j].isFlag())
						continue;
					// 如果当前距离是无穷大，
					// 或者比当前最小路径节点加上它和当前节点的边，那么说明后者的距离更小，更新
					if (dist[j].getDist() == -1
							|| dist[j].getDist() > (dist[curNode].getDist() + graph.getArc()[curNode][j])) {
						dist[j].setDist(dist[curNode].getDist() + graph.getArc()[curNode][j]);
						dist[j].setPath(dist[curNode].getPath()+"->"+vexNameArray[j]);
					}
				}
				
				//开始查找未进入最小路径节点集的最小点
				for(int j=0;j<vexNum;j++) {
					if(dist[j].isFlag()||dist[j].getDist()==-1)//已进入的不统计,dist仍为无穷的不计
						continue;
					//查找最小值作为下一次curNode
					if(dist[j].getDist()<min) {
						min=dist[j].getDist();
						curNode=j;
					}
				}
				//将最小的dist的值加入最小距离节点集合
				dist[curNode].setFlag(true);
			}
			for(int i=0;i<vexNum;i++) {
				System.out.println(dist[i].getPath()+":"+dist[i].getDist());
			}
		}

	}

}
