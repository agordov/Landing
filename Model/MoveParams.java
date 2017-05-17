package Landing.Model;

import java.lang.Math;

public class MoveParams {

    public static final double MAX_X = 1e10;
    public static final double MIN_X = -1e10;

    public static final double MAX_Y = 1e8;
    public static final double MIN_Y = -1e8;

    public static final double MAX_VX = 1e6;
    public static final double MIN_VX = -1e6;

    public static final double MAX_VY = 1e6;
    public static final double MIN_VY = -1e6;

    public static final double MAX_ZONDMASS = 5000;
    public static final double MIN_ZONDMASS = 60;

    public static final double MAX_PLANETMASS = 1.89e27;
    public static final double MIN_PLANETMASS = 1.9e22;

    public static final double MAX_PLANETRADIUS = 66.8e6;
    public static final double MIN_PLANETRADIUS = 1.2e6;

    public static final double MAX_ATMOSPHERERADIUS = 1e5;
    public static final double MIN_ATMOSPHERERADIUS = 2e4;

    private static final double G = 6.6e-11;
    private static final double dT = 100;
    private static final double airK = 0.8;

    private double vx; //checked
    private double vy; //checked

    private double probeMass; //checked
    private double planetMass; //checked

    private double planetRadius; //checked
    private double atmosphereRadius; //checked

    private double x; //checked
    private double y; //checked

    private double g;
    private double maxEngineThrustY;
    private double maxEngineThrustX;

    private static double generationOfParams(double maxValue, double minValue) {
        return minValue + Math.random() * (maxValue - minValue);
    }

    private static double generationOfY(double x, double planetRadius) {
        double y = generationOfParams(MAX_Y,MIN_Y);

        while (Math.sqrt(Math.pow(x,2) + Math.pow(y,2)) < planetRadius) {
            y = generationOfParams(MAX_Y,MIN_Y);
        }

        return y;
    }

    public double getG() {
        return g;
    }

    public MoveParams() {
        this.vx = generationOfParams(MAX_VX, MIN_VX);
        this.vy = generationOfParams(MAX_VY, MIN_VY);
        this.probeMass = generationOfParams(MAX_ZONDMASS, MIN_ZONDMASS);
        this.planetMass = generationOfParams(MAX_PLANETMASS, MIN_PLANETMASS);
        this.planetRadius = generationOfParams(MAX_PLANETRADIUS, MIN_PLANETRADIUS);
        this.atmosphereRadius = planetRadius + generationOfParams(MAX_ATMOSPHERERADIUS, MIN_ATMOSPHERERADIUS);
        this.x = generationOfParams(MAX_X, MIN_X);
        this.y = generationOfY(x, planetRadius);
        this.g = G * planetMass / Math.pow(planetRadius, 2);
        this.maxEngineThrustY = probeMass * g * 4;
        this.maxEngineThrustX = probeMass * g * 3;
    }

    public MoveParams(double x, double y, double vx, double vy, double probeMass, double planetRadius, double atmosphereRadius, double planetMass, double maxEngineThrustX, double maxEngineThrustY) {
        this.vx = vx;
        this.vy = vy;
        this.probeMass = probeMass;
        this.planetMass = planetMass;
        this.planetRadius = planetRadius;
        this.atmosphereRadius = atmosphereRadius;
        this.x = x;
        this.y = y;
        this.g = G * planetMass / Math.pow(planetRadius, 2);
        this.maxEngineThrustY = maxEngineThrustY;
        this.maxEngineThrustX = maxEngineThrustX;
    }

    public boolean checkPlanetRadius() {
        return planetRadius >= MIN_PLANETRADIUS && planetRadius <= MAX_PLANETRADIUS;
    }

    public boolean checkX() {
        return x >= MIN_X && x <= MAX_X;
    }

    public boolean checkY() {
        return y >= MIN_Y && y <= MAX_Y;
    }

    public boolean checkVx() {
        return vx >= MIN_VX && vx <= MAX_VX;
    }

    public boolean checkVy() {
        return vy >= MIN_VY && vy <= MAX_VY;
    }

    public boolean checkProbeMass() {
        return probeMass >= MIN_ZONDMASS && probeMass <= MAX_ZONDMASS;
    }

    public boolean checkPlanetMass() {
        return planetMass >= MIN_PLANETMASS && planetMass <= MAX_PLANETMASS;
    }

    public boolean checkAtmosphereRadius() {
        return atmosphereRadius >= (planetRadius + MIN_ATMOSPHERERADIUS) && atmosphereRadius <= (planetRadius + MAX_ATMOSPHERERADIUS);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public double getProbeMass() {
        return probeMass;
    }

    public double getPlanetMass() {
        return planetMass;
    }

    public double getPlanetRadius() {
        return planetRadius;
    }

    public double getAtmosphereRadius() {
        return atmosphereRadius;
    }

    public double getMaxEngineThrustY() {
        return maxEngineThrustY;
    }

    public double getMaxEngineThrustX() {
        return maxEngineThrustX;
    }

    public double getdT() {
        return dT;
    }

    public double getAirK() {
        return airK;
    }
}