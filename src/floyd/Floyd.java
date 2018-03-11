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
		// ʹ�õ�ͼ
		Graph graph = null;
		// �����㵽��ǰ�ڵ�����·��
		BufferedReader reader = new BufferedReader(new FileReader("resource/graph.txt"));
		BufferedWriter writer = new BufferedWriter(new FileWriter("resource/result.txt"));
		Scanner sc = new Scanner(System.in);
		while (true) {
			int vexNum = Integer.valueOf(reader.readLine());
			System.out.println("�ڵ�����" + vexNum);
			String vexNames = reader.readLine();
			System.out.println("�ڵ�����" + vexNames);
			String[] vexNameArray = vexNames.split(" ");
			graph = new Graph(vexNum, vexNameArray);
			int edgeNum = Integer.valueOf(reader.readLine());
			System.out.println("�ܹ��ı�����" + edgeNum);

			List<String> vexNameList = Arrays.asList(vexNameArray);
			while (edgeNum != 0) {
				String edgeLine = reader.readLine();
				String[] edgeLineArray = edgeLine.split(" ");
				int node1 = vexNameList.indexOf(edgeLineArray[0]);
				int node2 = vexNameList.indexOf(edgeLineArray[1]);
				int weight = Integer.valueOf(edgeLineArray[2]);
				System.out.println(node1 + "->" + node2 + "=" + weight);
				graph.getArc()[node1][node2] = weight;
				graph.getArc()[node2][node1] = weight;// ����������ͼ
				edgeNum--;
			}

			// ����ͼ��Ϣ��ɣ���ʼ����
			// ans[i][j]����i,j֮������·��
			int ans[][] = new int[vexNum][vexNum];
			// path[i][j]����i,j���·��ͨ���Ľڵ㣬�ö��ŷָ�
			String path[][] = new String[vexNum][vexNum];
			for (int i = 0; i < vexNum; i++)
				for (int j = 0; j < vexNum; j++) {
					ans[i][j] = graph.getArc()[i][j];
					path[i][j] = String.format("%s->%s:", vexNameArray[i], vexNameArray[j]);
				}

			for (int k = 0; k < vexNum; k++)// ����Ľڵ�����
				for (int i = 0; i < vexNum; i++)
					for (int j = 0; j < vexNum; j++) {
						// ����i,j�����е����·���²���k
						if (ans[i][k] == -1 || ans[k][j] == -1)
							continue;
						if (ans[i][j] == -1 || (ans[i][k] + ans[k][j]) < ans[i][j]) {
							// ��������������k֮����̣�����Ͳ�����֮��ĶԱ�
							ans[i][j] = ans[i][k] + ans[k][j];
							path[i][j]=String.format("%s->%s:", vexNameArray[i], vexNameArray[j]);//���
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
			System.out.println("�Ƿ�������룺");
			String cmd = sc.nextLine();
			if ("y".equals(cmd))
				continue;
			else
				break;

		}

	}

}
