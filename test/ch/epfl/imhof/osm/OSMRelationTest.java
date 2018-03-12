package ch.epfl.imhof.osm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import ch.epfl.imhof.Attributes;
import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.osm.OSMRelation.Member;
import ch.epfl.imhof.osm.OSMRelation.Member.Type;

public class OSMRelationTest {
    
    @Test
    public void enumTestWorks () {
        OSMNode.Builder nodeBuilder1 = new OSMNode.Builder(696969, new PointGeo(2,0));
        nodeBuilder1.setAttribute("key1", "value1");
        nodeBuilder1.setAttribute("key2", "value2");
        nodeBuilder1.setAttribute("key3", "value3");
        OSMNode node1 = nodeBuilder1.build();
        
        OSMRelation.Member m = new OSMRelation.Member(Type.WAY, "outer", node1);
       
    }

    
    @Test (expected = IllegalStateException.class)
    public void builderTest(){
        
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

        OSMRelation.Builder ruedi = new OSMRelation.Builder(23); 
        ruedi.addMember(Type.NODE, "outer", node1);
        ruedi.addMember(Type.NODE, "outer", node2);
        ruedi.setIncomplete();
        ruedi.build();

    }
   

}
