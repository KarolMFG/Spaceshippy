import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JFrame;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose a path: 1 for Straight Path, 2 for Orbital Path");
        int choice = scanner.nextInt();
        double initialVx = (choice == 1) ? 7.8 : Math.sqrt(9.81 * (Spacecraft.EARTH_RADIUS + 200000));
        Spacecraft spacecraft = new Spacecraft(
            1000,        // mass
            500,         // fuelMass
            300,         // specificImpulse
            0,           // initialX
            6771000,     // initialY (Earth's surface)
            initialVx,   // initialVx
            0,           // initialVy
            0,           // initialZ
            0            // initialVz
        );
        MissionControl missionControl = new MissionControl(spacecraft, 1000000);
        JFrame frame = new JFrame("Orbital Visualization");
        SpaceVisualization panel = new SpaceVisualization(spacecraft);
        frame.add(panel);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        List<CelestialBody> celestialBodies = new ArrayList<>();
        CelestialBody moon = new CelestialBody(7.347673e22, 384400e3, 0, 1737e3);
        celestialBodies.add(moon);
        for (int i = 0; i < 10000; i++) {
            spacecraft.applyDrag(1);
            spacecraft.updatePosition(1, celestialBodies);
            missionControl.update();
            if (missionControl.isTransferComplete()) {
                System.out.println("Hohmann transfer successful!");
                break;
            }
            panel.repaint();
            System.out.println("Time: " + i + "s | Altitude: " + 
                spacecraft.getAltitude() + "m | Fuel: " + spacecraft.getFuelMass());
            if (spacecraft.getFuelMass() <= 0) {
                System.out.println("Out of fuel! Mission failed.");
                break;
            }
            try { Thread.sleep(10); } 
            catch (InterruptedException e) { e.printStackTrace(); }
        }
        scanner.close();
    }
}