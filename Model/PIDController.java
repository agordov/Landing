package Landing.Model;

public class PIDController {

    private double p;
    private Tuple<Double, Double> integral;
    private double i;
    private double d;
    private double dT;
    private double maxFx;
    private double maxFy;
    private static final double DEFAULT_P = 7;
    private static final double DEFAULT_I = -1;
    private static final double DEFAULT_D = 1;

    public PIDController(double maxFx, double maxFy, double dT) {
        this(DEFAULT_P, DEFAULT_I, DEFAULT_D, maxFx, maxFy, dT);
    }

    public PIDController(double p, double i, double d, double maxFx, double maxFy, double dT) {
        this.p = p;
        this.i = i;
        this.d = d;
        this.integral = new Tuple<>(0d, 0d);
        this.maxFx = maxFx;
        this.maxFy = maxFy;
        this.dT = dT;
    }

    public Tuple<Double, Double> countForces(double dX, double dY) {
        integrate(dX, dY);
        double Fx = p + i * integral.getX() + d * derivative(dX);
        double Fy = p + i * integral.getY() + d * derivative(dY);
        return new Tuple<>(Fx > maxFx ? maxFx : Fx, Fy > maxFy ? maxFy : Fy);
    }

    private double derivative(double dV) {
        return dV/dT;
    }

    private void integrate(double dX, double dY) {
        integral.setX(integral.getX() + dX * dT);
        integral.setY(integral.getY() + dY * dT);
    }

    public double getI() {
        return i;
    }

    public double getD() {
        return d;
    }

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }

    public void setI(double i) {
        this.i = i;
    }

    public void setD(double d) {
        this.d = d;
    }
}
