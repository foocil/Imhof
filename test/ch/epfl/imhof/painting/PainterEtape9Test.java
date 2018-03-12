package ch.epfl.imhof.painting;

import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;

import javax.imageio.ImageIO;

import org.junit.Ignore;
import org.junit.Test;
import org.xml.sax.SAXException;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.Map;
import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.dem.Earth;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.osm.OSMMap;
import ch.epfl.imhof.osm.OSMMapReader;
import ch.epfl.imhof.osm.OSMToGeoTransformer;
import ch.epfl.imhof.projection.CH1903Projection;

public class PainterEtape9Test {
        
    
    @Test
    public void lausanneE9Test() throws IOException, SAXException {
     // Le peintre et ses filtres
        String fileName=getClass().getResource("/lausanne.osm.gz").getFile();
        OSMMap map = OSMMapReader.readOSMFile(fileName, true);
        CH1903Projection proj = new CH1903Projection();
        OSMToGeoTransformer transformeur = new OSMToGeoTransformer(proj);
        Map mappy = transformeur.transform( map);
        
      
        
        // La toile
        Point bl = new Point(532510, 150590);
        Point tr = new Point(539570, 155260);
        
        PointGeo blG = proj.inverse(bl);
        PointGeo trG = proj.inverse(tr);
        
        //Resolution
        double resolutionpiparpouce = 80;
        double resolutionpiparmetre = resolutionpiparpouce*39.3701;
        double height = (resolutionpiparmetre/25000)*(trG.latitude()-blG.latitude())*Earth.RADIUS;
        double width = ((tr.x()-bl.x())/(tr.y()-bl.y()))*height;
        
        Java2DCanvas canvas =
            new Java2DCanvas(bl, tr, (int)width, (int)height, resolutionpiparpouce, ColorCreation.WHITE);
        Painter painter = SwissPainter.painter();
        // Dessin de la carte et stockage dans un fichier
        painter.drawMap(mappy, canvas);
        ImageIO.write(canvas.image(), "png", new File("loz300.png"));
    }
    @Test
    public void berneE9Test()throws IOException, SAXException {
     // Le peintre et ses filtres
        String fileName=getClass().getResource("/berne.osm.gz").getFile();
        OSMMap map = OSMMapReader.readOSMFile(fileName, true);
        CH1903Projection proj = new CH1903Projection();
        OSMToGeoTransformer transformeur = new OSMToGeoTransformer(proj);
        Map mappy = transformeur.transform( map);
    
        // La toile
        Point bl = new Point(597475, 197590);
        Point tr = new Point(605705, 203363);
        Java2DCanvas canvas =
            new Java2DCanvas(bl, tr, 3840, 2160, 300, ColorCreation.WHITE);
        Painter painter = SwissPainter.painter();
        // Dessin de la carte et stockage dans un fichier
        painter.drawMap(mappy, canvas);
        ImageIO.write(canvas.image(), "png", new File("berne4K.png"));
    }
    @Test
    public void berneE9TestONFIRE()throws IOException, SAXException {
     // Le peintre et ses filtres
        String fileName=getClass().getResource("/berne.osm.gz").getFile();
        OSMMap map = OSMMapReader.readOSMFile(fileName, true);
        CH1903Projection proj = new CH1903Projection();
        OSMToGeoTransformer transformeur = new OSMToGeoTransformer(proj);
        Map mappy = transformeur.transform( map);
    
        // La toile
        Point bl = new Point(597475, 197590);
        Point tr = new Point(605705, 203363);
        Java2DCanvas canvas =
            new Java2DCanvas(bl, tr, 3840, 2160, 300, ColorCreation.RED);
        Painter painter = SwissPainter.painter();
        // Dessin de la carte et stockage dans un fichier
        painter.drawMap(mappy, canvas);
        ImageIO.write(canvas.image(), "png", new File("berne4KONFIRE.png"));
    }
    @Test
    public void berneE9TestUNDERWATER()throws IOException, SAXException {
        // Le peintre et ses filtres
           String fileName=getClass().getResource("/berne.osm.gz").getFile();
           OSMMap map = OSMMapReader.readOSMFile(fileName, true);
           CH1903Projection proj = new CH1903Projection();
           OSMToGeoTransformer transformeur = new OSMToGeoTransformer(proj);
           Map mappy = transformeur.transform( map);
       
           // La toile
           Point bl = new Point(597475, 197590);
           Point tr = new Point(605705, 203363);
           Java2DCanvas canvas =
               new Java2DCanvas(bl, tr, 3840, 2160, 300, ColorCreation.BLUE);
           Painter painter = SwissPainter.painter();
           // Dessin de la carte et stockage dans un fichier
           painter.drawMap(mappy, canvas);
           ImageIO.write(canvas.image(), "png", new File("berne4KUNDERWATER.png"));
       }
    @Test
    public void interlakenE9Test()throws IOException, SAXException {
     // Le peintre et ses filtres
        String fileName=getClass().getResource("/interlaken.osm.gz").getFile();
        OSMMap map = OSMMapReader.readOSMFile(fileName, true);
        CH1903Projection proj = new CH1903Projection();
        OSMToGeoTransformer transformeur = new OSMToGeoTransformer(proj);
        Map mappy = transformeur.transform( map);
    
        // La toile
        Point bl = new Point(628764, 167585);
        Point tr = new Point(634991, 172331);
        Java2DCanvas canvas =
            new Java2DCanvas(bl, tr, 3840, 2160, 300, ColorCreation.WHITE);
        Painter painter = SwissPainter.painter();
        // Dessin de la carte et stockage dans un fichier
        painter.drawMap(mappy, canvas);
        ImageIO.write(canvas.image(), "png", new File("interlaken4K.png"));
    }
}
