/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package grafos;

import java.util.ArrayList;
/**
 *
 * @author humberto e douglas
 */
public interface Grafo {
	
    /**
     * Indica a cardinalidade do conjunto V.
     * @return número de vértices do grafo.
     */
    public int numeroDeVertices();
    
    /**
     * Indica a cardinalidade do conjunto A.
     * @return número de arestas do grafo.
     */    
    public int numeroDeArestas();
    
    /**
     * Indica os vértices adjacentes ao vertice indicado.
     * @param vertice
     * @return Uma coleção de vértices adjacentes ao vértice indicado.
     * @throws java.lang.Exception Uma exceção é lançada quando o vértice indicado
     * não existe.
     */
    public ArrayList<Vertice> adjacentesDe(Vertice vertice);
        
    /**
     * Retorna uma coleção com as arestas existentes entre origem e destino.
     * @param origem
     * @param destino
     * @return Uma coleção com as arestas existentes entre origem e destino.
     * @throws java.lang.Exception Se não existe origem e/ou destino.
     */
    public Aresta arestaEntre(Vertice origem, Vertice destino);
    
    public Aresta[] arestas();
    
    /**
     * Retorna o conjunto de vértices do grafo.
     * @return vértices do grafo.
     */
    public Vertice[] vertices();
    
    public Vertice getVertice(int id);
    
}
