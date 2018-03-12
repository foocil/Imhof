package ch.epfl.imhof.painting;

import static org.junit.Assert.*;

import org.junit.Test;

import ch.epfl.imhof.painting.LineStyle.LineCap;
import ch.epfl.imhof.painting.LineStyle.LineJoin;

public class LineStyleTest {
    private static final double DELTA = 0.000001;
    
    @Test  (expected = IllegalArgumentException.class)
    public void constructorWidthException() {
        LineStyle s = new LineStyle(-1, ColorCreation.WHITE);
    }
    
    @Test  (expected = IllegalArgumentException.class)
    public void constructorTableauException(){
       float[] tab = {1,-1,4};
        LineStyle s = new LineStyle(5, ColorCreation.RED, LineCap.BUTT, LineJoin.BEVEL, tab);
    }
    
    @Test
    public void immuabilityConstructor(){
        float[] tab = {1,3,4};
        LineStyle s = new LineStyle(5, ColorCreation.RED, LineCap.BUTT, LineJoin.BEVEL, tab);
        tab[0]=0;
        tab[1]=5;
        tab[2]= 6;
        
        assertEquals(1, s.alternationSequency()[0], DELTA);
        assertEquals(3, s.alternationSequency()[1], DELTA);
        assertEquals(4, s.alternationSequency()[2], DELTA);
    }
    
    public void immuabilityGet(){
        
        float[] tab = {1,3,4};
        LineStyle s = new LineStyle(5, ColorCreation.RED, LineCap.BUTT, LineJoin.BEVEL, tab);
        s.alternationSequency()[0] = 6;
        s.alternationSequency()[1] = 7;
        s.alternationSequency()[2] = 8;
        
        
        assertEquals(1, s.alternationSequency()[0], DELTA);
        assertEquals(3, s.alternationSequency()[1], DELTA);
        assertEquals(4, s.alternationSequency()[2], DELTA);
    }
}
