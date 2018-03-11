package floyd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import dijstra.Graph;

public class Floyd {

	public static void main(String[] args) throws IOException {
		// 使用的图
		Graph graph = null;
		// 出生点到当前节点的最短路径
		BufferedReader reader = new BufferedReader(new FileReader("resource/graph.txt"));
		BufferedWriter writer = new BufferedWriter(new FileWriter("resource/result.txt"));
		Scanner sc = new Scanner(System.in);
		while (true) {
			int vexNum = Integer.valueOf(reader.readLine());
			System.out.println("节点数：" + vexNum);
			String vexNames = reader.readLine();
			System.out.println("节点名：" + vexNames);
			String[] vexNameArray = vexNames.split(" ");
			graph = new Graph(vexNum, vexNameArray);
			int edgeNum = Integer.valueOf(reader.readLine());
			System.out.println("总共的边数：" + edgeNum);

			List<String> vexNameList = Arrays.asList(vexNameArray);
			while (edgeNum != 0) {
				String edgeLine = reader.readLine();
				String[] edgeLineArray = edgeLine.split(" ");
				int node1 = vexNameList.indexOf(edgeLineArray[0]);
				int node2 = vexNameList.indexOf(edgeLineArray[1]);
				int weight = Integer.valueOf(edgeLineArray[2]);
				System.out.println(node1 + "->" + node2 + "=" + weight);
				graph.getArc()[node1][node2] = weight;
				graph.getArc()[node2][node1] = weight;// 假设是无像图
				edgeNum--;
			}

			// 输入图信息完成，开始处理
			// ans[i][j]代表i,j之间的最短路径
			int ans[][] = new int[vexNum][vexNum];
			// path[i][j]代表i,j最短路径通过的节点，用逗号分隔
			String path[][] = new String[vexNum][vexNum];
			for (int i = 0; i < vexNum; i++)
				for (int j = 0; j < vexNum; j++) {
					ans[i][j] = graph.getArc()[i][j];
					path[i][j] = String.format("%s->%s:", vexNameArray[i], vexNameArray[j]);
				}

			for (int k = 0; k < vexNum; k++)// 插入的节点索引
				for (int i = 0; i < vexNum; i++)
					for (int j = 0; j < vexNum; j++) {
						// 对于i,j在现有的最短路径下插入k
						if (ans[i][k] == -1 || ans[k][j] == -1)
							continue;
						if (ans[i][j] == -1 || (ans[i][k] + ans[k][j]) < ans[i][j]) {
							// 符合条件，插入k之后更短，插入和不插入之间的对比
							ans[i][j] = ans[i][k] + ans[k][j];
							path[i][j]=String.format("%s->%s:", vexNameArray[i], vexNameArray[j]);//清空
							path[i][j]+= "["+path[i][k] + "]"+"["+path[k][j]+"]";
						}
					}
			for (int i = 0; i < vexNum; i++) {
				for (int j = 0; j < vexNum; j++) {
					writer.write(ans[i][j] + "	");
				}
				writer.newLine();
			}
			writer.flush();

			for (int i = 0; i < vexNum; i++) {
				for (int j = 0; j < vexNum; j++) {
					writer.write(path[i][j] + "\n");
				}
				writer.flush();
			}

			writer.flush();
			writer.close();
			System.out.println("是否继续输入：");
			String cmd = sc.nextLine();
			if ("y".equals(cmd))
				continue;
			else
				break;

		}

	}

}
