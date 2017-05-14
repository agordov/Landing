package Landing.Model;

import java.lang.Math;

public class MoveParams {

    private static final double MAX_X = 1e8;
    private static final double MIN_X = 0;

    private static final double MAX_Y = 1e8;
    private static final double MIN_Y = 0;

    private static final double MAX_VX = 1e6;
    private static final double MIN_VX = 0;

    private static final double MAX_VY = 1e6;
    private static final double MIN_VY = 0;

    private static final double MAX_ZONDMASS = 5000;
    private static final double MIN_ZONDMASS = 60;

    private static final double MAX_PLANETMASS = 1.89e27;
    private static final double MIN_PLANETMASS = 1.9e22;

    private static final double MAX_PLANETRADIUS = 66.8e6;
    private static final double MIN_PLANETRADIUS = 1.2e6;

    private static final double MAX_ATMOSPHERERADIUS = 1e5;
    private static final double MIN_ATMOSPHERERADIUS = 2e4;

    private static final double MAX_ENGINETHRUSTX = 50000;
    private static final double MIN_ENGINETHRUSTX = 10000;

    private static final double G = 6.6e-11;

    private double vx = generationOfParams(MAX_VX, MIN_VX);
    private double vy = generationOfParams(MAX_VY, MIN_VY);

    private double zondMass = generationOfParams(MAX_ZONDMASS, MIN_ZONDMASS);
    private double planetMass = generationOfParams(MAX_PLANETMASS, MIN_PLANETMASS);

    private double planetRadius = generationOfParams(MAX_PLANETRADIUS, MIN_PLANETRADIUS);
    private double atmosphereRadius = planetRadius + generationOfParams(MAX_ATMOSPHERERADIUS, MIN_ATMOSPHERERADIUS);

    private double x = planetRadius + generationOfParams(MAX_X, MIN_X);
    private double y = planetRadius + generationOfParams(MAX_Y, MIN_Y);

    private double g = G * planetMass / Math.pow(planetRadius, 2);
    private double engineThrustY = zondMass * g;
    private double engineThrustX = generationOfParams(MAX_ENGINETHRUSTX, MIN_ENGINETHRUSTX);

    private static double generationOfParams(double maxValue, double minValue) {
        return minValue + Math.random() * (maxValue - minValue);
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
}