package grafos;

import java.util.ArrayList;

public class ReturnAGM {

	private ArrayList<Aresta> a;
	private int[] tam = null;
	private ArrayList<Integer> idVerticeSemLig = new ArrayList<Integer>();

	public ArrayList<Aresta> getA() {
		return a;
	}

	public void setA(ArrayList<Aresta> a) {
		this.a = a;
	}

	public int[] getTam() {
		return tam;
	}

	public void setTam(int[] tam) {
		this.tam = tam;
	}

	public ArrayList<Integer> getIdVerticeSemLig() {
		return idVerticeSemLig;
	}

}
