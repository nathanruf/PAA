package main;

import java.util.ArrayList;

import grafos.Aresta;
import grafos.Grafo;
import grafos.Vertice;

public class MatrizDeIncidencia implements Grafo {

	private Double[][] grafo;
	private Vertice[] v;

	@SuppressWarnings("unchecked")
	public MatrizDeIncidencia(ArrayList<String> arquivo, int numVertices) {
		ArrayList<Aresta>[] aux = new ArrayList[numVertices];
		v = new Vertice[numVertices];
		for (int i = 0; i < numVertices; i++) {
			v[i] = new Vertice(i);
		}

		while (!arquivo.isEmpty()) {
			String[] atual = arquivo.remove(0).split(";");
			String id = atual[0].trim();
			int index = id.indexOf(' ');
			int linha, tam = atual.length;

			if (index != -1) {
				linha = Integer.parseInt(atual[0].substring(0, index));
				atual[0] = atual[0].substring(index + 1);
			} else {
				linha = Integer.parseInt(id);
				tam = 0;
			}

			aux[linha] = new ArrayList<Aresta>();

			for (int i = 0; i < tam; i++) {
				String[] param = atual[i].split("-");
				int destino = Integer.parseInt(param[0].trim());
				double peso = Double.parseDouble(param[1].trim());

				aux[linha].add(new Aresta(v[linha], v[destino], peso + 1));
			}
		}

		int numArestas = 0;
		for (int i = 0; i < numVertices; i++) {
			numArestas += aux[i].size();
		}
		grafo = new Double[numVertices][numArestas];
		int coluna = 0;

		for (int i = 0; i < numVertices; i++) {
			int size = aux[i].size();
			for (int j = 0; j < size; j++) {
				double peso = aux[i].get(j).peso();
				int destino = aux[i].get(j).destino().id();
				grafo[destino][coluna] = peso;
				grafo[i][coluna] = peso * -1;
				coluna++;
			}
		}
	}

	@Override
	public int numeroDeVertices() {
		return grafo.length;
	}

	@Override
	public int numeroDeArestas() {
		return grafo[0].length;
	}

	@Override
	public ArrayList<Vertice> adjacentesDe(Vertice vertice) {
		ArrayList<Vertice> res = new ArrayList<Vertice>();
		int linha = vertice.id();
		int tam = grafo[linha].length;
		ArrayList<Integer> aux = new ArrayList<Integer>();

		for (int i = 0; i < tam; i++) {
			Double peso = grafo[linha][i];
			if (peso != null) {
				if (peso < 0)
					aux.add(i);
			}
		}

		tam = v.length;

		for (Integer pos : aux) {
			boolean encontrado = false;
			int i = 0;

			while (!encontrado && i < tam) {
				Double peso = grafo[i][pos];
				if (peso != null) {
					if (peso >= 0) {
						res.add(v[i]);
						encontrado = true;
					}
				}
				i++;
			}
			if (!encontrado)
				res.add(v[linha]);
		}

		return res;
	}

	@Override
	public Aresta arestaEntre(Vertice origem, Vertice destino) {
		Aresta res = null;
		boolean encontrado = false;
		int i = 0, o = origem.id(), d = destino.id(), tam = grafo[0].length;

		while (!encontrado && i < tam) {
			Double pesoOrigem = grafo[o][i], pesoDestino = grafo[d][i];
			if (pesoOrigem != null && pesoDestino != null && pesoOrigem < 0) {
				res = new Aresta(origem, destino, pesoDestino - 1);
				encontrado = true;
			}
			i++;
		}

		if (res.origem().id() == res.destino().id())
			res.setarPeso((-1 * res.peso()) - 2);

		return res;
	}

	public Aresta[] arestas() {
		Aresta[] res = new Aresta[numeroDeArestas()];
		int tam = grafo[0].length, count = 0, nVertices = v.length;

		for (int j = 0; j < tam; j++) {
			Vertice origem = null, destino = null;
			int i = 0;
			Double peso = null;

			while (origem == null || destino == null && i < nVertices) {
				Double atual = grafo[i][j];
				if (atual != null) {
					if (atual < 0) {
						origem = v[i];
						peso = -1 * atual;
						peso -= 1;
					} else
						destino = v[i];
				}
				i++;
			}
			if (destino == null) {
				destino = origem;
				peso -= 1;
			}

			res[count++] = new Aresta(origem, destino, peso);
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
