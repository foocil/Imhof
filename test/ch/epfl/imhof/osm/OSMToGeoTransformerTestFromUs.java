package ch.epfl.imhof.osm;
import static org.junit.Assert.*;
import java.io.IOException;
import org.junit.Test;
import org.xml.sax.SAXException;
import ch.epfl.imhof.Map;
import ch.epfl.imhof.projection.CH1903Projection;

public class OSMToGeoTransformerTestFromUs {

    @Test
    public void numberPolygoneBC ()  throws IOException, SAXException {
        
        String fileName=getClass().getResource("/bc.osm").getFile();
        OSMMap map = OSMMapReader.readOSMFile(fileName, false);
        
        CH1903Projection c = new CH1903Projection();
        OSMToGeoTransformer transformeur = new OSMToGeoTransformer(c);
        
        Map mappy = transformeur.transform(map);
        
        int a = mappy.polygons().size();
        int b = mappy.polyLines().size();
        
        assertEquals(1,a);
        assertEquals(0,b);
        assertEquals(0,mappy.polygons().get(0).value().holes().size());
                
    }
    @Test
    public void numberPolygoneLC ()  throws IOException, SAXException {

        String fileName=getClass().getResource("/lc.osm").getFile();
        OSMMap map = OSMMapReader.readOSMFile(fileName, false);
        
        CH1903Projection c = new CH1903Projection();
        OSMToGeoTransformer transformeur = new OSMToGeoTransformer(c);
        
        Map mappy = transformeur.transform(map);
        
        int a = mappy.polygons().size();
        int d= mappy.polygons().get(0).value().holes().size();
        int b = mappy.polyLines().size();
        
        assertEquals(1,a);
        assertEquals(14,d);
        assertEquals(20,b);
    }
    
    @Test
    public void numberPolygoneLausanne ()  throws IOException, SAXException {
        
        String fileName=getClass().getResource("/lausanne.osm.gz").getFile();
        OSMMap map = OSMMapReader.readOSMFile(fileName, true);
        
        CH1903Projection c = new CH1903Projection();
        OSMToGeoTransformer transformeur = new OSMToGeoTransformer(c);
        
        Map mappy = transformeur.transform(map);
        
        int a = mappy.polygons().size();
        int d= mappy.polygons().get(0).value().holes().size();
        int b = mappy.polyLines().size();
        
        
        System.out.println("Lausanne: ");
        System.out.println(a + " polygons size");
        System.out.println(b + " polyline size");
        
    }
    
    @Test
    public void numberPolygoneBerne ()  throws IOException, SAXException {

        String fileName=getClass().getResource("/berne.osm.gz").getFile();
        OSMMap map = OSMMapReader.readOSMFile(fileName, true);
        
        CH1903Projection c = new CH1903Projection();
        OSMToGeoTransformer transformeur = new OSMToGeoTransformer(c);
        
        Map mappy = transformeur.transform(map);
        
        int a = mappy.polygons().size();
        int d= mappy.polygons().get(0).value().holes().size();
        int b = mappy.polyLines().size();
        
        System.out.println("Berne");
        System.out.println(a + " polygons size");
        System.out.println(b + " polyline size");
        
    }
    
    @Test
    public void numberPolygoneInterlaken ()  throws IOException, SAXException {

        String fileName=getClass().getResource("/interlaken.osm.gz").getFile();
        OSMMap map = OSMMapReader.readOSMFile(fileName, true);
        
        CH1903Projection c = new CH1903Projection();
        OSMToGeoTransformer transformeur = new OSMToGeoTransformer(c);
        
        Map mappy = transformeur.transform(map);
        System.out.println("Interlaken");
        int a = mappy.polygons().size();
        int d= mappy.polygons().get(0).value().holes().size();
        int b = mappy.polyLines().size();
        
        System.out.println(a + " polygons size");
        System.out.println(b + " polyline size");
    }
    
    @Test
    public void numberPolygoneLausanneTest ()  throws IOException, SAXException {
        String fileName=getClass().getResource("/lausanne.osm.gz").getFile();
        OSMMap map = OSMMapReader.readOSMFile(fileName, true);
        
        CH1903Projection c = new CH1903Projection();
        OSMToGeoTransformer transformeur = new OSMToGeoTransformer(c);
        
        Map mappy = transformeur.transform(map);
        
        int a = mappy.polygons().size();
        int b = mappy.polyLines().size();
        
        assertEquals(47975,a);
        assertEquals(80165,b);
                
    }
    
    /*FichierTestE6 est un fichier créé par nous mêmes mais lors du rendu, le fichier n'a pas été pris 
     * en compte.
     */
    
    @Test
    public void noAttributeFileTest ()  throws IOException, SAXException {
     
        String fileName=getClass().getResource("/FichierTestE6.txt").getFile();
        OSMMap map = OSMMapReader.readOSMFile(fileName, false);
        
        CH1903Projection c = new CH1903Projection();
        OSMToGeoTransformer transformeur = new OSMToGeoTransformer(c);
        
        Map mappy = transformeur.transform(map);
        
        int a = mappy.polygons().size();
        int b = mappy.polyLines().size();
        
        assertEquals(0,a);
        assertEquals(0,b);
                
    }
   
    
}
