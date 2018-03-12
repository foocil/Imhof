package ch.epfl.imhof.painting;


import static ch.epfl.imhof.painting.Filters.tagged;
import static ch.epfl.imhof.painting.Painter.line;
import static ch.epfl.imhof.painting.Painter.outline;
import static ch.epfl.imhof.painting.Painter.polygon;
import ch.epfl.imhof.painting.ColorCreation;
import ch.epfl.imhof.painting.LineStyle.LineCap;
import ch.epfl.imhof.painting.LineStyle.LineJoin;
import ch.epfl.imhof.painting.Painter;
import ch.epfl.imhof.painting.RoadPainterGenerator;
import ch.epfl.imhof.painting.RoadPainterGenerator.RoadSpec;

public final class SwissPainter {
    private static final Painter PAINTER;

    static {
        ColorCreation black = ColorCreation.BLACK;
        ColorCreation darkGray = ColorCreation.gray(0.2);
        ColorCreation darkGreen = ColorCreation.rgb(0.75, 0.85, 0.7);
        ColorCreation darkRed = ColorCreation.rgb(0.7, 0.15, 0.15);
        ColorCreation darkBlue = ColorCreation.rgb(0.45, 0.7, 0.8);
        ColorCreation lightGreen = ColorCreation.rgb(0.85, 0.9, 0.85);
        ColorCreation lightGray = ColorCreation.gray(0.9);
        ColorCreation orange = ColorCreation.rgb(1, 0.75, 0.2);
        ColorCreation lightYellow = ColorCreation.rgb(1, 1, 0.5);
        ColorCreation lightRed = ColorCreation.rgb(0.95, 0.7, 0.6);
        ColorCreation lightBlue = ColorCreation.rgb(0.8, 0.9, 0.95);
        ColorCreation white = ColorCreation.WHITE;

        Painter roadPainter = RoadPainterGenerator.painterForRoads(
                new RoadSpec(tagged("highway", "motorway", "trunk"), 2, orange, 0.5f, black),
                new RoadSpec(tagged("highway", "primary"), 1.7f, lightRed, 0.35f, black),
                new RoadSpec(tagged("highway", "motorway_link", "trunk_link"), 1.7f, orange, 0.35f, black),
                new RoadSpec(tagged("highway", "secondary"), 1.7f, lightYellow, 0.35f, black),
                new RoadSpec(tagged("highway", "primary_link"), 1.7f, lightRed, 0.35f, black),
                new RoadSpec(tagged("highway", "tertiary"), 1.7f, white, 0.35f, black),
                new RoadSpec(tagged("highway", "secondary_link"), 1.7f, lightYellow, 0.35f, black),
                new RoadSpec(tagged("highway", "residential", "living_street", "unclassified"), 1.2f, white, 0.15f, black),
                new RoadSpec(tagged("highway", "service", "pedestrian"), 0.5f, white, 0.15f, black));

        Painter fgPainter =
                roadPainter
                .above(line(0.5f, darkGray, LineCap.ROUND, LineJoin.MITER, 1f, 2f).when(tagged("highway", "footway", "steps", "path", "track", "cycleway")))
                .above(polygon(darkGray).when(tagged("building")))
                .above(polygon(lightBlue).when(tagged("leisure", "swimming_pool")))
                .above(line(0.7f, darkRed).when(tagged("railway", "rail", "turntable")))
                .above(line(0.5f, darkRed).when(tagged("railway", "subway", "narrow_gauge", "light_rail")))
                .above(polygon(lightGreen).when(tagged("leisure", "pitch")))
                .above(line(1, darkGray).when(tagged("man_made", "pier")))
                .layered();

        Painter bgPainter =
                outline(1, darkBlue).above(polygon(lightBlue)).when(tagged("natural", "water").or(tagged("waterway", "riverbank")))
                .above(line(1, lightBlue).above(line(1.5f, darkBlue)).when(tagged("waterway", "river", "canal")))
                .above(line(1, darkBlue).when(tagged("waterway", "stream")))
                .above(polygon(darkGreen).when(tagged("natural", "wood").or(tagged("landuse", "forest"))))
                .above(polygon(lightGreen).when(tagged("landuse", "grass", "recreation_ground", "meadow", "cemetery").or(tagged("leisure", "park"))))
                .above(polygon(lightGray).when(tagged("landuse", "residential", "industrial")))
                .layered();

        PAINTER = fgPainter.above(bgPainter);
    }

    public static Painter painter() {
        return PAINTER;
    }
}
