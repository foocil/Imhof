package ch.epfl.imhof.painting;

import java.awt.Color;

/**
 *  représente une couleur, décrite par ses trois composantes rouge, verte et bleue
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 */

public final class ColorCreation {
    private final double r, g, b;

    /**
     * La couleur « rouge » (pur).
     * 
     */
    
    public final static ColorCreation RED = new ColorCreation(1, 0, 0);

    /**
     * La couleur « vert » (pur).
     * 
     */
    
    public final static ColorCreation GREEN = new ColorCreation(0, 1, 0);

    /**
     * La couleur « bleu » (pur).
     */
    
    public final static ColorCreation BLUE = new ColorCreation(0, 0, 1);

    /**
     * La couleur « noir ».
     * 
     */
    
    public final static ColorCreation BLACK = new ColorCreation(0, 0, 0);

    /**
     * La couleur « blanc ».
     * 
     */
    
    public final static ColorCreation WHITE = new ColorCreation(1, 1, 1);

    /**
     * Construit une couleur avec les composantes rouges, vertes et bleues
     * données, qui doivent être dans l'intervalle [0;1].
     *
     * @param r
     *            la composante rouge.
     * @param g
     *            la composante verte.
     * @param b
     *            la composante bleue.
     * @throws IllegalArgumentException
     *             si l'une des composantes est hors de l'intervalle [0;1].
     *             
     */
    
    private ColorCreation(double r, double g, double b) {
        if (!(0.0 <= r && r <= 1.0))
            throw new IllegalArgumentException("invalid red component: " + r);
        if (!(0.0 <= g && g <= 1.0))
            throw new IllegalArgumentException("invalid green component: " + g);
        if (!(0.0 <= b && b <= 1.0))
            throw new IllegalArgumentException("invalid blue component: " + b);

        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * La couleur « gris ».
     * 
     */
    
    public final static ColorCreation gray(double a) {
        return new ColorCreation(a, a, a);
    }

    /**
     * méthode construisant la couleur correspondant au mélange des 3 couleurs
     * 
     * @param c1
     *          première couleur à mélanger
     * 
     * @param c2
     *          deuxième couleur à mélanger
     * 
     * @param c3
     *          troisième couleur à mélanger
     * 
     * @return la couleur composée des 3 couleurs passée en argument
     * 
     */
    
    public final static ColorCreation rgb(double c1, double c2, double c3) {
        return new ColorCreation(c1, c2, c3);
    }

    /**
     * Construit une couleur en « déballant » les trois composantes RGB stockées
     * chacune sur 8 bits. La composante R est supposée occuper les bits 23 à
     * 16, la composante G les bits 15 à 8 et la composante B les bits 7 à 0.
     *
     * @param packedRGB
     *            la couleur encodée, au format RGB.
     */
    
    public final static ColorCreation rgb(int packedRGB) {
        return new ColorCreation((((packedRGB >> 16) & 0xFF) / 255d),
                (((packedRGB >> 8) & 0xFF) / 255d),
                ((packedRGB & 0xFF) / 255d));
    }

    /**
     * Retourne la composante rouge de la couleur, comprise entre 0 et 1.
     *
     * @return la composante rouge de la couleur.
     */
    
    public double r() {
        return r;
    }

    /**
     * Retourne la composante verte de la couleur, comprise entre 0 et 1.
     *
     * @return la composante verte de la couleur.
     */
    
    public double g() {
        return g;
    }

    /**
     * Retourne la composante bleue de la couleur, comprise entre 0 et 1.
     *
     * @return la composante bleue de la couleur.
     */
    
    public double b() {
        return b;
    }

    
    /**
     * multiplie deux couleurs entre elles, par composantes individuelles
     * 
     * @param c
     *          la couleur avec laquelle on veut multiplier
     *          
     * @return la couleur qui a été multipliée par les 2 autres
     */
    
    public ColorCreation multiply(ColorCreation c) {
        return new ColorCreation(c.r * r, c.g * g, c.b * b);
    }


    /**
     * converti une couleur en couleur de l'API Java
     * 
     * @return une Color du package AWT
     * 
     */
    
    public Color toAwt() {
        return new Color((float) r, (float) g, (float) b);
    }

}
