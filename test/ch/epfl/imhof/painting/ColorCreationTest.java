package ch.epfl.imhof.painting;

import static org.junit.Assert.*;

import org.junit.Test;

public class ColorCreationTest {
    private static final double DELTA = 0.000001;

    
    @Test
    public void rgbTest(){
        ColorCreation colorTest = ColorCreation.rgb(11029141);
        
        assertEquals((double)168/255, colorTest.r(), DELTA );
        assertEquals((double)74/255, colorTest.g(), DELTA );
        assertEquals((double)149/255, colorTest.b(), DELTA );
        
    }
}
