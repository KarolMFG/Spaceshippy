public class CelestialBody {
    private double mass;
    private double positionX;
    private double positionY;
    private double radius;
    public CelestialBody(double mass, double positionX, double positionY, double radius) {
        this.mass = mass;
        this.positionX = positionX;
        this.positionY = positionY;
        this.radius = radius;
    }
    public double getMass() {
        return mass;
    }
    public double getPositionX() {
        return positionX;
    }
    public double getPositionY() {
        return positionY;
    }
    public double getRadius() {
        return radius;
    }
    public double calculateGravitationalForce(Spacecraft spacecraft) {
        double distanceX = positionX - spacecraft.getPositionX();
        double distanceY = positionY - spacecraft.getPositionY();
        double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
        return (Spacecraft.G * mass * spacecraft.getMass()) / (distance * distance);
    }
}