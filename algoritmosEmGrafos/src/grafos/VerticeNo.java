package grafos;

import java.util.ArrayList;
import java.util.List;

public class VerticeNo {

	private Vertice v;
	private List<VerticeNo> lv = new ArrayList<VerticeNo>();
	
	public VerticeNo(Vertice v) {
		this.v = v;
	}

	public Vertice getV() {
		return v;
	}

	public void setV(Vertice v) {
		this.v = v;
	}

	public List<VerticeNo> getLv() {
		return lv;
	}

	public void setLv(List<VerticeNo> lv) {
		this.lv = lv;
	}
	
}
