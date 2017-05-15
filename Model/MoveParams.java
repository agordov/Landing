package Landing.Model;

import java.lang.Math;

public class MoveParams {

    private static final double MAX_X = 1e8;
    private static final double MIN_X = -1e8;

    private static final double MAX_Y = 1e8;
    private static final double MIN_Y = -1e8;

    private static final double MAX_VX = 1e6;
    private static final double MIN_VX = -1e6;

    private static final double MAX_VY = 1e6;
    private static final double MIN_VY = -1e6;

    private static final double MAX_ZONDMASS = 5000;
    private static final double MIN_ZONDMASS = 60;

    private static final double MAX_PLANETMASS = 1.89e27;
    private static final double MIN_PLANETMASS = 1.9e22;

    private static final double MAX_PLANETRADIUS = 66.8e6;
    private static final double MIN_PLANETRADIUS = 1.2e6;

    private static final double MAX_ATMOSPHERERADIUS = 1e5;
    private static final double MIN_ATMOSPHERERADIUS = 2e4;

    private static final double G = 6.6e-11;
    private static final double dT = 0.1;
    private static final double airK = 0.8;

    private double vx;
    private double vy;

    private double zondMass;
    private double planetMass;

    private double planetRadius;
    private double atmosphereRadius;

    private double x;
    private double y;

    private double g;
    private double engineThrustY;
    private double engineThrustX;

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

    public MoveParams() {
        this.vx = generationOfParams(MAX_VX, MIN_VX);
        this.vy = generationOfParams(MAX_VY, MIN_VY);
        this.zondMass = generationOfParams(MAX_ZONDMASS, MIN_ZONDMASS);
        this.planetMass = generationOfParams(MAX_PLANETMASS, MIN_PLANETMASS);
        this.planetRadius = generationOfParams(MAX_PLANETRADIUS, MIN_PLANETRADIUS);
        this.atmosphereRadius = planetRadius + generationOfParams(MAX_ATMOSPHERERADIUS, MIN_ATMOSPHERERADIUS);
        this.x = generationOfParams(MAX_X, MIN_X);
        this.y = generationOfY(x, planetRadius);
        this.g = G * planetMass / Math.pow(planetRadius, 2);
        this.engineThrustY = zondMass * g * 4;
        this.engineThrustX = zondMass * g * 3;
    }

    public MoveParams(double vx, double vy, double zondMass, double planetMass, double planetRadius, double atmosphereRadius, double x, double y, double engineThrustX, double engineThrustY) {
        this.vx = vx;
        this.vy = vy;
        this.zondMass = zondMass;
        this.planetMass = planetMass;
        this.planetRadius = planetRadius;
        this.atmosphereRadius = atmosphereRadius;
        this.x = x;
        this.y = y;
        this.g = g;
        this.engineThrustY = engineThrustY;
        this.engineThrustX = engineThrustX;
    }

    public MoveParams(double vx, double vy, double zondMass, double planetMass, double planetRadius, double atmosphereRadius, double x, double y) {

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

    public double getZondMass() {
        return zondMass;
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

    public double getEngineThrustY() {
        return engineThrustY;
    }

    public double getEngineThrustX() {
        return engineThrustX;
    }

    public double getdT() {
        return dT;
    }

    public double getAirK() {
        return airK;
    }
}