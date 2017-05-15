package Landing.Model;

import java.util.List;

public class PIDController {

    private double p;
    private Tuple<Double, Double> integral;
    private double i;
    private double d;
    private double dT;
    private double maxFx;
    private double maxFy;
    private double previousX;
    private double previousY;
    private static final double DEFAULT_P = 1;
    private static final double DEFAULT_I = 1;
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
        this.previousX = 0;
        this.previousY = 0;
    }

    public Tuple<Double, Double> countForces(double dX, double dY) {
        double Fx;
        double Fy;
        integrate(dX, dY);
        Fx = p + i * integral.getX() + d * derivative(dX);
        Fy = p + i * integral.getY() + d * derivative(dY);
        return new Tuple<>(Fx > maxFx ? maxFx : Fx, Fy > maxFy ? maxFy : Fy);
    }

    private double derivative(double dV) {
        return dV/dT;
    }

    public void integrate(double dX, double dY) {
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

    public double getIntegral() {
        return integral.getX();
    }
//    public static void main(String[] args) {
//        double dt = 0.1;
//        PIDController pidController = new PIDController(1, 1, 1, 1, 1, dt);
//        System.out.println(pidController.getClass().getDeclaredFields()[0]);
//        for(int x = 0; x <= 100 ; x++) {
//            pidController.integrate(x, x);
//        }
//        System.out.println(pidController.getIntegral());
//    }
}
