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
        integrate(dX, dY);
        Fx = p + i * integral.getLeft() + d * derivative(dX);
        Fy = p + i * integral.getRight() + d * derivative(dY);
        return new Tuple<>(Fx > maxFx ? maxFx : Fx, Fy > maxFy ? maxFy : Fy);
    }

    private double derivative(double dV) {
        return dV/dT;
    }

    private void integrate(double dX, double dY) {
        integral.setLeft(integral.getLeft() + dX * dT);
        integral.setRight(integral.getRight() + dY * dT);
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
