package ch.epfl.imhof.osm;

import ch.epfl.imhof.Attributes;

/**
 * sert de classe mère aux autres classes représentant les entités OSM (donc
 * Node, Way et Relation)
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 */

public abstract class OSMEntity {

    private final long id;
    private final Attributes attributes;

    /**
     * construit une entité OSM dotée d'un identifiant unique et des attributs
     * donnés.
     * 
     * @param id
     *            identifiant unique de l'entité
     * 
     * @param attributes
     *            table associative d'attributs avec les paires clef/valeur
     *
     */
    
    public OSMEntity(long id, Attributes attributes) {
        this.id = id;
        this.attributes = attributes;
    }

    /**
     * getteur de l'identifiant unique de l'entité
     * 
     * @return id, 
     *          identifiant unique de l'entité
     * 
     */
    
    public long id() {
        return id;
    }

    /**
     * getteur des attributs de l'entité
     * 
     * @return attributes, 
     *                  table associative d'attributs avec les paires clef/valeur
     *                  
     */
    
    public Attributes attributes() {
        return attributes;
    }

    /**
     * méthode pour savoir si l'entité possiède l'attribut passé en argument
     * 
     * @param key
     *            une entrée dans la table associative associée à une valeur
     * 
     * @return  
     *              vrai si l'entité possède l'attribut passé en argument,
     *              faux sinon
     * 
     */
    
    public boolean hasAttribute(String key) {
        return attributes.contains(key);
    }

    /**
     * retourne la valeur de l'attribut donné, ou null si celui-ci n'existe pas
     * 
     * @param key
     *            une entrée dans la table associative associée à une valeur
     * 
     * @return String,
     *            la valeur de la clef donné dans la table associative
     * 
     */

    public String attributeValue(String key) {
        return attributes.get(key);
    }

    /**
     * sert de classe mère à toutes les classes de bâtisseurs d'entités OSM
     *
     * @author Julien von Felten (234865)
     * @author Simon Haefeli (246663)
     * 
     */

    public static class Builder {
        
        protected final long id;
        protected final Attributes.Builder attributes = new Attributes.Builder();
        private boolean call = false;

        /**
         * construit un bâtisseur pour une entité OSM identifiée par l'entier
         * donné
         * 
         * @param id
         *            identifiant unique de l'entité
         * 
         */

        public Builder(long id) {
            this.id = id;
        }

        /**
         * ajoute l'association (clef, valeur) donnée à l'ensemble d'attributs
         * de l'entité en cours de construction
         * 
         * @param key
         *            une entrée dans la table associative associée à une valeur
         * 
         * @param value
         *            valeur associée à la clef donnée en argument
         * 
         */

        public void setAttribute(String key, String value) {
            attributes.put(key, value);
        }

        /**
         * déclare que l'entité en cours de construction est incomplète
         * 
         */

        public void setIncomplete() {
            call = true;
        }

        /**
         * méthode pour savoir si l'entité en cours de construction est
         * imcomplète
         * 
         * @return call, 
         *              boolean: vrai si l'entité est imcomplète, 
         *                       faux sinon
         * 
         */

        public boolean isIncomplete() {
            return call;

        }

    }

}
