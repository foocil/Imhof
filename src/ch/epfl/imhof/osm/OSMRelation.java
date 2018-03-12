package ch.epfl.imhof.osm;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

import ch.epfl.imhof.Attributes;

/**
 * représente une relation OSM, des ensembles d'autres entités appelés membres
 * 
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 * 
 */

public final class OSMRelation extends OSMEntity {

    private final List<Member> memberList;

    /**
     * construit une relation étant donnés son identifiant unique, ses membres et ses attributs
     * 
     * @param id
     *            identifiant unique de l'identité
     * 
     * @param members
     *            liste de membres d'une relation OSM
     * 
     * @param attributes
     *            table associative d'attributs avec les paires clef/valeur
     * 
     */
    
    public OSMRelation(long id, List<Member> members, Attributes attributes) {
        super(id, attributes);
        memberList = Collections
                .unmodifiableList(new ArrayList<Member>(members));
    }

    /**
     * getteur de la liste des membres de la relation.
     * 
     * @return memberList 
     *              liste de membres d'une relation OSM
     *              
     */
    
    public List<Member> members() {
        return memberList;

    }

    /**
     * représente un membre d'une relation OSM, ayant un type et un role
     * 
     * @author Julien von Felten (234865)
     * @author Simon Haefeli (246663)
     * 
     */
    
    public final static class Member {

        private final Type type;
        private final String role;
        private final OSMEntity member;

        /**
         * construit un membre ayant le type, le rôle et la valeur donnés.
         * 
         * @param type
         *            type du membre, pouvant être seulement Node, Way ou Relation
         * 
         * @param role
         *            décrit la fonction du membre dans la relation
         * 
         * @param member
         *              ensemble des entités qui forment la relation
         * 
         */
        
        public Member(Type type, String role, OSMEntity member) {
            this.type = type;
            this.role = role;
            this.member = member;

        }

        /**
         * getteur du type du membre
         * 
         * @return type
         *         type du membre, pouvant être seulement Node, Way ou Relation
         * 
         */
        
        public Type type() {
            return type;

        }

        /**
         * getteur du role du membre
         * 
         * @return role 
         *          décrit la fonction du membre dans la relation
         *          
         */
        
        public String role() {
            return role;

        }

        /**
         * getteur du membre lui-même
         * 
         * @return membre
         *             ensemble des entités qui forment la relation
         * 
         */
        
        public OSMEntity member() {
            return member;

        }

        /**
         * énumère les trois types de membres qu'une relation peut comporter
         * 
         * @author Julien von Felten (234865)
         * @author Simon Haefeli (246663)
         * 
         */
        
        public enum Type {
            NODE, WAY, RELATION
        };

    }

    /**
     * bâtisseur à la classe OSMRelation 
     * 
     * @author Julien von Felten (234865)
     * @author Simon Haefeli (246663)
     * 
     */
    
    public final static class Builder extends OSMEntity.Builder {

        private final List<Member> memberList = new ArrayList<>();

        /**
         * construit un bâtisseur pour une relation ayant l'identifiant unique donné
         * 
         * @param id
         *          identifiant unique de l'entité
         *          
         */
        
        public Builder(long id) {
            super(id);

        }

        /**
         * ajoute un nouveau membre de type et de rôle donnés à la relation
         * 
         * @param type
         *              type du membre, pouvant être seulement Node, Way ou Relation
         * 
         * @param role
         *              décrit la fonction du membre dans la relation
         *              
         * @param newMember
         *              l'entité qui forme la relation
         *              
         */
        
        public void addMember(Member.Type type, String role, OSMEntity newMember) {
            memberList.add(new Member(type, role, newMember));

        }

        /**
         * construit et retourne la relation ayant l'identifiant, les membres et les attributs ajoutés jusqu'à
         * présent au bâtisseur
         * 
         * @throws IllegalStateException
         *                  exception si la relation en cours de construction est incomplète
         * 
         * @return OSMRelation
         *                  relation ayant l'identifiant, les membres et les attributs ajoutés
         *                  
         */
        
        public OSMRelation build() {
            if (super.isIncomplete()) {
                throw new IllegalStateException("Relation incomplète!");
            }
            return new OSMRelation(id, memberList, attributes.build());

        }

    }

}
