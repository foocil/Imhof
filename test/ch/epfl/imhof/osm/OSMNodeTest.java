package ch.epfl.imhof.osm;


import static org.junit.Assert.*;

import org.junit.Test;

import ch.epfl.imhof.PointGeo;



public class OSMNodeTest {
    
    @Test
    public void OSMNodeBuilderWorks(){
     
        OSMNode.Builder nodeBuilder = new OSMNode.Builder(696969, new PointGeo(2,0));
        nodeBuilder.setAttribute("key1", "value1");
        nodeBuilder.setAttribute("key2", "value2");
        nodeBuilder.setAttribute("key3", "value3");
        OSMNode node = nodeBuilder.build();
        assertEquals(0, node.position().latitude(), 0.0);
    }
    
    @Test (expected = IllegalStateException.class)
    public void OSMNodeBuilderNotWorks(){
     
        OSMNode.Builder nodeBuilder = new OSMNode.Builder(696969, new PointGeo(2,0));
        nodeBuilder.setAttribute("key1", "value1");
        nodeBuilder.setAttribute("key2", "value2");
        nodeBuilder.setAttribute("key3", "value3");
        nodeBuilder.setIncomplete();
        OSMNode node = nodeBuilder.build();   
    }
}
