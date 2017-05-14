package Landing.Model;

public class PIDController {

    private double pX;
    private double pY;
    private double i;
    private double d;
    private double dX;
    private double dT;
    private double maxFx;
    private double maxFy;

    public PIDController(double pX, double pY, double i, double d, double maxFx, double maxFy) {
        this.pX = pX;
        this.pY = pY;
        this.i = i;
        this.d = d;
        this.maxFx = maxFx;
        this.maxFy = maxFy;
    }

    public Tuple<Double, Double> countForce(double dX) {
        double Fx = 0;
        double Fy = 0;
        Fx = pX + i * integral() + d * derivative(dX);
        return new Tuple<>(Fx, Fy);
    }

    private double derivative(double x) {
        return x/dT;
    }

    private double integral() {
        return 0;
    }

    public double getpX() {
        return pX;
    }

    public void setpY(double pY) {
        this.pY = pY;
    }

    public void setpX(double pX) {

        this.pX = pX;
    }

    public double getI() {
        return i;
    }

    public double getD() {
        return d;
    }

    public double getpY() {
        return pY;
    }

    public void setI(double i) {
        this.i = i;
    }

    public void setD(double d) {
        this.d = d;
    }
}
