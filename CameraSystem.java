import java.awt.Point;
public class CameraSystem {
    private double camX, camY, camZ;
    private double yaw, pitch;
    private double zoom = 1.0;
    public void updateFromMouse(Point dragStart, Point current) {
        yaw += (current.x - dragStart.x) * 0.01;
        pitch += (current.y - dragStart.y) * 0.01;
    }
    public double[] project(double x, double y, double z) {
        double scale = 1000.0 / (z + 2000);
        return new double[] {
            (x - camX) * scale,
            (y - camY) * scale,
            (z - camZ) * scale
        };
    }
    public double getZoom() {
	    return zoom;
	}
}