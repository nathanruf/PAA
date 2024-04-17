package algoritmogenetico;

import algoritmogenetico.controller.AlgoritmoGenetico;
import algoritmogenetico.controller.ManipulaIndividuo;
import algoritmogenetico.model.Individuo;

import java.util.ArrayList;

public class ProblemaCaixeiroViajanteAlgoritmoGenetico {

	public static void main(String[] args) {
		
		FileManager fileManager = new FileManager();
		ArrayList<String> text = fileManager.stringReader("./data/Teste_2.txt");

		Graph graph = null;

		int nVertex = 0;

		for (int i = 0; i < text.size(); i++) {
			String line = text.get(i);
			if (i == 0) {
				nVertex = Integer.parseInt(line.trim());
				graph = new AdjMatrix(nVertex);
			} else {
				int oriVertex = Integer.parseInt(line.split(" ")[0]);
				String splits[] = line.substring(line.indexOf(" "), line.length()).split(";");
				for (String part : splits) {
					String edgeData[] = part.split("-");
					int targetVertex = Integer.parseInt(edgeData[0].trim());
					int weight = Integer.parseInt(edgeData[1]);

					graph.setEdge(oriVertex, targetVertex, weight);
				}
			}
		}

		int kIndividuos = 1000;
		ManipulaIndividuo controle = new ManipulaIndividuo();
		AlgoritmoGenetico ag = new AlgoritmoGenetico();
		Individuo[] populacao = new Individuo[kIndividuos];
		Individuo melhor;
		
		for (int i = 0; i < kIndividuos; i++) {
			Individuo individuo = new Individuo(nVertex);
			controle.criarIndividuoAleatorio(individuo);
			populacao[i] = individuo;
		}
		ag.fitness(populacao, graph);
		melhor = ag.melhor(populacao);
		
		final int NUM_GERACOES = 1000;
		final double PORCENTAGEM_PAIS = 0.7;
		final double PORCENTAGEM_MUTACAO = 0.03;

		for (int i = 0; i < NUM_GERACOES; i++) {
			Individuo[] novos = ag.geraDescendentes(populacao, (int) (kIndividuos * PORCENTAGEM_PAIS));
			ag.mutacao(novos, PORCENTAGEM_MUTACAO);
			ag.fitness(novos, graph);
			Individuo aux = ag.melhor(novos);
			if(aux.getCustoCaminho() < melhor.getCustoCaminho())
				melhor = aux;
			ag.novaPopulacao(populacao, novos);
		}
		
		int tamanho = populacao[0].getTamanho();
		int[] genes = melhor.getGenes();
		System.out.println("Custo " + melhor.getCustoCaminho());
		for(int i = 0; i<tamanho; i++) {
			System.out.printf("%d\t->\t" ,genes[i]);
			if(i % 8 == 0 && i != 0)
				System.out.println("\n");
		}
	}

}
