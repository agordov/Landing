package Landing.Model;

public class PIDController {

    private double p;
    private double i;
    private double d;
    private Tuple<Double, Double> integral;
    private double maxFx;
    private double maxFy;
    private double prevDX;
    private double prevDY;
    private static final double DEFAULT_P = 10;
    private static final double DEFAULT_I = 1;
    private static final double DEFAULT_D = 50;

    public PIDController(double maxFx, double maxFy) {
        this(DEFAULT_P, DEFAULT_I, DEFAULT_D, maxFx, maxFy);
    }

    public PIDController(double p, double i, double d, double maxFx, double maxFy) {
        this.p = p;
        this.i = i;
        this.d = d;
        this.maxFx = maxFx;
        this.maxFy = maxFy;
        this.integral = new Tuple<>(0d, 0d);
        this.prevDX = 0;
        this.prevDY = 0;
    }

    public Tuple<Double, Double> countForces(double dX, double dY) {
        setIntegral(dX, dY);
        double Fx = p * dX + i * integral.getX() + d * (dX - prevDX);
        prevDX = dX;
        double Fy = p * dY + i * integral.getY() + d * (dY - prevDY);
        prevDY = dY;
        return new Tuple<>(Math.abs(Fx) > maxFx ? Math.signum(Fx) * maxFx : Fx, Math.abs(Fy) > maxFy ? Math.signum(Fy) * maxFy : Fy);
    }

    private void setIntegral(double dX, double dY) {
        integral.setX(integral.getX() + dX);
        integral.setY(integral.getY() + dY);
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
