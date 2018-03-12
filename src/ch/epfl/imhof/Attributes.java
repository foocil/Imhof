package ch.epfl.imhof;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * représente une table assosiative d'attributs avec des paires 
 * clef/valeur (en chaines de caractères) associées.
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 *
 */

public final class Attributes {
    private final Map<String, String> attributes;

    /**
     * construit un ensemble d'attributs avec les paires clef/valeur
     * présentes dans la table associative donnée.
     * 
     * @param attributes
     *            table associative d'attributs avec les paires clef/valeur
     *
     */
    
    public Attributes(Map<String, String> attributes) {
        this.attributes = Collections.unmodifiableMap(new HashMap<String, String>(attributes));

    }

    /**
     * méthode qui test si l'ensemble d'attributs est vide
     * 
     * @return  
     *          vrai si l'ensemble d'attributs est vide 
     *          faux si l'ensemble d'attributs n'est pas vide.
     * 
     */
    public boolean isEmpty() {
        return attributes.isEmpty();
    }

    /**
     * méthode qui indique si l'ensemble d'attributs contient une clef donnée
     * 
     * @param key
     *            une entrée dans la table associative associée à une valeur 
     *                    
     * @return  
     *            vrai si l'ensemble d'attributs contient la clef donnée,
     *            faux sinon
     *                  
     */

    public boolean contains(String key) {
        return attributes.containsKey(key);

    }

    /**
     * méthode qui retourne la valeur associée à la clef donnée, ou null si la
     * clef n'existe pas.
     * 
     * @param key
     *            une entrée dans la table associative associée à une valeur
     *            
     * @return String,
     *           la valeur associée à la clef donnée dans la table associative
     * 
     */
    
    public String get(String key) {
        return attributes.get(key);

    }

    /**
     * méthode qui retourne la valeur associée à la clef donnée, ou la valeur
     * par défaut donnée si aucune valeur ne lui est associée.
     * 
     * @param key
     *            une entrée dans la table associative associée à une valeur
     *            
     * @param defaultValue
     *            un string donné et retourné si la clef n'a pas de valeur associée
     *            
     * @return String,
     *            la valeur associée à la clef dans la table associative
     *         
     * @return String,
     *            un string donné et retourné si la clef n'a pas de valeur associée
     *                  
     */

    public String get(String key, String defaultValue) {
        return attributes.getOrDefault(key, defaultValue);

    }

    /**
     * méthode qui retourne la valeur de la clé, ou la valeur par défaut si la valeur 
     * n'est pas entière
     * 
     * 
     * @param key
     *            une entrée dans la table associative associée à une valeur
     *            
     * @param defaultValue
     *            un entier donné si la clef n'a pas de valeur associée
     * 
     * @return intValue,
     *          une entrée dans la table associative associée à une key
     * 
     * @return defaultValue,
     *            un entier donné et retourné si la clef n'a pas de valeur associée
     * 
     */
    
    public int get(String key, int defaultValue) {
        try {
            int intValue = Integer.parseInt(attributes.get(key));
            return intValue;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * méthode qui retourne une version filtrée des attributs ne contenant que
     * ceux dont le nom figure dans l'ensemble passé.
     * 
     * @param keysToKeep
     *            Une table associative de clef/valeur voulant être gardée 
     *            dans une table
     * 
     * @return attributeToKeep,
     *            table associative ayant gardé seulement les éléments présents
     *            dans keysToKeep et dans attributes.
     * 
     */
    public Attributes keepOnlyKeys(Set<String> keysToKeep) {
        Map<String, String> attributeToKeep = new HashMap<>();
        for (String s : keysToKeep)
            if (attributes.containsKey(s)) {
                attributeToKeep.put(s, attributes.get(s));
            }
        return new Attributes(attributeToKeep);
    }
    
    /**
     * builder de la classe Attributes
     * 
     * @author Julien von Felten (234865)
     * @author Simon Haefeli (246663)
     */
    
    public final static class Builder {

        private Map<String, String> attributes = new HashMap<>();

        /**
         * méthode qui ajoute l'association (clef/valeur) donnée à l'ensemble
         * d'attributs en cours de construction. 
         * 
         * @param key
         *          une entrée dans la table associative à associer à une valeur
         *          
         * @param value
         *          la valeur à associer à la clef dans la table associative
         *        
         */
        
        public void put(String key, String value) {
            attributes.put(key, value);

        }

        /**
         * méthode qui construit un ensemble d'attributs contenant les
         * associations clef/valeur ajoutées jusqu'à présent.
         * 
         * 
         * @return attributes,
         *          table associative d'attributs contenant les associations clef/valeur ajoutées jusqu'à présent
         *                 
         *                  
         */
        public Attributes build() {
            return new Attributes(attributes);

        }
    }

}
