package main;

import java.util.ArrayList;

import grafos.Aresta;
import grafos.Grafo;
import grafos.Vertice;

public class ListaDeAdjacencia implements Grafo{
	
	private ArrayList<Aresta>[] grafo;
	private Vertice[] v;

	@SuppressWarnings("unchecked")
	public ListaDeAdjacencia(ArrayList<String> arquivo, int numVertices) {
		grafo = new ArrayList[numVertices];
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
			
			grafo[linha] = new ArrayList<Aresta>();

			for (int i = 0; i < tam; i++) {
				String[] aux = atual[i].split("-");
				int destino = Integer.parseInt(aux[0].trim());
				double peso = Double.parseDouble(aux[1].trim());

				grafo[linha].add(new Aresta(v[linha], v[destino], peso));
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
			res += grafo[i].size();
		}

		return res;
	}

	@Override
	public ArrayList<Vertice> adjacentesDe(Vertice vertice) {
		ArrayList<Vertice> res = new ArrayList<Vertice>();
		int linha = vertice.id();
		int tam = grafo[linha].size();
		ArrayList<Aresta> a = grafo[linha];
		
		for(int i = 0; i<tam; i++) {
			res.add(a.get(i).destino());
		}
		
		return res;
	}

	@Override
	public Aresta arestaEntre(Vertice origem, Vertice destino) {
		ArrayList<Aresta> a = grafo[origem.id()];
		int tam = a.size();
		int d = destino.id();
		Aresta res = null;
		
		for(int i = 0; i<tam; i++) {
			if(a.get(i).destino().id() == d)
				res = a.get(i);
		}
		
		return res;
	}
	
	public Aresta[] arestas(){
		Aresta[] res = new Aresta[numeroDeArestas()];
		int tam = grafo.length, count = 0;

		for (int i = 0; i < tam; i++) {
			int size = grafo[i].size();
			for(int j = 0; j<size; j++)
				res[count++] = grafo[i].get(j);
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
