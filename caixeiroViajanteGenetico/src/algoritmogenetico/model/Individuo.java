/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmogenetico.model;

/**
 *
 * @author Douglas
 */
public class Individuo implements Cloneable {

	private int[] genes;
	private int tamanho;
	private int custoCaminho;

	public Individuo(int tamanho) {
		this.tamanho = tamanho;
		this.genes = new int[tamanho];
		for (int i = 0; i < tamanho; i++) {
			this.genes[i] = -1;
		}
	}
	
	public Individuo(int[] genes) {
		this.tamanho = genes.length;
		this.genes = genes;
	}

	public void setGene(int posicao, int gene) {
		this.genes[posicao] = gene;
	}

	public int[] getGenes() {
		return genes;
	}

	public int getTamanho() {
		return tamanho;
	}

	public void printIndividuo() {
		for (Integer gene : this.genes) {
			System.out.print(gene + "\t");
		}
		System.out.println("");

	}

	public int getCustoCaminho() {
		return custoCaminho;
	}

	public void setCustoCaminho(int custoCaminho) {
		this.custoCaminho = custoCaminho;
	}

}
