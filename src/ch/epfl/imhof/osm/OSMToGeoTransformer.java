    package ch.epfl.imhof.osm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.Attributes;
import ch.epfl.imhof.Graph;
import ch.epfl.imhof.Map;
import ch.epfl.imhof.geometry.ClosedPolyLine;
import ch.epfl.imhof.geometry.PolyLine;
import ch.epfl.imhof.geometry.Polygon;
import ch.epfl.imhof.osm.OSMRelation.Member;
import ch.epfl.imhof.projection.Projection;

/**
 * représente un convertisseur de données OSM en une carte
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 * 
 */

public final class OSMToGeoTransformer {

    private final static List<String> listWay = Arrays.asList("bridge", "highway", "layer",
            "man_made", "railway", "tunnel", "waterway");
    
    private final static List<String> listRelation = Arrays.asList("building", "landuse",
            "layer", "leisure", "natural", "waterway");
    
    private final Projection projection;

    /**
     * construit un convertisseur OSM en géométrie qui utilise la projection
     * donnée
     * 
     * @param projection
     *            méthode de projection sur une carte
     * 
     */

    public OSMToGeoTransformer(Projection projection) {
        this.projection = projection;
    }

    /**
     * convertit une « carte » OSM en une carte géométrique projetée.
     * 
     * @param map
     *            carte OpenStreetMap, un ensemble de chemins et de relations
     * 
     * @return Map, une carte géométrique projetée
     * 
     */

    public Map transform(OSMMap map) {

        Map.Builder mappy = new Map.Builder();

        // PARCOURT TOUTES LES WAYS DE LA MAP ET LES TRANSFORME EN POLYGONE OU
        // POLYLINE
        Iterator<OSMWay> iterWays = map.ways().iterator();

        while (iterWays.hasNext()) {

            OSMWay way = iterWays.next();

            // Construit un polygone sans trou
            if (wayToPolygon(way)) {

                Attributes attributoadd = filterPolygone(way.attributes());

                if (!attributoadd.isEmpty()) {

                    // Ne construit rien s'il ne reste plus d'attributs
                    ClosedPolyLine.Builder polyBuilderClose = new ClosedPolyLine.Builder();

                    Iterator<OSMNode> iterNodes = way.nonRepeatingNodes()
                            .iterator();

                    while (iterNodes.hasNext()) {
                        polyBuilderClose.addPoint(projection.project(iterNodes
                                .next().position()));
                    }

                    Polygon polygon = new Polygon(
                            polyBuilderClose.buildClosed());

                    mappy.addPolygon(new Attributed<Polygon>(polygon,
                            attributoadd));
                }

            }

            else {
                
                    Attributes attributoadd = filterPolyLine(way.attributes());
                    if (!attributoadd.isEmpty()) {
                        PolyLine.Builder polyBuilder = new PolyLine.Builder();

                        Iterator<OSMNode> iterNodes = way.nonRepeatingNodes().iterator();

                        while (iterNodes.hasNext()) {
                            polyBuilder.addPoint(projection
                                    .project(iterNodes.next().position()));
                        }
                      
                        //Construit une ClosedPolyLine
                        if (way.isClosed()) {  
                            mappy.addPolyLine(new Attributed<PolyLine>(
                                    polyBuilder.buildClosed(), attributoadd));
                            
                    }  // Construit une OpenPolyline
                        else{
                            mappy.addPolyLine(new Attributed<PolyLine>(
                            polyBuilder.buildOpen(), attributoadd)); 
   
                    }
                }
            }
        }

        // PARCOURT TOUTES LES RELATIONS DE LA MAP ET LES TRANSFORME EN POLYGONE
        Iterator<OSMRelation> iterRelation = map.relations().iterator();

        while (iterRelation.hasNext()) {

            OSMRelation currentRelation = iterRelation.next();

            if (currentRelation.attributeValue("type") != null
                    && (currentRelation.attributeValue("type"))
                            .equals("multipolygon")) {
                if (!filterPolygone(currentRelation.attributes()).isEmpty()) {
                    List<Attributed<Polygon>> polygonAttribut = assemblePolygon(
                            currentRelation,
                            filterPolygone(currentRelation.attributes()));

                    for (Attributed<Polygon> p : polygonAttribut) {

                        mappy.addPolygon(p);
                    }
                }
            }

        }

        // CONSTRUIT LA MAP
        return mappy.build();

    }

    /**
     * Méthode qui trie la liste do ClosedPolyLine du plus petit au plus grand
     * 
     * @param line
     *            une liste de ClosedPolyLine à trier
     * 
     */

    private static void sortByArea(List<ClosedPolyLine> line) {
        Collections
                .sort(line, (p1, p2) -> Double.compare(p1.area(), p2.area()));
    }

    /**
     * méthode pour savoir si un OSMWay fermé décrit une surface, pour le
     * convertir en PolyGone sans trou
     * 
     * @param way
     *            une séquence de noeuds avec des attributs
     * 
     * @return vrai s'il doit être converti en PolyGone, faux sinon
     * 
     * 
     */

    private boolean wayToPolygon(OSMWay way) {

        // variable qui permet d'éviter des NullPointerException dans le retour
        boolean b = way.attributeValue("area") == null;

        return way.isClosed()
                && (containAttribut(way) || (!b && (way.attributeValue("area")
                        .equals("1")
                        || way.attributeValue("area").equals("true") || way
                        .attributeValue("area").equals("yes"))));
    }

    /**
     * méthode pour savoir si way a un attribut de la liste, pour le convertir
     * en PolyGone.
     * 
     * @param way
     *            une séquence de noeuds avec des attributs
     * 
     * @return vrai si way contient un des attributs de la liste
     * 
     */

    private boolean containAttribut(OSMWay way) {

        String[] listAttribute = { "aeroway", "amenity", "building", "harbour",
                "historic", "landuse", "leisure", "man_made", "military",
                "natural", "office", "place", "power", "public_transport",
                "shop", "sport", "tourism", "water", "waterway", "wetland" };

        for (String attribute : listAttribute) {
            if (way.hasAttribute(attribute)) {
                return true;
            }
        }
        return false;

    }

    /**
     * méthode qui permet de filtrer les attributs utiles selon l'entité
     * 
     * 
     * @param attributbase
     *            table assosiative clef/valeur de l'entité à filtrer
     * 
     * @param entite
     *            peut être polyline soit polygone
     * 
     * @return Attributes table assosiative clef/valeur filtrée de l'entité
     * 
     */
    private Attributes filterPolyLine(Attributes attributbase){
        Set<String> setpolyWay = new HashSet<>();
        setpolyWay.addAll(listWay);
        
        return attributbase.keepOnlyKeys(setpolyWay);
    }
    
    private Attributes filterPolygone(Attributes attributbase){
        Set<String> setpolyRelation = new HashSet<>();
        setpolyRelation.addAll(listRelation);

        return attributbase.keepOnlyKeys(setpolyRelation);
    }

    /**
     * calcule et retourne la liste des polygones attribués de la relation
     * donnée, en leur attachant les attributs donnés.
     * 
     * @param relation
     *            des ensembles d'autres entités appelés membres
     * 
     * @param attributes
     *            Table associative clef/valeur
     * 
     * @return List<Attributed<Polygon>> Liste de polygone qui ont été
     *         attribuées
     * 
     * 
     */

    private List<Attributed<Polygon>> assemblePolygon(OSMRelation relation,
            Attributes attributes) {

        List<Attributed<Polygon>> listPolyAtt = new ArrayList<Attributed<Polygon>>();

        List<ClosedPolyLine> closePolyOuter = ringsForRole(relation, "outer");
        List<ClosedPolyLine> closePolyInner = ringsForRole(relation, "inner");

        sortByArea(closePolyInner);// Liste de trous du polygon
        sortByArea(closePolyOuter);// Liste de shell du polygon

        for (ClosedPolyLine c : closePolyOuter) {

            List<ClosedPolyLine> holesAdd = new ArrayList<>();

            for (ClosedPolyLine p : closePolyInner) {
                if (c.area() > p.area()) {
                    if (c.containsPoint(p.firstPoint())) {
                        // Ajoute un trou si le trou est contenu dans le shell
                        // et que son aire est plus petite.
                        holesAdd.add(p);

                    }
                } else {
                    break;
                }
            }

            // Un trou ne peut pas être contenu dans deux shell différents
            closePolyInner.removeAll(holesAdd);

            Polygon polygonAdd = new Polygon(c, holesAdd);

            Attributed<Polygon> polygonAttribue = new Attributed<Polygon>(
                    polygonAdd, attributes);
            listPolyAtt.add(polygonAttribue);

        }

        return listPolyAtt;

    }

    /**
     * calcule et retourne l'ensemble des anneaux de la relation donnée ayant le
     * rôle spécifié, ou une liste vide si ca échoue
     * 
     * @param relation
     *            des ensembles d'autres entités appelés membres
     * 
     * @param role
     *            le role de la relation, soit outer soit inner
     * 
     * @return List<ClosedPolyLine> une liste composée de ClosedPolyLine
     * 
     */

    private List<ClosedPolyLine> ringsForRole(OSMRelation relation, String role) {

        Graph.Builder<OSMNode> graphy = new Graph.Builder<>();

        // CREATTION DU GRAPH
        // Parcourt chaque way de la relation
        for (Member m : relation.members()) {
            if (m.role().equals(role) && m.type()==Member.Type.WAY) {
                OSMWay way = (OSMWay) m.member();

                // Ajoute chaque noeud au graph
                for (OSMNode nodeWay : way.nodes()) {
                    graphy.addNode(nodeWay);

                }
                // Crée une arête entre chaque noeud de la way
                for (int i = 0; i < way.nodesCount(); i++) {
                    if (i != way.nodesCount() - 1) {
                        graphy.addEdge(way.nodes().get(i),
                                way.nodes().get(i + 1));
                    }
                }
            }
        }

        // CONSTRUIT DES CLOSEDPOLYLINE A PARTIR DU GRAPH
        Graph<OSMNode> graph = graphy.build();

        // test si tous les noeuds ont deux voisins :

        for (OSMNode onode : graph.nodes()) {
            if (graph.neighborsOf(onode).size() != 2) {
                return new ArrayList<>();
            }
        }

        // Variable de retour
        List<ClosedPolyLine> listyClosed = new ArrayList<>();

        // Ensemble de noeuds non visités
        Set<OSMNode> unvisitedNodes = new HashSet<>(graph.nodes());

        while (!unvisitedNodes.isEmpty()) {

            boolean polyLineInProgress = true; // Vaut true tant que la polyline
                                               // n'est pas prête à être
                                               // construite
            OSMNode nextNode = unvisitedNodes.iterator().next();
            PolyLine.Builder polyBuilder = new PolyLine.Builder();
            List<OSMNode> listNode = new ArrayList<>();

            while (polyLineInProgress) {

                if (!(listNode.contains(nextNode))) {

                    listNode.add(nextNode);
                    unvisitedNodes.remove(nextNode);

                    Set<OSMNode> neighs = graph.neighborsOf(nextNode);

                    // Obligation de copie à cause de l'immuabilité du set de
                    // neighbors de graph
                    Set<OSMNode> neighsCopy = new HashSet<>(neighs);
                    neighsCopy.retainAll(unvisitedNodes);

                    if (!neighsCopy.isEmpty()) {
                        nextNode = neighsCopy.iterator().next();

                    } else {
                        // s'il n'y a plus de voisins, la polyline est prêtre à
                        // être construite
                        polyLineInProgress = false;
                        for (OSMNode n : listNode) {
                            polyBuilder.addPoint(projection.project(n
                                    .position()));
                        }
                    }
                }
            }

            // ajout de la polyline à la liste de ClosedPolyLine
            listyClosed.add(polyBuilder.buildClosed());
        }
        return listyClosed;

    }

}
