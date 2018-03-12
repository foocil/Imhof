package ch.epfl.imhof.osm;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import ch.epfl.imhof.Attributes;

/**
 * représente un chemin OSM, une séquence de noeuds avec des attributs.
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 * 
 */

public final class OSMWay extends OSMEntity {

    private final List<OSMNode> nodes;

    /**
     * construit un chemin étant donnés un identifiant unique, ses nœuds et ses
     * attributs
     * 
     * @param id
     *            identifiant unique de l'entité
     * 
     * @param nodes
     *            représente une liste de noeud OSM
     * 
     * @param attributes
     *            table associative d'attributs avec les paires clef/valeur
     * 
     * @throws IllegalArgumentException
     *             exception si la liste de nœuds possède moins de deux éléments
     * 
     */
    
    public OSMWay(long id, List<OSMNode> nodes, Attributes attributes) {
        super(id, attributes);
        if (nodes.size() < 2) {
            throw new IllegalArgumentException("Liste de moins de 2 elements");

        }
        this.nodes = Collections
                .unmodifiableList(new ArrayList<OSMNode>(nodes));
    }

    /**
     * retourne le nombre de nœuds du chemin
     * 
     * @return
     *           le nombre de noeuds du chemin
     *              
     */
    
    public int nodesCount() {
        return nodes.size();

    }

    /**
     * getteur de la liste des nœuds du chemin
     * 
     * @return nodes, 
     *          la liste des noeuds du chemin
     *          
     */
    
    public List<OSMNode> nodes() {
        return nodes;

    }

    /**
     * retourne la liste des nœuds du chemin sans le dernier si celui-ci est
     * identique au premier
     * 
     * @return nodes, 
     *              la liste des noeuds du chemin sans le dernier si celui-ci est identique au premier
     * 
     */
    
    public List<OSMNode> nonRepeatingNodes() {

        if (this.isClosed()) {
            return nodes.subList(0, nodes.size() - 1);

        } else {

            return nodes;
        }

    }

    /**
     * retourne le premier nœud du chemin
     * 
     * @return 
     *           le premier noeud du chemin
     */
    
    public OSMNode firstNode() {
        return nodes.get(0);

    }

    /**
     * retourne le dernier nœud du chemin
     * 
     * @return  
     *          le dernier noeud du chemin
     */
    
    public OSMNode lastNode() {
        return nodes.get(nodes.size() - 1);

    }
    
    /**
     * méthode pour savoir si le chemin est fermé (si le premier et le dernier noeuds sont les mêmes)
     * 
     * @return  
     *              vrai si le chemin est fermé, 
     *              faux sinon
     */
    
    public boolean isClosed() {
        if (firstNode().equals(lastNode())) {
            return true;

        } else {

            return false;
        }

    }

    /**
     * bâtisseur à la classe OSMWay ce qui permet de construire un chemin en plusieurs étapes
     * 
     * @author Julien von Felten (234865)
     * @author Simon Haefeli (246663)
     * 
     */
    
    public static class Builder extends OSMEntity.Builder {

        private final List<OSMNode> nodes = new ArrayList<>();

        /**
         * construit un chemin avec l'identifiant donné
         * 
         * @param id
         *            identifiant unique de l'entité
         *
         */
        
        public Builder(long id) {
            super(id);

        }

        /**
         * ajoute un nœud à (la fin) des nœuds du chemin en cours de construction
         * 
         * 
         * @param newNode
         *            liste des noeuds en cours de construction
         *            
         */
        
        public void addNode(OSMNode newNode) {
            nodes.add(newNode);

        }

        /**
         * construit et retourne le chemin ayant les nœuds et les attributs ajoutés jusqu'à présent
         * 
         * 
         * @throw IllegalStateException 
         *                   exception si le chemin en cours de construction est incomplet
         *                          
         * @return OSMWay
         *             le chemin ayant les noeuds et les attributs ajoutes jusqu a present
         * 
         */
        
        public OSMWay build() {
            if (isIncomplete()) {
                throw new IllegalStateException("OSMWay incomplet");
            }
            return new OSMWay(id, nodes, attributes.build());
        }

        /**
         * 
         * 
         * 
         * méthode pour savoir si un chemin est en cours de construction, si moins de deux noeuds, il sera
         * incomplet
         * 
         * 
         * @return 
         *           vrai si le chemin possède moins de deux noeuds ou est en construction
         * 
         */
        
        @Override
        public boolean isIncomplete() {
            if (nodes.size() < 2) {
                return true;
            } else {
                return super.isIncomplete();
            }
            
        }

    }

}
