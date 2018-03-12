package ch.epfl.imhof;

/**
 * représente une entité de type T dotée d'attributs
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 */

public final class Attributed<T> {
    private final T t;
    private final Attributes attributes;
       
    /**
     * construit une valeur attribuée dont la valeur et les attributs sont ceux
     *  donnés
     * 
     * @param value
     *                  attribution d'un attribut de la classe Attributes 
     * @param attributes
     *                  table associative d'attributs avec les paires clef/valeur
     */
    
    public Attributed(T value, Attributes attributes) {
       t=value;
       this.attributes = attributes;
    }
    
    /**
     * méthode qui retourne la valeur à laquelle les attributs sont attachés
     * 
     * @return t
     *          attribution d'un attribut de la classe Attributes 
     */
    
    public T value() {
        return t;
        
    }
    
    /**
     * méthode qui retourne les attributs attachés à la valeur.
     * 
     * @return attributes
     *                  table associative d'attributs avec les paires clef/valeur
     */
    
    public Attributes attributes(){
        return attributes;

    }
     
    /**
     * méthode qui retourne vrai si et seulement si les attributs 
     * incluent celui dont le nom est passé en argument
     * 
     * @param attributeName
     *                  une entrée dans la table associative associée à une valeur
     * 
     * @return  
     *                  vrai si les attributs incluent celui de attributeName,
     *                  faux sinon
     */
    
    public boolean hasAttribute(String attributeName){
        return attributes.contains(attributeName);
        
    }
    
    /**
     * méthode qui retourne la valeur associée à l'attribut donné, 
     * ou null si celui-ci n'existe pas
     * 
     * @param attributeName
     *                      une entrée dans la table associative associée à une valeur
     * @return String,
     *                      valeur associée à la clef dans la table associative ou null si
     *                      attributeName n'existe pas dans attributs.
     */
    
    public String attributeValue(String attributeName){
        return attributes.get(attributeName);
        
    }
    
    /**
     * méthode  qui retourne la valeur associée à l'attribut donné, 
     * ou la valeur par défaut donnée si celui-ci n'existe pas
     * 
     * @param attributeName
     *                    une entrée dans la table associative associée à une valeur
     *                     
     * @param defaultValue
     *                    un string donné et retourné si la clef n'a pas de valeur associée
     *                  
     * @return String,
     *                    valeur associée à la clef dans la table associative ou la defaultValue si la clef n'a pas
     *                    de valeur associée
     * 
     */
    
    public String attributeValue(String attributeName, String defaultValue) {
        return attributes.get(attributeName, defaultValue);
    }
    
    /**
     * méthode qui retourne la valeur de la clé, ou la valeur par défaut si la valeur 
     * n'est pas entière
     * 
     * @param attributeName
     *                  une entrée dans la table associative associée à une valeur
     *                     
     * @param defaultValue
     *                  un entier donné et retourné si la clef n'a pas de valeur associée
     *                  
     * @return int,
     *                  valeur associée à la clef dans la table associative  ou defaultValue si la clef n'a pas 
     *                  de valeur associée
     * 
     */
    
    public int attributeValue(String attributeName, int defaultValue){
        return attributes.get(attributeName, defaultValue);
        
    }
}
