package Landing.Model;

import java.util.ArrayList;
import java.util.List;

public class Trajectory {

    private List<State> trajectory = new ArrayList<>();

    public Trajectory(PIDController startPid, MoveParams startParams) {
        generateTrajectory(startPid, startParams);
    }


    private void generateTrajectory(PIDController startPid, MoveParams startParams) { // стоит ли мне dt передавать отдельно, или занести в статус?
        double radiusOfAtmosphere = startParams.getAtmosphereRadius();
        double g = startParams.getG();
        double airK = startParams.getAirK();
        double dt = startParams.getdT();
        double radiusOfPlanet = startParams.getPlanetRadius();
        //Tuple<Double, Double> plantPoint = nearestPoint(startParams.getX(), startParams.getY(), radiusOfPlanet);
        double dr = Math.sqrt(Math.pow(startParams.getY() - radiusOfPlanet, 2) + Math.pow(startParams.getX(), 2)); // типа приземляюсь в точку (0, R)
        //double  dr = Math.sqrt(Math.pow(startParams.getY() - plantPoint.getY(), 2) + Math.pow(startParams.getX() - plantPoint.getX(), 2)); // типа приземляюсь в точку (0, R)
        trajectory.add(new State(new Tuple<>(startParams.getX(), startParams.getY()), new Tuple<>(startParams.getVx(), startParams.getVy()), new Tuple<>(0d, 0d), new Tuple<>(0d, 0d),new Tuple<>(0d, 0d), startParams.getProbeMass()));




        while(dr > 0 && dr < 1e12) {
            State state = new State(trajectory.get(trajectory.size() - 1));
            double distance = Math.sqrt(Math.pow(startParams.getY(), 2) + Math.pow(startParams.getX(), 2));
            double fOutX;
            double fOutY;

            if(distance < radiusOfAtmosphere) {
                fOutX = g * state.getM() - airK * state.getVelocity().getX() * state.getVelocity().getX();
                fOutY = g * state.getM() - airK * state.getVelocity().getY() * state.getVelocity().getY();
            }
            else {
                fOutX = g * state.getM() * Math.signum(state.getCoordinates().getX());
                fOutY = g * state.getM() * Math.signum(state.getCoordinates().getY());
            }

            state.setForceOut(new Tuple<>(fOutX, fOutY));
            double aX = (state.getForceIn().getX() + state.getForceOut().getX()) / state.getM();
            double aY = (state.getForceIn().getY() + state.getForceOut().getY()) / state.getM();;
            state.setAcceleration(new Tuple<>(aX, aY));
            double vX = state.getVelocity().getX() + state.getAcceleration().getX() * dt;
            double vY = state.getVelocity().getY() + state.getAcceleration().getY() * dt;;
            state.setVelocity(new Tuple<>(vX, vY));
            double x = state.getCoordinates().getX() + state.getVelocity().getX() * dt / 2;
            double y = state.getCoordinates().getY() + state.getVelocity().getY() * dt / 2;
            state.setForceIn(startPid.countForces(x, y - startParams.getPlanetRadius())); /*startParams.getPlanetRadius()))*/;
            state.setCoordinates(new Tuple<>(x, y));
            state.setT(state.getT() + dt);

            dr = Math.sqrt(Math.pow(state.getCoordinates().getY() - startParams.getPlanetRadius(), 2) + Math.pow(state.getCoordinates().getX(), 2)); // типа приземляюсь в точку (0, R)
           // dr = Math.sqrt(Math.pow(state.getCoordinates().getY() - plantPoint.getY(), 2) + Math.pow(state.getCoordinates().getX() - plantPoint.getX(), 2)); // типа приземляюсь в точку (0, R)
            trajectory.add(state);

        }
    }

   /* private Tuple<Double, Double> nearestPoint(double x, double y, double radiusOfPlanet) {
        double cosA = x / Math.sqrt(x * x + y * y);
        double sinA = y / Math.sqrt(x * x + y * y);
        return new Tuple<>(radiusOfPlanet * cosA, radiusOfPlanet * sinA);
    }*/

    public List<State> getTrajectory() {
        return trajectory;
    }
}
