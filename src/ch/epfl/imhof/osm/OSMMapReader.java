package ch.epfl.imhof.osm;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.osm.OSMRelation.Member.Type;

/**
 * Construit une OSMMap à partir de données stockées dans un fichier au format
 * OSM
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 * 
 */

public final class OSMMapReader {

    private final static String node = "node";
    private final static String way = "way";
    private final static String relation = "relation";
    private final static String nd = "nd";
    private final static String tag = "tag";
    private final static String member = "member";

    private OSMMapReader() {}

    /**
     * Lit le fichier OSM passé en argument et ajoute les entités OSM du fichier
     * à l'OSMMap.
     * 
     * @param fileName
     *            le nom du fichier XML à transformer
     * 
     * @param unGZip
     *            boolean, vrai si le fichier doit être décompressé
     * 
     * @throws SAXEException
     *             erreur dans le format du fichier XML contenant la carte
     * 
     * @throws IOException
     *             autre erreur d'entrée/sortie
     * 
     * @return OSMMap, un ensemble de chemins et de relations
     * 
     */

    public static OSMMap readOSMFile(String fileName, boolean unGZip)
            throws IOException, SAXException {

        OSMMap.Builder map = new OSMMap.Builder();

        // Lecture du fichier
        try (InputStream s = (unGZip ? new GZIPInputStream(
                new BufferedInputStream(new FileInputStream(fileName)))
                : new BufferedInputStream(new FileInputStream(fileName)))) {
            XMLReader r = XMLReaderFactory.createXMLReader();
            r.setContentHandler(new DefaultHandler() {

                // currentEntity représentant un NODE, une WAY ou une RELATION
                // auquel est ajouté des attributs/entités/membres

                String currentEntity;
                OSMNode.Builder nodeBuilder;
                OSMWay.Builder wayBuilder;
                OSMRelation.Builder relationBuilder;

                /**
                 * Redéfinition de la méthode startElement de la classe
                 * DefaultHandler. Construit le début des entités OSM
                 * 
                 * @param uri
                 *            Element non utilisé de la balise
                 * 
                 * @param lName
                 *            Element non utilisé de la balise
                 * 
                 * @param qName
                 *            Nom de la balise
                 * 
                 * @param atts
                 *            Attributs de la balise
                 * 
                 * @throws SAXException
                 *             exception en cas d'erreur dans le format du
                 *             fichier XML contenant la carte
                 * 
                 * 
                 */
                @Override
                public void startElement(String uri, String lName,
                        String qName, Attributes atts) throws SAXException {

                    switch (qName) {

                    // Un node est créé
                    case node:
                        nodeBuilder = new OSMNode.Builder(Long.parseLong(atts
                                .getValue("id")), new PointGeo(Math
                                .toRadians(Double.parseDouble(atts
                                        .getValue("lon"))), Math
                                .toRadians(Double.parseDouble(atts
                                        .getValue("lat")))));
                        currentEntity = node;
                        break;

                    // Une way est créée
                    case way:
                        wayBuilder = new OSMWay.Builder(Long.parseLong(atts
                                .getValue("id")));
                        currentEntity = way;

                        break;

                    // Un node est ajouté à la way courante
                    case nd:
                        wayBuilder.addNode(getNode(atts.getValue("ref")));
                        
                        break;

                    // Des attributs sont associés à l'entité courante
                    case tag:
                        addTag(atts.getValue("k"), atts.getValue("v"));
                        break;

                    // Une relation est créée
                    case relation:
                        relationBuilder = new OSMRelation.Builder(Long
                                .parseLong(atts.getValue("id")));
                        currentEntity = relation;
                        break;

                    // Un membre est ajouté à la relation courante
                    case member:
                        if (atts.getValue("type") != null) {
                            relationBuilder.addMember(
                                    getType(atts.getValue("type")),
                                    atts.getValue("role"),
                                    getEntity(atts.getValue("type"),
                                            atts.getValue("ref")));
                        }
                        break;

                    }

                }

                /**
                 * Redéfinition de la méthode endElement de la classe
                 * DefaultHandler. Construit et ajoute les entités OSM à
                 * l'OSMMap
                 * 
                 * @param uri
                 *            Element non utilisé de la balise
                 * 
                 * @param lName
                 *            Element non utilisé de la balise
                 * 
                 * @param qName
                 *            Nom de la balise
                 * 
                 * @throws SAXException
                 *             Exception en cas d'erreur dans le format du
                 *             fichier XML contenant la carte
                 * 
                 */
                @Override
                public void endElement(String uri, String lName, String qName)
                        throws SAXException {
                    
                    switch (qName) {
                    
                   
                    case node:;
                        if (!nodeBuilder.isIncomplete()) {
                            map.addNode(nodeBuilder.build());
                        }
                        break;

                    case way:
                        if (!wayBuilder.isIncomplete()) {
                      
                            map.addWay(wayBuilder.build());
                        }
                        break;

                    case relation:
                        if (!relationBuilder.isIncomplete()) {
                            map.addRelation(relationBuilder.build());
                        }
                        break;

                    }

                }

                /**
                 * Retourne le noeud associé à un id passé en argument
                 * 
                 * @param ref
                 *            référence du noeud dans le fichier xml
                 * 
                 * @return le noeud associé à la référence dans le fichier xml
                 * @throws SAXException
                 * 
                 * 
                 */

                private OSMNode getNode(String ref) {
                    long ref1 = Long.parseLong(ref);
                    OSMNode node = map.nodeForId(ref1);
                    if (node == null) {
                        wayBuilder.setIncomplete();
                    }
                    return node;
                }

                /**Cette méthode ajoute l'attribut à l'entité courante
                 * @param key
                 *         clé de l'attribut
                 * @param value
                 *         valeur de l'attribut
                 * @throws SAXException
                 *          n'arrive jamais dans notre cas
                 */
                private void addTag(String key, String value)
                        throws SAXException {
                    switch (currentEntity) {
                    case node:
                        nodeBuilder.setAttribute(key, value);
                        break;
                    case way:
                        wayBuilder.setAttribute(key, value);
                        break;
                    case relation:
                        relationBuilder.setAttribute(key, value);
                        break;
                    default:
                        throw new SAXException("type non connu"); // case
                                                                  // default
                                                                  // n'arrivera
                                                                  // jamais.
                    }
                }

                /**
                 * Transforme une String en un élément de l'énumération Type.
                 * 
                 * @param String
                 *            typ Un string du type
                 * 
                 * @return type, Un élément de l'énumération Type
                 * 
                 * @throws SAXException
                 *             exception si le type de membre n'est pas connu
                 * 
                 */

                private Type getType(String typ) throws SAXException {

                    switch (typ) {
                    case node:
                        return Type.NODE;

                    case way:
                        return Type.WAY;

                    case relation:
                        return Type.RELATION;

                    default:
                        throw new SAXException("type de membre pas connu"); // case
                                                                            // default
                                                                            // n'arrivera
                                                                            // jamais.

                    }
                }

                /**
                 * Retourne l'entité OSM correspondant au type et l'id passés en
                 * argument
                 * 
                 * @param type
                 *            type de l'entité recherchée
                 * 
                 * @param id
                 *            identifiant unique de l'entité recherchée
                 * 
                 * @return OSMEntity, l'OSMEntity associé au type et id passé en
                 *         argument
                 */

                private OSMEntity getEntity(String type, String id) {
                    long i = Long.parseLong(id);

                    switch (type) {
                    case node:
                        if (map.nodeForId(i) == null)
                            relationBuilder.setIncomplete();
                        return map.nodeForId(i);

                    case way:
                        if (map.wayForId(i) == null)
                            relationBuilder.setIncomplete();
                        return map.wayForId(i);

                    case relation:
                        if (map.relationForId(i) == null)
                            relationBuilder.setIncomplete();
                        return map.relationForId(i);

                    default:
                        return null;

                    }

                }

            });

            r.parse(new InputSource(s));

        }

        return map.build();

    }

}
