package grafos;

public class ReturnDFS {

	private int[] d;
	private int[] f;
	private Aresta[] arestas;
	private TipoDeAresta[] t;

	public int[] getD() {
		return d;
	}

	public void setD(int[] d) {
		this.d = d;
	}

	public int[] getF() {
		return f;
	}

	public void setF(int[] f) {
		this.f = f;
	}

	public Aresta[] getArestas() {
		return arestas;
	}

	public void setArestas(Aresta[] arestas) {
		this.arestas = arestas;
	}

	public TipoDeAresta[] getT() {
		return t;
	}

	public void setT(TipoDeAresta[] t) {
		this.t = t;
	}

}
