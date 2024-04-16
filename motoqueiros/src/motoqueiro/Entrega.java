package motoqueiro;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Entrega {

	private static ArrayList<Integer> entregas = new ArrayList<Integer>();;

	private static void ler(String nome) {
		File file = new File(nome);
		try {
			Scanner scan = new Scanner(file);
			while (scan.hasNextInt())
				entregas.add(scan.nextInt());
			scan.close();
		} catch (Exception e) {
			System.out.println("Ocorreu um erro na leitura do arquivo");
		}
		entregas.sort(null);
	}

	public static void calcular() {
		ler("exemplo.txt");
		int ultimo1 = entregas.remove(entregas.size() - 1);
		int ultimo2 = entregas.remove(entregas.size() - 1);
		int maiorEnt = ultimo1 > ultimo2 ? ultimo1 : ultimo2;
		int segMaiorEnt = ultimo1 > ultimo2 ? ultimo2 : ultimo1;
		double total = 0;
		int maiorIdeal = 0;
		int numEnt = entregas.size();
		int ok = 0;
		int tAtual = 0;
		int tM1 = 0;
		ArrayList<Integer> m1 = new ArrayList<Integer>();
		ArrayList<Integer> aux = new ArrayList<Integer>();
		if (numEnt != 0) {
			for (int i = 0; i < numEnt; i++)
				total += entregas.get(i);
			maiorIdeal = defineIdeal((total * 2) + maiorEnt + segMaiorEnt, maiorEnt, segMaiorEnt);
			double media = (total + maiorEnt + segMaiorEnt) / 2;
			
			if (maiorEnt < media) {
				tM1 = entregas.get(numEnt - 1);
				int j, indPenTermo, i = 0;
				while (tM1 > maiorIdeal) {
					i++;
					tM1 = entregas.get(numEnt - 1 - i);
				}
				m1.add(tM1);
				if (tM1 == maiorIdeal)
					ok = 1;

				while (ok != 1 && i != numEnt - 1) {
					j = 0;
					indPenTermo = numEnt - 2 - i;
					aux.clear();
					aux.add(entregas.get(numEnt - 1 - i));
					tAtual = aux.get(0);

					while (ok != 1 && !aux.isEmpty()) {
						tAtual += entregas.get(j);

						if (tAtual < maiorIdeal)
							aux.add(entregas.get(j));
						else if (tAtual == maiorIdeal) {
							aux.add(entregas.get(j));
							tM1 = tAtual;
							m1 = aux;
							ok = 1;
						} else {
							tAtual -= entregas.get(j);
							if (tAtual > tM1) {
								tM1 = tAtual;
								m1.clear();
								for (int x : aux)
									m1.add(x);
							}
							tAtual -= aux.get(aux.size() - 1);
							j = entregas.lastIndexOf(aux.remove(aux.size() - 1));
						}

						if (j == indPenTermo && ok != 1) {
							if (tAtual > tM1) {
								tM1 = tAtual;
								m1.clear();
								for (int x : aux)
									m1.add(x);
							}
							tAtual -= aux.remove(aux.size() - 1);
							tAtual -= aux.get(aux.size() - 1);
							j = entregas.lastIndexOf(aux.remove(aux.size() - 1));
						}
						j++;
						if (j == numEnt)
							aux.clear();
					}
					i++;
				}
			}
		}

		total *= 2;
		tM1 *= 2;
		int tM2 = (int) (total - tM1);

		if (tM1 > tM2) {
			tM1 += segMaiorEnt;
			tM2 += maiorEnt;
			m1.add(segMaiorEnt);
			entregas.add(maiorEnt);
		} else {
			tM2 += segMaiorEnt;
			tM1 += maiorEnt;
			m1.add(maiorEnt);
			entregas.add(segMaiorEnt);
		}

		if (segMaiorEnt == maiorEnt)
			entregas.add(maiorEnt);

		for (Integer entrega : m1)
			entregas.remove(entrega);

		try {
			FileWriter f = new FileWriter("exemplo_saida.txt");
			if (tM1 > tM2) {
				f.write("Melhor tempo: " + tM1);
				for (Integer entrega : m1)
					f.write(" \n" + entrega);
				f.write("\n\nTempo moto 2: " + tM2);
				for (int entrega : entregas)
					f.write(" \n" + entrega);
			} else {
				f.write("Melhor tempo: " + tM2);
				for (int entrega : entregas)
					f.write(" \n" + entrega);
				f.write("\n\nTempo moto 2: " + tM1);
				for (Integer entrega : m1)
					f.write(" \n" + entrega);
			}

			f.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int defineIdeal(double soma, int maiorEnt, int segMaiorEnt) {
		double ideal = soma / 2;
		int res = -1, somaSemMaiores = (int) ((soma - maiorEnt - segMaiorEnt) / 2);

		if (isDouble(ideal)) {
			int idealMais = (int) (ideal + 1), idealMenos = (int) ideal;
			if (!isDouble((idealMais - segMaiorEnt) / 2))
				res = (idealMais - segMaiorEnt) / 2;
			else if (!isDouble((idealMais - maiorEnt) / 2))
				res = (idealMais - maiorEnt) / 2;
			else if (!isDouble((idealMenos - segMaiorEnt) / 2))
				res = (idealMenos - segMaiorEnt) / 2;
			else
				res = (idealMenos - maiorEnt) / 2;

		} else {
			if (!isDouble((ideal - maiorEnt) / 2))
				res = (int) ((ideal - maiorEnt) / 2);
			else
				res = (int) Math.ceil((ideal - segMaiorEnt) / 2);
		}

		return res < (somaSemMaiores - res) ? res : (somaSemMaiores - res);
	}

	private static boolean isDouble(double num) {
		return num % 1 != 0;
	}
}