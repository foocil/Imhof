package ch.epfl.imhof.osm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import ch.epfl.imhof.Attributes;
import ch.epfl.imhof.PointGeo;
import static org.junit.Assert.*;

public class OSMWayTest {
    
    @Test(expected = IllegalArgumentException.class) 
    public void IllegaleConstructorWorks() {
        
        OSMNode.Builder nodeBuilder = new OSMNode.Builder(696969, new PointGeo(2,0));
        nodeBuilder.setAttribute("key1", "value1");
        nodeBuilder.setAttribute("key2", "value2");
        nodeBuilder.setAttribute("key3", "value3");
        OSMNode node = nodeBuilder.build();
       
        List<OSMNode> nodes = new ArrayList<>();
        nodes.add(node);
        
    
        
        Map<String, String> Mappy = new HashMap<>();
        Mappy.put("keys1", "value1");
        Mappy.put("keys2", "value2");
        Attributes attributes = new Attributes(Mappy);
     
        OSMWay chemin = new OSMWay (696969,nodes, attributes);
        
    }
    
    @Test 
    public void ConstructorWorks() {
       
        List<OSMNode> nodes = new ArrayList<>();
        
        OSMNode.Builder nodeBuilder1 = new OSMNode.Builder(696969, new PointGeo(2,0));
        nodeBuilder1.setAttribute("key1", "value1");
        nodeBuilder1.setAttribute("key2", "value2");
        nodeBuilder1.setAttribute("key3", "value3");
        OSMNode node1 = nodeBuilder1.build();
        
        OSMNode.Builder nodeBuilder2 = new OSMNode.Builder(696969, new PointGeo(2,0));
        nodeBuilder2.setAttribute("key1", "value1");
        nodeBuilder2.setAttribute("key2", "value2");
        nodeBuilder2.setAttribute("key3", "value3");
        OSMNode node2 = nodeBuilder2.build();
        
        nodes.add(node1);
        nodes.add(node2);
        
        Map<String, String> Mappy = new HashMap<>();
        Mappy.put("keys1", "value1");
        Mappy.put("keys2", "value2");
        Attributes attributes = new Attributes(Mappy);
     
        OSMWay chemin = new OSMWay (696969,nodes, attributes);
        
    }
    
    @Test 
    public void isClosedWorks() {
       
        List<OSMNode> nodes = new ArrayList<>();
        
        OSMNode.Builder nodeBuilder1 = new OSMNode.Builder(696969, new PointGeo(2,0));
        nodeBuilder1.setAttribute("key1", "value1");
        nodeBuilder1.setAttribute("key2", "value2");
        nodeBuilder1.setAttribute("key3", "value3");
        OSMNode node1 = nodeBuilder1.build();
        
        OSMNode.Builder nodeBuilder2 = new OSMNode.Builder(696969, new PointGeo(2,0));
        nodeBuilder2.setAttribute("key1", "value1");
        nodeBuilder2.setAttribute("key2", "value2");
        nodeBuilder2.setAttribute("key3", "value3");
        OSMNode node2 = nodeBuilder2.build();
        
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node1);
        
        Map<String, String> Mappy = new HashMap<>();
        Mappy.put("keys1", "value1");
        Mappy.put("keys2", "value2");
        Attributes attributes = new Attributes(Mappy);
     
        OSMWay chemin = new OSMWay (696969,nodes, attributes);
        
        assertTrue(chemin.isClosed());
    }
    
    @Test 
    public void isClosedNotWorks() {
       
        List<OSMNode> nodes = new ArrayList<>();
        
        OSMNode.Builder nodeBuilder1 = new OSMNode.Builder(696969, new PointGeo(2,0));
        nodeBuilder1.setAttribute("key1", "value1");
        nodeBuilder1.setAttribute("key2", "value2");
        nodeBuilder1.setAttribute("key3", "value3");
        OSMNode node1 = nodeBuilder1.build();
        
        OSMNode.Builder nodeBuilder2 = new OSMNode.Builder(696969, new PointGeo(2,0));
        nodeBuilder2.setAttribute("key1", "value1");
        nodeBuilder2.setAttribute("key2", "value2");
        nodeBuilder2.setAttribute("key3", "value3");
        OSMNode node2 = nodeBuilder2.build();
        
        OSMNode.Builder nodeBuilder3 = new OSMNode.Builder(696969, new PointGeo(2,0));
        nodeBuilder3.setAttribute("key1", "value1");
        nodeBuilder3.setAttribute("key2", "value2");
        nodeBuilder3.setAttribute("key3", "value3");
        OSMNode node3 = nodeBuilder3.build();
        
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);
        
        Map<String, String> Mappy = new HashMap<>();
        Mappy.put("keys1", "value1");
        Mappy.put("keys2", "value2");
        Attributes attributes = new Attributes(Mappy);
     
        OSMWay chemin = new OSMWay (696969,nodes, attributes);
        
        assertFalse(chemin.isClosed());
    }
    
    @Test
    public void nonRepeatingNodeTest(){
        
        List<OSMNode> nodes = new ArrayList<>();
        
        OSMNode.Builder nodeBuilder1 = new OSMNode.Builder(696969, new PointGeo(2,0));
        nodeBuilder1.setAttribute("key1", "value1");
        OSMNode node1 = nodeBuilder1.build();
        
        OSMNode.Builder nodeBuilder2 = new OSMNode.Builder(696969, new PointGeo(2,0));
        nodeBuilder2.setAttribute("key1", "value1");
        OSMNode node2 = nodeBuilder2.build();
        
        OSMNode.Builder nodeBuilder3 = new OSMNode.Builder(696969, new PointGeo(2,0));
        nodeBuilder3.setAttribute("key1", "value1");
        OSMNode node3 = nodeBuilder3.build();
        
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);
        nodes.add(node1);
        
        Map<String, String> Mappy = new HashMap<>();
        Mappy.put("keys1", "value1");
        Mappy.put("keys2", "value2");
        Attributes attributes = new Attributes(Mappy);
     
        OSMWay chemin = new OSMWay (696969,nodes, attributes);
        
  
        OSMWay cheminOuvert = new OSMWay (696969, chemin.nonRepeatingNodes(), attributes);
 
        assertFalse(cheminOuvert.isClosed());
        
    }
    
    

}
