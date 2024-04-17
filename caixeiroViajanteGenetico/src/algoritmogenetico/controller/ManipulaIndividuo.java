/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmogenetico.controller;

import algoritmogenetico.model.Individuo;
import java.util.Random;

/**
 *
 * @author Douglas
 */
public class ManipulaIndividuo {
    
    private Random aleatorio;
    
    public ManipulaIndividuo (){
        this.aleatorio = new Random(System.nanoTime());
    }
    
    public void criarIndividuoAleatorio (Individuo individuo){
        for (int i = 0; i < individuo.getTamanho(); i++) {
            int posicao = 0;
            do {
                posicao = aleatorio.nextInt(individuo.getTamanho());
            } while (individuo.getGenes()[posicao] != -1);
            individuo.setGene(posicao, i);
        }
    }
    
}
