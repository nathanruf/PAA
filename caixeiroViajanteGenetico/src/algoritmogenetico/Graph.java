/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algoritmogenetico;

import java.util.ArrayList;

/**
 *
 * @author Douglas
 */
public interface Graph {
    
    public void setEdge (int ori, int target, int weight);
    
    public ArrayList<Integer> getAdjVertex (int node);
    
    public int getVertexSize ();
    
    public void printGraph ();
    
    public int getEdgeWeight (int ori, int target);
    
}
