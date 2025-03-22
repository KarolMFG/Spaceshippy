public class OrbitalMechanics {
    private static final double G = 6.67430e-11;
    private static final double EARTH_MASS = 5.972e24;
    private static final double EARTH_RADIUS = 6371000; 
    public static double calculateOrbitalVelocity(double altitude) {
        double radius = EARTH_RADIUS + (altitude * 1000); 
        return Math.sqrt(G * EARTH_MASS / radius) / 1000; 
    }
}