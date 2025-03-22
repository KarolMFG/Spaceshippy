public class PIDController {
    private double kp, ki, kd;
    private double previousError;
    private double integral;
    public PIDController(double kp, double ki, double kd) {
        this.kp = kp;
        this.ki = ki;
        this.kd = kd;
    }
    public double calculate(double target, double current, double deltaTime) {
        double error = target - current;
        integral += error * deltaTime;
        double derivative = (error - previousError) / deltaTime;
        previousError = error;
        return kp * error + ki * integral + kd * derivative;
    }
}