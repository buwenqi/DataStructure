package dijstra;

public class Dist {
	private int dist;
	private boolean flag;
	private String path;
	
	public Dist() {
		dist=-1;//-1���������ע���б�
		flag=false;//false����δ���ʹ�
		path=null;
	}

	public int getDist() {
		return dist;
	}

	public void setDist(int dist) {
		this.dist = dist;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	

}
