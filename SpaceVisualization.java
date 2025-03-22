import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JPanel;
public class SpaceVisualization extends JPanel {
    private CameraSystem camera = new CameraSystem();
    private Point mouseDragStart;
    private Spacecraft spacecraft;
    public SpaceVisualization(Spacecraft spacecraft) {
        this.spacecraft = spacecraft;
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseDragStart = e.getPoint();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                camera.updateFromMouse(mouseDragStart, e.getPoint());
                repaint();
            }
        });
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());
        double[] earthPos = camera.project(0, 0, 0);
        int earthSize = (int)(20 / camera.getZoom());
        g.setColor(Color.BLUE);
        g.fillOval(
            (int)(getWidth()/2 + earthPos[0] - earthSize/2),
            (int)(getHeight()/2 + earthPos[1] - earthSize/2),
            earthSize, earthSize
        );
        double[] craftPos = camera.project(
            spacecraft.getPositionX(),
            spacecraft.getPositionY(),
            spacecraft.getPositionZ()
        );
        g.setColor(Color.WHITE);
        int craftSize = (int)(5 / (craftPos[2]/1000 + 1));
        g.fillOval(
            (int)(getWidth()/2 + craftPos[0] - craftSize/2),
            (int)(getHeight()/2 + craftPos[1] - craftSize/2),
            craftSize, craftSize
        );
    }
}