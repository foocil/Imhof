package ch.epfl.imhof.painting;


import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.xml.sax.SAXException;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.Map;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.osm.OSMMap;
import ch.epfl.imhof.osm.OSMMapReader;
import ch.epfl.imhof.osm.OSMToGeoTransformer;
import ch.epfl.imhof.projection.CH1903Projection;

public class PainterTest {
    @Test
    public void profTestLoz() throws IOException, SAXException{
     // Le peintre et ses filtres
        Predicate<Attributed<?>> isLake =
            Filters.tagged( "natural","water");
        Painter lakesPainter =
            Painter.polygon(ColorCreation.BLUE).when(isLake);
    
        Predicate<Attributed<?>> isBuilding =
            Filters.tagged("building");
        Painter buildingsPainter =
            Painter.polygon(ColorCreation.BLACK).when(isBuilding);
    
        Painter painter = buildingsPainter.above(lakesPainter);
    
        String fileName=getClass().getResource("/lausanne.osm.gz").getFile();
        OSMMap map = OSMMapReader.readOSMFile(fileName, true);
        CH1903Projection proj = new CH1903Projection();
        OSMToGeoTransformer transformeur = new OSMToGeoTransformer(proj);
        Map mappy = transformeur.transform( map);
    
        // La toile
        Point bl = new Point(532510, 150590);
        Point tr = new Point(539570, 155260);
        Java2DCanvas canvas =
            new Java2DCanvas(bl, tr, 800, 530, 72, ColorCreation.WHITE);
    
        // Dessin de la carte et stockage dans un fichier
        painter.drawMap(mappy, canvas);
        ImageIO.write(canvas.image(), "png", new File("loz.png"));
    }
    @Test
    public void profTestBern() throws IOException, SAXException{
        // Le peintre et ses filtres
           Predicate<Attributed<?>> isLake =
               Filters.tagged( "natural","water");
           Painter lakesPainter =
               Painter.polygon(ColorCreation.BLUE).when(isLake);
       
           Predicate<Attributed<?>> isBuilding =
               Filters.tagged("building");
           Painter buildingsPainter =
               Painter.polygon(ColorCreation.BLACK).when(isBuilding);
       
           Painter painter = buildingsPainter.above(lakesPainter);
       
           String fileName=getClass().getResource("/berne.osm.gz").getFile();
           OSMMap map = OSMMapReader.readOSMFile(fileName, true);
           CH1903Projection proj = new CH1903Projection();
           OSMToGeoTransformer transformeur = new OSMToGeoTransformer(proj);
           Map mappy = transformeur.transform( map);
       
           // La toile
           Point bl = new Point(597475, 197590);
           Point tr = new Point(605705, 203363);
           Java2DCanvas canvas =
               new Java2DCanvas(bl, tr, 800, 530, 72, ColorCreation.WHITE);
       
           // Dessin de la carte et stockage dans un fichier
           painter.drawMap(mappy, canvas);
           ImageIO.write(canvas.image(), "png", new File("BB.png"));
       }
    @Test
    public void profTestInterlaken() throws IOException, SAXException{
        // Le peintre et ses filtres
           Predicate<Attributed<?>> isLake =
               Filters.tagged( "natural","water");
           Painter lakesPainter =
               Painter.polygon(ColorCreation.BLUE).when(isLake);
       
           Predicate<Attributed<?>> isBuilding =
               Filters.tagged("building");
           Painter buildingsPainter =
               Painter.polygon(ColorCreation.BLACK).when(isBuilding);
       
           Painter painter = buildingsPainter.above(lakesPainter);
       
           String fileName=getClass().getResource("/interlaken.osm.gz").getFile();
           OSMMap map = OSMMapReader.readOSMFile(fileName, true);
           CH1903Projection proj = new CH1903Projection();
           OSMToGeoTransformer transformeur = new OSMToGeoTransformer(proj);
           Map mappy = transformeur.transform( map);
       
           // La toile
           Point bl = new Point(628764, 167585);
           Point tr = new Point(634991, 172331);
           Java2DCanvas canvas =
               new Java2DCanvas(bl, tr, 800, 530, 72, ColorCreation.WHITE);
       
           // Dessin de la carte et stockage dans un fichier
           painter.drawMap(mappy, canvas);
           ImageIO.write(canvas.image(), "png", new File("IL.png"));
       }
    @Test
    public void forestTestLoz () throws IOException, SAXException{
     // Le peintre et ses filtres
        Predicate<Attributed<?>> isLake =
                Filters.tagged( "natural","water");
            Painter lakesPainter =
                Painter.polygon(ColorCreation.BLUE).when(isLake);
        
        Predicate<Attributed<?>> isForest =
                Filters.tagged( "landuse","forest");
             Painter ForestPainter =
                Painter.polygon(ColorCreation.GREEN).when(isLake);
    
        Predicate<Attributed<?>> isBuilding =
            Filters.tagged("building");
        Painter buildingsPainter =
            Painter.polygon(ColorCreation.BLACK).when(isBuilding);
    
        Painter painter1 = buildingsPainter.above(lakesPainter);
        Painter painter2 = painter1.above(ForestPainter);
    
        String fileName=getClass().getResource("/lausanne.osm.gz").getFile();
        OSMMap map = OSMMapReader.readOSMFile(fileName, true);
        CH1903Projection proj = new CH1903Projection();
        OSMToGeoTransformer transformeur = new OSMToGeoTransformer(proj);
        Map mappy = transformeur.transform( map);
    
        // La toile
        Point bl = new Point(532510, 150590);
        Point tr = new Point(539570, 155260);
        Java2DCanvas canvas =
            new Java2DCanvas(bl, tr, 800, 530, 72, ColorCreation.WHITE);
    
        // Dessin de la carte et stockage dans un fichier
        painter2.drawMap(mappy, canvas);
        ImageIO.write(canvas.image(), "png", new File("lozForest.png"));
    }
}
