import java.util.List;
public class Spacecraft {
    private double positionX, positionY, velocityX, velocityY, mass, fuelMass, specificImpulse;
    public static final double G = 6.67430e-11;
    private static final double EARTH_MASS = 5.972e24;
    public static final double EARTH_RADIUS = 6371e3;
    private static final double G0 = 9.81;
    private static final double ATMOSPHERIC_DENSITY = 1.225;
    private static final double DRAG_COEFFICIENT = 0.47; 
    private static final double CROSS_SECTIONAL_AREA = Math.PI * Math.pow(2.5, 2);
    private double positionZ = 0; 
    private double velocityZ = 0;
    public Spacecraft(double mass, double fuelMass, double specificImpulse, double initialX, double initialY, double initialVx, double initialVy,double initialZ, double initialVz) {
        this.mass = mass;
        this.fuelMass = fuelMass;
        this.specificImpulse = specificImpulse;
        this.positionX = initialX;
        this.positionY = initialY;
        this.velocityX = initialVx;
        this.velocityY = initialVy;
    }
    public double getPositionZ() { return positionZ; }
    public void updatePosition(double deltaTime, List<CelestialBody> celestialBodies) {
        double distance = Math.sqrt(positionX * positionX + positionY * positionY);
        double force = (G * EARTH_MASS) / (distance * distance);
        double accX = force * (-positionX / distance);
        double accY = force * (-positionY / distance);
        for (CelestialBody body : celestialBodies) {
            double bodyForce = body.calculateGravitationalForce(this);
            double bodyDistanceX = body.getPositionX() - positionX;
            double bodyDistanceY = body.getPositionY() - positionY;
            double bodyDistance = Math.sqrt(bodyDistanceX * bodyDistanceX + bodyDistanceY * bodyDistanceY);
            accX += bodyForce * (bodyDistanceX / bodyDistance);
            accY += bodyForce * (bodyDistanceY / bodyDistance);
            performSlingshot(body);
        }
        velocityX += accX * deltaTime;
        velocityY += accY * deltaTime;
        positionX += velocityX * deltaTime;
        positionY += velocityY * deltaTime;
    }
    public void applyDrag(double deltaTime) {
        double altitude = getAltitude();
        if (altitude < 0) return;
        double density = ATMOSPHERIC_DENSITY * Math.exp(-altitude / 8500); 
        double dragForce = 0.5 * density * Math.pow(Math.sqrt(velocityX * velocityX + velocityY * velocityY), 2) * DRAG_COEFFICIENT * CROSS_SECTIONAL_AREA;
        double dragAccX = -dragForce * (velocityX / getMass());
        double dragAccY = -dragForce * (velocityY / getMass());
        velocityX += dragAccX * deltaTime;
        velocityY += dragAccY * deltaTime;
    }// dealerHand.get(0) + " [Hidden]");
    public void applyThrust(double thrust, double burnTime, double direction) {
	    if (fuelMass <= 0) return;
	    double exhaustVelocity = specificImpulse * G0;
	    double massFlowRate = thrust / exhaustVelocity;
	    double fuelUsed = massFlowRate * burnTime;
	    if (fuelUsed > fuelMass) {
	        burnTime = fuelMass / massFlowRate;
	        fuelUsed = fuelMass;
	    }
	    if (fuelUsed > fuelMass) {
	        fuelUsed = fuelMass;
	    }
        double deltaV = exhaustVelocity * Math.log((mass + fuelMass) / (mass + fuelMass - fuelUsed));
        velocityX += deltaV * Math.cos(direction);
        velocityY += deltaV * Math.sin(direction);
        fuelMass -= fuelUsed;
    }
    public double calculateHohmannDeltaV(double targetAltitude) {
        double r1 = EARTH_RADIUS + getAltitude();
        double r2 = EARTH_RADIUS + targetAltitude;
        double v1 = Math.sqrt(G * EARTH_MASS / r1);
        double v2 = Math.sqrt(G * EARTH_MASS / r2);
        double transferV = Math.sqrt(G * EARTH_MASS * (2 / r1 - 1 / ((r1 + r2) / 2)));
        return (transferV - v1) + (v2 - transferV);
    }
    public boolean performHohmannTransfer(double targetAltitude) {
        double deltaV = calculateHohmannDeltaV(targetAltitude);
        if (fuelMass <= 0) return false;
        applyThrust(deltaV * getMass(), 10, Math.atan2(positionY, positionX));
        return true;
    }
    public double getAltitude() {
        return Math.sqrt(positionX * positionX + positionY * positionY) - EARTH_RADIUS;
    }
    public double getPositionX() { return positionX; }
    public double getPositionY() { return positionY; }
    public double getVelocityX() { return velocityX; }
    public double getVelocityY() { return velocityY; }
    public double getFuelMass() { return fuelMass; }
    public double getMass() { return mass + fuelMass; }
    public void performSlingshot(CelestialBody body) {
	    double distanceToBody = Math.sqrt(Math.pow(positionX - body.getPositionX(), 2) + Math.pow(positionY - body.getPositionY(), 2));
	    if (distanceToBody < body.getRadius() + 1000) { 
	        double relativeVelocity = Math.sqrt(velocityX * velocityX + velocityY * velocityY);
	        double gravitationalPull = (G * body.getMass()) / (distanceToBody * distanceToBody);
	        	        velocityX += gravitationalPull * (body.getPositionX() - positionX) / relativeVelocity;
	        velocityY += gravitationalPull * (body.getPositionY() - positionY) / relativeVelocity;
	    }
	}
}