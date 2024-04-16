package main;

import java.util.ArrayList;

import grafos.Aresta;
import grafos.Grafo;
import grafos.Vertice;

public class MatrizDeAdjacencia implements Grafo {

	private Double[][] grafo;
	private Vertice[] v;

	public MatrizDeAdjacencia(ArrayList<String> arquivo, int numVertices) {
		grafo = new Double[numVertices][numVertices];
		v = new Vertice[numVertices];
		for(int i = 0; i<numVertices; i++) {
			v[i] = new Vertice(i);
		}

		while (!arquivo.isEmpty()) {
			String[] atual = arquivo.remove(0).split(";");
			String id = atual[0].trim();
			int index = id.indexOf(' '), linha, tam = atual.length;
			
			if (index != -1) {
				linha = Integer.parseInt(atual[0].substring(0, index));
				atual[0] = atual[0].substring(index + 1);
			} else {
				linha = Integer.parseInt(id);
				tam = 0;
			}

			for (int i = 0; i < tam; i++) {
				String[] aux = atual[i].split("-");
				int coluna = Integer.parseInt(aux[0].trim());
				double peso = Double.parseDouble(aux[1].trim());

				grafo[linha][coluna] = peso;
			}
		}
	}

	@Override
	public int numeroDeVertices() {
		return grafo.length;
	}

	@Override
	public int numeroDeArestas() {
		int res = 0;
		int tam = grafo.length;

		for (int i = 0; i < tam; i++) {
			for (int j = 0; j <tam; j++)
				if (grafo[i][j] != null)
					res++;
		}

		return res;
	}

	@Override
	public ArrayList<Vertice> adjacentesDe(Vertice vertice) {
		ArrayList<Vertice> res = new ArrayList<Vertice>();
		int linha = vertice.id();
		int tam = grafo.length;
		
		for(int i = 0; i<tam; i++) {
			if(grafo[linha][i] != null)
				res.add(v[i]);
		}
		
		return res;
	}

	@Override
	public Aresta arestaEntre(Vertice origem, Vertice destino) {
		Double peso = grafo[origem.id()][destino.id()];
		return peso == null ? null : new Aresta(origem, destino, peso);
	}
	
	public Aresta[] arestas(){
		Aresta[] res = new Aresta[numeroDeArestas()];
		int tam = grafo.length, count = 0;

		for (int i = 0; i < tam; i++) {
			for (int j = 0; j < tam; j++)
				if (grafo[i][j] != null)
					res[count++] = new Aresta(v[i], v[j], grafo[i][j]);
		}

		return res;
	}

	@Override
	public Vertice[] vertices() {
		return v;
	}
	
	public Vertice getVertice(int id) {
		return v[id];
	}

}
