package ch.epfl.imhof;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.xml.sax.SAXException;

import ch.epfl.imhof.dem.Earth;
import ch.epfl.imhof.dem.HGTDigitalElevationModel;
import ch.epfl.imhof.dem.ReliefShader;
import ch.epfl.imhof.geometry.Point;
import ch.epfl.imhof.osm.OSMMap;
import ch.epfl.imhof.osm.OSMMapReader;
import ch.epfl.imhof.osm.OSMToGeoTransformer;
import ch.epfl.imhof.painting.ColorCreation;
import ch.epfl.imhof.painting.Java2DCanvas;
import ch.epfl.imhof.painting.Painter;
import ch.epfl.imhof.painting.SwissPainter;
import ch.epfl.imhof.projection.CH1903Projection;
import ch.epfl.imhof.projection.Projection;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException, SAXException{
        
       if(args.length != 8){
            System.out.println("Le nombre d'argument à entrer n'est pas juste, il faut 8 arguments...");
            System.exit(1);
       }      
        //variable d'initialisation
        final String OSMFileName = args[0];
        final String HGDTFileName = args[1];
        final double longitudeBL =Math.toRadians(Double.parseDouble(args[2]));
        final double latitudeBL = Math.toRadians(Double.parseDouble(args[3]));
        final double longitudeTR = Math.toRadians(Double.parseDouble(args[4]));
        final double latitudeTR = Math.toRadians(Double.parseDouble(args[5]));
        final int resolution = Integer.parseInt(args[6]); 
        final String FileName = args[7];
       
        
        //calculs des variables utilitaires
        final double rayonF = 0.0017;
        
        final PointGeo BLGeo = new PointGeo(longitudeBL, latitudeBL);
        final PointGeo TRGeo = new PointGeo(longitudeTR, latitudeTR);
        
        final CH1903Projection projection = new CH1903Projection();
        
        final Point BL = projection.project(BLGeo);
        final Point TR = projection.project(TRGeo);

        double resolutionPixelPerMeter = resolution*39.370078740157d;
        int height = Math.round((float)(((resolutionPixelPerMeter/25000)*(TRGeo.latitude()-BLGeo.latitude())*Earth.RADIUS)));
        int width =Math.round((float)(((TR.x()-BL.x())/(TR.y()-BL.y()))*height));
        
        
        //Dessin de la carte finale
        final BufferedImage imageRelief = reliefImage(HGDTFileName, resolutionPixelPerMeter, rayonF, BL, TR, width, height);
        final BufferedImage imageCarte = imageCarte(OSMFileName, projection, BL, TR, width, height, resolution);
        
        BufferedImage imageFinale = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        for(int i=0;i<height;i++){
            for(int j=0;j<width;j++){
                ColorCreation colorMap = ColorCreation.rgb(imageCarte.getRGB(j, i));
                ColorCreation colorRelief = ColorCreation.rgb( imageRelief.getRGB(j, i));
                imageFinale.setRGB(j, i,colorMap.multiply(colorRelief).toAwt().getRGB());
            }
        }
        ImageIO.write(imageFinale, "png", new File(FileName));      
    }
    
    
    //retourne l'image du relief 
    private static BufferedImage reliefImage(String HGDTFileName, double resolutionPixelPerMeter, double rayonF, Point BL, Point TR,int width, int height)
                                                throws FileNotFoundException, IOException{  
        
        final Vector3 light = new Vector3(-1,1,1);
        File HGTfile = new File(HGDTFileName);
        HGTDigitalElevationModel monModele = new HGTDigitalElevationModel(HGTfile);
        
        ReliefShader relief = new ReliefShader(new CH1903Projection(),monModele,light );
        double rayonFpixel = rayonF * resolutionPixelPerMeter; 
        
        return relief.shadedRelief(BL, TR, width,height,(int) Math.ceil(rayonFpixel));              
    }
    
    //retourne l'image de la carte à plat
    private static BufferedImage imageCarte(String OSMFileName, Projection projection, Point BL, Point TR,int width, int height, double resolution) 
                                                 throws IOException, SAXException{
        
        String fileOSM = new File(OSMFileName).getName();
        
        int nameLength = fileOSM.length();
        boolean zip = fileOSM.substring(nameLength-3, nameLength).equals(".gz");
        OSMMap map = OSMMapReader.readOSMFile(fileOSM, zip);
        
        OSMToGeoTransformer transformeur = new OSMToGeoTransformer(projection);
        Map mappy = transformeur.transform(map);
        Java2DCanvas canvas =
            new Java2DCanvas(BL, TR, width, height, resolution, ColorCreation.WHITE);
        Painter painter = SwissPainter.painter();
        painter.drawMap(mappy, canvas);
        return canvas.image();
    }
}
    