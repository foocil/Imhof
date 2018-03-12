package ch.epfl.imhof.painting;

import java.util.function.Predicate;

import ch.epfl.imhof.Attributed;
import ch.epfl.imhof.painting.LineStyle.LineCap;
import ch.epfl.imhof.painting.LineStyle.LineJoin;

/**
 *  Le générateur de peintre de réseau routier
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 */

public final class RoadPainterGenerator {
    private final static Predicate<Attributed<?>> isBridge = Filters.tagged("bridge");
    private final static Predicate<Attributed<?>> isTunnel = Filters.tagged("tunnel");
    private final static Predicate<Attributed<?>> isRoad = isBridge.negate().and(isTunnel.negate());

    private RoadPainterGenerator() {}

    /**
     * retourne un peintre qui est une pile d'autres peintres pour le réseau routier correspondant
     * 
     * @param roadspeci
     *          tableau variable de spécifications de routes 
     * 
     * @return Painter
     *          pile de peintres du réseau routier correspondant
     * 
     */

    public static Painter painterForRoads(RoadSpec... roadspeci){
  
           Painter bridgeIn = inBridge(roadspeci[0]);
           Painter bridgeOut = outBridge(roadspeci[0]);
           Painter roadIn = inRoad(roadspeci[0]);
           Painter roadOut = outRoad(roadspeci[0]);
           Painter tunnel = tunnel(roadspeci[0]);
           
           for(int i=1; i<roadspeci.length;i++){
               RoadSpec rdSpec = roadspeci[i];
               bridgeIn = bridgeIn.above(inBridge(rdSpec));
               bridgeOut = bridgeOut.above(outBridge(rdSpec));
               roadIn = roadIn.above(inRoad(rdSpec));
               roadOut = roadOut.above(outRoad(rdSpec));
               tunnel = tunnel.above(tunnel(rdSpec));
           }
           
           return bridgeIn.above(bridgeOut.above(roadIn.above(roadOut.above(tunnel))));  
    }

    /**
     * un peintre pour l'intérieur des ponts
     * 
     * @param roadspeci
     *          détail de construction du pont
     * 
     * @return Painter
     *          peintre pour le dessin de l'intérieur des points
     *
     *
     */
    
    public static Painter inBridge(RoadSpec roadspeci) {
        return Painter.line(roadspeci.wi, roadspeci.ci,LineCap.ROUND, LineJoin.ROUND, new float[0]).when(isBridge).when(roadspeci.predicate);
    }

    /**
     * un peintre pour la bordure des ponts
     * 
     * @param roadspeci
     *          détail de construction du pont
     * 
     * @return Painter
     *          peintre pour le dessin de la bordure des ponts
     *
     */

    public static Painter outBridge(RoadSpec roadspeci) {
        return Painter.line(roadspeci.wi + 2 * roadspeci.wc, roadspeci.cc,
                LineCap.BUTT, LineJoin.ROUND, new float[0]).when(
                isBridge).when(roadspeci.predicate);
    }

    /**
     * un peintre pour l'intérieur des routes normales (ni pont ni tunnel)
     * 
     * @param roadspeci
     *          détail de construction du pont
     * 
     * @return Painter
     *          peintre pour le dessin de l'intérieur des routes 
     *
     */

    public static Painter inRoad(RoadSpec roadspeci) {
        return Painter.line(roadspeci.wi, roadspeci.ci,
                LineCap.ROUND, LineJoin.ROUND, new float[0]).when(isRoad).when(roadspeci.predicate);
    }

    /**
     * un peintre pour la bordure des routes normales (ni pont ni tunnel)
     * 
     * @param roadspeci
     *          détail de construction du pont
     * 
     * @return Painter
     *          peintre pour le dessin des routes normales
     *
     */

    public static Painter outRoad(RoadSpec roadspeci) {
        return Painter.line(roadspeci.wi + 2 * roadspeci.wc, roadspeci.cc,
                LineCap.ROUND, LineJoin.ROUND,new float[0]).when(
                isRoad).when(roadspeci.predicate);
    }

    /**
     * un peintre pour les tunnels, qui n'ont pas de bordure
     * 
     * @param roadspeci
     *         détail de construction du pont
     * 
     * @return Painter
     *          peintre pour le dessin des tunnels
     *
     */

    public static Painter tunnel(RoadSpec roadspeci) {
        float[] sequency = { 2 * roadspeci.wi, 2 * roadspeci.wi };
        return Painter.line(roadspeci.wi / 2, roadspeci.cc,
                LineCap.BUTT, LineJoin.ROUND, sequency).when(isTunnel).when(roadspeci.predicate);
    }

    /**
     * classe imbriquée servant à spécifier les détails (couleur, largeur) de constructions du réseau routier
     * 
     * @author Julien von Felten (234865)
     * @author Simon Haefeli (246663)
     */

    public final static class RoadSpec {
        private final float wi;
        private final float wc;
        private final ColorCreation ci;
        private final ColorCreation cc;
        private final Predicate<Attributed<?>> predicate;

        /**
         * Construit une route avec la largeur et la couleur du trait intérieur
         * et avec la largeur et la couleur du trait de la bordure
         * 
         * @param predicate
         *          predicate spécifiant s'il faut le prend ou pas
         * 
         * @param wi
         *          largeur du trait intérieur
         * 
         * @param ci
         *          couleur du trait intérieur
         * 
         * @param wc
         *          largeur du trait de la bordure
         * 
         * @param cc
         *          couleur du trait de la bordure
         *
         */

        public RoadSpec(Predicate<Attributed<?>> predicate, float wi,
                ColorCreation ci, float wc, ColorCreation cc) {
            this.wi = wi;
            this.wc = wc;
            this.ci = ci;
            this.cc = cc;
            this.predicate = predicate;
        }
    }
}
    