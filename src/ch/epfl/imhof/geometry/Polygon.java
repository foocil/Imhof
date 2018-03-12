package ch.epfl.imhof.geometry;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

import ch.epfl.imhof.geometry.ClosedPolyLine;

/**
 * Modélisation d'une ClosedPolyLine pouvant contenir plusieurs trous (aussi des ClosedPolyLine)
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 */

public final class Polygon {

    private final ClosedPolyLine shell;
    private final List<ClosedPolyLine> holes;

    /**
     * construit un polygon avec l'enveloppe et les trous donnés
     * 
     * @param shell
     *            une polyline fermée qui joue le role d'enveloppe
     * 
     * @param holes
     *            une liste de polyline fermée qui représente des trous
     * 
     */
    
    public Polygon(ClosedPolyLine shell, List<ClosedPolyLine> holes) {
        this.shell = shell;
        this.holes = Collections.unmodifiableList(new ArrayList<ClosedPolyLine>(holes));
    }

    /**
     * construit un polygone avec l'enveloppe donnée, sans trous.
     * 
     * @param shell
     *            une polyline fermée qui joue le role d'enveloppe
     */
    
    public Polygon(ClosedPolyLine shell) {
        this(shell, new ArrayList<ClosedPolyLine>());
    }

    /**
     * getteur qui sert à retourner l'enveloppe
     * 
     * @return shell 
     *              une polyline fermée qui joue le role d'enveloppe
     */
    
    public ClosedPolyLine shell() {
        return shell;
    }

    /**
     * getter retournant la liste des trous.
     * 
     * @return holes 
     *          une liste de polyline fermée qui représente des trous
     *          
     */

    public List<ClosedPolyLine> holes() {

        return holes;

    }

}
