package ch.epfl.imhof.painting;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.function.Predicate;

import org.junit.Test;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.Attributes;
import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.osm.OSMNode;

public class FiltersTest {
    private static final double DELTA = 0.000001;
    
    @Test
    public void tagged1Test(){
        
        HashMap<String, String> mappy = new HashMap<>();
        mappy.put("testKey 1", "testValue 1");
        mappy.put("testKey 2", "testValue 2");
        mappy.put("testKey 3", "testValue 3");

       Attributes attributes = new Attributes(mappy);
       
       OSMNode node = new OSMNode(17862757, new PointGeo(0,0), attributes);
       
       
       
       HashMap<String, String> mappy2 = new HashMap<>();
       mappy2.put("testKey 1", "testValue 1");
       mappy2.put("building", "coucou");
       mappy2.put("testKey 3", "testValue 3");
       
       Attributes attributes2 = new Attributes(mappy2);
       
       Attributed noeudAttribue = new Attributed(node,attributes2);

       Predicate<Attributed<?>> isBuilding =  Filters.tagged("building");
       assertTrue(isBuilding.test(noeudAttribue));
       
       Predicate<Attributed<?>> isBuilding2 =  Filters.tagged("rien");
       assertFalse(isBuilding2.test(noeudAttribue));
       
       
       HashMap<String, String> mappy3 = new HashMap<>();
       mappy3.put("testKey 1", "testValue 1");
       mappy3.put("nimportquoi", "coucou");
       mappy3.put("testKey 3", "testValue 3");
       
       Attributes attributes3 = new Attributes(mappy3);
       
       Attributed noeudAttribue3 = new Attributed(node,attributes3);
       
       Predicate<Attributed<?>> isBuilding3 =  Filters.tagged("building");
       assertFalse(isBuilding3.test(noeudAttribue3));
    }
    
    @Test
    public void tagged2Test(){
        HashMap<String, String> mappy = new HashMap<>();
        mappy.put("testKey 1", "testValue 1");
        mappy.put("testKey 2", "testValue 2");
        mappy.put("testKey 3", "testValue 3");

       Attributes attributes = new Attributes(mappy);
       
       OSMNode node = new OSMNode(17862757, new PointGeo(0,0), attributes);
       
       
       
       HashMap<String, String> mappy2 = new HashMap<>();
       mappy2.put("testKey 1", "testValue 1");
       mappy2.put("building", "coucou");
       mappy2.put("testKey 3", "testValue 3");
       
       Attributes attributes2 = new Attributes(mappy2);
       
       Attributed noeudAttribue = new Attributed(node,attributes2);

       Predicate<Attributed<?>> isBuilding =  Filters.tagged("building", "maman");
       assertFalse(isBuilding.test(noeudAttribue));
       
       Predicate<Attributed<?>> isBuilding2 =  Filters.tagged("rien","encore","plein","de","truc");
       assertFalse(isBuilding2.test(noeudAttribue));
       
       
    }
    @Test
    public void onLayerTest() {
        HashMap<String, String> mappy = new HashMap<>();
        mappy.put("testKey 1", "testValue 1");
        mappy.put("testKey 2", "testValue 2");
        mappy.put("testKey 3", "testValue 3");

       Attributes attributes = new Attributes(mappy);
       
       OSMNode node = new OSMNode(17862757, new PointGeo(0,0), attributes);
       
       
       
       HashMap<String, String> mappy2 = new HashMap<>();
       mappy2.put("testKey 1", "testValue 1");
       mappy2.put("building", "coucou");
       mappy2.put("Layer", "5");
          
       Attributes attributes2 = new Attributes(mappy2);
       
       Attributed noeudAttribue = new Attributed(node,attributes2);

       Predicate<Attributed<?>> onLayer5 = Filters.onLayer(5);
       
       assertTrue(onLayer5.test(noeudAttribue));
       

       HashMap<String, String> mappy3 = new HashMap<>();
       mappy3.put("testKey 1", "testValue 1");
       mappy3.put("nimportquoi", "coucou");
       mappy3.put("testKey 3", "testValue 3");
       
       Attributes attributes3 = new Attributes(mappy3);
       
       Attributed noeudAttribue3 = new Attributed(node,attributes3);
       
       assertFalse(onLayer5.test(noeudAttribue3));
       
       
       Predicate<Attributed<?>> onLayer0 = Filters.onLayer(0);
       assertTrue(onLayer0.test(noeudAttribue3));
       
       
    }
    

}
