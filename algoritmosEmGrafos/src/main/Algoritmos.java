package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Arrays;

import grafos.*;

public class Algoritmos {

	private Grafo g;
	private Integer count;

	public Grafo getGrafo() {
		return g;
	}

	public Algoritmos(String path, TipoDeRepresentacao t) throws Exception {
		FileManager fileManager = new FileManager();
		ArrayList<String> arquivo = fileManager.stringReader(path);
		int numVertices = Integer.parseInt(arquivo.remove(0));

		if (t == TipoDeRepresentacao.MATRIZ_DE_ADJACENCIA) {
			g = new MatrizDeAdjacencia(arquivo, numVertices);
		}

		else if (t == TipoDeRepresentacao.LISTA_DE_ADJACENCIA) {
			g = new ListaDeAdjacencia(arquivo, numVertices);
		}

		else {
			g = new MatrizDeIncidencia(arquivo, numVertices);
		}
	}

	public ReturnBFS buscaEmLargura(int o) {
		Vertice origem = g.getVertice(o);
		int tempo = 0, tam = g.numeroDeVertices(), id = origem.id();
		int[] d = new int[tam];
		int[] num = new int[tam];
		Cor[] cor = new Cor[tam];
		Vertice[] pai = new Vertice[tam];
		d[id] = tempo;
		num[id] = id;
		ReturnBFS res = new ReturnBFS();

		for (int i = 0; i < tam; i++)
			cor[i] = Cor.BRANCO;

		cor[id] = Cor.CINZA;
		List<Vertice> bfs = new ArrayList<Vertice>();
		List<Vertice> adjacente = new ArrayList<Vertice>();
		bfs.add(origem);
		Vertice paiAtual;

		while (!bfs.isEmpty()) {
			paiAtual = bfs.remove(0);
			id = paiAtual.id();
			tempo = d[id] + 1;
			adjacente.addAll(g.adjacentesDe(paiAtual));
			int size = adjacente.size();
			for (int i = 0; i < size; i++) {
				Vertice atual = adjacente.get(i);
				int idAtual = atual.id();
				if (cor[idAtual] == Cor.BRANCO) {
					cor[idAtual] = Cor.CINZA;
					d[idAtual] = tempo;
					num[idAtual] = idAtual;
					pai[idAtual] = paiAtual;
					for (int j = 0; j < size; j++) {
						Vertice aux = adjacente.get(i);
						if (!bfs.contains(aux))
							bfs.add(aux);
					}
				}
			}
			cor[id] = Cor.PRETO;
			adjacente.clear();
		}

		List<Integer> aux = new ArrayList<Integer>();
		for (int i = 0; i < tam; i++)
			if (pai[i] != null || i == o) {
				aux.add(i);
			}
		int size = aux.size();
		int[] dPodado = new int[size];
		int[] idPodado = new int[size];
		Vertice[] paiPodado = new Vertice[size];

		for (int i = 0; i < size; i++) {
			dPodado[i] = d[aux.get(0)];
			paiPodado[i] = pai[aux.get(0)];
			idPodado[i] = num[aux.remove(0)];
		}

		res.setD(dPodado);
		res.setPai(paiPodado);
		res.setId(idPodado);

		return res;
	}

	public ReturnDFS buscaEmProfundidade(int o) {
		count = 0;
		ReturnDFS res = new ReturnDFS();
		int tamA = g.numeroDeArestas();
		Aresta[] arestas = new Aresta[tamA];
		TipoDeAresta[] t = new TipoDeAresta[tamA];
		int tempo = 0, tam = g.numeroDeVertices() + 1;
		Vertice[] vertices = new Vertice[tam--];
		int[] d = new int[tam];
		int[] f = new int[tam];
		Cor[] cor = new Cor[tam];
		vertices[0] = g.getVertice(o);
		Vertice[] aux = g.vertices();
		for (int i = 0; i < tam; i++) {
			vertices[i + 1] = aux[i];
		}
		res.setArestas(arestas);
		res.setD(d);
		res.setF(f);
		res.setT(t);

		for (int i = 0; i < tam; i++)
			cor[i] = Cor.BRANCO;

		for (Vertice v : vertices) {
			int id = v.id();
			if (cor[id] == Cor.BRANCO) {
				cor[id] = Cor.CINZA;
				d[id] = tempo++;

				tempo = recursaoDFS(res, v, cor, g.adjacentesDe(v), tempo, new VerticeNo(v));

				f[id] = tempo++;
				cor[id] = Cor.PRETO;
			}
		}

		return res;
	}

	private int recursaoDFS(ReturnDFS res, Vertice origem, Cor[] cor, List<Vertice> adj, int tempo, VerticeNo no) {

		for (Vertice v : adj) {
			int id = v.id();
			res.getArestas()[count] = g.arestaEntre(origem, v);

			if (cor[id] == Cor.BRANCO) {
				res.getT()[count] = TipoDeAresta.ARVORE;
				res.getD()[id] = tempo;
				cor[id] = Cor.CINZA;
				VerticeNo vn = new VerticeNo(v);
				no.getLv().add(vn);
				count++;

				tempo = recursaoDFS(res, v, cor, g.adjacentesDe(v), tempo + 1, vn);

				cor[id] = Cor.PRETO;
				res.getF()[id] = tempo++;

			} else if (cor[id] == Cor.CINZA) {
				res.getT()[count] = TipoDeAresta.RETORNO;
				count++;
			} else {
				res.getT()[count] = avOuCruz(v, encontraOrigem(no, origem));
				count++;
			}
		}

		return tempo;
	}

	private VerticeNo encontraOrigem(VerticeNo no, Vertice origem) {
		VerticeNo[] res = new VerticeNo[1];
		if (no.getV().equals(origem))
			return no;
		else {
			no.getLv().forEach((v) -> res[0] = encontraOrigem(v, origem));
		}

		return res[0];
	}

	private TipoDeAresta avOuCruz(Vertice destino, VerticeNo no) {
		TipoDeAresta[] t = new TipoDeAresta[1];
		if (no.getV().equals(destino))
			return TipoDeAresta.AVANCO;
		else {
			no.getLv().forEach((v) -> t[0] = avOuCruz(destino, v));
		}

		return t[0] == null ? TipoDeAresta.CRUZAMENTO : t[0];
	}

	public ReturnAGM agmUsandoKruskall() {
		Aresta[] aresta = g.arestas();
		Arrays.sort(aresta, (a, b) -> {
			return a.peso() >= b.peso() ? 1 : -1;
		});
		List<ArrayList<Vertice>> conjuntosVertice = new ArrayList<ArrayList<Vertice>>();
		ArrayList<Aresta> a = new ArrayList<Aresta>();
		Vertice[] vertices = g.vertices();
		int tam = vertices.length, count = 0;

		for (int i = 0; i < tam; i++) {
			conjuntosVertice.add(new ArrayList<Vertice>());
			conjuntosVertice.get(i).add(vertices[i]);
		}

		tam = aresta.length;

		while (conjuntosVertice.size() != 1 && tam > count) {
			Aresta atual = aresta[count++];
			int origem = -1, destino = -1, i = 0;

			while (origem == -1 || destino == -1) {
				if (conjuntosVertice.get(i).contains(atual.origem())) {
					origem = i;
				}
				if (conjuntosVertice.get(i).contains(atual.destino())) {
					destino = i;
				}
				i++;
			}

			if (origem != destino) {
				a.add(atual);
				ArrayList<Vertice> viajantes = conjuntosVertice.get(destino);
				conjuntosVertice.get(origem).addAll(viajantes);
				conjuntosVertice.remove(destino);
			}
		}

		ReturnAGM res = new ReturnAGM();
		ArrayList<Aresta> arestas = new ArrayList<Aresta>();
		res.setA(a);
		tam = conjuntosVertice.size();
		if (tam > 1) {
			res.setA(arestas);
			res.setTam(new int[tam]);
			for (int i = 0; i < tam; i++)
				res.getTam()[i] = conjuntosVertice.get(i).size() - 1;

			for (int i = 0; i < tam; i++) {
				ArrayList<Vertice> vertice = conjuntosVertice.get(i);
				int length = vertice.size() - 1, j = 0;
				if (length == 0)
					res.getIdVerticeSemLig().add(vertice.get(0).id());
				else {
					while (j <= length) {
						Aresta atual = a.get(j++);
						if (vertice.contains(atual.origem()) || vertice.contains(atual.destino()))
							arestas.add(atual);
					}
				}
			}
		}

		return res;
	}

	class QueueComparator implements Comparator<Vertice> {

		@Override
		public int compare(Vertice a, Vertice b) {
			if (a.getCusto() >= b.getCusto())
				return 1;
			else
				return -1;
		}

	}

	public ArrayList<Aresta> caminhoMaisCurto(int o, int d) throws Exception {
		Vertice origem = g.getVertice(o);
		Vertice destino = g.getVertice(d);
		ArrayList<Aresta> res = new ArrayList<Aresta>();
		if (o == d)
			return res;
		Queue<Vertice> min = new PriorityQueue<Vertice>(new QueueComparator());
		Vertice[] vertices = g.vertices();
		int tam = vertices.length;
		int idDestino = destino.id();
		boolean[] fixo = new boolean[tam];
		Vertice atual = origem;

		for (int i = 0; i < tam; i++) {
			fixo[i] = false;
			vertices[i].setCusto(Double.MAX_VALUE);
		}

		atual.setCusto(0);
		fixo[atual.id()] = true;

		while (!fixo[idDestino]) {
			ArrayList<Vertice> adj = g.adjacentesDe(atual);

			for (Vertice v : adj) {
				if (fixo[v.id()] == false) {
					Aresta aresta = g.arestaEntre(atual, v);
					if (v.getCusto() > atual.getCusto() + aresta.peso()) {
						v.setCusto(atual.getCusto() + aresta.peso());
						min.add(v);
						v.setOrigem(atual);
					}
				}
			}

			Vertice aux = min.poll();

			res.add(g.arestaEntre(aux.getOrigem(), aux));
			atual = aux;
			fixo[atual.id()] = true;
		}

		int i = res.size() - 1;
		o = res.get(i).origem().id();
		i--;
		while (i > -1) {
			if (res.get(i).destino().id() != o)
				res.remove(i);
			else
				o = res.get(i).origem().id();
			i--;
		}

		return res;
	}

	public Aresta[] fluxoMaximo(int o, int d) throws Exception {
		Aresta[] arestas = g.arestas();
		ArrayList<Aresta> a = caminhoMaisLongo(o, d, arestas);
		while (a != null) {
			a.sort((x, y) -> {
				return x.getResiduo() >= y.getResiduo() ? 1 : -1;
			});
			double residuo = a.get(0).getResiduo();
			for (Aresta aresta : a) {
				aresta.setResiduo(aresta.getResiduo() - residuo);
			}

			a = caminhoMaisLongo(o, d, arestas);
		}

		return arestas;
	}

	class maisLongoComparator implements Comparator<Aresta> {

		@Override
		public int compare(Aresta a, Aresta b) {
			if (a.getResiduo() >= b.getResiduo())
				return -1;
			else
				return 1;
		}

	}

	private ArrayList<Aresta> caminhoMaisLongo(int o, int d, Aresta[] arestas) throws Exception {
		Vertice origem = g.getVertice(o);
		ArrayList<Aresta> res = new ArrayList<Aresta>();
		Queue<Aresta> max = new PriorityQueue<Aresta>(new maisLongoComparator());
		Vertice atual = origem;
		boolean nExiste = false;
		boolean[] fixo = new boolean[g.numeroDeVertices()];
		
		fixo[atual.id()] = true;

		while (atual.id() != d && !nExiste) {
			ArrayList<Vertice> adj = g.adjacentesDe(atual);

			for (Vertice v : adj) {
				Aresta aresta = devolveAresta(atual.id(), v.id(), arestas);
				if (aresta.getResiduo() != 0 && fixo[v.id()] == false) {
					max.add(aresta);
				}
			}

			if (!max.isEmpty()) {
				Aresta maior = max.poll();
				max.clear();
				res.add(maior);
				atual = maior.destino();
				fixo[atual.id()] = true;
			} else {
				int size = res.size();
				if(size > 0) {
					Aresta aux = res.remove(size-1);
					atual = aux.origem();
					fixo[aux.destino().id()] = true;
				}
				else
					nExiste = true;
			}
		}

		return nExiste ? null : res;
	}
	
	private Aresta devolveAresta(int origem, int destino, Aresta[] arestas) {
		int i = 0;
		Aresta a = null;
		while(a == null) {
			Aresta atual = arestas[i++];
			if(atual.origem().id() == origem && atual.destino().id() == destino) {
				a = atual;
			}
		}
		
		return a;
	}

}
