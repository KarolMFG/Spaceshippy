public class MissionControl {
    private Spacecraft spacecraft;
    private double targetAltitude;
    private boolean transferStarted = false;
    public MissionControl(Spacecraft spacecraft, double targetAltitude) {
        this.spacecraft = spacecraft;
        this.targetAltitude = targetAltitude;
    }
    public void update() {
        if (!transferStarted && spacecraft.getAltitude() > 200000) {
            transferStarted = spacecraft.performHohmannTransfer(targetAltitude);
            if (transferStarted) System.out.println("Hohmann transfer initiated!");
        }
    }
    public boolean isTransferComplete() {
        return transferStarted && spacecraft.getAltitude() >= targetAltitude - 1000;
    }
}