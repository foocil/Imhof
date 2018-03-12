package ch.epfl.imhof.dem;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;
import org.xml.sax.SAXException;

import ch.epfl.imhof.Map;
import ch.epfl.imhof.PointGeo;
import ch.epfl.imhof.Vector3;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.osm.OSMMap;
import ch.epfl.imhof.osm.OSMMapReader;
import ch.epfl.imhof.osm.OSMToGeoTransformer;
import ch.epfl.imhof.painting.ColorCreation;
import ch.epfl.imhof.painting.Java2DCanvas;
import ch.epfl.imhof.painting.Painter;
import ch.epfl.imhof.painting.SwissPainter;
import ch.epfl.imhof.projection.CH1903Projection;

public class E11Test {

    @Test
    public void premierTest() throws FileNotFoundException, IOException, SAXException {
        //Variables d'initialisation
        int width = 800;
        int height = 500;
        double latitudeMax = Math.toRadians(46.9742);
        double latitudeMin =  Math.toRadians(46.9322);
        double longitudeMax =Math.toRadians(7.4841);
        double longitudeMin = Math.toRadians(7.3912);
        // en pixels
        int rayonFloutage = 10;
        Vector3 light = new Vector3(-1,1,1);
//        Point BL = new CH1903Projection().project(new PointGeo(longitudeMin,latitudeMin));
//        Point TR = new CH1903Projection().project(new PointGeo(longitudeMax,latitudeMax));
        Point BL = new Point(628764, 167585);
        Point TR = new Point(634991, 172331);
        
        
        //Shaded Relief
        //Why le chemin d'accès complet?????
        File HGTfile = new File("D:/Julien/EPFL_Infomatique/2eme_semestre/PratiquePOO/Projet/Workspaceprojet/Imhof/data/N46E007.hgt");
        HGTDigitalElevationModel monModele = new HGTDigitalElevationModel(HGTfile);
        
        ReliefShader relief = new ReliefShader(new CH1903Projection(), monModele, light );
        BufferedImage imageRelief = relief.shadedRelief(BL, TR, width, height, rayonFloutage);
        
        System.out.println("1");
        
        //Carte
        String fileName=getClass().getResource("/interlaken.osm.gz").getFile();
        OSMMap map = OSMMapReader.readOSMFile(fileName, true);
        CH1903Projection proj = new CH1903Projection();
        OSMToGeoTransformer transformeur = new OSMToGeoTransformer(proj);
        Map mappy = transformeur.transform( map);
    
        System.out.println("2");
        
        Java2DCanvas canvas =
            new Java2DCanvas(BL, TR, width, height, 150, ColorCreation.WHITE);
        Painter painter = SwissPainter.painter();
        painter.drawMap(mappy, canvas);
        BufferedImage imageCarte = canvas.image();
        
        System.out.println("3");
        
        //Dessin imageFinale
        BufferedImage imageFinale = new BufferedImage(width,height, BufferedImage.TYPE_INT_RGB);
        
        //pk pas i=0; j=0?
        
        for(int i=1;i<height;i++){
            for(int j=1;j<width;j++){
                imageFinale.setRGB(j, i, ColorCreation.rgb(imageCarte.getRGB(j, i)).multiply(ColorCreation.rgb( imageRelief.getRGB(j, i))).toAwt().getRGB());
            }
        }
       
        ImageIO.write(imageFinale, "png", new File("E11Fin.png"));
      

    }
}
