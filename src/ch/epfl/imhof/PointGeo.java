package ch.epfl.imhof;

/**
 * Un point à la surface de la Terre, en coordonnées sphériques (longitude,
 * latitude)
 * 
 * @author Julien von Felten (234865)
 * @author Simon Haefeli (246663)
 * 
 */

public final class PointGeo {

    private final double longitude;
    private final double latitude;

    /**
     * Construit un point avec la latitu de et la longitude données.
     * 
     * @param longitude
     *            la longitude du point en radians.
     * 
     * @param latitude
     *            la latitude du point en radians
     *
     * @throws IllegalArgumentException
     *             exception si la longitude est invalide, c-à-d hors de
     *             l'intervalle [-π;π]
     * 
     * @throws IllegalArgumentException
     *             exception si la latitude est invalide, c-à-d hors de
     *             l'intervalle [-π/2; π/2]
     * 
     * 
     */

    public PointGeo(double longitude, double latitude) {

        if (!(longitude >= -(Math.PI) && longitude <= Math.PI)) {
            throw new IllegalArgumentException("Longitude incorrecte");
        }

        if (!(latitude >= -(Math.PI / 2) && latitude <= (Math.PI / 2))) {
            throw new IllegalArgumentException("Latitude incorrecte");
        }

        this.longitude = longitude;
        this.latitude = latitude;

    }

    /**
     * getteur pour la longitude
     * 
     * @return longitude, la longitude du point en radians.
     */

    public double longitude() {
        return longitude;
    }

    /**
     * getteur pour la latitude
     * 
     * @return latitude, la latitude du point en radians.
     */

    public double latitude() {
        return latitude;
    }
}
