package ch.epfl.imhof.painting;

import java.util.function.Predicate;

import ch.epfl.imhof.Attributed;

/**
 * détermine étant donnée une entité attribuée si elle doit être gardée ou non
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 */
public final class Filters {

    private Filters() {}

 
    /**
     * retourne un prédicat qui n'est vrai que si la valeur attribuée 
     * à laquelle on l'applique possède un attribut portant ce nom, indépendemment de sa valeur
     * 
     * @param attribut
     *          l'attribut que l'on veut savoir s'il est attribué
     *          
     * @return Predicate <Attributed<?>>
     *          predicate confirmant si l'attribut est possédé ou non
     */
    public static Predicate<Attributed<?>> tagged(String attribut) {
        return x -> x.attributes().contains(attribut);
    }

    /**
     * retourne un prédicat qui n'est vrai que si la valeur attribuée à laquelle 
     * on l'applique possède un attribut portant le nom donné et si de plus la valeur 
     * associée à cet attribut fait partie de celles données
     * 
     * @param key
     *          l'attribut que l'ont veut savoir s'il est attribué
     * 
     * @param attributs
     *          tableau des valeurs de l'attribut
     * 
     * @return Predicate <Attributed<?>>
     *          predicate confirmant si la key a au moins une des valeurs du tableau
     * 
     */
    public static Predicate<Attributed<?>> tagged(String key, String ... values) {
        return x->{
            boolean contained=false;
            if(x.attributes().contains(key)){
                
                String attributValue = x.attributes().get(key);
                for (int i = 0; i < values.length; i++) {
                    if(attributValue.equals(values[i])){
                        contained = true;
                        break;
                    }
                }
            }
            return contained;  
        };
   
    }

   
    /** retourne un prédicat qui n'est vrai que lorsqu'on l'applique à une entité attribuée appartenant à cette couche
     * 
     * @param n 
     *          numéro de couche 
     * 
     * @return Predicate <Attributed<?>>
     *          predicate confirmant si on applique une entité attribuée appartenant à cette couche
     *
     */
    public static Predicate<Attributed<?>> onLayer(int n) {
        return atts -> atts.attributeValue("layer",0)==n;
    }

}
