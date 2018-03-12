package ch.epfl.imhof.osm;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;
import org.xml.sax.SAXException;

public class testdebugBC {
    
    @Test
    public void bcTestDebug() throws IOException, SAXException{
        String fileName=getClass().getResource("/bc.osm").getFile();
        OSMMap map = OSMMapReader.readOSMFile(fileName, false);
    }
    
    
    @Test
    public void fichierPourriWayTest() throws IOException, SAXException{
        String fileName=getClass().getResource("/fichierPourri.osm").getFile();
        OSMMap map = OSMMapReader.readOSMFile(fileName, false);
        
        int a = map.ways().size();
        assertEquals(2, a);

    }



}
