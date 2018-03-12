package ch.epfl.imhof.osm;


import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.xml.sax.SAXException;

public class TestXML {
    
    /*Les fichiers fichierPourri, fichierPourriRelation et FichierTestE6 sont 3 fichiers 
    que nous avons créé nous même pour certain tests, mais en y postant, les fichiers n'ont pas été 
    pris en compte, ce qui fait que les tests ne marcheront pas.
    */
    
    @Test
    public void readerTest() throws IOException, SAXException{
        String fileName = getClass().getResource("/berne.osm.gz").getFile();
        OSMMapReader.readOSMFile(fileName, true);
     
    } 
    
    
    @Test
    public void bcTest() throws IOException, SAXException{
        String fileName=getClass().getResource("/bc.osm").getFile();
        OSMMap map = OSMMapReader.readOSMFile(fileName, false);
            
            int a = map.ways().get(0).nodesCount();
            assertEquals(5,a);
            assertTrue( map.ways().get(0).isClosed());
            
            long id =  map.ways().get(0).id();
            assertEquals(5,id);
            
            assertEquals(3,map.ways().get(0).nodes().get(2).id());
            assertEquals(6.5616658,Math.toDegrees(map.ways().get(0).nodes().get(1).position().longitude()),0.1);
            
            assertEquals("yes", map.ways().get(0).attributes().get("building"));  
    }
    
    @Test
    public void fichierPourriWayTest() throws IOException, SAXException{
        String fileName=getClass().getResource("/fichierPourri.osm").getFile();
        OSMMap map = OSMMapReader.readOSMFile(fileName, false);
        
        int a = map.ways().size();
        assertEquals(2, a);

    }
    @Test
    public void fichierPourriRelationTest() throws IOException, SAXException{
        String fileName=getClass().getResource("/fichierPourriRelation.osm").getFile();
        OSMMap map = OSMMapReader.readOSMFile(fileName, false);
        
        int a = map.ways().size();
        int b = map.relations().size();
        
        assertEquals(4, a);
        assertEquals(2, b);
        
        
    }
    
}
