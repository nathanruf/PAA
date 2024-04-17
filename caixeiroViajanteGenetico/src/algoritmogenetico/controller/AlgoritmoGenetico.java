package algoritmogenetico.controller;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import algoritmogenetico.Graph;
import algoritmogenetico.model.Individuo;

public class AlgoritmoGenetico {

	private int[] realizaSorteio(Individuo[] populacao, int quant) {
		int[] sorteados = new int[quant];
		long total = getSoma(populacao), size = populacao.length;
		Random random = new Random(System.nanoTime());

		for (int j = 0; j < quant; j++) {
			long num = 0;

			for (int i = 0; i < size; i++) {
				num += (total - populacao[i].getCustoCaminho());
			}

			long numSorteio = random.nextLong(num);
			int sorteado = -1;

			while (numSorteio >= 0) {
				numSorteio -= (total - populacao[++sorteado].getCustoCaminho());
			}

			total -= populacao[sorteado].getCustoCaminho();
			sorteados[j] = sorteado;
		}

		return sorteados;
	}

	private long getSoma(Individuo[] populacao) {
		long total = 0;

		for (Individuo individuo : populacao) {
			total += individuo.getCustoCaminho();
		}

		return total;
	}

	private Individuo[] cruzamento(int[] index, Individuo[] populacao) {
		int length = index.length;
		Individuo[] novos = new Individuo[length];
		int tamanho = populacao[0].getTamanho();
		for (int i = 0; i < length; i += 2) {
			int[] genitor1 = populacao[i].getGenes(), genitor2 = populacao[i + 1].getGenes();
			int corte1 = (int) (Math.random() * (tamanho - 1));
			int corte2 = (int) (Math.random() * (tamanho - 1));
			Set<Integer> valores1 = new HashSet<Integer>();
			Set<Integer> valores2 = new HashSet<Integer>();
			int[] filho1 = new int[tamanho], filho2 = new int[tamanho];

			for (int j = 0; j < corte1; j++) {
				valores1.add(genitor1[j]);
				filho1[j] = genitor1[j];
			}
			for (int j = 0; j < corte2; j++) {
				valores2.add(genitor2[j]);
				filho2[j] = genitor2[j];
			}

			int pos1 = corte1;
			int pos2 = 0;
			while (pos1 < tamanho) {
				int geneAtual = genitor2[pos2];
				if (!valores1.contains(geneAtual)) {
					filho1[pos1++] = geneAtual;
				}
				pos2++;
			}

			pos1 = 0;
			pos2 = corte2;
			while (pos2 < tamanho) {
				int geneAtual = genitor1[pos1];
				if (!valores2.contains(geneAtual)) {
					filho2[pos2++] = geneAtual;
				}
				pos1++;
			}

			novos[i] = new Individuo(filho1);
			novos[i + 1] = new Individuo(filho2);
		}

		if (length % 2 != 0) {
			int[] index2 = { index[length - 1], index[length - 2] };
			Individuo[] populacao2 = { populacao[length - 1], populacao[length - 2] };
			populacao2 = cruzamento(index2, populacao2);
			novos[length - 1] = populacao2[(int) (Math.random() * 2)];
		}

		return novos;
	}

	public void fitness(Individuo[] populacao, Graph graph) {
		for (Individuo individuo : populacao) {
			int[] genes = individuo.getGenes();
			int length = genes.length - 1;
			int custo = 0;

			for (int i = 0; i < length; i++) {
				custo += graph.getEdgeWeight(genes[i], genes[i + 1]);
			}

			custo += graph.getEdgeWeight(genes[length], genes[0]);

			individuo.setCustoCaminho(custo);
		}
	}

	public Individuo[] geraDescendentes(Individuo[] populacao, int quant) {
		int[] index = realizaSorteio(populacao.clone(), quant);
		Individuo[] novos = cruzamento(index, populacao);

		return novos;
	}

	public void mutacao(Individuo[] populacao, double porcentagem) {
		int length = populacao.length;
		for (int i = 0; i < length; i++) {
			double sorteado = Math.random();
			if (sorteado <= porcentagem) {
 				int tamanho = populacao[0].getTamanho();
				Random random = new Random(System.nanoTime());
				int pos1 = random.nextInt(tamanho);
				int pos2 = random.nextInt(tamanho);
				int[] genes = populacao[i].getGenes();
				int aux = genes[pos1];
				populacao[i].setGene(pos1, genes[pos2]);
				populacao[i].setGene(pos2, aux);
			}
		}
	}

	class IndividuoComparator implements Comparator<Individuo> {

		@Override
		public int compare(Individuo o1, Individuo o2) {
			int custo1 = o1.getCustoCaminho();
			int custo2 = o2.getCustoCaminho();

			if (custo1 > custo2)
				return 1;
			else if (custo1 < custo2)
				return -1;
			else
				return 0;
		}
	}

	public void novaPopulacao(Individuo[] populacao, Individuo[] novos) {
		int index = populacao.length;
		int length = novos.length;
		Arrays.sort(populacao, new IndividuoComparator());
		
		for(int i = 0; i<length; i++) {
			populacao[--index] = novos[i];
		}
	}

	public Individuo melhor(Individuo[] populacao) {
		int melhor = Integer.MAX_VALUE;
		int size = populacao.length;
		int index = -1;

		for (int i = 0; i < size; i++) {
			int custo = populacao[i].getCustoCaminho();
			if (custo < melhor) {
				melhor = custo;
				index = i;
			}
		}

		return populacao[index];
	}

}
