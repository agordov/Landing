package Landing.Model;

public class PIDController {

    private double p;
    private Tuple<Double, Double> integral;
    private double i;
    private double d;
    private double dT;
    private double maxFx;
    private double maxFy;

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
        double Fx;
        double Fy;
        Fx = p + i * integral.getX() + d * derivative(dX);
        Fy = p + i * integral.getY() + d * derivative(dY);
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

    public void setI(double i) {
        this.i = i;
    }

    public void setD(double d) {
        this.d = d;
    }
}
