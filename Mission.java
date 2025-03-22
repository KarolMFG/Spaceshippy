public class Mission {
    private double targetAltitude;
    private boolean completed;
    public Mission(double targetAltitude) {
        this.targetAltitude = targetAltitude;
        this.completed = false;
    }
    public boolean checkCompletion(Spacecraft spacecraft) {
        if (spacecraft.getPositionY() >= targetAltitude) {
            completed = true;
        }
        return completed;
    }
    public boolean isCompleted() {
        return completed;
    }
    public double getTargetAltitude() {
        return targetAltitude;
    }
}