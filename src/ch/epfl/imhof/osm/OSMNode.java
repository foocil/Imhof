package ch.epfl.imhof.osm;

import ch.epfl.imhof.Attributes;
import ch.epfl.imhof.PointGeo;

/**
 * représente un nœud OSM, un point à la surface de la Terre
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 * 
 */

public final class OSMNode extends OSMEntity {

    private final PointGeo position;

    /**
     * 
     * construit un nœud OSM avec un identifiant unique, sa position géographique et ses attributs donnés
     * 
     * @param id
     *           identifiant unique de l'entité
     * 
     * @param position
     *            Un point à la surface de la Terre, en coordonnées sphériques
     * 
     * @param attributes
     *            table associative d'attributs avec les paires clef/valeur
     * 
     */
    
    public OSMNode(long id, PointGeo position, Attributes attributes) {
        super(id, attributes);
        this.position = position;

    }

    /**
     * getteur de la position du nœud
     * 
     * @return position 
     *          Un point à la surface de la Terre, en coordonnées sphériques
     *          
     */
    
    public PointGeo position() {
        return position;

    }

    /**
     * bâtisseur de classe OSMNode et permet de construire un nœud en
     * plusieurs étapes
     * 
     * @author Julien von Felten (234865)
     * @author Simon Haefeli (246663)
     * 
     */
    
    public static class Builder extends OSMEntity.Builder {

        private final PointGeo position;

        /**
         * construit un bâtisseur pour un nœud ayant l'identifiant et la
         * position donnés
         * 
         * @param id
         *            identifiant unique de l'entité
         * 
         * @param position
         *            Un point à la surface de la Terre, en coordonnées sphériques
         *
         */
        
        public Builder(long id, PointGeo position) {
            super(id);
            this.position = position;

        }

        /**
         * construit un nœud OSM avec l'identifiant et la position et les attributs
         * 
         * @throws IllegalStateException
         *             exception si le nœud en cours de construction est incomplet
         * 
         * @return OSMNode
         *              un point à la surface de la terre avec son identifiant, sa position et sa liste d'attributs.
         *             
         */
        
        public OSMNode build() {
            if (isIncomplete()) {
                throw new IllegalStateException("OSMNode incomplet");
            }
            return new OSMNode(id, position, attributes.build());
        }

    }

}
