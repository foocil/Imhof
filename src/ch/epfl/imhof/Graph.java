package ch.epfl.imhof;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**Création d'un graphe non orienté contenant des variables de type N
 * 
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 */

public final class Graph<N> {
    private final Map<N, Set<N>> neighbors;
    
    /**
     * construit un graphe non orienté avec la table d'adjacence donnée 
     * 
     * @param neighbors
     *            une table associative de noeuds et de leurs voisins
     *                
     */
    
    public Graph(Map<N, Set<N>> neighbors){

        Map<N, Set<N>> mapNeighbors = new HashMap<>();
        
        //copie tous les sets de la Map neighbors pour rendre immuable
        for (Map.Entry<N, Set<N>> voisinSet: neighbors.entrySet()){
            
            mapNeighbors.put(voisinSet.getKey(), Collections.unmodifiableSet(new HashSet<>(voisinSet.getValue())));

        }
        
        this.neighbors = Collections.unmodifiableMap(new HashMap<>(mapNeighbors));      
        
    }
    
    /**
     * retourne l'ensemble des nœuds du graphe
     * 
     * @return Set<N>,
     *           Ensemble des noeuds du graphe
     */
    
    public Set<N> nodes(){
        
        return  neighbors.keySet();
    }
    
    /**
     * retourne l'ensemble des nœuds voisins du nœud donné
     * 
     * @param node
     *          un noeud, élément du graphe
     *  
     * @throws IllegalArgumentException
     *              si le nœud donné ne fait pas partie du graphe
     *              
     * @return Set<N>,
     *          Ensemble de voisins correspondant à un noeud
     */
  
    public Set<N> neighborsOf(N node){
        if(neighbors.get(node)==null){
            throw new IllegalArgumentException ("Noeud inexistant");
        }
        return neighbors.get(node);
        
    }
    
    /**
     *  Sert de bâtisseur à la classe Graph
     * 
     * @author Julien von Felten (234865)
     * @author Simon Haefeli (246663)
     */
    
    public final static class Builder<N> {
        
        private final Map<N, Set<N>> neighbors = new HashMap<>();
        /**
         * Ajoute le nœud donné au graphe en cours de construction,
         *  s'il n'en faisait pas déjà partie
         * 
         * @param n
         *          un noeud, élément du graphe
         * 
         */
        
        public void addNode(N n){
            if(!neighbors.containsKey(n)){
                neighbors.put(n, new HashSet<N>());
            }
        }
        
        /**
         * Ajoute une arête entre les deux nœuds donnés(
         *  ajoute le premier nœud
         *  à l'ensemble des voisins du second, et vice versa)
         * 
         * @param n1
         *           premier noeud pour l'arête
         * 
         * @param n2
         *          second noeud pour l'arête
         * 
         * @throws IllegalArgumentException
         *                 exception si l'un des nœuds n'appartient pas au graphe en cours de construction
         */
        
        public void addEdge(N n1, N n2){
            if(!neighbors.containsKey(n2) || !neighbors.containsKey(n1)){
                throw new IllegalArgumentException ("Le noeud n'est pas contenu !");
            }
            neighbors.get(n1).add(n2);
            neighbors.get(n2).add(n1);

        }
        
        /**
         *  Construit le graphe composé des nœuds et arêtes ajoutés jusqu'à présent au bâtisseur
         * 
         * @return 
         *          Graph non orienté contenant des variables de type N
         */
        
        public Graph<N> build(){
            return new Graph<N>(neighbors);
        }
    }
}
