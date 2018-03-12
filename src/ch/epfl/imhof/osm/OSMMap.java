package ch.epfl.imhof.osm;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * représente une carte OpenStreetMap, un ensemble de chemins et de relations
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 * 
 */

public final class OSMMap {
    private final List<OSMWay> ways;
    private final List<OSMRelation> relations;
  
    
    /**
     * construit une carte OSM avec les chemins et les relations donnés.
     * 
     * @param ways
     *          un chemin, représentant une séquence de noeuds
     *          
     * @param relations
     *          représentant des ensembles d'autres entités appelés membres
     *          
     */
    
    public OSMMap(Collection<OSMWay> ways, Collection<OSMRelation> relations){
        this.ways = Collections.unmodifiableList
                (new ArrayList<OSMWay>(ways));
        this.relations = Collections.unmodifiableList
                (new ArrayList<OSMRelation>(relations));
        
        
    }
    
    /**
     *  getteur de la liste des chemins de la carte
     * 
     * @return ways,
     *          la liste des chemins de la carte
     *          
     */
    
    public List<OSMWay> ways() {
        return ways;
    }
    /**
     *  getteur de la liste des relations de la carte
     * 
     * @return relations,
     *           la liste des relations de le carte
     */
    
    public List<OSMRelation> relations() {
        return relations;
    }
    
    /**
     * Batisseur de la classe OSMMap, pouvant stocker des noeuds, 
     * 
     * @author Julien von Felten (234865)
     * @author Simon Haefeli (246663)
     * 
     */
    
    public final static class Builder {
        
        private final Map<Long,OSMNode> nodes = new HashMap<>();
        private final Map<Long,OSMWay> ways = new HashMap<>();
        private final Map<Long,OSMRelation> relations = new HashMap<>();
       

        /**
         * ajoute le nœud donné au bâtisseur
         * 
         * @param newNode
         *          un nouveau nœud OSM à ajouter au batisseur
         *          
         */
        
        public void addNode(OSMNode newNode) {
            nodes.put(newNode.id(),newNode);
        }
 
        /**
         * retourne le node dont l'identifiant est passé en argument ou null si le noeud
         * n'a pas été ajouté précédemment au batisseur
         * 
         * @param id
         *          identifiant du noeud cherché
         *          
         * @return  
         *          noeud qui correspond à l'id passé en argument
         *              
         */
        
        public OSMNode nodeForId(long id) {
            return nodes.get(id);
        }
        
        /**
         * ajoute le chemin donné à la carte en cours de construction
         * 
         * @param newWay
         *              nouveau chemin à ajouter à la carte
         */
        
        public void addWay(OSMWay newWay){
            ways.put(newWay.id(),newWay);
        }

        /**
         * retourne le chemin dont l'identifiant est passé en argument ou null si le chemin
         * n'a pas été ajouté précédemment au batisseur
         * 
         * @param id
         *          identifiant du chemin à retourner
         * 
         * @return 
         *          chemin qui correspond à l'id passé en argument
         *
         */
        
        public OSMWay wayForId(long id){
            return ways.get(id);
        }
        
        /**
         * ajoute la relation donnée à la carte en cours de construction
         * 
         * @param newRelation
         *              relation à ajouter à la carte
         */
        
        public void addRelation(OSMRelation newRelation){
            relations.put(newRelation.id(),newRelation);
        }

        /**
         * retourne la relation dont l'identifiant est passé en argument ou null si la relation
         * n'a pas été ajoutée précédemment au batisseur
         * 
         * @param id 
         *          identifiant de la relation à retourner
         * 
         * @return 
         *          relation qui correspond à l'id passé en argument
         */
        
        public OSMRelation relationForId(long id){
            return relations.get(id);
        }

        /**
         * construit une carte OSM avec
         * les chemins et les relations ajoutés jusqu'à présent
         * 
         * @return OSMMap
         *          carte OSM avec les chemins et les relations ajoutés
         */
        
        public OSMMap build (){
            return new OSMMap(ways.values(), relations.values());
        }
        
    }
    
}
