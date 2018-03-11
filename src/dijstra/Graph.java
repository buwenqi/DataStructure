package dijstra;

public class Graph {

	private int arc[][];
	private String vex[];
	private int vexnum;

	public Graph() {
	}

	public Graph(int vexnum, String vex[]) {
		this.vexnum = vexnum;
		arc = new int[vexnum][vexnum];
		for (int i = 0; i < vexnum; i++)
			for (int j = 0; j < vexnum; j++) {
				if (i == j)
					arc[i][j] = 0;
				else
					arc[i][j] = -1;// 没有边定义
			}

		if (vex.length < vexnum) {
			System.out.println("顶点数量小于顶点数");
		} else {
			this.vex = vex;
		}
	}

	public int[][] getArc() {
		return arc;
	}

	public void setArc(int[][] arc) {
		this.arc = arc;
	}

	public String[] getVex() {
		return vex;
	}

	public void setVex(String[] vex) {
		this.vex = vex;
	}

	public int getVexnum() {
		return vexnum;
	}

	public void setVexnum(int vexnum) {
		this.vexnum = vexnum;
	}

}
